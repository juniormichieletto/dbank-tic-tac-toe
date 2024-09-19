## Game details

### Rules of Tic-Tac-Toe

- The game is played on a 3x3 grid.
- Two players take turns. One symbol is "X," and the other symbol is "O."
- Players place their marks (X or O) in empty squares.
- The first symbol to place three of their marks in a horizontal, vertical, or diagonal row wins the game.
- If all nine squares are filled without any symbol forming a line of three, the game ends in a draw.

### How to Play

- Choose who goes first: Decide who will be "X" and who will be "O."
- Take turns: Players alternate turns, placing their mark (X or O) on an empty square in the grid.
- Check for a winner: After each move, check if either symbol has three of their marks in a row, column, or diagonal.
- End the game: The game ends when a symbol wins by forming a line of three marks, or when all squares are filled, resulting in a draw.
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

- Add terminal based interface to actually play the game
- Migrate to java 23 or newest
