package name.abhijitsarkar.java.logminer.repo;

import static java.nio.file.Paths.get;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Configuration
public class ReverseProxyTestPropertySourceConfig {
    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public PropertySourcesPlaceholderConfigurer properties()
	    throws IOException, URISyntaxException {
	PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();

	Resource[] resources = new ClassPathResource[] { new ClassPathResource(
		"application-test.properties") };
	pspc.setLocations(resources);
	MutablePropertySources sources = new MutablePropertySources();
	sources.addFirst(reverseProxyPropertySource());
	pspc.setPropertySources(sources);
	pspc.setIgnoreUnresolvablePlaceholders(true);

	return pspc;
    }

    public static MapPropertySource reverseProxyPropertySource()
	    throws IOException, URISyntaxException {
	Map<String, Object> properties = new HashMap<>();
	properties.put(
		"path",
		get(
			ReverseProxyTestPropertySourceConfig.class.getResource(
				"/rp").toURI()).toString());
	properties.put("type", "rp");

	return new MapPropertySource("my-props", properties);
    }
}
