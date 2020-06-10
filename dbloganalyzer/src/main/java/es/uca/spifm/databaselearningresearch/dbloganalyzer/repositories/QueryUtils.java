/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

/**
 * @author ivanruizrube
 *
 */
public class QueryUtils {

	
	public static String processInputQuery(String inputText) {
		
		inputText= inputText.toLowerCase().trim();
		
		inputText = inputText.replace("\n", " ").replace("\r", " ");				
		
		if(inputText.contains("#")) {
			inputText=inputText.substring(0, inputText.indexOf("#"));
		}
	
		if(inputText.contains("-- ")) {
			inputText=inputText.substring(0, inputText.indexOf("-- "));
		}
		
		if(inputText.contains("--	")) {
			inputText=inputText.substring(0, inputText.indexOf("--	"));
		}
		if(inputText.contains("--\n")) {
			inputText=inputText.substring(0, inputText.indexOf("--	"));
		}
		
		if(inputText.contains("/*") && inputText.contains("*/")) {
			inputText=inputText.substring(0, inputText.indexOf("/*")) + inputText.substring(inputText.indexOf("*/")+2,inputText.length());  
		
		}	
		
		return inputText;

	}

}
