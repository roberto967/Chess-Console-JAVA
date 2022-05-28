package chess.pieces;

import boardLayer.Board;
import boardLayer.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

  public Pawn(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "P";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
    Position p = new Position(0, 0);

    // white
    if (getColor() == Color.WHITE) {
      p.setValues(position.getRow() - 1, position.getColumn());
      if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      Position p2 = new Position(position.getRow() - 1, position.getColumn()); // Caso esteja na posição inicial
      p.setValues(position.getRow() - 2, position.getColumn());
      if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && getBoard()
          .positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      p.setValues(position.getRow() - 1, position.getColumn() - 1); // Diagonal esquerda
      if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      p.setValues(position.getRow() - 1, position.getColumn() + 1); // Diagonal direita
      if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }
    }

    else {
      p.setValues(position.getRow() + 1, position.getColumn());
      if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      Position p2 = new Position(position.getRow() + 1, position.getColumn()); // Caso esteja na posição inicial
      p.setValues(position.getRow() + 2, position.getColumn());
      if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && getBoard()
          .positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      p.setValues(position.getRow() + 1, position.getColumn() - 1); // Diagonal esquerda
      if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }

      p.setValues(position.getRow() + 1, position.getColumn() + 1); // Diagonal direita
      if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
        mat[p.getRow()][p.getColumn()] = true;
      }
    }

    return mat;
  }
}
