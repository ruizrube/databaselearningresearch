package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
 
public class LongestCommonSubsequenceExamples {
 
	public static void main(String[] args) {
 
		String[][] inputStrings = new String[][] {
 
				// Same characters but different counts
				{ "singing", "sign" },
				// Similar but different case
				{ "Binary", "bINARY" },
				// One word similar
				{ "Binary", "Its All Binary" },
				// Not same
				{ "cat", "dog" },
				// Variations of contains abc
				{ "abcxyz", "abc" }, { "xyzabc", "abc" }, { "xyabcz", "abc" }, { "xabcyz", "abc" }, { "axbycz", "abc" },
				// Minor variations
				{ "Binary", "Biryani" }, { "Biryani", "Binary" }, { "Binary", "Binary" }, { "Binary", "ABinary" },
				{ "Binary", "BinaryA" }, { "Binary", "BiAnary" }, { "Binary", "Binray" }, { "ABinary", "BinaryA" } };
 
		for (String[] input : inputStrings) {
 
			CharSequence longestCommonSubsequence = new LongestCommonSubsequence().longestCommonSubsequence(input[0],
					input[1]);
 
			System.out.println("LongestCommonSubsequence of '" + input[0] + "' & '" + input[1] + "' is '"
					+ longestCommonSubsequence + "'");
		}
	}
}