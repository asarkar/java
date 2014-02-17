/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.hadoop.distributedcache;

import static name.abhijitsarkar.hadoop.io.IOUtils.compressFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.createMapFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.findInMapFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.removeExtension;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class works on the cite.txt and apat.txt. It implements a contrived demonstration of the use of DistributedCache
 * and MapFile. It accepts apat.txt local path and any citation number from cite.txt as input. It then creates an
 * archive from apat.txt and puts that in the distributed cache as apat.gz. It also sets the citation number in the
 * configuration to be later retrieved and used by the Mapper. The Mapper processes cite.txt and suppresses all records
 * from it except for those that correspond to the input citation number. The Reducer retrieves the cached archive from
 * distributed cache, uncompresses it and creates a MapFile out of it. The Reducer then looks up the record in the
 * MapFile corresponding to the citation number emitted by the Mapper.
 * 
 * @author Abhijit Sarkar
 */
public class CitationRetriever extends Configured implements Tool {
	public static final Logger LOGGER = LoggerFactory.getLogger(CitationRetriever.class);

	public static final String COMMA = ",";

	public static class CitationMapper extends Mapper<LongWritable, Text, Text, Text> {

		@SuppressWarnings("unchecked")
		/*
		 * Key is the byte offset of the line which is useless. Value is the line content
		 */
		public void map(LongWritable key, Text value, @SuppressWarnings("rawtypes") Mapper.Context context)
				throws IOException, InterruptedException {

			final String[] lineSplit = value.toString().split(COMMA);
			String keyStr = null;
			String valueStr = null;

			if (lineSplit.length == 2) {
				keyStr = lineSplit[0];
				valueStr = lineSplit[1];
			}

			LOGGER.debug("Key: {}, Value: {}.", keyStr, valueStr);

			/* Skip the header row */
			try {
				Long.valueOf(keyStr);

				Configuration conf = context.getConfiguration();

				if (conf.get("citationNum") != null && conf.get("citationNum").equals(keyStr)) {
					LOGGER.debug("Found the citation {}.", keyStr);
					
					context.write(new Text(keyStr), new Text(valueStr));
				}
			} catch (NumberFormatException nfe) {
				LOGGER.warn("NumberFormatException: {}.", nfe.getMessage());
			}
		}
	}

	public static class CitationReducer extends Reducer<Text, Text, Text, Text> {
		public static final byte[] COMMA_BYTES = new Text(COMMA).getBytes();
		private URI cacheFileURI = null;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);

			final Configuration conf = context.getConfiguration();

			String[] symlinks = conf.get("symlinks") != null ? conf.get("symlinks").split(",") : null;

			final Path[] cacheArchives = DistributedCache.getLocalCacheArchives(conf);

			if (cacheArchives == null || cacheArchives.length == 0 || cacheArchives[0] == null) {
				throw new RuntimeException("Didn't find any cache files");
			} else {
				try {
					LOGGER.info("Found cache archive: {}.", cacheArchives[0].toUri());

					if (symlinks == null) { // Symlinks not configured
						symlinks = new String[] { removeExtension(cacheArchives[0].getName()) };
					}
					/*
					 * CAUTION: Hadoop creates a directory by the same name as the symlink (or archive, in case a
					 * symlink wasn't configured) and puts the archive there. Trying to process the directory as the
					 * archive will lend to enormous debugging time, frustration and, of course, reduce failure.
					 */
					final String workDir = System.getenv().get("HADOOP_WORK_DIR");
					if (workDir == null) {
						throw new IOException("Environment variable HADOOP_WORK_DIR is not set");
					}

					LOGGER.info("workDir: {}.", workDir);
					cacheFileURI = new Path(workDir, symlinks[0]).toUri();

					cacheFileURI = createMapFile(cacheFileURI, conf);
				} catch (Exception e) {
					LOGGER.error("Couldn't create MapFile from cache archive", e);
					throw new IllegalStateException("Couldn't create MapFile from cache archive", e);
				}
			}
		}

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

			/*
			 * All the heavy lifting is already been done by setup(). Just look up the key in the MapFile
			 */
			final Text value = (Text) findInMapFile(key, cacheFileURI, context.getConfiguration());

			if (value == null) {
				return;
			}

			context.write(key, value);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		/*
		 * Create an archive using the supplied codec name. The codec name is currently unused and the archive returned
		 * is always Gzip. See the method for details
		 */
		final Path compressedFilePath = new Path(compressFile(new Path(args[2]).toUri(), "gzip", conf));

		final FileSystem fs = FileSystem.get(conf);
		final String remoteLocation = File.separator + compressedFilePath.getName();
		LOGGER.debug("Copying from: {} to {}.", compressedFilePath.toUri(), remoteLocation);
		/*
		 * Copy it to the HDFS from where it is distributed to all task nodes. GenericOptionsParser, if used, does it
		 * behind the scenes
		 */
		fs.copyFromLocalFile(compressedFilePath, new Path(remoteLocation));

		/*
		 * There are easier ways to put files in the Distributed Cache, like using the GenericOptionsParser command line
		 * arguments '-archives' but that will be too easy, won't it?
		 */

		/* Create a symlink */

		final String[] symlinks = new String[] { removeExtension(compressedFilePath.getName()) };
		conf.set("symlinks", StringUtils.arrayToString(symlinks));

		DistributedCache.addCacheArchive(new URI(remoteLocation + "#" + symlinks[0]), conf);
		DistributedCache.addCacheArchive(new URI(remoteLocation), conf);
		DistributedCache.createSymlink(conf);

		/*
		 * Set the citation number in the configuration to be later used by the Mapper
		 */
		conf.set("citationNum", args[3]);

		Job job = new Job(conf, "distributed-cache");

		job.setMapperClass(CitationMapper.class);
		job.setReducerClass(CitationReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setJarByClass(getClass());

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			throw new IllegalArgumentException("Usage: CitationRetriever input_file output_dir cache_file citation_num");
		}
		
		// TODO: Get rid of the GenericOptionsParser and just pass the args to the ToolRunner
		GenericOptionsParser parser = new GenericOptionsParser(new Configuration(), args);

		ToolRunner.run(new CitationRetriever(), parser.getRemainingArgs());
	}
}
