package dbank.tictactoe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dbank.tictactoe.Player.O;
import static dbank.tictactoe.Player.X;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void getRemainPlaysShouldReturnMaxWhenCreateEmptyBoard() {
        var board = new Board();
        assertThat(board.getRemainPlays()).isEqualTo(9);
    }

    @ParameterizedTest
    @MethodSource("customBoardProvider")
    void getRemainPlaysShouldReturnBasedOnBoardCreation(long expectedRemainPlays, Player[] row0, Player[] row1, Player[] row2) {
        Player[][] grid = new Player[3][3];
        grid[0] = row0;
        grid[1] = row1;
        grid[2] = row2;

        var board = new Board(grid);

        assertThat(board.getRemainPlays())
                .isEqualTo(expectedRemainPlays);
    }

    public static Stream<Arguments> customBoardProvider() {
        return Stream.of(
                Arguments.of(8,
                        new Player[]{X, null, null},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                ),
                Arguments.of(7,
                        new Player[]{X, O, null},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                ),
                Arguments.of(6,
                        new Player[]{X, O, X},
                        new Player[]{null, null, null},
                        new Player[]{null, null, null}
                ),
                Arguments.of(5,
                        new Player[]{X, O, X},
                        new Player[]{O, null, null},
                        new Player[]{null, null, null}
                ),
                Arguments.of(4,
                        new Player[]{X, O, X},
                        new Player[]{O, O, null},
                        new Player[]{null, null, null}
                ),
                Arguments.of(3,
                        new Player[]{X, O, X},
                        new Player[]{O, O, X},
                        new Player[]{null, null, null}
                ),
                Arguments.of(2,
                        new Player[]{X, O, X},
                        new Player[]{O, O, X},
                        new Player[]{X, null, null}
                ),
                Arguments.of(1,
                        new Player[]{X, O, X},
                        new Player[]{O, O, X},
                        new Player[]{X, X, null}
                ),
                Arguments.of(0,
                        new Player[]{X, O, X},
                        new Player[]{O, O, X},
                        new Player[]{X, X, O}
                )
        );
    }
}