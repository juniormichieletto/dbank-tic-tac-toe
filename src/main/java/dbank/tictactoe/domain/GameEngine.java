package dbank.tictactoe.domain;

import dbank.tictactoe.exception.GameOverException;
import dbank.tictactoe.exception.InvalidPlayerTurnException;
import dbank.tictactoe.exception.InvalidPositionException;
import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.PlayPosition;
import dbank.tictactoe.model.Player;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;

public class GameEngine {

    public Optional<Player> getWinner(Board board) {
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
        if (hasWinnerInOppositeParallel(grid)) {
            return Optional.of(grid[0][2]);
        }
        return empty();
    }

    public void play(Player player, Board board, PlayPosition play) throws Exception {
        requireNonNull(player);
        requireNonNull(board);
        requireNonNull(play);

        if (board.getLastPlayer() == player) {
            throw new InvalidPlayerTurnException();
        }
        if (isPositionOccupied(board, play)) {
            throw new InvalidPositionException();
        }
        if (getWinner(board).isPresent()) {
            throw new GameOverException();
        }

        board.getGrid()[play.row() - 1][play.column() - 1] = player;
        board.setLastPlayer(player);
    }

    private static boolean hasWinnerInRow(Player[][] grid, int row) {
        return grid[row][0] != null && grid[row][0] == grid[row][1] && grid[row][1] == grid[row][2];
    }

    private boolean hasWinnerInColumn(Player[][] grid, int column) {
        return grid[0][column] != null && grid[0][column] == grid[1][column] && grid[1][column] == grid[2][column];
    }

    private boolean hasWinnerInParallel(Player[][] grid) {
        return grid[0][0] != null && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2];
    }

    private boolean hasWinnerInOppositeParallel(Player[][] grid) {
        return grid[0][2] != null && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0];
    }

    private boolean isPositionOccupied(Board board, PlayPosition play) {
        return board.getGrid()[play.row() - 1][play.column() - 1] != null;
    }
}
