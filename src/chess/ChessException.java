package chess;

import boardgame.BoardException;

public class ChessException extends BoardException 
{	
	//Essa classe extends BoardException porque uma erro de tabuleiro tambem é um erro de xadrez
	private static final long serialVersionUID = 1L;

	public ChessException(String msg) 
	{
		super(msg);
	}

}