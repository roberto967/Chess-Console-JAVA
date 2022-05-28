package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import boardLayer.Board;
import boardLayer.Piece;
import boardLayer.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
  private Board board;
  private Color currentPlayer;
  private int turn;
  private boolean check;
  private boolean checkMate;

  private List<Piece> piecesOnTheBoard = new ArrayList<>();
  private List<Piece> capturedPieces = new ArrayList<>();

  public ChessMatch() {
    board = new Board(8, 8);
    turn = 1;
    currentPlayer = Color.WHITE;
    check = false;
    inicialSetup();
  }

  public int getTurn() {
    return turn;
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  public boolean getCheckMate() {
    return checkMate;
  }

  public boolean getCheck() {
    return check;
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
    piecesOnTheBoard.add(piece);
  }

  private void inicialSetup() {
    placeNewPiece('A', 8, new Rook(board, Color.BLACK));
    placeNewPiece('H', 8, new Rook(board, Color.BLACK));
    placeNewPiece('D', 8, new King(board, Color.BLACK));

    // placeNewPiece('A', 1, new Rook(board, Color.WHITE));
    placeNewPiece('H', 1, new Rook(board, Color.WHITE));
    placeNewPiece('A', 1, new King(board, Color.WHITE));
  }

  public boolean[][] possibleMoves(ChessPosition sourcePosition) {
    Position position = sourcePosition.toPosition();
    validateSourcePosition(position);

    return board.piece(position).possibleMoves();
  }

  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();

    validateSourcePosition(source);
    validateSourcePosition(source, target);

    Piece capturedPiece = makeMove(source, target);

    if (testCheck(currentPlayer)) {
      undoMove(source, target, capturedPiece);

      throw new ChessExeption("Você não pode se por em CHECK.");
    }

    check = (testCheck(opponet(currentPlayer))) ? true : false;

    if (testCheckMate(opponet(currentPlayer))) {
      checkMate = true;
    } else {
      nextTurn();
    }

    return (ChessPiece) capturedPiece;
  }

  private void validateSourcePosition(Position source, Position target) {
    if (!board.piece(source).possibleMove(target)) {
      throw new ChessExeption("A peça escolhida não pode ser movida para a posição de destino");
    }
  }

  private void validateSourcePosition(Position position) {
    if (!board.thereIsAPiece(position)) {
      throw new ChessExeption("Não há peça no local de origem.");
    }

    if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
      throw new ChessExeption("A peça escolhida não corresponde a cor de jogada atual.");
    }

    if (!board.piece(position).isThereAnyPossibleMove()) {
      throw new ChessExeption("Não há movimentos possiveis para a peça escolhida.");
    }
  }

  private boolean testCheck(Color color) {
    Position kingpPosition = king(color).getChessPosition().toPosition();
    List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponet(color))
        .collect(Collectors.toList());

    for (Piece p : opponentPieces) {
      boolean[][] mat = p.possibleMoves();

      if (mat[kingpPosition.getRow()][kingpPosition.getColumn()]) {
        return true;
      }
    }

    return false;
  }

  private boolean testCheckMate(Color color) {
    if (!testCheck(color)) {
      return false;
    }

    List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
        .collect(Collectors.toList());

    for (Piece p : list) {
      boolean[][] mat = p.possibleMoves();

      for (int i = 0; i < board.getRows(); i++) {
        for (int j = 0; j < board.getColumns(); j++) {
          if (mat[i][j]) {
            Position source = ((ChessPiece) p).getChessPosition().toPosition();

            Position target = new Position(i, j);

            Piece capturedPiece = makeMove(source, target);

            boolean testCheck = testCheck(color);
            undoMove(source, target, capturedPiece);
            if (!testCheck) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private Piece makeMove(Position source, Position target) {
    ChessPiece p = (ChessPiece)board.removePiece(source);
    p.increaseMoveCount();

    Piece capturedPiece = board.removePiece(target);

    board.placePiece(p, target);

    if (capturedPiece != null) {
      piecesOnTheBoard.remove(capturedPiece);
      capturedPieces.add(capturedPiece);
    }

    return capturedPiece;
  }

  private void undoMove(Position source, Position target, Piece capturedPiece) {
    ChessPiece p = (ChessPiece)board.removePiece(target);
    board.placePiece(p, source);
    p.decreaseMoveCount();

    if (capturedPiece != null) {
      board.placePiece(capturedPiece, target);

      capturedPieces.remove(capturedPiece);
      piecesOnTheBoard.add(capturedPiece);
    }
  }

  private void nextTurn() {
    turn++;
    currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  private Color opponet(Color color) {
    return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  private ChessPiece king(Color color) {
    List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
        .collect(Collectors.toList());

    for (Piece p : list) {
      if (p instanceof King) {
        return (ChessPiece) p;
      }
    }

    String s = (color == Color.WHITE) ? "BRANCO" : "PRETO";

    throw new IllegalStateException("Não há rei da cor" + s);
  }
}
