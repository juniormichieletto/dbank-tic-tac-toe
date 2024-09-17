package dbank.tictactoe.domain;

import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.Player;
import org.junit.jupiter.api.Test;

import static dbank.tictactoe.model.Player.O;
import static dbank.tictactoe.model.Player.X;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void newBoardShouldCreateWithEmptyGrid() {
        Player[][] emptyGrid = {
                new Player[]{null, null, null},
                new Player[]{null, null, null},
                new Player[]{null, null, null}
        };

        var board = new Board();

        assertThat(board.getGrid()).isEqualTo(emptyGrid);
    }

    @Test
    void newCustomBoardShouldCreateWithCustomGrid() {
        Player[][] expectedGrid = {
                new Player[]{X, O, null},
                new Player[]{O, X, null},
                new Player[]{O, X, X}
        };

        var board = new Board(expectedGrid);

        assertThat(board.getGrid()).isEqualTo(expectedGrid);
    }
}