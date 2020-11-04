package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.QueryStatus;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentGrade;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;

public class StudentGradeDBMapper implements RowMapper<StudentGrade>  {

	@Override
	public StudentGrade mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		StudentGrade grade=new StudentGrade();
		grade.setPracticeGrade(rs.getDouble("grades.grade"));
		grade.setTheoryGrade(rs.getDouble("grades.exam"));
		grade.setUserId(rs.getString("grades.user_host"));
		
		return grade;

	}

	
	

	
}
