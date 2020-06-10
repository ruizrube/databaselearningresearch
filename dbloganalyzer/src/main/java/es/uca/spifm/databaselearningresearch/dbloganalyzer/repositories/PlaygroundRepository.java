/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * @author ivanruizrube
 *
 */
@Component
public class PlaygroundRepository {

	@Qualifier("jdbc_bdplayground")
	private final JdbcTemplate jdbcTemplate;

	
	@Value( "${app.datasource.bdindividualplayground.url}" )
	private String bdIndividualPlaygroundURL;
	
	@Value( "${app.datasource.bdindividualplayground.username}" )
	private String bdIndividualPlaygroundUsername;
	
	@Value( "${app.datasource.bdindividualplayground.password}" )
	private String bdIndividualPlaygroundPassword;
		
	
	public PlaygroundRepository(@Qualifier("jdbc_bdplayground") JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;

	}

	public List<Map<String, Object>> retrieveDataFromDefaultDB(String query) {

		return jdbcTemplate.queryForList(query);

	}

	
	
	public List<Map<String, Object>> retrieveDataFromSpecificDatabase(String query, String databaseName) {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		String finalURL=bdIndividualPlaygroundURL.replace("SCHEMA_NAME",databaseName);
		//dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");		
		dataSource.setUrl(finalURL);
		dataSource.setUsername(bdIndividualPlaygroundUsername);
		dataSource.setPassword(bdIndividualPlaygroundPassword);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		return jdbcTemplate.queryForList(query);

	}

}
