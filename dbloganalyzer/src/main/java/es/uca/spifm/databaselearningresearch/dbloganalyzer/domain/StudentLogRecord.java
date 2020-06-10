/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

/**
 * @author ivanruizrube
 *
 */
public class StudentLogRecord {

	private Long id;
	private String startTime;
	private String userId;
	private String queryTime;
	private Long rowsSent;
	private String rowsExamined;
	private String db;
	private String queryText;
	private Double similarity;

	private Exercise matchedExercise;
	private QueryStatus queryStatus = QueryStatus.UNKNOWN;
	private String queryResult="";

	public StudentLogRecord() {
		// TODO Auto-generated constructor stub
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public Long getRowsSent() {
		return rowsSent;
	}

	public void setRowsSent(Long rowsSent) {
		this.rowsSent = rowsSent;
	}

	public String getRowsExamined() {
		return rowsExamined;
	}

	public void setRowsExamined(String rowsExamined) {
		this.rowsExamined = rowsExamined;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public Exercise getMatchedExercise() {
		return matchedExercise;
	}

	public void setMatchedExercise(Exercise matchedExercise) {
		this.matchedExercise = matchedExercise;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public QueryStatus getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(QueryStatus queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryResult() {

		if (queryResult!=null && queryResult.length() > 510) {
			queryResult = queryResult.substring(0, 510);
		}

		return queryResult;
	}

	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}

}
