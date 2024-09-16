package dbank.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dbank.tictactoe.Player.O;
import static dbank.tictactoe.Player.X;
import static org.assertj.core.api.Assertions.assertThat;

class GameEngineTest {

    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
    }

    @Test
    void getWinnerShouldReturnEmptyBoardIsStillEmpty() {
        var board = new Board();
        var winner = gameEngine.getWinner(board);
        assertThat(winner).isEmpty();
    }

    @Test
    void getWinnerShouldReturnEmptyBoardIsFullButNotWinner() {
        var board = new Board(
                new Player[][]{
                        new Player[]{O, O, X},
                        new Player[]{X, O, O},
                        new Player[]{O, X, X}
                });

        var winner = gameEngine.getWinner(board);

        assertThat(winner).isEmpty();
    }

    static Stream<Player[][]> customBoardWithoutEnoughPlaysToWinProvider() {
        return Stream.of(
                new Player[][]{
                        new Player[]{null, null, null},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                },
                new Player[][]{
                        new Player[]{X, null, null},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                },
                new Player[][]{
                        new Player[]{X, O, null},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                },
                new Player[][]{
                        new Player[]{X, O, X},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                },
                new Player[][]{
                        new Player[]{X, O, X},
                        new Player[]{O, null, null},
                        new Player[]{null, null, null}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("customBoardWithoutEnoughPlaysToWinProvider")
    void getWinnerShouldReturnEmptyWhenNotHaveEnoughPlays(Player[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner).isEmpty();
    }

    static Stream<Player[][]> boardWithHorizontalWinProvider() {
        return Stream.of(
                new Player[][]{
                        new Player[]{X, X, X},
                        new Player[]{null, null, null},
                        new Player[]{null, O, O}
                },
                new Player[][]{
                        new Player[]{O, O, X},
                        new Player[]{X, X, X},
                        new Player[]{O, O, null}
                },
                new Player[][]{
                        new Player[]{O, O, null},
                        new Player[]{X, O, O},
                        new Player[]{X, X, X}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithHorizontalWinProvider")
    void getWinnerShouldReturnWinnerWhenHorizontalWinner(Player[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner)
                .isNotEmpty()
                .get().isEqualTo(X);
    }

    static Stream<Player[][]> boardWithVerticalWinProvider() {
        return Stream.of(
                new Player[][]{
                        new Player[]{O, O, X},
                        new Player[]{O, X, O},
                        new Player[]{O, X, X}
                },
                new Player[][]{
                        new Player[]{O, O, X},
                        new Player[]{X, O, O},
                        new Player[]{O, O, X}
                },
                new Player[][]{
                        new Player[]{X, O, O},
                        new Player[]{X, X, O},
                        new Player[]{O, X, O}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithVerticalWinProvider")
    void getWinnerShouldReturnWinnerWhenVerticalWin(Player[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner)
                .isNotEmpty()
                .get().isEqualTo(O);
    }

    static Stream<Player[][]> boardWithParallelWinProvider() {
        return Stream.of(
                new Player[][]{
                        new Player[]{X, O, null},
                        new Player[]{O, X, null},
                        new Player[]{O, X, X}
                },
                new Player[][]{
                        new Player[]{O, O, X},
                        new Player[]{X, X, null},
                        new Player[]{X, O, null}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithParallelWinProvider")
    void getWinnerShouldReturnWinnerWhenParallelWin(Player[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner)
                .isNotEmpty()
                .get().isEqualTo(X);
    }
}
