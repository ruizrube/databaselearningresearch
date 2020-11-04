/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;

/**
 * @author ivanruizrube
 *
 */
public class DescribeExercise extends Exercise {

	public DescribeExercise(Long id, String problemSolution, String problemStatement, int session) {

		super(id, problemSolution, problemStatement, session);
	}

	@Override
	public Double computeDistance(String inputText) {

		// Evitamos matching con sentencias describe
//		if (this.fuzzyScore.fuzzyScore(inputText, problemQuery.toLowerCase()) > 13) {
//			return (Double) distance.apply(inputText, problemQuery.toLowerCase());
//		} else {
			return 0.0;
		//}

	}

	public DescribeExercise(long id) {
		super(id);
	}
}
