package dbank.tictactoe;

public class Board {
    private Player[][] grid = new Player[3][3];

    public Board(Player[][] grid) {
        this.grid = grid;
    }

    public Board() {
    }

    public Player[][] getGrid() {
        return grid;
    }
}
