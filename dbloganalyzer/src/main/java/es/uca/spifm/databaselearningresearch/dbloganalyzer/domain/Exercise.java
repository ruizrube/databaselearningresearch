/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.util.TablesNamesFinder;

import net.sf.jsqlparser.statement.Statement;

/**
 * @author ivanruizrube
 *
 */
public abstract class Exercise {

	protected static Long MAX_DISTANCE = (long) 1000;

	protected Long id;

	protected String problemQuery;

	
	protected String problemStatement;

	protected int session;

	// https://itsallbinary.com/similar-strings-or-same-sounding-strings-algorithms-comparison-apache-implementation-in-java/#jaccard

	protected FuzzyScore fuzzyScore = new FuzzyScore(Locale.forLanguageTag("es"));

	protected EditDistance distance = new JaroWinklerDistance();

	//private List<Map<String, Object>> problemResult;

	private String validationQuery;

	public Exercise(Long id) {
		this.id = id;
	}

	public Exercise(Long id, String problemQuery, String problemStatement, int session) {
		super();
		this.id = id;
		this.problemQuery = problemQuery;
		this.problemStatement = problemStatement;
		this.session = session;
	}

	public Exercise() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void Long(Long id) {
		this.id = id;
	}

	public String getProblemQuery() {
		return problemQuery;
	}

	public void setProblemQuery(String problemQuery) {
		this.problemQuery = problemQuery;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	

	public abstract Double computeDistance(String inputText);

//	public void setProblemResult(List<Map<String, Object>> results) {
//		this.problemResult = results;
//
//	}
//
//	public List<Map<String, Object>> getProblemResult() {
//		return problemResult;
//	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getValidationQuery() {
		return validationQuery;
	}
	
	
	public boolean wasCompletedByUser(List<StudentLogRecord> userPreviousLogs) {

		for (StudentLogRecord record : userPreviousLogs) {
			if (record.getMatchedExercise() != null) {
				if (record.getMatchedExercise().getId().equals(this.id)) {
					if (record.getQueryResult().equals(QueryStatus.QUERY_OK)) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public List<String> getTableNamesFromQuery() {

		List<String> tableList;
		Statement statement;
		try {
			statement = CCJSqlParserUtil.parse(this.getProblemQuery());
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			tableList = tablesNamesFinder.getTableList(statement);
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tableList = new ArrayList<String>();
		}

		return tableList;

	}
}
