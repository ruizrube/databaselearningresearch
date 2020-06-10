package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import org.apache.commons.text.similarity.LevenshteinDistance;
 
public class LevenshteinDistanceExamples {
 
	public static void main(String[] args) {
 
		String[][] inputStrings = new String[][] {
 
				// Same characters different counts
				{ "singing", "sign" },
				// Similar but different case.
				{ "Binary", "bINARY" },
				// One word same
				{ "Binary", "Its All Binary" },
				// Different characters
				{ "cat", "dog" }, { "abcxyz", "abc" },
				// Different variations of contains abc
				{ "xyzabc", "abc" }, { "xyabcz", "abc" }, { "xabcyz", "abc" }, { "axbycz", "abc" },
				// Similar words
				{ "Binary", "Biryani" },
				// Exact same
				{ "Binary", "Binary" },
				// One character variations
				{ "Binary", "ABinary" }, { "Binary", "BinaryA" }, { "Binary", "BiAnary" },
				// Spelling mistake
				{ "Binary", "Binray" },
				// MIsplaced A
				{ "ABinary", "BinaryA" } };
 
		for (String[] input : inputStrings) {
 
			double levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(input[0], input[1]);
 
			System.out.println("LevenshteinDistance of '" + input[0] + "' & '" + input[1] + "' | Need to change "
					+ levenshteinDistance + " characters to match both strings.");
		}
	}
}