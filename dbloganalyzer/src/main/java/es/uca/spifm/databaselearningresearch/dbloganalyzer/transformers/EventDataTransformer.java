/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentEventData;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.PlaygroundRepository;

/**
 * @author ivanruizrube
 *
 */
@Component
public class EventDataTransformer {

	private PlaygroundRepository userTableRepository;

	public EventDataTransformer(PlaygroundRepository userTableRepository) {
		super();
		this.userTableRepository = userTableRepository;
	}

	public List<StudentEventData> transformDataToEvents(List<StudentLogRecord> sourceData, List<Exercise> exerciseData) {

		List<StudentEventData> results = new ArrayList<StudentEventData>();

		Map<Long,Exercise> exerciseMap=new HashMap<Long,Exercise>();
		for(Exercise ex: exerciseData) {
			exerciseMap.put(ex.getId(),ex);
		}
		
		
		StudentEventData userEvent;

		for (StudentLogRecord record : sourceData) {

			// Create a new event

			userEvent = new StudentEventData();
			if (record.getMatchedExercise() != null) {
				userEvent.setCaseId(record.getUserId());
				userEvent.setActivity("Exercise #" + record.getMatchedExercise().getId());
				userEvent.setQueryStatus(record.getQueryStatus());
				userEvent.setTimestamp(record.getStartTime());
				userEvent.setQueryText(record.getQueryText());
				userEvent.setExpectedQuery(exerciseMap.get(record.getMatchedExercise().getId()).getProblemQuery());

				results.add(userEvent);
			}

		}

		return results;

		// Map<String, List<StudentEventData>> result = new HashMap<String,
		// List<StudentEventData>>();

		// Obtain user' previous events
//		if (result.containsKey(caseId)) {
//			userEvents = result.get(caseId);
//		} else {
//			userEvents = new ArrayList<StudentEventData>();
//		}

		// Store the event
//		userEvents.add(userEvent);
//		result.put(caseId, userEvents);

		// return
		// result.values().stream().flatMap(List::stream).collect(Collectors.toList());

	}

}
