#!/bin/bash
set +v echo off
if [ -e output/solutions.csv ]; then
      echo "DELETE OLD solutions.csv file & CREATE NEW solutions.csv"
      rm -rf output/solutions.csv
else
      echo "CREATE solutions.csv file"
fi

num_veicoli=0


echo -n "Enter the first number --> "
read num_veicoli


for WORD in `cat files_1.txt`
do
echo $WORD
java -jar solverVRP.jar -i $WORD -n $num_veicoli
done
