package chess;

import boardLayer.Board;
import boardLayer.Piece;
import boardLayer.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
  private Board board;

  public ChessMatch() {
    board = new Board(8, 8);
    inicialSetup();
  }

  public ChessPiece[][] getPieces() {
    ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getColumns(); j++) {
        mat[i][j] = (ChessPiece) board.piece(i, j);
      }
    }

    return mat;
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    board.placePiece(piece, new ChessPosition(column, row).toPosition());
  }

  private void inicialSetup() {
    placeNewPiece('A', 8, new Rook(board, Color.BLACK));
    placeNewPiece('H', 8, new Rook(board, Color.BLACK));
    placeNewPiece('D', 8, new King(board, Color.BLACK));

    placeNewPiece('A', 1, new Rook(board, Color.WHITE));
    placeNewPiece('H', 1, new Rook(board, Color.WHITE));
    placeNewPiece('D', 1, new King(board, Color.WHITE));
  }

  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();

    validateSourcePosition(source);

    Piece capturedPiece = makeMove(source, target);

    return (ChessPiece) capturedPiece;
  }

  private void validateSourcePosition(Position position) {
    if(!board.thereIsAPiece(position)){
      throw new ChessExeption("Não há peça no local de origem.");
    }
    
    if(!board.piece(position).isThereAnyPossibleMove()){
      throw new ChessExeption("Não há movimentos possiveis para a peça escolhida.");
    }
  }

  private Piece makeMove(Position source, Position target) {
    Piece p = board.removePiece(source);

    Piece capturedPiece = board.removePiece(target);

    board.placePiece(p, target);

    return capturedPiece;
  }
}
