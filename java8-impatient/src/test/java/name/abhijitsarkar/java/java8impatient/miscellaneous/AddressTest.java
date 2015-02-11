package name.abhijitsarkar.java.java8impatient.miscellaneous;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AddressTest {
    @Test
    public void testAddressMatchWith5DigitZipCode() {
	Address addr = new Address("seattle,WA,98101");

	assertEquals("seattle", addr.getCity());
	assertEquals("WA", addr.getState());
	assertEquals(98101, addr.getZipCode());
    }
    
    @Test
    public void testAddressMatchWith9DigitZipCode() {
	Address addr = new Address("seattle,WA,981010000");

	assertEquals("seattle", addr.getCity());
	assertEquals("WA", addr.getState());
	assertEquals(981010000, addr.getZipCode());
    }
    
    @Test
    public void testAddressMatchWithSpaceInCityName() {
	Address addr = new Address("Des Moines,IA,503201863");

	assertEquals("Des Moines", addr.getCity());
	assertEquals("IA", addr.getState());
	assertEquals(503201863, addr.getZipCode());
    }
}
