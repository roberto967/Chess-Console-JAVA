package boardLayer;

public class BoardException extends RuntimeException{
  private static final long serialVersioUID = 1L;

  public BoardException (String msg){
    super(msg);
  }
}
