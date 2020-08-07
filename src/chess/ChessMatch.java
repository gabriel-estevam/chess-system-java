package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//Esse classe vai ficar armazenado as regras do jogo, aqui � onde tudo funciona
public class ChessMatch 
{
	private Board board;
	private int turn;
	private Color currentPlayer;
	
	public ChessMatch() 
	{
		//Assim que instanciado ja define um tabuleiro 8x8
		board = new Board(8, 8);
		//Assim que iniciado come�a no turno 1 e com o jogador branco
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	//Criado dois metodos get, para retorna o turno e o jogador atual
	public int getTurn()
	{
		return turn;
	}
	public Color getCurrentPlayer()
	{
		return currentPlayer;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) 
	{
		/*Opera��o que vai retornar os movimentos possiveis para uma determinada pe�a*/
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMove();
	}
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) 
	{
		//Opera��o para mover uma pe�a, nesse momento n�o � a o metodo de captura de pe�a
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); //Valida��o da posi��o de origem
		validateTargerPosition(source,target); //Valida��o da posi��o de destino
		Piece capturedPiece = makeMove(source, target); //faz o movimento da pe�a
		nextTurn(); //troca de turno dos jogadores
		return (ChessPiece)capturedPiece;
	}

	private Piece makeMove(Position source, Position target)
	{
		//Opera��o para fazer um movimento de pe�a
		/*Para fazer o movimento de uma pe�a ela antes de mudar de lugar de que sair da sua origem
		  Que � o que acontece com a linha abaixo*/
		Piece p = board.removePiece(source); //Remove a pe�a de origem
		/*Em seguida, caso tenha uma pe�a na posi��o de destino, ele remove essa pe�a tambem, que � o
		 * que acontece com a linha abaixo*/
		Piece capturedPiece = board.removePiece(target); //Remove a pe�a que estiver na posi��o de origem
		/*Por fim a pe�a de origem chega na de destino*/
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position)
	{
		//Opera��o para validar uma posi��o de origem
		//Caso n�o tenha pe�a na posi��o lan�a a exception abaixo
		if(!board.thereIsAPiece(position))
		{
			throw new ChessException("There is no place on source position");
		}
		if(currentPlayer != ( (ChessPiece)board.piece(position)).getColor() )
		{
			//Nesse express�o foi feito um downcasting para poder acessar o metodo getColor()
			throw new ChessException("The chosen piece is not yours");
		}
		
		if(!board.piece(position).isThereAnyPossibleMove())
		{
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargerPosition(Position source, Position target) 
	{
		/*Caso a posi��o de origem em rela��o a de destino n�o seja uma movimento valido para uma 
		 * determinada pe�a n�o vai funcionar*/
		if(!board.piece(source).possibleMove(target)) 
		{
			throw new ChessException("The chosen piece can't move to targe position");
		}
	}
	
	private void nextTurn()
	{
		//Metodo para passar o turno
		turn++; //Incrementa siginifica que passou o turno
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //a pos incrementar sera a vez da proxima jogada
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
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
