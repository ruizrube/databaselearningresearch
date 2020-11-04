package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;

public class StudentLogRecordResultSetExtractor implements ResultSetExtractor<Map<String, List<StudentLogRecord>>> {
	@Override
	public Map<String, List<StudentLogRecord>> extractData(ResultSet rs) throws SQLException, DataAccessException {
		StudentLogRecordDBMapper studentLogRecordDBMapper = new StudentLogRecordDBMapper();
		ExerciseDBMapper exerciseDBMapper = new ExerciseDBMapper();

		Map<String, List<StudentLogRecord>> result = new HashMap<String, List<StudentLogRecord>>();

		List<StudentLogRecord> aux;
		int rowNum = 0;
		while (rs.next()) {
			StudentLogRecord logRecord = studentLogRecordDBMapper.mapRow(rs, rowNum);

			if (!logRecord.getQueryText().startsWith("set") && !logRecord.getQueryText().startsWith("describe")
					&& !logRecord.getQueryText().startsWith("show")) {

				String type = rs.getString("exercises.type");

				if (type != null) {
					Exercise exercise = exerciseDBMapper.mapRow(rs, rowNum++);
					logRecord.setMatchedExercise(exercise);
				}

				if (result.containsKey(logRecord.getUserId())) {
					aux = result.get(logRecord.getUserId());
				} else {
					aux = new ArrayList<StudentLogRecord>();
				}
				aux.add(logRecord);
				result.put(logRecord.getUserId(), aux);

			}

		}
		return result;
	}
}