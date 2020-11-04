package es.uca.spifm.databaselearningresearch.dbloganalyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DataIntegrationCommands {

	private final DataIntegrationServices service;

	@Autowired
	public DataIntegrationCommands(DataIntegrationServices service) {
		this.service = service;
	}

	// pipeline.prepareNoQueriesTable();
	// pipeline.prepareLogTable();
	// pipeline.matchExercisesWithLogs();
	// pipeline.checkExercisesWithLogs();
	// pipeline.generateProcessMiningOutput();
	// pipeline.generateDataMiningOutput();

	@ShellMethod("Step 0: Searching for meaningless queries (it is a very very long process...)")
	public void prepareNoQueriesTable() {

		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.prepareNoQueriesTable();

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}

	@ShellMethod("Step 1: Extracting queries from database logs (it is a long process...)")
	public void extractLogs() {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.deleteLogs();
		service.extractLogs();
		
		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}

	@ShellMethod("Step 2: Matching logs with exercises (it is a very long process...)")
	public void matchLogs() {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.matchExercisesWithLogs();

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}

	
	@ShellMethod("Step 2a: Matching logs of a given user with exercises (it is a very long process...)")
	public void rematchLogsOfUser(String userId) {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.matchExercisesWithLogsOfUser(userId);

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}
	
	
	
	@ShellMethod("Step 3: Checking logs regarding expecting solutions (it is a long process...)")
	public void checkLogs() {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.checkExercisesWithLogs();

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}
	
	
	
	
	

	@ShellMethod("Step 4a: Generating CSV for analying logs with a process mining tool like Disco (it is a very very short process...)")
	public void generateProcessMiningOutput() {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.generateProcessMiningOutput();

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}

	@ShellMethod("Step 4b: Generating CSV for analying logs with a data mining tool like Weka (very very short process...)")
	public void generateDataMiningOutput() {
		LocalDateTime before = LocalDateTime.now();

		// invoke service
		service.generateDataMiningOutput();

		System.out.println("Operación lanzada a las " + before.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		System.out.println(
				"Operacion finalizada a las " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

	}
}