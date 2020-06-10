package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.SelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;

public class StudentLogRecordResultSetExtractor implements ResultSetExtractor<List<StudentLogRecord>> {
	@Override
	public List<StudentLogRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {
		StudentLogRecordDBMapper studentLogRecordDBMapper = new StudentLogRecordDBMapper();
		ExerciseDBMapper exerciseDBMapper = new ExerciseDBMapper();

		List<StudentLogRecord> result = new ArrayList<StudentLogRecord>();
		int rowNum = 0;
		while (rs.next()) {
			StudentLogRecord logRecord = studentLogRecordDBMapper.mapRow(rs, rowNum);
			
			Long exerciseId = rs.getLong("exercises.id");
			
			String type=rs.getString("exercises.type");
				
			if(type!=null) {
				Exercise exercise = exerciseDBMapper.mapRow(rs, rowNum++);
				logRecord.setMatchedExercise(exercise);
			}
			result.add(logRecord);
		}
		return result;
	}
}