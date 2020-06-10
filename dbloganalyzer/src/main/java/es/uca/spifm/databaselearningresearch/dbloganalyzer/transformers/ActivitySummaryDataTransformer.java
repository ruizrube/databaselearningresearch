package es.uca.spifm.databaselearningresearch.dbloganalyzer.transformers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.QueryStatus;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentActivitySummaryData;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.ExerciseRepository;

@Component
public class ActivitySummaryDataTransformer {

	private ExerciseRepository exerciseRepository;

	
	public ActivitySummaryDataTransformer(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;		
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

		int[] attemps;
		boolean[] completed;
		int unknownAttemps;

		int numExercises = exerciseRepository.countExercises();
		
		for (String studentId : eventsByUser.keySet()) {
		
			
			attemps = new int[numExercises];
			completed = new boolean[numExercises];
			unknownAttemps=0;
			
			Arrays.fill(attemps, 0);
			Arrays.fill(completed, false);

			for (StudentLogRecord logRecord : eventsByUser.get(studentId)) {
				
				if(logRecord.getMatchedExercise()!=null) {
					attemps[logRecord.getMatchedExercise().getId().intValue() - 1]++;					
										
					if (logRecord.getQueryStatus().equals(QueryStatus.QUERY_OK)) {
						completed[logRecord.getMatchedExercise().getId().intValue() - 1] = true;
					}
				} else {
					unknownAttemps++;
				}

				

			}

			
			StudentActivitySummaryData studentActivitySummary = new StudentActivitySummaryData();
			studentActivitySummary.setStudentId(studentId);
			studentActivitySummary.setAttemptsByExercise(attemps);
			studentActivitySummary.setCompletedExercises(completed);
			studentActivitySummary.setUnknownAttemps(unknownAttemps);
			result.add(studentActivitySummary);
			
			
		}
		return result;
	}
//	DataSource source = new DataSource("/some/where/data.arff");
//	Instances data = source.getDataSet();
//	// setting class attribute if the data format does not provide this information
//	// For example, the XRFF format saves the class attribute information as well
//	if (data.classIndex() == -1)
//	  data.setClassIndex(data.numAttributes() - 1);
//
//	

}
