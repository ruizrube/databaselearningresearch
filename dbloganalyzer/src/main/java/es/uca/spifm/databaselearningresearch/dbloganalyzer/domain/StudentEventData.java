/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

/**
 * @author ivanruizrube
 *
 */
public class StudentEventData {

	private String timestamp; // start_time
	private String caseId; // user_host
	private String activity; // exercise
	private String queryText; // exercise
	private QueryStatus queryStatus;
	private String expectedQuery;
	
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public QueryStatus getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(QueryStatus queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public String getExpectedQuery() {
		return expectedQuery;
	}

	public void setExpectedQuery(String expectedQuery) {
		this.expectedQuery = expectedQuery;
	}

}
