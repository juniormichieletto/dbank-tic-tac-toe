package dbank.tictactoe;

public class Board {
    private Player[][] grid = new Player[3][3];
    private long remainPlays = 9;

    public Board(Player[][] grid) {
        this.grid = grid;
        calculateRemainPlays(grid);
    }

    public Board() {
    }

    public long getRemainPlays() {
        return remainPlays;
    }

    private void calculateRemainPlays(Player[][] grid) {
        for (Player[] plays : grid) {
            for (Player playerPLay : plays) {
                if (playerPLay != null) {
                    remainPlays--;
                }
            }
        }
    }

    public Player[][] getGrid() {
        return grid;
    }
}
