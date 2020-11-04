/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;

/**
 * @author ivanruizrube
 *
 */
public class DMLExercise extends Exercise {

	public DMLExercise(Long id, String problemQuery, String problemStatement, int session) {
		super(id, problemQuery, problemStatement, session);
	}

	public DMLExercise() {
		super();
	}

	public DMLExercise(long id) {
		super(id);
	}

	@Override
	public Double computeDistance(String inputText) {

		Double result = 0.0;
		Double currentResult = 0.0;

		String[] problemQueryParts = this.problemQuery.split(";");

		for (String problemQueryPart : problemQueryParts) {
			currentResult = computeLocalDistance(inputText, problemQueryPart);
			if (currentResult > result) {
				result = currentResult;
			}
		}

		return result;
	}

	private double computeLocalDistance(String inputText, String problemQueryPart) {

		if (inputText.length() > 5) {

			String providedSentenceType = inputText.substring(0, 6);

			if ((this.fuzzyScore.fuzzyScore(providedSentenceType, "insert") >= 4)
					|| (this.fuzzyScore.fuzzyScore(providedSentenceType, "update") >= 4)
					|| (this.fuzzyScore.fuzzyScore(providedSentenceType, "delete") >= 4)) {

				return (Double) distance.apply(inputText, problemQueryPart.toLowerCase());
			}
		}
		return (double) 0;

	}

}
