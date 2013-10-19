package com.github.mkalin.jwsur2.ch2.adages;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class AdagesTest extends JerseyTest {
	private static final List<String> APHORISMS = Arrays
			.asList(Adages.APHORISMS);
	private static AdagesTest self = null;

	@Override
	public synchronized void setUp() throws Exception {
		if (self == null) {
			self = this;
			super.setUp();
		}
	}

	@Override
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void stop() throws Exception {
		self.stopContainer();
		self = null;
	}

	private void stopContainer() throws Exception {
		super.tearDown();
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(Adages.class);
	}

	@Test
	public void testXMLAdage() {
		Adage adage = target("/").request().accept(MediaType.APPLICATION_XML)
				.get(Adage.class);
		Assert.assertTrue("Unexpected aphorism",
				APHORISMS.contains(adage.getWords()));
	}

	@Test
	public void testJSONAdage() {
		Adage adage = target("/json").request()
				.accept(MediaType.APPLICATION_JSON).get(Adage.class);
		Assert.assertTrue("Unexpected aphorism",
				APHORISMS.contains(adage.getWords()));
	}

	@Test
	public void testPlainAdage() {
		String adage = target("/plain").request().accept(MediaType.TEXT_PLAIN)
				.get(String.class);
		adage = adage.split("--")[0].trim();
		Assert.assertTrue("Unexpected aphorism", APHORISMS.contains(adage));
	}
}
