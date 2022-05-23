package chess;

public class ChessExeption extends RuntimeException{
  private static final long serialVersioUID = 1L;

  public ChessExeption (String msg){
    super(msg);
  }
}
