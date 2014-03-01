package name.abhijitsarkar.programminginterviews.arraysnstrings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

public class PracticeQuestionsCh6 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh6.class);
    
	/* Q6.22: TODO  */
	public List<String> keypadPermutation(int numSeq) {
	    char[] digits  = String.valueOf(numSeq).toCharArray();

		List<String> perms = new ArrayList<>();

        for (int idx = 0; idx <= digits.length; idx++) {
		    permute(digits[idx], perms);
		}

		return perms;
	}

	private permute(int digit, List<String> perms) {
	    String charSeq = charSeq(digit);

		for (String s : perms) {
		    
		}
	}

	private String charSeq(char digit) {
	    String charSeq = "";
		final int idxOfZeroInASCII = 48;
        int intVal = digit - idxOfZeroInASCII;

		switch (intVal) {
		    case 2:
		        charSeq = "ABC";
			    break;
		    case 3:
		        charSeq = "DEF";
			    break;
		    case 4:
		        charSeq = "GHI";
			    break;
		    case 5:
		        charSeq = "JKL";
			    break;
		    case 6:
		        charSeq = "MNO";
			    break;
		    case 7:
		        charSeq = "PQRS";
			    break;
		    case 8:
		        charSeq = "TUV";
			    break;
		    case 9:
		        charSeq = "WXWZ";
			    break;
			default:
				break;
		}

		return charSeq;
	}
}
