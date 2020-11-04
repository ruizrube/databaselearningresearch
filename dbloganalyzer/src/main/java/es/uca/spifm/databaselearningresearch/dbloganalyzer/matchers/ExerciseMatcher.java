package es.uca.spifm.databaselearningresearch.dbloganalyzer.matchers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.PlaygroundRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.StudentLogRecordRepository;

@Component
public class ExerciseMatcher {
	private static final double THRESHOLD = 0.49;
	private static final double PUNISHMENT = 0.070;
	private StudentLogRecordRepository studentLogRecordRepository;

	public ExerciseMatcher(PlaygroundRepository userTableRepository,
			StudentLogRecordRepository studentLogRecordRepository) {
		super();
		this.studentLogRecordRepository = studentLogRecordRepository;
	}

	

	@Async
	public CompletableFuture<Void> matchUserLogWithExercises(List<StudentLogRecord> userLogData,
			List<Exercise> exerciseData) {

		Set<Long> previousExerciseIds = new HashSet<Long>();

		// Iterate all the sql logs
		for (int i = 0; i < userLogData.size(); i++) {

			// Get the current log
			StudentLogRecord inputRecord = userLogData.get(i);
			
			System.out.println(
					"> Localizando consulta con id=" + inputRecord.getId() + " (" + (i + 1) + "/" + userLogData.size()
							+ ") del usuario " + inputRecord.getUserId() + " con texto: " + inputRecord.getQueryText());

			// Try to match the input query with any of the exercises
			if (discoverExcercise(inputRecord, previousExerciseIds, exerciseData)) {
				System.out.println("> > SI localizada! Ejercicio #" + inputRecord.getMatchedExercise().getId());

				studentLogRecordRepository.update(inputRecord);

				previousExerciseIds.add(inputRecord.getMatchedExercise().getId());

			} else {
				System.out.println("> > NO localizada! ");

			}

		}
		return new CompletableFuture<Void>();

	}

	private boolean discoverExcercise(StudentLogRecord inputRecord, Set<Long> previousExerciseIds,
			List<Exercise> exerciseData) {

		Exercise currentExercise = null;
		Double currentDistance = 0.0;
		Exercise matchedExercise = null;
		Double matchedDistance = 0.0;

		Double punishesment = 0.0;

		Long expectedExerciseId = searchExpectedExercise(previousExerciseIds);

		int lowerLimit = (int) Math.max(1, expectedExerciseId - 6);
		int higherLimit = (int) Math.min(expectedExerciseId + 6, exerciseData.size());

		for (int i = lowerLimit; i <= higherLimit; i++) {

			currentExercise = exerciseData.get(i - 1);
			currentDistance = currentExercise.computeDistance(inputRecord.getQueryText());

			punishesment = (double) (Math.abs(expectedExerciseId - i) * PUNISHMENT);// conforme más nos alejamos del
																					// siguiente
			// ejercicio esperado mayor penalización

			currentDistance -= punishesment;

			if (expectedExerciseId == i) {
				System.out.print("* * * ");
			} else {
				System.out.print("> > > ");
			}

			System.out.println("Comparando con ejer. " + currentExercise.getId() + "["
					+ currentExercise.getProblemQuery() + "]" + ". Distancia actual: " + currentDistance);

			// We hold the most likely exercise
			if (currentDistance > THRESHOLD && currentDistance >= matchedDistance) {
				matchedExercise = currentExercise;
				matchedDistance = currentDistance;
			}
		}

		if (matchedExercise != null) {

			// Assign exercise to the query attempt
			inputRecord.setSimilarity(matchedDistance);

			inputRecord.setMatchedExercise(matchedExercise);

			return true;

		} else {
			return false;
		}

	}

	private Long searchExpectedExercise(Set<Long> previousExerciseIds) {
		Long result = (long) 1;

		for (Long i = (long) 2; i <= 1000; i++) {

			if (!previousExerciseIds.contains(i)) {
				result = i;
				break;
			}
		}

		return result;
	}

}
