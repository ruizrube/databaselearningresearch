package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import org.apache.commons.text.similarity.HammingDistance;
 
public class HammingDistanceExamples {
	public static void main(String[] args) {
 
		String[][] inputStrings = new String[][] {
				// Exact same strings
				{ "Binary", "Binary" },
				// Same characters but 1 character misplaced
				{ "ABinary", "BinaryA" },
				// Only 2 characters misplaced.
				{ "Binary", "Binray" },
				// Different case.
				{ "Binary", "bINARY" },
				// Same characters different order
				{ "sing", "sign" },
				// Different length strings
				{ "singing", "sign" },
				// Completely different characters
				{ "Binary", "uvwstu" } };
 
		for (String[] input : inputStrings) {
			try {
				// How many characters need to be changed to match both strings.
				double hammingDistance = new HammingDistance().apply(input[0], input[1]);
				System.out.println("HammingDistance of '" + input[0] + "' & '" + input[1] + "' | Need to change "
						+ hammingDistance + " characters to match both strings.");
			} catch (Exception e) {
				System.out.println("HammingDistance: input-1 = " + input[0] + " | input-2 " + input[1] + " | Result = "
						+ e.getClass() + ' ' + e.getMessage());
				continue;
			}
		}
 
		// Real world usage example of 1-bit error detection.
		double bitErrorCount = new HammingDistance().apply("0101", "0100");
		System.out.println("Bit stream of " + bitErrorCount + " bit error.");
	}
}