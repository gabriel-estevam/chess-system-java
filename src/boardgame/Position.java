package boardgame;
//Classe que representa a posi��o de uma pe�a dentro do tabuleiro, seria a coodenada da pe�a
public class Position 
{
	private int row;
	private int column;
	
	public Position(int row, int column) 
	{
		this.row = row;
		this.column = column;
	}

	public int getRow() 
	{
		return row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public int getColumn() 
	{
		return column;
	}


	public void setColumn(int column) 
	{
		this.column = column;
	}

	public void setValues(int row, int column)
	{
		//Opera��o para atualizar os valores de uma posi��o
		this.row = row;
		this.column = column;
	}
	
	@Override

	public String toString() 
	{

		return row + ", " + column;
	}
	
	
}
