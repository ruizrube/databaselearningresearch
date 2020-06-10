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

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;

/**
 * @author ivanruizrube
 *
 */
@Component
public class ExerciseRepository {

	@Autowired
	@Qualifier("jdbc_bdadmin")
	private final JdbcTemplate jdbcTemplate;

	public ExerciseRepository(@Qualifier("jdbc_bdadmin") JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;

	}

	@Cacheable
	public List<Exercise> findAll() {

		List<Exercise> result = new ArrayList<Exercise>();

		result = jdbcTemplate.query("SELECT * FROM exercises", new ExerciseDBMapper());

		return result;

	}

	public int countExercises() {

		return jdbcTemplate.queryForObject("SELECT count(*) FROM exercises", Integer.class);

	}

}
