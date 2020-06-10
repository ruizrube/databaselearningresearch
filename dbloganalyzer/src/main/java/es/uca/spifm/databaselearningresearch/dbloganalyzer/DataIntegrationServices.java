package es.uca.spifm.databaselearningresearch.dbloganalyzer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.Exercise;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentActivitySummaryData;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentEventData;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.domain.StudentLogRecord;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.generators.CSVGenerator;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.matchers.ExerciseChecker;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.matchers.ExerciseMatcher;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.ExerciseRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.repositories.StudentLogRecordRepository;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.transformers.ActivitySummaryDataTransformer;
import es.uca.spifm.databaselearningresearch.dbloganalyzer.transformers.EventDataTransformer;

@Service
public class DataIntegrationServices {
	@Autowired
	public StudentLogRecordRepository logRepository;

	@Autowired
	public ExerciseRepository exerciseRepository;

	@Autowired
	public ExerciseMatcher matcher;

	@Autowired
	public ExerciseChecker checker;

	@Autowired
	public EventDataTransformer eventTransformer;

	@Autowired
	public ActivitySummaryDataTransformer activitySummaryDataTransformer;

	@Autowired
	public CSVGenerator csvGenerator;

	public DataIntegrationServices(StudentLogRecordRepository logRepository,
			CSVGenerator<StudentEventData> csvGenerator) {
		super();
		this.logRepository = logRepository;
		this.csvGenerator = csvGenerator;
	}

	public void prepareNoQueriesTable() {
		System.out.println("Leyendo queries no significativas e insertando en tabla...");
		logRepository.fillNoQueriesTable();
		System.out.println("> Queries insertadas!");
	}

	public void prepareLogTable() {
		System.out.println("Truncando tabla de logs...");
		logRepository.truncateLogTable();
		System.out.println("> Tabla truncada");
		System.out.println("Leyendo logs e insertando en tabla...");
		logRepository.fillLogTable();
		System.out.println("> Logs insertados!");
	}

	public void matchExercisesWithLogs() {
		System.out.println("Cargando en memoria el conjunto de ejercicios...");
		List<Exercise> exerciseData = exerciseRepository.findAll();
		System.out.println("> Ejercicios cargados!");

		System.out.println("Cargando en memoria log de consultas lanzadas por alumnos...");
		List<StudentLogRecord> logData = logRepository.findAll();
		System.out.println("> Log de consultas cargado!");

		System.out.println("Matching de consultas lanzadas con los ejercicios");
		logData = matcher.matchLogWithExercises(logData, exerciseData);
		System.out.println("> Queries vinculadas a los ejercicios!");
	}

	public void checkExercisesWithLogs() {
		System.out.println("Cargando en memoria el conjunto de ejercicios...");
		List<Exercise> exerciseData = exerciseRepository.findAll();
		System.out.println("> Ejercicios cargados!");

		System.out.println("Cargando en memoria log de consultas lanzadas por alumnos...");
		List<StudentLogRecord> logData = logRepository.findAll();
		System.out.println("> Log de consultas cargado!");

		System.out.println("Comprobando la corrección de los ejercicios realizados");
		logData = checker.checkLogWithExercises(logData,exerciseData);
		System.out.println("> Queries vinculadas a los ejercicios!");

	}

	public void generateProcessMiningOutput() {

		System.out.println("Cargando en memoria el conjunto de ejercicios...");
		List<Exercise> exerciseData = exerciseRepository.findAll();
		System.out.println("> Ejercicios cargados!");

		System.out.println("Cargando log de consultas lanzadas por alumnos...");
		List<StudentLogRecord> logData = logRepository.findAll();
		System.out.println("> Log de consultas cargado!");

		System.out.println("Transformando log de consultas en flujo de eventos...");
		List<StudentEventData> eventData = eventTransformer.transformDataToEvents(logData, exerciseData);
		System.out.println("> Flujos de eventos obtenidos!");

		try {
			System.out.println("Generando fichero CSV para process mining...");
			csvGenerator.createCSVFile(eventData);
			System.out.println("> Fichero CSV generado!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void generateDataMiningOutput() {

		System.out.println("Cargando log de consultas lanzadas por alumnos...");
		List<StudentLogRecord> logData = logRepository.findAll();
		System.out.println("> Log de consultas cargado!");

		System.out.println("Transformando log de consultas en un conjunto de resúmenes de actividades...");
		List<StudentActivitySummaryData> activitySummaryData = activitySummaryDataTransformer
				.transformDataToActivitySummary(logData);
		System.out.println("> Resumen de actividades obtenidas!");

		try {
			System.out.println("Generando fichero CSV para data mining...");
			csvGenerator.createCSVFile(activitySummaryData);
			System.out.println("> Fichero CSV generado!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
