/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentGrade;

/**
 * @author ivanruizrube
 *
 */
@Component
public class GradesRepository {

	@Autowired
	@Qualifier("jdbc_bdadmin")
	private final JdbcTemplate jdbcTemplate;

	public GradesRepository(@Qualifier("jdbc_bdadmin") JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;

	}

	@Cacheable
	public List<StudentGrade> findAll() {

		List<StudentGrade> result = new ArrayList<StudentGrade>();

		result = jdbcTemplate.query("SELECT * FROM grades", new StudentGradeDBMapper());

		return result;

	}

	public int countExercises() {

		return jdbcTemplate.queryForObject("SELECT count(*) FROM grades", Integer.class);

	}



}
