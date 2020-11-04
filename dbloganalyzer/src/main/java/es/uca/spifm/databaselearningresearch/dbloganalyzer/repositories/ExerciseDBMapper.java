package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.DMLExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.DescribeExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.FunctionSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.JoinSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.NestedSelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.SelectExercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.exercisetypes.SimpleSelectExercise;

public class ExerciseDBMapper implements RowMapper<Exercise> {
	public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Long id = rs.getLong("exercises.id");
		Exercise record = new SelectExercise(id);
	
		if(rs.getString("exercises.type").equals("describe")) {
			record = new DescribeExercise(id);
		} else if(rs.getString("exercises.type").equals("simple")) {
			record = new SimpleSelectExercise(id);
		} else if(rs.getString("exercises.type").equals("function")) {
			record = new FunctionSelectExercise(id);
		} else if(rs.getString("exercises.type").equals("nested")) {
			record = new NestedSelectExercise(id);
		} else if(rs.getString("exercises.type").equals("join")) {
			record = new JoinSelectExercise(id);
		} else if(rs.getString("exercises.type").equals("dml")) {
			record = new DMLExercise(id);			
		}
		
		
		record.setValidationQuery(rs.getString("exercises.validation"));
		record.setProblemStatement(rs.getString("exercises.description"));
		record.setSession(rs.getInt("exercises.session"));

		record.setProblemQuery(QueryUtils.processInputQuery(rs.getString("exercises.query")));

		return record;
	}

	
}