# Temple of Gloom - Group C

[![Java CI with Gradle](https://github.com/University-of-London/csm100-sdp-group-coursework-group-c/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/University-of-London/csm100-sdp-group-coursework-group-c/actions/workflows/gradle.yml)

## Introduction 

Our project is a game called Temple of Gloom, which involves two phases: exploration and escape. The Exploration phase focused on finding an orb in as little time as possible as it sets the multiplier for the next stage. Escape is broken up into two objectives, collect as much gold as possible, and always escape. The project itself is written in Java and challenged the team to utilise pathfinding algorithms effectively. To this end, we explored and experimented with a plethora of different algorithms and strategies to maximise gold collection and ensure a timely escape.

## Building and Running the Project

Gradle wrapper is recommended to build, test and run the project

```shell
# To build without running test
./gradlew build -x test

# To build and run tests
./gradlew build

# To run tests
./gradlew tests

# To run the project
./gradlew :temple:run -PchooseMain=main.TXTmain --args="-s <seed>"

# Example
./gradlew :temple:run -PchooseMain=main.TXTmain --args="-s 24"
```
## Leaderboard Comparison

Additionally, we have implemented a bash script to run the application
against the league table seeds which provides a clear comparison between scores
and the overall performance of the project.

```shell
# To run the shell script
bash league-table.sh
```

## Switching Strategies

The project supports dynamic loading of strategies for exploration and escape. 
This is configured via the strategies.properties file located in the resources folder. 
This approach allows for easy configuration and switching of strategies without requiring any code changes

The strategies.properties file is used to specify the strategies to be used for exploration and escape. 

Currently Available Strategies:

	•	Exploration Strategies:
	•	dfs: Depth-First Search
	•	Escape Strategies:
	•	dijkstra: Dijkstra’s Algorithm (High score of 70389)
	•	a*: A* Algorithm (Beats the leaderboard 15 times)

How to Switch Strategies:

	1.	Open the strategies.properties file located in the resources folder.
	2.	Edit the values for explore.strategy and escape.strategy to the desired strategy names.

Example:

Here is an example configuration using Depth-First Search for exploration and A* for escape:
```shell
active.exploration.strategy=dfs
active.escape.strategy=a*
```


## Contributors

* @Claire-Gao0930 - Ziyuan
* @Worldly-Disaster - Jonathan
* @alicedotfilm - Alice
* @juanpalopez - Juan

## Contributions

Direct roles and Contributions:

* Explore – DFS: Implemented by Jonathan
* Escape – Dijkstra: Implemented by Ziyuan
* Coin collecting algorithm: Implemented by Ziyuan
* Escape – Dijkstra: Additional research and experimentation by Alice
* Escape - A*: Implemented by Jonathan
* Escape – A*: Additional research and experimentation by Alice
* Refactoring of Escape classes – Jonathan and Juan
* Maintain constants and add professional Javadocs – Jonathan and Ziyuan
* ShortestPath Class: Utilised by A* and Dijkstra – Juan
* BaseEscapeStrategy Abstract class: Utilised by A* and Dijkstra - Jonathan
* Strategy interfaces – Conceptualised by Juan, implemented by Jonathan
* Singleton Factory design and implementation – Implemented by Jonathan
* Bash script for Leaderboard Comparison – Developed by Juan
* CI/CD Setup and Git Pre-hooks – Managed by Juan
* Dynamic loading of strategies – Implemented by Jonathan
* GameStateGenerator Class – Implemented by Juan
* DFS Explore Unit Testing – Implemented by Juan
* Refactoring of DFS Explore Unit Testing –Ziyuan
* ShortestPath Unit Test  – Implemented by Ziyuan
* A* Escape Unit Testing – Implemented by Alice and Ziyuan
* Dijkstra Escape Unit Testing – Implemented by Ziyuan
* Strategy Factory Unit Testing – Implemented by Jonathan
