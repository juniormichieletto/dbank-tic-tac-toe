package dbank.tictactoe.model;

public class Board {
    private Player[][] grid = new Player[3][3];
    private Player lastPlayer;

    public Board(Player[][] grid) {
        this.grid = grid;
    }

    public Board() {
    }

    public Player[][] getGrid() {
        return grid;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(Player player) {
        this.lastPlayer = player;
    }
}
