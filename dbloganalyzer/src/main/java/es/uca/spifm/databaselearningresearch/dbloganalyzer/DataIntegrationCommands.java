package es.uca.spifm.databaselearningresearch.dbloganalyzer;

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

	@ShellMethod("Searching for meaningless queries (it is a very very long process...)")
	public void prepareNoQueriesTable() {
		// invoke service
		service.prepareNoQueriesTable();
	}
	
	@ShellMethod("Extracting queries from database logs (it is a long process...)")
	public void extractLogs() {
		// invoke service
		service.prepareLogTable();
	}
	
	@ShellMethod("Matching logs with exercises (it is a very long process...)")
	public void matchLogs() {
		// invoke service
		service.matchExercisesWithLogs();
	}
	
	@ShellMethod("Checking logs regarding expecting solutions (it is a very long process...)")
	public void checkLogs() {
		// invoke service
		service.checkExercisesWithLogs();
	}
	
	@ShellMethod("Generating CSV for analying logs with a process mining tool like Disco (it is a very very short process...)")
	public void generateProcessMiningOutput() {
		// invoke service
		service.generateProcessMiningOutput();
	}
	
	@ShellMethod("Generating CSV for analying logs with a data mining tool like Weka (very very short process...)")
	public void generateDataMiningOutput() {
		// invoke service
		service.generateDataMiningOutput();
	}
}