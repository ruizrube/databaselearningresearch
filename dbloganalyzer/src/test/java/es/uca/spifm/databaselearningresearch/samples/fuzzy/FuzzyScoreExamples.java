package es.uca.spifm.databaselearningresearch.samples.fuzzy;
import java.util.Locale;
import org.apache.commons.text.similarity.FuzzyScore;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.SelectExercise;

public class FuzzyScoreExamples {
	public static void main(String[] args) {

		
		SelectExercise ejer=new SelectExercise();
		ejer.setProblemQuery("SELECT * FROM pesos");
		
		
		System.out.println("->" + ejer.computeDistance("SELECT * FROM peos"));
		
		
		System.out.println("->->->->" +  processInputText("/*  ivan  */ SELECT  * FROM peos"));
		
		
	}
	
	static String processInputText(String inputText) {
		
		inputText= inputText.toLowerCase().trim();
		
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