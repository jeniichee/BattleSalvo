# BattleSalvo 

Collaborators: Jennifer Cheung, Marcus Feng <br>  
***Code is Available Upon Request***

## Introduction 

BattleSalvo is a command-line renedition in Java of the classic game Battleship, with a few twists to it. 

## Features

The game supports both a single-player mode against an AI opponent, and a multiplayer mode 
using sockets for comunication. Client to server communcication protocol implements JSON encoding. The game currently only supports the multiplayer mode based on the provided `Server.jar` file, and will further support other servers in future development. 

## The Twist

1. *Board Size* - board sizes are flexible with height and width dimensions ranging from 6 to 15. Users can input their desired board sized once prompted in the command-line.
2. *Ship Size* - there are a total of four types of ships in BattleSalvo: carrier (size 6), battleship (size 5), destroyer (size 4), and submarine (size 3).
3. *Fleet Size* - players can select the fleet size of the game when prompted. players must have a at least one of each boat type in their fleet, but the total fleet size cannot exceed the smaller board dimension.
4. *Shots* - the number of remaining shots reflects the number of remaining ships on the player's board.
5. *Shooting order* - players select all their shots simultaneously, and the shots are exchanged between players.

Hits and misses are determined and updated on the boards, and the process repeats until one or both players have no more surviving ships (a tie is a valid result). Visuals, prompts, and user-input are all conducted in the command-line. 

## Important Concepts

The project structure adheres to the Model-View-Controller (MVC) design pattern, and incorporates SOLID priniciples for clean and maintainable code. The Model of this project implementes the game logic, such as the board generation, ship generation, and gameplay rules. The View of this project handled the user interface and display of game information. The Controller acts as the client, and manages the flow of the game and handles user input. Additionally, a `ProxyController` class was introduced in order to handle the communication with the server in multiplayer mode and rely the information to the Controller. Accuracy of the code implementation is determined through thorough unit-testing using the JUnit5 testing framework, which secured a 90% Jacoco test coverage.
