package name.abhijitsarkar.java.logminer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReverseProxyLogRecordTest {
    @Test
    public void testReadLineForAAC1() {
	String line = "https-aac-allied-1 aac.alliedinsurance.com 0 155.188.183.17 - - [27/Feb/2015:07:18:04 -0600] "
		+ "\"GET /download/IT%20Tech%20Writing%20Only/PL%20NB%20Billing%20Changes%20Feb%202015%20TipSheet.pdf "
		+ "HTTP/1.1\" 200 207773 \"https://www.nwagri.com/center/index.cfm?event=main:buildMain\" \"Mozilla/4.0 "
		+ "(compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; "
		+ ".NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET CLR 1.1.4322; .NET4.0C; "
		+ ".NET4.0E; InfoPath.2)\"";
	ReverseProxyLogRecord rec = new ReverseProxyLogRecord(line);

	assertFalse(rec.isSkipped());
	assertEquals("https-aac-allied-1", rec.getJvm().toString());
	assertEquals(27, rec.getDay());
	assertEquals(2, rec.getMonth());
	assertEquals(2015, rec.getYear());
	assertEquals(
		"/download/IT Tech Writing Only/PL NB Billing Changes Feb 2015 TipSheet.pdf",
		rec.getPath().toString());
	assertEquals("download", rec.getRoot().toString());
	assertEquals("PL NB Billing Changes Feb 2015 TipSheet.pdf", rec
		.getFilename().toString());
	assertEquals(200, rec.getStatus());
	assertEquals(207773, rec.getSize());
    }

    @Test
    public void testReadLineForAAC2() {
	String line = "https-aac-allied-2 aac.alliedinsurance.com 0 155.188.183.17 - - [27/Feb/2015:07:18:04 -0600] "
		+ "\"GET /download/IT%20Tech%20Writing%20Only/PL%20NB%20Billing%20Changes%20Feb%202015%20TipSheet.pdf "
		+ "HTTP/1.1\" 200 207773 \"https://www.nwagri.com/center/index.cfm?event=main:buildMain\" \"Mozilla/4.0 "
		+ "(compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; "
		+ ".NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET CLR 1.1.4322; .NET4.0C; "
		+ ".NET4.0E; InfoPath.2)\"";
	ReverseProxyLogRecord rec = new ReverseProxyLogRecord(line);

	assertFalse(rec.isSkipped());
	assertEquals("https-aac-allied-2", rec.getJvm().toString());
	assertEquals(27, rec.getDay());
	assertEquals(2, rec.getMonth());
	assertEquals(2015, rec.getYear());
	assertEquals(
		"/download/IT Tech Writing Only/PL NB Billing Changes Feb 2015 TipSheet.pdf",
		rec.getPath().toString());
	assertEquals("download", rec.getRoot().toString());
	assertEquals("PL NB Billing Changes Feb 2015 TipSheet.pdf", rec
		.getFilename().toString());
	assertEquals(200, rec.getStatus());
	assertEquals(207773, rec.getSize());
    }

    @Test
    public void testReadLineForOtherApp() {
	String line = "https-other aac.alliedinsurance.com 0 155.188.183.17 - - [27/Feb/2015:07:18:04 -0600] "
		+ "\"GET /download/IT%20Tech%20Writing%20Only/PL%20NB%20Billing%20Changes%20Feb%202015%20TipSheet.pdf "
		+ "HTTP/1.1\" 200 207773 \"https://www.nwagri.com/center/index.cfm?event=main:buildMain\" \"Mozilla/4.0 "
		+ "(compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; "
		+ ".NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET CLR 1.1.4322; .NET4.0C; "
		+ ".NET4.0E; InfoPath.2)\"";
	ReverseProxyLogRecord rec = new ReverseProxyLogRecord(line);

	assertTrue(rec.isSkipped());
    }
}
