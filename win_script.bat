@echo off
IF EXIST output\solutions.csv. (
echo  DELETE OLD solutions.csv file & CREATE NEW solutions.csv
del /Q output\solutions.csv.
) ELSE (
echo  CREATE solutions.csv file
)

set MYPROG=java -jar solverVRP.jar
set num_veicoli=0

echo -n "Enter the first number --> "
read num_veicoli

#lasciare files_1.txt, ma se non funziona provare con files.txt
for /F %%i in (files_1.txt) do (
	echo %MYPROG% -i %%i
	%MYPROG% -i %%i -n %num_veicoli%
)
