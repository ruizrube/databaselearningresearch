package es.uca.spifm.databaselearningresearch.dbloganalyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.bdadmin")
	public DataSourceProperties adminDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.bdadmin.configuration")
	public HikariDataSource adminDataSource() {
		return adminDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean("jdbc_bdadmin")
	@Primary
	public JdbcTemplate adminJdbcTemplate() {
		return new JdbcTemplate(adminDataSource());
	}

	
	@Bean
	@ConfigurationProperties("app.datasource.bdplayground")
	public DataSourceProperties playgroundDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("app.datasource.bdplayground.configuration")
	public HikariDataSource playgroundDataSource() {
		return playgroundDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean("jdbc_bdplayground")
	@Primary
	public JdbcTemplate playgroundJdbcTemplate() {
		return new JdbcTemplate(playgroundDataSource());
	}

	@Autowired
	DataIntegrationServices pipeline;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);

	}

	@Override
	public void run(String... arg0) throws Exception {

		LocalDateTime before = LocalDateTime.now();

		System.out.println("Arrancando pipeline..");

		//pipeline.prepareNoQueriesTable();
		//pipeline.prepareLogTable();
		//pipeline.matchExercisesWithLogs();
		//pipeline.checkExercisesWithLogs();
		//pipeline.generateProcessMiningOutput();
		//pipeline.generateDataMiningOutput();

		System.out.println("Pipeline arrancado a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Deteniendo pipeline a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}
}
