package dbank.tictactoe.domain;

import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.Symbol;
import org.junit.jupiter.api.Test;

import static dbank.tictactoe.model.Symbol.O;
import static dbank.tictactoe.model.Symbol.X;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void newBoardShouldCreateWithEmptyGrid() {
        Symbol[][] emptyGrid = {
                new Symbol[]{null, null, null},
                new Symbol[]{null, null, null},
                new Symbol[]{null, null, null}
        };

        var board = new Board();

        assertThat(board.getGrid()).isEqualTo(emptyGrid);
    }

    @Test
    void newCustomBoardShouldCreateWithCustomGrid() {
        Symbol[][] expectedGrid = {
                new Symbol[]{X, O, null},
                new Symbol[]{O, X, null},
                new Symbol[]{O, X, X}
        };

        var board = new Board(expectedGrid);

        assertThat(board.getGrid()).isEqualTo(expectedGrid);
    }
}