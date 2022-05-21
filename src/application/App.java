package application;

import boardLayer.Board;
import boardLayer.Position;
import chess.ChessMatch;

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, Chess!");
        //Board board = new Board(8, 8);

        ChessMatch chessMatch = new ChessMatch();

        //chessMatch.getPieces();

        UI.printBoard(chessMatch.getPieces());
    }
}