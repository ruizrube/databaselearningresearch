package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import java.util.Locale;
import org.apache.commons.text.similarity.FuzzyScore;

public class FuzzyScoreExamples2 {
	public static void main(String[] args) {

		String[][] inputStrings = new String[][] {
				// Matches abc at start of term
				{ "abcxyz", "abc" },
				// ABC in different case than term
				{ "abcxyz", "ABC" },
				// Matches abc at end of term
				{ "xyzabc", "abc" },
				// Matches abc in middle
				{ "xabcyz", "abc" },
				// Matches abc but not continuous.
				{ "abxycz", "abc" }, { "axbycz", "abc" },
				// Reverse order of abc
				{ "cbaxyz", "abc" },
				// Matches abc but different order.
				{ "cabxyz", "abc" } };

		for (String[] input : inputStrings) {
			String term = input[0];
			String query = input[1];
			// Fuzzy score of query against term
			double fuzzyScore = new FuzzyScore(Locale.getDefault()).fuzzyScore(term, query);

			System.out.println(
					"FuzzyScore of query '" + query + "' against term '" + term + "' is " + fuzzyScore + " points");
		}
	}
}