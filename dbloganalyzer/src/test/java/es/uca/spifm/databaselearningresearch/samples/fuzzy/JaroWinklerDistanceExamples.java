package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import org.apache.commons.text.similarity.JaroWinklerDistance;
 
public class JaroWinklerDistanceExamples {
 
	public static void main(String[] args) {
 
		String[][] inputStrings = new String[][] {
 
				{ "winkler", "welfare" }, { "singing", "sign" },
				// Similar but different case
				{ "Binary", "bINARY" },
				// Similar word at the end
				{ "Binary", "Its All Binary" },
				// Different characters
				{ "cat", "dog" },
				// Variations of containing abc
				{ "abcxyz", "abc" }, { "xyzabc", "abc" }, { "xabcyz", "abc" }, { "axbycz", "abc" },
				// Exact similar
				{ "Binary", "Binary" },

				{ "SELECT * FROM proveedores", "SELECT *\n" + 
						"FROM proveedores" },
				
				
				{"SELECT * FROM ventas WHERE vnt_clt = 7","SELECT * FROM ventas"},
				
				{ "SELECT * FROM proveedores", "SELECT *  FROM proveedores WHERE upper(prv_nom) LIKE 'E%'" },
				
				// One character difference
				{ "ABinary", "BinaryA" }, { "ABinary", "BAinary" } };
 
		for (String[] input : inputStrings) {
			double jaroWinklerDistance = new JaroWinklerDistance().apply(input[0], input[1]);
 
			System.out.println("JaroWinklerDistance of '" + input[0] + "' & '" + input[1]
					+ "' | " + (jaroWinklerDistance * 100) + "% similar");

			//			System.out.println("JaroWinklerDistance of '" + input[0] + "' & '" + input[1]
//					+ "' | Distinct characters in strings are " + (jaroWinklerDistance * 100) + "% similar or "
//					+ ((1 - jaroWinklerDistance) * 100) + "% dis-similar.");
		}
	}
}