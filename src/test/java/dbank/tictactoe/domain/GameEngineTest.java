package dbank.tictactoe.domain;

import dbank.tictactoe.exception.GameOverException;
import dbank.tictactoe.exception.InvalidPlayerTurnException;
import dbank.tictactoe.exception.InvalidPositionException;
import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.PlayPosition;
import dbank.tictactoe.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dbank.tictactoe.model.Player.O;
import static dbank.tictactoe.model.Player.X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

    static Stream<Arguments> IllegalArgumentExceptionProvider() {
        return Stream.of(
                Arguments.of(null, new Board(), new PlayPosition(0, 0)),
                Arguments.of(X, null, new PlayPosition(0, 0)),
                Arguments.of(X, new Board(), null)
        );
    }

    @ParameterizedTest
    @MethodSource("IllegalArgumentExceptionProvider")
    void playShouldThrowsInvalidArgumentException(Player player, Board board, PlayPosition position) {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> gameEngine.play(player, board, position));
    }

    @Test
    void playShouldThrowsInvalidPositionExceptionWhenPositionIsOccupied() {
        var board = new Board(
                new Player[][]{
                        new Player[]{X, null, null},
                        new Player[]{null, null, O},
                        new Player[]{null, null, null}}
        );
        var firstTry = new PlayPosition(1, 1);
        var secondTry = new PlayPosition(2, 3);

        assertThatExceptionOfType(InvalidPositionException.class)
                .isThrownBy(() -> gameEngine.play(X, board, firstTry));

        assertThatExceptionOfType(InvalidPositionException.class)
                .isThrownBy(() -> gameEngine.play(X, board, secondTry));
    }

    @Test
    void playShouldAddNewPositionInTheBoard() throws Exception {
        var board = new Board(
                new Player[][]{
                        new Player[]{X, null, null},
                        new Player[]{null, null, O},
                        new Player[]{null, null, null}
                }
        );
        var play = new PlayPosition(1, 2);

        gameEngine.play(X, board, play);

        assertThat(board.getGrid())
                .isEqualTo(
                        new Player[][]{
                                new Player[]{X, X, null},
                                new Player[]{null, null, O},
                                new Player[]{null, null, null}
                        });
    }

    @Test
    void playShouldThrowInvalidPlayerTurnException() throws Exception {
        var board = new Board(
                new Player[][]{
                        new Player[]{X, null, null},
                        new Player[]{null, null, O},
                        new Player[]{null, null, null}
                }
        );
        var firstPlay = new PlayPosition(1, 2);
        var secondTry = new PlayPosition(1, 3);

        gameEngine.play(X, board, firstPlay);

        assertThatExceptionOfType(InvalidPlayerTurnException.class)
                .isThrownBy(() -> gameEngine.play(X, board, secondTry));
    }

    @Test
    void playEntireGameWithoutWinner() throws Exception {
        var board = new Board();

        gameEngine.play(X, board, new PlayPosition(1, 1));
        gameEngine.play(O, board, new PlayPosition(1, 2));
        gameEngine.play(X, board, new PlayPosition(1, 3));
        gameEngine.play(O, board, new PlayPosition(2, 1));
        gameEngine.play(X, board, new PlayPosition(2, 3));
        gameEngine.play(O, board, new PlayPosition(2, 2));
        gameEngine.play(X, board, new PlayPosition(3, 1));
        gameEngine.play(O, board, new PlayPosition(3, 3));
        gameEngine.play(X, board, new PlayPosition(3, 2));

        assertThat(gameEngine.getWinner(board))
                .isEmpty();

        assertThat(board.getGrid())
                .isEqualTo(
                        new Player[][]{
                                new Player[]{X, O, X},
                                new Player[]{O, O, X},
                                new Player[]{X, X, O}
                        });
    }

    @Test
    void playShouldThrowGameOverExceptionWhenAPlayerHasWonAlready() throws Exception {
        var board = new Board(
                new Player[][]{
                        new Player[]{X, X, X},
                        new Player[]{O, null, O},
                        new Player[]{null, null, null}
                }
        );
        var firstPlay = new PlayPosition(2, 2);

        assertThatExceptionOfType(GameOverException.class)
                .isThrownBy(() -> gameEngine.play(O, board, firstPlay));
    }
}
