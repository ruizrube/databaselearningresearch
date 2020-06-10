/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ivanruizrube
 *
 */
public class StudentActivitySummaryData {

	private String studentId; // user_host
	private int[] attemptsByExercise; // exercise
	private boolean[] completedExercises; // exercise
	private int unknownAttemps;
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<Integer> getAttemptsByExercise() {
		
		List<Integer> result=new ArrayList<Integer>();
		for (int attempts:attemptsByExercise) {
			result.add(attempts);
		}
		return result;
		
	}

	public void setAttemptsByExercise(int[] attemptsByExercise) {
		this.attemptsByExercise = attemptsByExercise;
	}

	public List<Integer> getCompletedExercises() {
		
		List<Integer> result=new ArrayList<Integer>();
		for (boolean completedExercise:completedExercises) {
			result.add((completedExercise) ? 1 : 0);
		}
		return result;
	}

	public void setCompletedExercises(boolean[] completedExercises) {
		this.completedExercises = completedExercises;
	}

	public int getUnknownAttemps() {
		return unknownAttemps;
	}

	public void setUnknownAttemps(int unknownAttemps) {
		this.unknownAttemps = unknownAttemps;
	}

}
