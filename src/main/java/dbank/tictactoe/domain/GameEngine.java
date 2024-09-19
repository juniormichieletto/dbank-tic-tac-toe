package dbank.tictactoe.domain;

import dbank.tictactoe.exception.GameOverException;
import dbank.tictactoe.exception.InvalidPlayerTurnException;
import dbank.tictactoe.exception.InvalidPositionException;
import dbank.tictactoe.model.Board;
import dbank.tictactoe.model.PlayPosition;
import dbank.tictactoe.model.Symbol;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

public class GameEngine {

    public Optional<Symbol> getWinner(Board board) {
        var grid = board.getGrid();

        return getWinnerFunction(i -> hasWinnerInRow(grid, i), i -> grid[i][0])
                .or(() -> getWinnerFunction(i -> hasWinnerInColumn(grid, i), i -> grid[0][i]))
                .or(() -> getWinnerInParallelFunction(grid));
    }

    private Optional<Symbol> getWinnerFunction(Predicate<Integer> winnerPredicate, Function<Integer, Symbol> mapToPlayer) {
        return IntStream.rangeClosed(0, 2)
                .boxed()
                .filter(winnerPredicate)
                .map(mapToPlayer)
                .findFirst();
    }

    public void play(Symbol symbol, Board board, PlayPosition play) throws Exception {
        requireNonNull(symbol);
        requireNonNull(board);
        requireNonNull(play);

        if (board.getLastPlayer() == symbol) {
            throw new InvalidPlayerTurnException();
        }
        if (isPositionOccupied(board, play)) {
            throw new InvalidPositionException();
        }
        if (getWinner(board).isPresent()) {
            throw new GameOverException();
        }

        board.getGrid()[play.row() - 1][play.column() - 1] = symbol;
        board.setLastPlayer(symbol);
    }

    private Optional<Symbol> getWinnerInParallelFunction(Symbol[][] grid) {
        if (hasWinnerInParallel(grid)) {
            return Optional.of(grid[0][0]);
        }
        if (hasWinnerInOppositeParallel(grid)) {
            return Optional.of(grid[0][2]);
        }
        return Optional.empty();
    }

    private static boolean hasWinnerInRow(Symbol[][] grid, int row) {
        return grid[row][0] != null && grid[row][0] == grid[row][1] && grid[row][1] == grid[row][2];
    }

    private boolean hasWinnerInColumn(Symbol[][] grid, int column) {
        return grid[0][column] != null && grid[0][column] == grid[1][column] && grid[1][column] == grid[2][column];
    }

    private boolean hasWinnerInParallel(Symbol[][] grid) {
        return grid[0][0] != null && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2];
    }

    private boolean hasWinnerInOppositeParallel(Symbol[][] grid) {
        return grid[0][2] != null && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0];
    }

    private boolean isPositionOccupied(Board board, PlayPosition play) {
        return board.getGrid()[play.row() - 1][play.column() - 1] != null;
    }
}
