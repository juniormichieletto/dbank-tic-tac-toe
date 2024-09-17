# Tic-Tac-Toe Exercise

The purpose of this exercise is to enable our team and candidates to jointly discover if we are well suited to working together.
It is not intended as a test of technical ability but a look at the way you approach a problem.
Candidates should read our Engineering Culture pdf to understand how we work and what we view as important.

Please do not share this exercise with a wider audience.

## The Exercise

Given the rules of the game tic-tac-toe

1. Define a data model for the board and write a function that given an instance of a board either returns the winner
or an indication that no one has won.

For example, the signature of the function may look like:
```
(board) -> player?
```

Use whatever types make most sense to you in terms of inputs and outputs to the function.

---

## Game details

### Rules of Tic-Tac-Toe

- The game is played on a 3x3 grid.
- Two players take turns. One player is "X," and the other player is "O."
- Players place their marks (X or O) in empty squares.
- The first player to place three of their marks in a horizontal, vertical, or diagonal row wins the game.
- If all nine squares are filled without any player forming a line of three, the game ends in a draw.

### How to Play

- Choose who goes first: Decide who will be "X" and who will be "O."
- Take turns: Players alternate turns, placing their mark (X or O) on an empty square in the grid.
- Check for a winner: After each move, check if either player has three of their marks in a row, column, or diagonal.
- End the game: The game ends when a player wins by forming a line of three marks, or when all squares are filled, resulting in a draw.
- Reset (optional): If playing multiple rounds, clear the grid to start a new game.

---
## Tech details:

Requirements:
- Java 17+ installed
- Docker runtime (optional)

How to run all the tests in command line:

```bash
./mvnw clean test
```

or run the tests inside a docker container (non-local maven cache)
```bash
docker run --rm -v $(pwd):/app -w /app amazoncorretto:17-alpine ./mvnw test
```

or, using your local maven settings and repository (mapping your user/.m2)

```bash
docker run --rm -v $(pwd):/app -v ~/.m2/:/root/.m2/ -w /app amazoncorretto:17-alpine ./mvnw test
```

---

## TODO:

- Improve GameEngine::getWinner to have a dynamic code
- Add terminal based interface to actually play the game
- Migrate to java 23 or newest
