package boardgame;

//Classe Tabuleiro de xadrez
public class Board
{
	private int rows;
	private int columns;
	private Piece[][] pieces; //Para o Tabuleiro as pe�as � uma matriz

	public Board(int rows, int columns)
	{
		if (rows < 1 || columns < 1) 
		{
			//Aqui estamos aplicando uma programa��o defensiva, que ao instanciar um tabuleiro
			//n�o pode haver linhas ou colunas menores que 1, n�o existe isso
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //Assim que for instanciado essa classe a matriz de pe�a recebe a linha e a coluna da pe�a no tabuleiro
	}
	//Retirado os metodos sets de rows e columns
	public int getRows() 
	{
		return rows;
	}

	public int getColumns() 
	{
		return columns;
	}

	public Piece piece(int row, int column) 
	{	
		if(!positionExists(row, column))
		{
			//Aqui tambem aplicamos uma programa��o defensiva
			//Caso as coordenadas pessada n�o exista
			throw new BoardException("Position not on the board");
		}
		//Aqui vai retorna na posi��o passado como parametro
		return pieces[row][column];
	}
	
	public Piece piece(Position position) 
	{	
		if(!positionExists(position))
		{
			//Aqui tambem aplicamos uma programa��o defensiva
			//Caso as coordenadas pessada n�o exista
			throw new BoardException("Position not on the board");
		}
		//Aqui vai retorna uma pe�a de acordo com o objeto position 
		//passado como parametro
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) 
	{
		if (thereIsAPiece(position)) 
		{
			//Aqui tambem aplicamos uma programa��o defensiva, caso ja exista uma pe�a em uma determinada posi��o
			throw new BoardException("There is already a piece on position " + position);
		}
		//Essa fun��o colocar uma pe�a no tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position)
	{
		//Metodo para remover uma pe�a
		
		//Programa��o defensiva
		if(!positionExists(position))
		{
			throw new BoardException("There is already a piece on position " + position);
		}
		//Antes de remover vai testar se a pe�a passada na posi��o existe, caso n�o, retorna null
		if(piece(position) == null)
		{
			return null;
		}
		//Removendo uma pe�a
		/*Criado uma variavel auxiliar do tipo piece, essa auxliar na posi��o
		 * informada recebe nulo, em seguida na posi��o da matriz que vai
		 * remover recebe nulo, que significa a remo��o da pe�a da matiz
		 * em seguida retorna a variavel auxiliar */
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column)
	{
		/*Nesse metodo vamos testar se uma determinada posi��o existe. 
		 *Se estiver no padr�o do tabuleiro, caso contrario n�o existe*/
		return row >=0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) 
	{
		//Essa fun��o tem o mesmo proposito que a fun��o acima, na verdade estamos reutilizando o codigo acima
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) 
	{
		//Fun��o para validar uma pe�a em uma determinada posi��o
		if (!positionExists(position)) 
		{
			//Aqui tambem tem uma programa��o defensiva, antes de rodar esse metodo ele ja testa se a posi��o informada existe
			throw new BoardException("Position not on the board");
		}
		
		return piece(position) != null;
	}
}


