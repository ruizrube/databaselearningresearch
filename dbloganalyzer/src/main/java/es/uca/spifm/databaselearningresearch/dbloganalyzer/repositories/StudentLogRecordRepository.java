package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;

@Component
public class StudentLogRecordRepository {


	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentLogRecordRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Transactional(readOnly = true)
	public Map<String, List<StudentLogRecord>> findAllGrouped() {

		Map<String, List<StudentLogRecord>> result;

		result = jdbcTemplate.query("SELECT * FROM logs LEFT JOIN exercises ON logs.exercise_id=exercises.id ORDER BY user_host ASC, start_time ASC",
				new StudentLogRecordResultSetExtractor());
	
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<StudentLogRecord> findAll() {

		List<StudentLogRecord> result=new ArrayList<StudentLogRecord>();
		
		Map<String, List<StudentLogRecord>> aux = findAllGrouped();
		
		for(String userId: aux.keySet()) {
			result.addAll(aux.get(userId));
		}
		
		
		return result;
	}

	@Transactional
	public int[] batchUpdate(final List<StudentLogRecord> records) {
		int[] updateCounts = jdbcTemplate.batchUpdate(
				"update logs set exercise_id = ?, query_status= ?, query_result= ?, similarity=?, query_text=? where id = ?",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, records.get(i).getMatchedExercise().getId());
						ps.setString(2, records.get(i).getQueryStatus().name());
						ps.setString(3, records.get(i).getQueryResult());
						ps.setDouble(4, records.get(i).getSimilarity());
						ps.setString(5, records.get(i).getQueryText());						
						ps.setLong(6, records.get(i).getId());
					}

					public int getBatchSize() {
						return records.size();
					}
				});
		return updateCounts;
	}
	
	
	@Transactional
	public int update(StudentLogRecord record) {
		int updateCounts = jdbcTemplate.update(
				"update logs set exercise_id = ?, query_status= ?, query_result= ?, similarity=?, query_text=? where id = ?",
				record.getMatchedExercise().getId(), record.getQueryStatus().name(), record.getQueryResult(),
				record.getSimilarity(), record.getQueryText(), record.getId());
		return updateCounts;
	}
	
	
	@Transactional
	public int updateNullMatching(String userId) {
		int updateCounts = jdbcTemplate.update(
				"update logs set exercise_id = null, query_status= NULL, query_result= NULL, similarity=NULL where user_host = ?",
				 userId);
		return updateCounts;
	}

	@Transactional
	public void fillNoQueriesTable() {
		// Hay restriccion de no duplicados en query. Hacemos un insert ignore
		jdbcTemplate.execute("INSERT IGNORE INTO noqueries(query,ocurrencies,enabled)\n"
				+ "SELECT DISTINCT LEFT(CONVERT(sql_text USING latin1), 254) as query, count(sql_text), 0 \n"
				+ "FROM mysql.slow_log GROUP BY sql_text "
				+ "HAVING count(query) >120");

	}



	@Transactional
	public void fillLogTable() {
		jdbcTemplate.execute(
				"INSERT INTO logs (start_time, user_host, query_time, rows_sent, rows_examined, db, query_text)\n"
						+ "SELECT start_time, LEFT(user_host, LOCATE('[',user_host)-1) AS user_host, query_time, rows_sent, rows_examined, db, lower(CONVERT(sql_text USING latin1)) as query_text \n"
						+ "FROM mysql.slow_log\n"
						+ "WHERE user_host like 'u%' AND CONVERT(sql_text USING latin1) NOT IN (SELECT lower(query) FROM noqueries WHERE enabled=1)");

	}
	
	@Transactional
	public void truncateNoQueriesTable() {

		//jdbcTemplate.execute("TRUNCATE TABLE noqueries;");

	}

	@Transactional
	public void truncateLogTable() {

		jdbcTemplate.execute("TRUNCATE TABLE logs;");

	}
}