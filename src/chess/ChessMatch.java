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

//Esse classe vai ficar armazenado as regras do jogo, aqui é onde tudo funciona
public class ChessMatch 
{
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Lista que sera responsavel pela contagem de peças no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); //Lista que sera responsavel pela contagem de peças capturadas 
	
	public ChessMatch() 
	{
		//Assim que instanciado ja define um tabuleiro 8x8
		board = new Board(8, 8);
		//Assim que iniciado começa no turno 1 e com o jogador branco
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
		/*Operação que vai retornar os movimentos possiveis para uma determinada peça*/
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMove();
	}
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) 
	{
		//Operação para mover uma peça, nesse momento não é a o metodo de captura de peça
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); //Validação da posição de origem
		
		validateTargerPosition(source,target); //Validação da posição de destino
		
		Piece capturedPiece = makeMove(source, target); //faz o movimento da peça
		
		if( testCheck(currentPlayer) )
		{
			/*Caso o jogador atual ficar em check ele desfaz o movimento e lança uma excessão
			 * pois o jogador atual não pode entrar em check*/
			
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		/*Caso o jogador atual não fique em cheque, significa que o oponente esta em cheque
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
		//Operação para fazer um movimento de peça
		/*Para fazer o movimento de uma peça ela antes de mudar de lugar de que sair da sua origem
		  Que é o que acontece com a linha abaixo*/
		ChessPiece p = (ChessPiece) board.removePiece(source); //Remove a peça de origem
		/*Em seguida, caso tenha uma peça na posição de destino, ele remove essa peça tambem, que é o
		 * que acontece com a linha abaixo*/
		
		p.increaseMoveCount();
		
		Piece capturedPiece = board.removePiece(target); //Remove a peça que estiver na posição de origem
		/*Por fim a peça de origem chega na de destino*/
		board.placePiece(p, target);
		
		if(capturedPiece != null)
		{
			piecesOnTheBoard.remove(capturedPiece); //Remove uma peça da lista de peças do tabuleiro
			capturedPieces.add(capturedPiece); //Adciona uma peça capturada (retirada do tabuleiro) do tabuleiro
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
		//Operação para desfazer o movimento de uma peça
		
		ChessPiece p = (ChessPiece)board.removePiece(target); //Criado uma peça que ja esta removendo a peça de destino
		
		p.decreaseMoveCount();
		
		board.placePiece(p, source); //Volta para a posição de origem
		
		if(capturedPiece != null)
		{
			//Caso ja tenha capturada a peça adversaria entre nesse if
			board.placePiece(capturedPiece, target); //retorna a peça capturada para posição de destino
			capturedPieces.remove(capturedPiece); //Remove a peça capturada da lista de peças capturadas
			capturedPieces.add(capturedPiece); //adiciona a peça novamente a lista de peças do tabuleiro
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
		//Operação para validar uma posição de origem
		//Caso não tenha peça na posição lança a exception abaixo
		if(!board.thereIsAPiece(position))
		{
			throw new ChessException("There is no place on source position");
		}
		if(currentPlayer != ( (ChessPiece)board.piece(position)).getColor() )
		{
			//Nesse expressão foi feito um downcasting para poder acessar o metodo getColor()
			throw new ChessException("The chosen piece is not yours");
		}
		
		if(!board.piece(position).isThereAnyPossibleMove())
		{
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargerPosition(Position source, Position target) 
	{
		/*Caso a posição de origem em relação a de destino não seja uma movimento valido para uma 
		 * determinada peça não vai funcionar*/
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
		//Aqui temos uma operação de inserção de uma nova peça de xadrez no tabuleiro
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		piecesOnTheBoard.add(piece); //Assim que é colocado todas as peças no tabuleiro, sera add na lista
	}
	
	private Color opponent(Color color)
	{
		//Função para saber a cor do oponente
		
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color)
	{
		/*Essa operação vai varrer a lista de peças para saber quem é o rei da cor informada
		 * no parametro*/
		//Começa filtrando a lista de peças no tabuleiro, dentro do filter o predicado foi feito um downcasting para acessar o metodo getColor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for(Piece p : list)
		{
			/*A estrutura for vai percorre a lista buscando pelo rei, que é o objeto king*/
			if(p instanceof King)
			{
				/*Caso seja encontrado a instancia do objeto king na cor indicada vai retorna p fazendo um downcasting para ChessPiece*/
				return (ChessPiece)p;
			}
		}
		/*Caso ele não encontre nada, isto é nenhum rei gera uma excessão, siginificando algo erro no sistema*/
		throw new IllegalStateException("There is no "+ color + " king on the board"); 	
	}
	
	private boolean testCheckMate(Color color)
	{
		/*Operação para testar se uma peça esta em cheque mate*/
		if(!testCheck(color))
		{
			/*Antes de iniciar é testado se a peça na cor atual não esta em cheque, caso não esteja
			 * retona false, indicando que não esta em cheque*/
			return false;
		}
		/*Logica para testar se o rei ests em cheque
		 * 
		 * Primeiro foi criado uma lista que vai filtrar todas as peças do 
		 * tabuleiro na cor pessada por parametro*/
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p: list)
		{
			/*foreach para percorrer a lista
			 * 
			 * Em seguida criado uma matriz que recebe um movimento 
			 * possivel de uma peça e dentro mais dois for para percorrer essa matriz
			 * Nisso ele vai testar se a peça atual for um movimento possivel, significa 
			 * que o rei não esta em cheque mate, veja abaixo:
			 * */
			
			boolean[][]mat = p.possibleMove(); //Matriz que recebe um movimento possivel de uma peça
			for(int i = 0; i < board.getRows(); i++)
			{
				//for para percorrer linhas e colunas da matriz de movimentos possiveis
				for(int j = 0; j < board.getColumns(); j++)
				{
					if(mat[i][j])
					{
						//if para entrar na matriz
						Position source = ( (ChessPiece)p ).get().toPosition(); //variavel auxiliar origem (atual) que recebe a posição atual da peça
						Position target = new Position(i,j); //Variavel auxiliar de destino de uma peça que instancia uma nova posição que é a de possivel moviemento 
						Piece capturedPiece = makeMove(source, target);//Variavel auxliar que recebe um movimento da peça, faz o movimento para possivel posição
						boolean testCheck = testCheck(color);//Variavel auxiliar que vai testar se ainda o rei esta em cheque
						undoMove(source, target, capturedPiece);//Em seguida desfaz o movimento feito, pois trata-se de apenas um teste
						if(!testCheck)
						{
							//Caso o rei não esta em cheque, significa que o movimento da peça não deixou o em rei em cheque
							//retorna false indicando que não esta em cheque
							return false;
						}
					}
				}
			}
		}
		/*Caso a peça esteja em cheque mate retona true, siginifica que esta em cheque mate a peça,
		 * isto é se acobou o for e nada da peça ter uma movimento possivel*/
		return true;
	}
	
	private boolean testCheck(Color color)
	{
		/*Operação para teste se o rei esta em check*/
		
		Position kingPosition = king(color).get().toPosition(); //Criado um objeto do tipo positon que esta recebendo a posição de uma peça rei de uma determinada cor
		
		/*Apos criar a posição do rei, sera filtrado as peças da lista de peças que esta no tabuleiro
		 * Se a cor capturada seja da peça oponete entre nesse filtro (que é uma nova lista - opponentPieces)*/
		
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ( (ChessPiece)x ).getColor() == opponent(color) ).collect(Collectors.toList());
		
		/*O foreach abaixo vai percorrer a lista de oponentes, dentro do foreach é declarado uma matriz booleana
		 * essa matrz é para saber se a peça p é uma possivel movimento.
		 * 
		 * A caso o movimento seja possivel para peça na posição que se encontra o rei (linha e coluna) retorna true
		 * significa que o reit esta em check
		 * 
		 * Se o for esgotar e não achar nada sinal que o rei não estava em check
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
		//Função para inicializar uma partida de xadrez
		
		/*Esse seria o jeito certo de inserir uma peça no tabuleiro, pois se observarmos o
		 * tabuleiro ele tem duas coordenas que mostra o exato luga da peça
		 * a coordenada 1 a 8 - lateral esquerda;
		 * a coordenada 'a' a 'h' que esta na parte inferior do tabuleiro.
		 * Desse jeito fica mais facil de entender a joga, e faz mais sentido para essa camada chess
		 * Oberserve o codigo anterior: 
		 * board.placePiece(new Rook(board,Color.WHITE), new Position(2,1)); - nesse codigo temos teriamos que
		 * instanciar uma peça de xadrez e instanciar uma posição ja informando o local sem usar as coordenadas do tabuleiro
		 * errado não esta, poderia ser tambem*/
		
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
