package chess;

import boardLayer.BoardException;

public class ChessExeption extends BoardException {
  private static final long serialVersioUID = 1L;

  public ChessExeption(String msg) {
    super(msg);
  }
}
