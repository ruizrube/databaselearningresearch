package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import org.apache.commons.text.similarity.JaccardDistance;

public class JaccardDistanceExamples {
	public static void main(String[] args) {

		String[][] inputStrings = new String[][] {
				// Exact same
				{ "Binary", "Binary" },
				// Same characters but different counts of characters
				{ "singing", "sign" },
				// Half characters similar
				{ "Binary", "ray" },
				// Different case.
				{ "Binary", "bINARY" },
				// No similarity
				{ "cat", "dog" } };

		for (String[] input : inputStrings) {
			// How much of distinct charaters are similar between two string.
			double jaccardDistance = new JaccardDistance().apply(input[0], input[1]);

			System.out.println("JaccardDistance of '" + input[0] + "' & '" + input[1]
					+ "' | Distinct characters in strings are " + (jaccardDistance * 100) + "% dis-similar or "
					+ ((1 - jaccardDistance) * 100) + "% similar.");
		}
	}
}