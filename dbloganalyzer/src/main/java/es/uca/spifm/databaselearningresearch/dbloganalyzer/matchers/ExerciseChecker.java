package es.uca.spifm.databaselearningresearch.dbloganalyzer.matchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.QueryStatus;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.PlaygroundRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.StudentLogRecordRepository;

@Component
public class ExerciseChecker {

	private PlaygroundRepository playgroundRepository;

	private StudentLogRecordRepository studentLogRecordRepository;

	Map<Long, List<Map<String, Object>>> aux = new HashMap<Long, List<Map<String, Object>>>();

	public ExerciseChecker(PlaygroundRepository playgroundRepository,
			StudentLogRecordRepository studentLogRecordRepository) {
		super();
		this.playgroundRepository = playgroundRepository;
		this.studentLogRecordRepository = studentLogRecordRepository;
	}

	@Async
	public List<StudentLogRecord> checkUserLogWithExercises(List<StudentLogRecord> logData,
			List<Exercise> exerciseData) {

		StudentLogRecord inputRecord;
		for (int i = 1; i <= logData.size(); i++) {
			inputRecord = logData.get(i - 1);
			System.out.println(
					"> Validando input query (" + i + "/" + logData.size() + "): " + inputRecord.getQueryText());

			if (inputRecord.getMatchedExercise() != null) {
				inputRecord = checkQueryStatus(inputRecord);
				studentLogRecordRepository.update(inputRecord);
				System.out.println("> > Resultado: " + inputRecord.getQueryStatus());
			} else {
				System.out.println("> > Ignorada..");
			}

		}
		return logData;

	}

	public List<Exercise> runSolutions(List<Exercise> exerciseData) {

		Exercise exercise;
		for (int i = 1; i <= exerciseData.size(); i++) {
			exercise = exerciseData.get(i - 1);
			System.out.println("> Ejecutando solución de ejercicio (" + i + "/" + exerciseData.size() + "): "
					+ exercise.getProblemStatement());

			if (exercise.getValidationQuery() != null && !exercise.getValidationQuery().isEmpty()) {
				aux.put(exercise.getId(), playgroundRepository
						.retrieveDataFromSpecificDatabase(exercise.getValidationQuery(), "u12345678"));
			} else {

				aux.put(exercise.getId(), playgroundRepository.retrieveDataFromDefaultDB(exercise.getProblemQuery()));

			}

		}
		return exerciseData;
	}

	private StudentLogRecord checkQueryStatus(StudentLogRecord inputRecord) {

		List<Map<String, Object>> expectedResults = aux.get(inputRecord.getMatchedExercise().getId());

		String input_query = inputRecord.getQueryText();
		Long rows_sent = inputRecord.getRowsSent();

	
		// Executing the queries
		List<Map<String, Object>> obtainedResults;
		try {

			if (inputRecord.getMatchedExercise().getValidationQuery() != null
					&& !inputRecord.getMatchedExercise().getValidationQuery().isEmpty()) {
				obtainedResults = playgroundRepository.retrieveDataFromSpecificDatabase(
						inputRecord.getMatchedExercise().getValidationQuery(), inputRecord.getUserId());
			} else {
				obtainedResults = playgroundRepository.retrieveDataFromDefaultDB(input_query);
			}

		} catch (DataAccessException ex) {
			inputRecord.setQueryStatus(QueryStatus.QUERY_ERROR);
			inputRecord.setQueryResult(ex.getLocalizedMessage());
			return inputRecord;
		}

	
		// Checking the number of rows
		if (expectedResults.size() != obtainedResults.size()) {
			inputRecord.setQueryStatus(QueryStatus.WRONG_NUMBER_ROWS);
			return inputRecord;
		}

		// Checking the number of columns and cells

		if (expectedResults.size() > 0 && obtainedResults.size() > 0) {

			Map<String, Object> firstExpectedRow = expectedResults.get(0);

			Map<String, Object> firstObtainedRow = obtainedResults.get(0);

			if (firstExpectedRow.size() != firstObtainedRow.size()) {
				inputRecord.setQueryStatus(QueryStatus.WRONG_NUMBER_COLUMNS);
				return inputRecord;
			}

			for (int i = 0; i < expectedResults.size(); i++) {
				for (int j = 0; j < firstExpectedRow.size(); j++) {
					if (!expectedResults.get(i).keySet().toArray()[j].toString()
							.equals(obtainedResults.get(i).keySet().toArray()[j].toString())) {
						inputRecord.setQueryStatus(QueryStatus.WRONG_CELL_VALUES);
						return inputRecord;
					}
				}

			}

		}

		inputRecord.setQueryStatus(QueryStatus.QUERY_OK);

		return inputRecord;

	}

}
