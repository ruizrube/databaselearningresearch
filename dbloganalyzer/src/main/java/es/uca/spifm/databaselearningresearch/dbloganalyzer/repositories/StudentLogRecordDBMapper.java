package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.QueryStatus;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;

public class StudentLogRecordDBMapper implements RowMapper<StudentLogRecord> {
	public StudentLogRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		StudentLogRecord record = new StudentLogRecord();

		record.setId(rs.getLong("logs.id"));
		record.setQueryTime(rs.getString("logs.query_time"));
		record.setRowsExamined(rs.getString("logs.rows_examined"));
		record.setDb(rs.getString("logs.db"));

		record.setRowsSent(Long.valueOf(rs.getString("logs.rows_sent")));
		record.setStartTime(getTimestamp(rs.getString("logs.start_time")));
		record.setUserId(rs.getString("logs.user_host"));
		record.setSimilarity(rs.getDouble("logs.similarity"));

		record.setQueryText(QueryUtils.processInputQuery(rs.getString("logs.query_text")));

		if (rs.getString("query_status") != null) {
			record.setQueryStatus(QueryStatus.valueOf(rs.getString("logs.query_status")));
		}

		record.setQueryResult(rs.getString("logs.query_result"));

		return record;
	}

	private String getTimestamp(String startTime) {
		return startTime.substring(0, startTime.indexOf("."));
	}
}