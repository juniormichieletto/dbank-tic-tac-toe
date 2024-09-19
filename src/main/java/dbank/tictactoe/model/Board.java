package dbank.tictactoe.model;

public class Board {
    private Symbol[][] grid = new Symbol[3][3];
    private Symbol lastSymbol;

    public Board(Symbol[][] grid) {
        this.grid = grid;
    }

    public Board() {
    }

    public Symbol[][] getGrid() {
        return grid;
    }

    public Symbol getLastPlayer() {
        return lastSymbol;
    }

    public void setLastPlayer(Symbol symbol) {
        this.lastSymbol = symbol;
    }
}
