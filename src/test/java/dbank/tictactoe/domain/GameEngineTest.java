package dbank.tictactoe.domain;

import dbank.tictactoe.exception.GameOverException;
import dbank.tictactoe.exception.InvalidPlayerTurnException;
import dbank.tictactoe.exception.InvalidPositionException;
import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.PlayPosition;
import dbank.tictactoe.model.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dbank.tictactoe.model.Symbol.O;
import static dbank.tictactoe.model.Symbol.X;
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
                new Symbol[][]{
                        new Symbol[]{O, O, X},
                        new Symbol[]{X, O, O},
                        new Symbol[]{O, X, X}
                });

        var winner = gameEngine.getWinner(board);

        assertThat(winner).isEmpty();
    }

    static Stream<Symbol[][]> boardWithHorizontalWinProvider() {
        return Stream.of(
                new Symbol[][]{
                        new Symbol[]{X, X, X},
                        new Symbol[]{null, null, null},
                        new Symbol[]{null, O, O}
                },
                new Symbol[][]{
                        new Symbol[]{O, O, X},
                        new Symbol[]{X, X, X},
                        new Symbol[]{O, O, null}
                },
                new Symbol[][]{
                        new Symbol[]{O, O, null},
                        new Symbol[]{X, O, O},
                        new Symbol[]{X, X, X}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithHorizontalWinProvider")
    void getWinnerShouldReturnWinnerWhenHorizontalWinner(Symbol[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner)
                .isNotEmpty()
                .get().isEqualTo(X);
    }

    static Stream<Symbol[][]> boardWithVerticalWinProvider() {
        return Stream.of(
                new Symbol[][]{
                        new Symbol[]{O, O, X},
                        new Symbol[]{O, X, O},
                        new Symbol[]{O, X, X}
                },
                new Symbol[][]{
                        new Symbol[]{O, O, X},
                        new Symbol[]{X, O, O},
                        new Symbol[]{O, O, X}
                },
                new Symbol[][]{
                        new Symbol[]{X, O, O},
                        new Symbol[]{X, X, O},
                        new Symbol[]{O, X, O}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithVerticalWinProvider")
    void getWinnerShouldReturnWinnerWhenVerticalWin(Symbol[][] grid) {
        var board = new Board(grid);

        var winner = gameEngine.getWinner(board);

        assertThat(winner)
                .isNotEmpty()
                .get().isEqualTo(O);
    }

    static Stream<Symbol[][]> boardWithParallelWinProvider() {
        return Stream.of(
                new Symbol[][]{
                        new Symbol[]{X, O, null},
                        new Symbol[]{O, X, null},
                        new Symbol[]{O, X, X}
                },
                new Symbol[][]{
                        new Symbol[]{O, O, X},
                        new Symbol[]{X, X, null},
                        new Symbol[]{X, O, null}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("boardWithParallelWinProvider")
    void getWinnerShouldReturnWinnerWhenParallelWin(Symbol[][] grid) {
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
    void playShouldThrowsInvalidArgumentException(Symbol symbol, Board board, PlayPosition position) {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> gameEngine.play(symbol, board, position));
    }

    @Test
    void playShouldThrowsInvalidPositionExceptionWhenPositionIsOccupied() {
        var board = new Board(
                new Symbol[][]{
                        new Symbol[]{X, null, null},
                        new Symbol[]{null, null, O},
                        new Symbol[]{null, null, null}}
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
                new Symbol[][]{
                        new Symbol[]{X, null, null},
                        new Symbol[]{null, null, O},
                        new Symbol[]{null, null, null}
                }
        );
        var play = new PlayPosition(1, 2);

        gameEngine.play(X, board, play);

        assertThat(board.getGrid())
                .isEqualTo(
                        new Symbol[][]{
                                new Symbol[]{X, X, null},
                                new Symbol[]{null, null, O},
                                new Symbol[]{null, null, null}
                        });
    }

    @Test
    void playShouldThrowInvalidPlayerTurnException() throws Exception {
        var board = new Board(
                new Symbol[][]{
                        new Symbol[]{X, null, null},
                        new Symbol[]{null, null, O},
                        new Symbol[]{null, null, null}
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
                        new Symbol[][]{
                                new Symbol[]{X, O, X},
                                new Symbol[]{O, O, X},
                                new Symbol[]{X, X, O}
                        });
    }

    @Test
    void playShouldThrowGameOverExceptionWhenAPlayerHasWonAlready() throws Exception {
        var board = new Board(
                new Symbol[][]{
                        new Symbol[]{X, X, X},
                        new Symbol[]{O, null, O},
                        new Symbol[]{null, null, null}
                }
        );
        var firstPlay = new PlayPosition(2, 2);

        assertThatExceptionOfType(GameOverException.class)
                .isThrownBy(() -> gameEngine.play(O, board, firstPlay));
    }
}
