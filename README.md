Software utilities for research on database learning

# Installation instructions

### Configure application.properties with the following parameters:
server.port=5000

app.datasource.bdadmin.url=

app.datasource.bdadmin.username=

app.datasource.bdadmin.password=

app.datasource.bdadmin.initial-size=

app.datasource.bdadmin.max-wait=

app.datasource.bdadmin.max-active=

app.datasource.bdadmin.max-idle=

app.datasource.bdadmin.min-idle=

app.datasource.bdadmin.default-auto-commit= 


app.datasource.bdplayground.url=

app.datasource.bdplayground.username=

app.datasource.bdplayground.password=

app.datasource.bdplayground.tomcat.initial-size=

app.datasource.bdplayground.tomcat.max-wait=

app.datasource.bdplayground.tomcat.max-active=

app.datasource.bdplayground.tomcat.max-idle=

app.datasource.bdplayground.tomcat.min-idle=

app.datasource.bdplayground.tomcat.default-auto-commit= 


app.datasource.bdindividualplayground.url=

app.datasource.bdindividualplayground.username=

app.datasource.bdindividualplayground.password=


### Compile and package with Maven
mvn package

### Launch from command line (target folder)
java -jar databaselearningresearch-0.1.0.jar

# Use instructions
This app provides a command-line interface with the following commands:

Built-In Commands
#####        clear: Clear the shell screen.
#####        exit, quit: Exit the shell.
#####        help: Display help about available commands.
#####        script: Read and execute commands from a file.
#####        stacktrace: Display the full stacktrace of the last error.

Data Integration Commands
#####        prepare-no-queries-table: Searching for meaningless queries (it is a very very long process...)
#####        extract-logs: Extracting queries from database logs (it is a long process...)
#####        check-logs: Checking logs regarding expecting solutions (it is a very long process...)
#####        match-logs: Matching logs with exercises (it is a very long process...)
#####        generate-data-mining-output: Generating CSV for analying logs with a data mining tool like Weka (very very short process...)
#####        generate-process-mining-output: Generating CSV for analying logs with a process mining tool like Disco (it is a very very short process...)

