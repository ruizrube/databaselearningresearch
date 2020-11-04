package es.uca.spifm.databaselearningresearch.dbloganalyzer.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.QueryStatus;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentActivitySummaryData;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentGrade;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.DMLExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.DescribeExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.FunctionSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.JoinSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.NestedSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.SimpleSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.ExerciseRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.GradesRepository;

@Component
public class ActivitySummaryDataTransformer {

	private ExerciseRepository exerciseRepository;
	private GradesRepository gradesRepository;

	public ActivitySummaryDataTransformer(ExerciseRepository exerciseRepository, GradesRepository gradesRepository) {
		this.exerciseRepository = exerciseRepository;
		this.gradesRepository = gradesRepository;
	}

	public List<StudentActivitySummaryData> transformDataToActivitySummary(List<StudentLogRecord> logData) {

		List<StudentActivitySummaryData> result = new ArrayList<StudentActivitySummaryData>();

		Map<String, List<StudentLogRecord>> eventsByUser = new HashMap<String, List<StudentLogRecord>>();
		List<StudentLogRecord> userLogs;

		String userId;

		for (StudentLogRecord logRecord : logData) {
			userId = logRecord.getUserId();

			if (eventsByUser.containsKey(userId)) {
				userLogs = eventsByUser.get(userId);
			} else {
				userLogs = new ArrayList<StudentLogRecord>();
			}

			userLogs.add(logRecord);

			eventsByUser.put(userId, userLogs);

		}

		int unknownAttemps;
		int totalAttemptsByExercises;
		int totalCompletedExercises;

		int numExercises = exerciseRepository.countExercises();

		Map<String, StudentGrade> grades = new LinkedHashMap<String, StudentGrade>();

		gradesRepository.findAll().stream().forEach(g -> grades.put(g.getUserId(), g));

		for (String studentId : eventsByUser.keySet()) {

			StudentActivitySummaryData studentActivitySummary = new StudentActivitySummaryData();
			studentActivitySummary.setStudentId(studentId);
			studentActivitySummary.setPracticePassed(0);
			studentActivitySummary.setPracticeGrade(0.0);
			studentActivitySummary.setTheoryGrade(0.0);

			if (grades.get(studentId) != null) {

				if (grades.get(studentId).getPracticeGrade() != null) {
					studentActivitySummary.setPracticeGrade(grades.get(studentId).getPracticeGrade());

					if (grades.get(studentId).getPracticeGrade() > 4.9) {
						studentActivitySummary.setPracticePassed(1);
					}
				}
				if (grades.get(studentId).getTheoryGrade() != null) {
					studentActivitySummary.setTheoryGrade(grades.get(studentId).getTheoryGrade());
				}
			}

			unknownAttemps = 0;
			totalAttemptsByExercises = 0;
			totalCompletedExercises = 0;

			for (StudentLogRecord logRecord : eventsByUser.get(studentId)) {

				if (logRecord.getMatchedExercise() != null) {

					if (logRecord.getMatchedExercise() instanceof DescribeExercise) {
						studentActivitySummary.setAttemptsExercises_describe(
								studentActivitySummary.getAttemptsExercises_describe() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary.setCompletedExercises_describe(
									studentActivitySummary.getCompletedExercises_describe() + 1);
							
							totalCompletedExercises++;
						}

					} else if (logRecord.getMatchedExercise() instanceof DMLExercise) {
						studentActivitySummary
								.setAttemptsExercises_dml(studentActivitySummary.getAttemptsExercises_dml() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary
									.setCompletedExercises_dml(studentActivitySummary.getCompletedExercises_dml() + 1);
							
							totalCompletedExercises++;
						}

					} else if (logRecord.getMatchedExercise() instanceof FunctionSelectExercise) {
						studentActivitySummary.setAttemptsExercises_function(
								studentActivitySummary.getAttemptsExercises_function() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary.setCompletedExercises_function(
									studentActivitySummary.getCompletedExercises_function() + 1);
							
							totalCompletedExercises++;
						}

					} else if (logRecord.getMatchedExercise() instanceof JoinSelectExercise) {
						studentActivitySummary
								.setAttemptsExercises_join(studentActivitySummary.getAttemptsExercises_join() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary.setCompletedExercises_join(
									studentActivitySummary.getCompletedExercises_join() + 1);
							
							totalCompletedExercises++;
						}

					} else if (logRecord.getMatchedExercise() instanceof NestedSelectExercise) {
						studentActivitySummary
								.setAttemptsExercises_nested(studentActivitySummary.getAttemptsExercises_nested() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary.setCompletedExercises_nested(
									studentActivitySummary.getCompletedExercises_nested() + 1);
							
							totalCompletedExercises++;
						}

					} else if (logRecord.getMatchedExercise() instanceof SimpleSelectExercise) {
						studentActivitySummary
								.setAttemptsExercises_simple(studentActivitySummary.getAttemptsExercises_simple() + 1);

						if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
							studentActivitySummary.setCompletedExercises_simple(
									studentActivitySummary.getCompletedExercises_simple() + 1);
							
							totalCompletedExercises++;
						}

					} 

					totalAttemptsByExercises++;
					
				} else {
					unknownAttemps++;
				}

			}

			

			
			studentActivitySummary.setUnknownAttemps(unknownAttemps);
			studentActivitySummary.setCompletedExercises(totalCompletedExercises);
			studentActivitySummary.setAttemptsExercises(totalAttemptsByExercises);

			result.add(studentActivitySummary);

		}
		return result;
	}

}
