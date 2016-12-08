@echo off
if "%1"=="-r" goto Rebuild
if "%1"=="" goto Run 
:Rebuild
call mvn clean install compile assembly:single
del .\thescene-console-ui.jar
copy .\target\consoleui-0.0.1-SNAPSHOT-jar-with-dependencies.jar .\thescene-console-ui.jar

:Run
if exist thescene-console-ui.jar (
	echo [INFO] Running thescene-console-ui.jar
	echo [INFO] Support live music
	echo [INFO] If the source has changed a rebuild will be necessary. 
	echo [INFO] Type 'run -r' to rebuild. 
	java -jar thescene-console-ui.jar) else goto Rebuild
)

