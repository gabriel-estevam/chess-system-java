package boardgame;

//Classe Tabuleiro de xadrez
public class Board
{
	private int rows;
	private int columns;
	private Piece[][] pieces; //Para o Tabuleiro as peças é uma matriz

	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //Assim que for instanciado essa classe a matriz de peça recebe a linha e a coluna da peça no tabuleiro
	}

	public int getRows() 
	{
		return rows;
	}

	public void setRows(int rows) 
	{
		this.rows = rows;
	}

	public int getColumns() 
	{
		return columns;
	}
	
	public void setColumns(int columns) 
	{
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) 
	{
		return pieces[row][column];
		
	}
	
	public Piece piece(Position position) 
	{
		return pieces[position.getRow()][position.getColumn()];
	}
}
