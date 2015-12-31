@echo off
IF EXIST output\solutions.csv. (
echo  DELETE OLD solutions.csv file & CREATE NEW solutions.csv
del /Q output\solutions.csv.
) ELSE (
echo  CREATE solutions.csv file
)

set MYPROG=java -jar solverVRP.jar

#lasciare files_1.txt, ma se non funziona provare se non funziona con files.txt
for /F %%i in (files_1.txt) do (
	echo %MYPROG% -i %%i
	%MYPROG% -i %%i -n 5
)
