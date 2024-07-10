#!/bin/bash

# Function to run the app
function run_app() {
    local seed=$1
    java -cp "./temple/build/classes/java/main:./temple/build/resources/main" main.TXTmain -s $seed
}

# Function to extract the score from the output
extract_score() {
    local output=$1
    echo "$output" | grep Score | grep -o '[0-9]*' | sed 's/^0*//'
}

curDir=$(pwd)
max_score=0
max_seed=0
win_count=0

echo
echo "Building latest changes"
echo "===================="
./gradlew build
echo

echo
echo "Running comparisons"
echo "===================="
echo

# Read the seeds from the CSV file and run the command for each seed
while IFS=',' read -r seed gold_collected bonus_multiplier table_score; do
    if [[ $seed != "Seed" ]]; then
      output=$(run_app $seed)
      score=$(extract_score "$output")

      # Check for the winner
      winner="Table"
      if [[ $score > $table_score ]]; then
        winner="Current Implementation"
        ((win_count++))
      fi

      # Print results by seed
      echo "Seed: $seed - Current Score: $score - Table Score: $table_score"
      echo "Winner: $winner"
      echo " ------ "

      # Keep track of the max score
      if ((score > max_score)); then
          max_score=$score
          max_seed=$seed
      fi
    fi
done < "$curDir/league_table.csv"

# Print results
echo
echo "Current Implementation  - Max score: $max_score - Seed used: $max_seed"
echo "Total winning count: $win_count"