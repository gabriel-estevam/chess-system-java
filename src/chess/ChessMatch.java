package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//Esse classe vai ficar armazenado as regras do jogo, aqui � onde tudo funciona
public class ChessMatch 
{
	private Board board;
	
	public ChessMatch() 
	{
		//Assim que instanciado ja define um tabuleiro 8x8
		board = new Board(8, 8);
		initialSetup();
	}

	
	public ChessPiece[][] getPieces() 
	{

		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i=0; i<board.getRows(); i++) 
		{

			for (int j=0; j<board.getColumns(); j++) 
			{
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	private void placeNewPiece(char column, int row, ChessPiece piece)
	{
		//Aqui temos uma opera��o de inser��o de uma nova pe�a de xadrez no tabuleiro
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
	}
	private void initialSetup()
	{
		//Fun��o para inicializar uma partida de xadrez
		
		/*Esse seria o jeito certo de inserir uma pe�a no tabuleiro, pois se observarmos o
		 * tabuleiro ele tem duas coordenas que mostra o exato luga da pe�a
		 * a coordenada 1 a 8 - lateral esquerda;
		 * a coordenada 'a' a 'h' que esta na parte inferior do tabuleiro.
		 * Desse jeito fica mais facil de entender a joga, e faz mais sentido para essa camada chess
		 * Oberserve o codigo anterior: 
		 * board.placePiece(new Rook(board,Color.WHITE), new Position(2,1)); - nesse codigo temos teriamos que
		 * instanciar uma pe�a de xadrez e instanciar uma posi��o ja informando o local sem usar as coordenadas do tabuleiro
		 * errado n�o esta, poderia ser tambem*/
		
		placeNewPiece('b', 6, new Rook(board,Color.WHITE));
		placeNewPiece('e', 8, new King(board,Color.BLACK));
		placeNewPiece('e', 1, new King(board,Color.WHITE));
	}
}
