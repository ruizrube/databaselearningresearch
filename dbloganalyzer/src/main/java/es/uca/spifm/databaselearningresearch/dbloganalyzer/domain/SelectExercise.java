/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author ivanruizrube
 *
 */
public class SelectExercise extends Exercise {

	public SelectExercise(Long id, String problemSolution, String problemStatement, int session) {
		super(id, problemSolution, problemStatement, session);
	}

	public SelectExercise() {
		super();
	}

	public SelectExercise(long id) {
		super(id);
	}

	@Override
	public Double computeDistance(String inputText) {

		if (inputText.length() > 5) {

			String providedSentenceType = inputText.substring(0, 6);

			if (this.fuzzyScore.fuzzyScore(providedSentenceType, "select") >= 4) {

				String providedTableName = extractProvidedTableName(inputText);

				if (this.getTableNamesFromQuery().size() > 0) {
					String expectedTableName = this.getTableNamesFromQuery().get(0);

					if (this.fuzzyScore.fuzzyScore(providedTableName, expectedTableName) >= expectedTableName.length()
							- 3) {
						return (Double) distance.apply(inputText, problemQuery.toLowerCase());
					}
				} else { // No tiene tabla en FROM. Ej: SELECT dayname('1981-02-27')
					return (Double) distance.apply(inputText, problemQuery.toLowerCase());

				}
			}
		}
		return (double) 0;

	}

	private String extractProvidedTableName(String inputText) {
		int positionInitial = inputText.indexOf("from") + 4;
		int positionFinal = inputText.indexOf("from") + 16;

		return inputText.substring(positionInitial,
				(inputText.length() < positionFinal) ? inputText.length() : positionFinal);
	}

}
