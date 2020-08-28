package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

//Esse classe vai ficar armazenado as regras do jogo, aqui � onde tudo funciona
public class ChessMatch 
{
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Lista que sera responsavel pela contagem de pe�as no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); //Lista que sera responsavel pela contagem de pe�as capturadas 
	
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
	
	public boolean getCheck()
	{
		return check;
	}
	
	public boolean getCheckMate()
	{
		return checkMate;
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
		
		if( testCheck(currentPlayer) )
		{
			/*Caso o jogador atual ficar em check ele desfaz o movimento e lan�a uma excess�o
			 * pois o jogador atual n�o pode entrar em check*/
			
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		/*Caso o jogador atual n�o fique em cheque, significa que o oponente esta em cheque
		 * dai a propriedade check recebe true*/
		
	    check = (testCheck( opponent( currentPlayer) ) ) ? true : false;
	    
		if( testCheck( opponent(currentPlayer) ) )
		{
			checkMate = true;
		}
		else
		{
			nextTurn(); //troca de turno dos jogadores
		}
	    return (ChessPiece)capturedPiece;
	}

	private Piece makeMove(Position source, Position target)
	{
		//Opera��o para fazer um movimento de pe�a
		/*Para fazer o movimento de uma pe�a ela antes de mudar de lugar de que sair da sua origem
		  Que � o que acontece com a linha abaixo*/
		ChessPiece p = (ChessPiece) board.removePiece(source); //Remove a pe�a de origem
		/*Em seguida, caso tenha uma pe�a na posi��o de destino, ele remove essa pe�a tambem, que � o
		 * que acontece com a linha abaixo*/
		
		p.increaseMoveCount();
		
		Piece capturedPiece = board.removePiece(target); //Remove a pe�a que estiver na posi��o de origem
		/*Por fim a pe�a de origem chega na de destino*/
		board.placePiece(p, target);
		
		if(capturedPiece != null)
		{
			piecesOnTheBoard.remove(capturedPiece); //Remove uma pe�a da lista de pe�as do tabuleiro
			capturedPieces.add(capturedPiece); //Adciona uma pe�a capturada (retirada do tabuleiro) do tabuleiro
		}
		
		//#specialmove kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2)
		{
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		
		//#specialmove queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2)
		{
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece)
	{
		//Opera��o para desfazer o movimento de uma pe�a
		
		ChessPiece p = (ChessPiece)board.removePiece(target); //Criado uma pe�a que ja esta removendo a pe�a de destino
		
		p.decreaseMoveCount();
		
		board.placePiece(p, source); //Volta para a posi��o de origem
		
		if(capturedPiece != null)
		{
			//Caso ja tenha capturada a pe�a adversaria entre nesse if
			board.placePiece(capturedPiece, target); //retorna a pe�a capturada para posi��o de destino
			capturedPieces.remove(capturedPiece); //Remove a pe�a capturada da lista de pe�as capturadas
			capturedPieces.add(capturedPiece); //adiciona a pe�a novamente a lista de pe�as do tabuleiro
		}
		
		//#specialmove kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2)
		{
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
				
		//#specialmove queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2)
		{
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
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
		piecesOnTheBoard.add(piece); //Assim que � colocado todas as pe�as no tabuleiro, sera add na lista
	}
	
	private Color opponent(Color color)
	{
		//Fun��o para saber a cor do oponente
		
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color)
	{
		/*Essa opera��o vai varrer a lista de pe�as para saber quem � o rei da cor informada
		 * no parametro*/
		//Come�a filtrando a lista de pe�as no tabuleiro, dentro do filter o predicado foi feito um downcasting para acessar o metodo getColor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for(Piece p : list)
		{
			/*A estrutura for vai percorre a lista buscando pelo rei, que � o objeto king*/
			if(p instanceof King)
			{
				/*Caso seja encontrado a instancia do objeto king na cor indicada vai retorna p fazendo um downcasting para ChessPiece*/
				return (ChessPiece)p;
			}
		}
		/*Caso ele n�o encontre nada, isto � nenhum rei gera uma excess�o, siginificando algo erro no sistema*/
		throw new IllegalStateException("There is no "+ color + " king on the board"); 	
	}
	
	private boolean testCheckMate(Color color)
	{
		/*Opera��o para testar se uma pe�a esta em cheque mate*/
		if(!testCheck(color))
		{
			/*Antes de iniciar � testado se a pe�a na cor atual n�o esta em cheque, caso n�o esteja
			 * retona false, indicando que n�o esta em cheque*/
			return false;
		}
		/*Logica para testar se o rei ests em cheque
		 * 
		 * Primeiro foi criado uma lista que vai filtrar todas as pe�as do 
		 * tabuleiro na cor pessada por parametro*/
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p: list)
		{
			/*foreach para percorrer a lista
			 * 
			 * Em seguida criado uma matriz que recebe um movimento 
			 * possivel de uma pe�a e dentro mais dois for para percorrer essa matriz
			 * Nisso ele vai testar se a pe�a atual for um movimento possivel, significa 
			 * que o rei n�o esta em cheque mate, veja abaixo:
			 * */
			
			boolean[][]mat = p.possibleMove(); //Matriz que recebe um movimento possivel de uma pe�a
			for(int i = 0; i < board.getRows(); i++)
			{
				//for para percorrer linhas e colunas da matriz de movimentos possiveis
				for(int j = 0; j < board.getColumns(); j++)
				{
					if(mat[i][j])
					{
						//if para entrar na matriz
						Position source = ( (ChessPiece)p ).get().toPosition(); //variavel auxiliar origem (atual) que recebe a posi��o atual da pe�a
						Position target = new Position(i,j); //Variavel auxiliar de destino de uma pe�a que instancia uma nova posi��o que � a de possivel moviemento 
						Piece capturedPiece = makeMove(source, target);//Variavel auxliar que recebe um movimento da pe�a, faz o movimento para possivel posi��o
						boolean testCheck = testCheck(color);//Variavel auxiliar que vai testar se ainda o rei esta em cheque
						undoMove(source, target, capturedPiece);//Em seguida desfaz o movimento feito, pois trata-se de apenas um teste
						if(!testCheck)
						{
							//Caso o rei n�o esta em cheque, significa que o movimento da pe�a n�o deixou o em rei em cheque
							//retorna false indicando que n�o esta em cheque
							return false;
						}
					}
				}
			}
		}
		/*Caso a pe�a esteja em cheque mate retona true, siginifica que esta em cheque mate a pe�a,
		 * isto � se acobou o for e nada da pe�a ter uma movimento possivel*/
		return true;
	}
	
	private boolean testCheck(Color color)
	{
		/*Opera��o para teste se o rei esta em check*/
		
		Position kingPosition = king(color).get().toPosition(); //Criado um objeto do tipo positon que esta recebendo a posi��o de uma pe�a rei de uma determinada cor
		
		/*Apos criar a posi��o do rei, sera filtrado as pe�as da lista de pe�as que esta no tabuleiro
		 * Se a cor capturada seja da pe�a oponete entre nesse filtro (que � uma nova lista - opponentPieces)*/
		
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ( (ChessPiece)x ).getColor() == opponent(color) ).collect(Collectors.toList());
		
		/*O foreach abaixo vai percorrer a lista de oponentes, dentro do foreach � declarado uma matriz booleana
		 * essa matrz � para saber se a pe�a p � uma possivel movimento.
		 * 
		 * A caso o movimento seja possivel para pe�a na posi��o que se encontra o rei (linha e coluna) retorna true
		 * significa que o reit esta em check
		 * 
		 * Se o for esgotar e n�o achar nada sinal que o rei n�o estava em check
		 * */
		for(Piece p : opponentPieces)
		{
			boolean[][] mat = p.possibleMove();
			if( mat[kingPosition.getRow()][kingPosition.getColumn()] )
			{
				return true;
			}
		}
		
		return false;
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
		
			placeNewPiece('a', 1, new Rook(board, Color.WHITE));
			placeNewPiece('b', 1, new Knight(board, Color.WHITE));
			placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
			placeNewPiece('d', 1, new Queen(board, Color.WHITE));
	        placeNewPiece('e', 1, new King(board, Color.WHITE,this));
	        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
	        
	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK,this));
	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
