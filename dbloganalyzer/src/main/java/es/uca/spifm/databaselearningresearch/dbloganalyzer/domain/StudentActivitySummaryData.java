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
	private int practicePassed; // if practiceGrade>=5
	private Double practiceGrade;
	private Double theoryGrade;
	
	private int completedExercises;
	private int attemptsExercises;
	private int unknownAttemps;
	
	private int completedExercises_describe;
	private int attemptsExercises_describe;
	private int completedExercises_simple;
	private int attemptsExercises_simple;
	private int completedExercises_function;
	private int attemptsExercises_function;
	private int completedExercises_nested;
	private int attemptsExercises_nested;
	private int completedExercises_join;
	private int attemptsExercises_join;
	private int completedExercises_dml;
	private int attemptsExercises_dml;
	
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getPracticePassed() {
		return practicePassed;
	}
	public void setPracticePassed(int practicePassed) {
		this.practicePassed = practicePassed;
	}
	public Double getPracticeGrade() {
		return practiceGrade;
	}
	public void setPracticeGrade(Double practiceGrade) {
		this.practiceGrade = practiceGrade;
	}
	public Double getTheoryGrade() {
		return theoryGrade;
	}
	public void setTheoryGrade(Double theoryGrade) {
		this.theoryGrade = theoryGrade;
	}
	public int getCompletedExercises() {
		return completedExercises;
	}
	public void setCompletedExercises(int completedExercises) {
		this.completedExercises = completedExercises;
	}
	public int getAttemptsExercises() {
		return attemptsExercises;
	}
	public void setAttemptsExercises(int attemptsExercises) {
		this.attemptsExercises = attemptsExercises;
	}
	public int getUnknownAttemps() {
		return unknownAttemps;
	}
	public void setUnknownAttemps(int unknownAttemps) {
		this.unknownAttemps = unknownAttemps;
	}
	public int getCompletedExercises_describe() {
		return completedExercises_describe;
	}
	public void setCompletedExercises_describe(int completedExercises_describe) {
		this.completedExercises_describe = completedExercises_describe;
	}
	public int getAttemptsExercises_describe() {
		return attemptsExercises_describe;
	}
	public void setAttemptsExercises_describe(int attemptsExercises_describe) {
		this.attemptsExercises_describe = attemptsExercises_describe;
	}
	public int getCompletedExercises_simple() {
		return completedExercises_simple;
	}
	public void setCompletedExercises_simple(int completedExercises_simple) {
		this.completedExercises_simple = completedExercises_simple;
	}
	public int getAttemptsExercises_simple() {
		return attemptsExercises_simple;
	}
	public void setAttemptsExercises_simple(int attemptsExercises_simple) {
		this.attemptsExercises_simple = attemptsExercises_simple;
	}
	public int getCompletedExercises_function() {
		return completedExercises_function;
	}
	public void setCompletedExercises_function(int completedExercises_function) {
		this.completedExercises_function = completedExercises_function;
	}
	public int getAttemptsExercises_function() {
		return attemptsExercises_function;
	}
	public void setAttemptsExercises_function(int attemptsExercises_function) {
		this.attemptsExercises_function = attemptsExercises_function;
	}
	public int getCompletedExercises_nested() {
		return completedExercises_nested;
	}
	public void setCompletedExercises_nested(int completedExercises_nested) {
		this.completedExercises_nested = completedExercises_nested;
	}
	public int getAttemptsExercises_nested() {
		return attemptsExercises_nested;
	}
	public void setAttemptsExercises_nested(int attemptsExercises_nested) {
		this.attemptsExercises_nested = attemptsExercises_nested;
	}
	public int getCompletedExercises_join() {
		return completedExercises_join;
	}
	public void setCompletedExercises_join(int completedExercises_join) {
		this.completedExercises_join = completedExercises_join;
	}
	public int getAttemptsExercises_join() {
		return attemptsExercises_join;
	}
	public void setAttemptsExercises_join(int attemptsExercises_join) {
		this.attemptsExercises_join = attemptsExercises_join;
	}
	public int getCompletedExercises_dml() {
		return completedExercises_dml;
	}
	public void setCompletedExercises_dml(int completedExercises_dml) {
		this.completedExercises_dml = completedExercises_dml;
	}
	public int getAttemptsExercises_dml() {
		return attemptsExercises_dml;
	}
	public void setAttemptsExercises_dml(int attemptsExercises_dml) {
		this.attemptsExercises_dml = attemptsExercises_dml;
	}
	



}
