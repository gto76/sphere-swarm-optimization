@echo off

SET mainClass=sso.tests.Tester
SET appName=sphere-swarm-optimizer

SET scriptName=%~n0

echo %scriptName%: COMPILING...
mkdir target\main
dir /s /B *.java > sources.txt
javac -d target\main @sources.txt
del sources.txt
cd target\

echo %scriptName%: PACKAGING IN JAR...
jar cfe %appName%.jar %mainClass% -C main .

echo %scriptName%: EXECUTING JAR...
java -jar %appName%.jar

cd ..