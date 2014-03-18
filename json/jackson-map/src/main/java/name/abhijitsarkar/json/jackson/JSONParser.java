package name.abhijitsarkar.json.jackson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {
	public static Map<String, Employee> employees(String file) throws JsonParseException, JsonMappingException,
			IOException {
		final TypeReference<Map<String, Employee>> employeeMapType = new TypeReference<Map<String, Employee>>() {
		};

		return new ObjectMapper().readValue(JSONParser.class.getResource(file), employeeMapType);
	}
}
