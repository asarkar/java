package name.abhijitsarkar.json.jackson;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JSONParserTest {
	@Test
	public void testEmployees() throws JsonParseException, JsonMappingException, IOException {
		Map<String, Employee> employeeMap = JSONParser.employees("/employees.json");

		Assert.assertNotNull(employeeMap);
		Assert.assertEquals(2, employeeMap.size());

		Employee employee123 = employeeMap.get("123");
		Assert.assertNotNull(employee123);
		Assert.assertEquals("Abhijit", employee123.getName());
		Assert.assertEquals("Software", employee123.getDepartment());

		Employee employee456 = employeeMap.get("456");
		Assert.assertNotNull(employee456);
		Assert.assertEquals("Sarkar", employee456.getName());
		Assert.assertEquals("IT", employee456.getDepartment());
	}
}
