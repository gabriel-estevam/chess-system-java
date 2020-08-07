package chess;

import boardgame.Position;
//Essa classe representa a coordenada da peça no tabuleiro de xadrez, respeitando o numero de linha e as letras de 'a' a 'h' do tabuleiro
public class ChessPosition 
{
	private char column;
	private int row;

	public ChessPosition(char column, int row) 
	{		
		if (column < 'a' || column > 'h' || row < 1 || row > 8) 
		{
			//Programação defensiva
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}
	
	//Retirado os metods sets da linha e da coluna
	public char getColumn() 
	{
		return column;
	}

	public int getRow() 
	{
		return row;
	}

	protected Position toPosition() 
	{
		//Função para retornar a posição exata no tabuleiro
		//Retorna a posição exata dentro do tabuleiro
		return new Position(8 - row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position) 
	{
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() 
	{
		return "" + column + row;
	}
}

