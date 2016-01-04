@echo off

del /Q output\solutions.csv

set MYPROG=java -jar solverVRP.jar
set num_veicoli=0


set /P yn=Vuoi inserire il numero di veicoli? [y/n]?:
echo hai inserito %yn%

IF "%yn%"=="y" (
	set /P num_veicoli=Inserisci il numero di veicoli:

)

IF "%yn%"=="y" (
	echo hai inserito %num%
	for /F %%i in (files.txt) do (
		echo %MYPROG% -i %%i -n %num_veicoli%
		%MYPROG% -i %%i -n %num_veicoli%
		

	)

)


IF "%yn%" == "n" (
	for /F %%i in (files.txt) do (
		echo %MYPROG% -i %%i
		%MYPROG% -i %%i
	)

)


pause