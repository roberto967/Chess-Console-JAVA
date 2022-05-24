package chess.pieces;

import boardLayer.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

  public Rook(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "R";
  }

  @Override
  public boolean[][] possibleMoves() { //generico
    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getRows()];
    return mat;
  }
}
