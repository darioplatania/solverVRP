#!/bin/bash
set +v echo off
if [ -e output/solutions.csv ]; then
      echo "DELETE OLD solutions.csv file & CREATE NEW solutions.csv"
      rm -rf output/solutions.csv
else
      echo "CREATE solutions.csv file"
fi

for WORD in `cat files_1.txt`
do
echo $WORD
java -jar solverVRP.jar -i $WORD -n 5
done
