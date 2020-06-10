package es.uca.spifm.databaselearningresearch.dbloganalyzer.matchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.PlaygroundRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.StudentLogRecordRepository;

@Component
public class ExerciseMatcher {
	private static final double THRESHOLD = 0.5;
	private StudentLogRecordRepository studentLogRecordRepository;

	public ExerciseMatcher(PlaygroundRepository userTableRepository,
			StudentLogRecordRepository studentLogRecordRepository) {
		super();
		this.studentLogRecordRepository = studentLogRecordRepository;
	}

	public List<StudentLogRecord> matchLogWithExercises(List<StudentLogRecord> sourceData,
			List<Exercise> exerciseData) {

		Map<String, List<StudentLogRecord>> eventsByUser = new HashMap<String, List<StudentLogRecord>>();
		List<StudentLogRecord> userLogs;

		String userId;
		List<StudentLogRecord> recordsToUpdate = new ArrayList<StudentLogRecord>();

		// Iterate all the sql logs
		// for (int i = 0; i < 300; i++) {
		for (int i = 0; i < sourceData.size(); i++) {

			// Get the current log
			StudentLogRecord inputRecord = sourceData.get(i);

			// if (inputRecord.getUserId().equals("u17490334")) {
			// Get the student id
			userId = inputRecord.getUserId();

			// Get the previous queries of the student
			if (eventsByUser.containsKey(userId)) {
				userLogs = eventsByUser.get(userId);
			} else {
				userLogs = new ArrayList<StudentLogRecord>();
			}

			System.out.println(
					"> Localizando consulta con id=" + inputRecord.getId() + " (" + (i + 1) + "/" + sourceData.size()
							+ ") del usuario " + inputRecord.getUserId() + " con texto: " + inputRecord.getQueryText());

			// Try to match the input query with any of the exercises
			if (discoverExcercise(inputRecord, userLogs, exerciseData)) {
				System.out.println("> > SI localizada! Ejercicio #" + inputRecord.getMatchedExercise().getId());
				// studentLogRecordRepository.update(inputRecord);
				// System.out.println("> > Guardado en base de datos!");

				// Since we have located the exercise, we have to update in DB the data of the
				// input query
				recordsToUpdate.add(inputRecord);
				
				studentLogRecordRepository.update(inputRecord);

			} else {
				System.out.println("> > NO localizada! ");// + inputRecord.getQueryText());

			}

			userLogs.add(inputRecord);

			eventsByUser.put(userId, userLogs);

			// }
		}

//		if (recordsToUpdate.size() > 0) {
//			System.out.println("Actualizando en base de datos la información localizada de " + recordsToUpdate.size()
//					+ " logs sobre un total de " + sourceData.size());
//			studentLogRecordRepository.batchUpdate(recordsToUpdate);
//		}

		return sourceData;

	}

	private boolean discoverExcercise(StudentLogRecord inputRecord, List<StudentLogRecord> userPreviousLogs,
			List<Exercise> exerciseData) {

		
		Exercise currentExercise = null;
		Double currentDistance = 0.0;
		Exercise matchedExercise = null;
		Double matchedDistance = 0.0;

		Double punishesment = 0.0;

		// Locate the sql attempt of the student
		Exercise lastExercise = searchLastExercise(userPreviousLogs);
		Long expectedExerciseId = (long) 1;

		if (lastExercise != null) {
			expectedExerciseId = lastExercise.getId() + 1;
		}

		int lowLimit=(int) Math.max(0, expectedExerciseId-10);
		int hightLimit=(int) Math.min(expectedExerciseId+10, exerciseData.size() - 1);
		
		for (int i = lowLimit; i<=hightLimit; i++) {

			currentExercise = exerciseData.get(i);
			currentDistance = currentExercise.computeDistance(inputRecord.getQueryText());

			punishesment = (double) (Math.abs(expectedExerciseId - i) * 0.1);// conforme más nos alejamos del siguiente
																			// ejercicio esperado mayor penalización

			currentDistance -= punishesment;

			System.out.println("> > > Comparando con ejer. " + currentExercise.getId() + "["
					+ currentExercise.getProblemQuery() + "]" + ". Distancia actual: " + currentDistance);
			// We hold the most likely exercise
			if (currentDistance >= THRESHOLD && currentDistance >= matchedDistance) {
				matchedExercise = currentExercise;
				matchedDistance = currentDistance;
			}
		}

		if (matchedExercise != null) {

			// Assign exercise to the query attempt
			inputRecord.setSimilarity(matchedDistance);

			inputRecord.setMatchedExercise(matchedExercise);

			// Evaluate the query to check result
			// discoverQueryStatus(inputRecord, currentExercise);

			return true;

		} else {
			return false;
		}

	}

	private Exercise searchLastExercise(List<StudentLogRecord> userPreviousLogs) {

		Exercise result = null;

		for (int i = userPreviousLogs.size() - 1; i >= 0; i--) {
			result = userPreviousLogs.get(i).getMatchedExercise();
			if (result != null) {
				break;
			}
		}

		return result;
	}

}
