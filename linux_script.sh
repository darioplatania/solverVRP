#!/bin/bash
set +v echo off
if [ -e output/solutions.csv ]; then
      echo "DELETE OLD solutions.csv file & CREATE NEW solutions.csv"
      rm -rf output/solutions.csv
else
      echo "CREATE solutions.csv file"
fi

num_veicoli=0


    read -p "Vuoi inserire il numero di veicoli y/n?" yn
    case $yn in
        [Yy]* )
            echo -n "Quanti veicoli vuoi inserire?"
            read num_veicoli
            for WORD in `cat files_1.txt`
            do
            echo $WORD
            java -jar solverVRP.jar -i $WORD -n $num_veicoli
            done;;
        [Nn]* )
            for WORD in `cat files_1.txt`
            do
            echo $WORD
            java -jar solverVRP.jar -i $WORD
            done;;
        * ) echo "Please answer yes or no.";;
    esac
