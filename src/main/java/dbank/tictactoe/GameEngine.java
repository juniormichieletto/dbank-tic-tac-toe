package dbank.tictactoe;

import java.util.Optional;

import static java.util.Optional.empty;

public class GameEngine {

    public Optional<Player> getWinner(Board board) {
        if (board.getRemainPlays() >= 5) {
            return empty();
        }
        return getPlayerWinner(board);
    }

    private Optional<Player> getPlayerWinner(Board board) {
        var grid = board.getGrid();

        if (hasWinnerInRow(grid, 0)) {
            return Optional.of(grid[0][0]);
        }
        if (hasWinnerInRow(grid, 1)) {
            return Optional.of(grid[1][0]);
        }
        if (hasWinnerInRow(grid, 2)) {
            return Optional.of(grid[2][0]);
        }
        if (hasWinnerInColumn(grid, 0)) {
            return Optional.of(grid[0][0]);
        }
        if (hasWinnerInColumn(grid, 1)) {
            return Optional.of(grid[0][1]);
        }
        if (hasWinnerInColumn(grid, 2)) {
            return Optional.of(grid[0][2]);
        }
        if (hasWinnerInParallel(grid)) {
            return Optional.of(grid[0][0]);
        }
        if (hasWinnerInOppositeeParallel(grid)) {
            return Optional.of(grid[0][2]);
        }
        return empty();
    }

    private static boolean hasWinnerInRow(Player[][] grid, int row) {
        return grid[row][0] == grid[row][1] && grid[row][1] == grid[row][2];
    }

    private static boolean hasWinnerInColumn(Player[][] grid, int column) {
        return grid[0][column] == grid[1][column] && grid[1][column] == grid[2][column];
    }

    private static boolean hasWinnerInParallel(Player[][] grid) {
        return grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2];
    }

    private static boolean hasWinnerInOppositeeParallel(Player[][] grid) {
        return grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0];
    }
}
