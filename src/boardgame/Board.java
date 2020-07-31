package boardgame;

//Classe Tabuleiro de xadrez
public class Board
{
	private int rows;
	private int columns;
	private Piece[][] pieces; //Para o Tabuleiro as peças é uma matriz

	public Board(int rows, int columns)
	{
		if (rows < 1 || columns < 1) 
		{
			//Aqui estamos aplicando uma programação defensiva, que ao instanciar um tabuleiro
			//não pode haver linhas ou colunas menores que 1, não existe isso
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //Assim que for instanciado essa classe a matriz de peça recebe a linha e a coluna da peça no tabuleiro
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
			//Aqui tambem aplicamos uma programação defensiva
			//Caso as coordenadas pessada não exista
			throw new BoardException("Position not on the board");
		}
		//Aqui vai retorna na posição passado como parametro
		return pieces[row][column];
	}
	
	public Piece piece(Position position) 
	{	
		if(!positionExists(position))
		{
			//Aqui tambem aplicamos uma programação defensiva
			//Caso as coordenadas pessada não exista
			throw new BoardException("Position not on the board");
		}
		//Aqui vai retorna uma peça de acordo com o objeto position 
		//passado como parametro
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) 
	{
		if (thereIsAPiece(position)) 
		{
			//Aqui tambem aplicamos uma programação defensiva, caso ja exista uma peça em uma determinada posição
			throw new BoardException("There is already a piece on position " + position);
		}
		//Essa função colocar uma peça no tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position)
	{
		//Metodo para remover uma peça
		
		//Programação defensiva
		if(!positionExists(position))
		{
			throw new BoardException("There is already a piece on position " + position);
		}
		//Antes de remover vai testar se a peça passada na posição existe, caso não, retorna null
		if(piece(position) == null)
		{
			return null;
		}
		//Removendo uma peça
		/*Criado uma variavel auxiliar do tipo piece, essa auxliar na posição
		 * informada recebe nulo, em seguida na posição da matriz que vai
		 * remover recebe nulo, que significa a remoção da peça da matiz
		 * em seguida retorna a variavel auxiliar */
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column)
	{
		/*Nesse metodo vamos testar se uma determinada posição existe. 
		 *Se estiver no padrão do tabuleiro, caso contrario não existe*/
		return row >=0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) 
	{
		//Essa função tem o mesmo proposito que a função acima, na verdade estamos reutilizando o codigo acima
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) 
	{
		//Função para validar uma peça em uma determinada posição
		if (!positionExists(position)) 
		{
			//Aqui tambem tem uma programação defensiva, antes de rodar esse metodo ele ja testa se a posição informada existe
			throw new BoardException("Position not on the board");
		}
		
		return piece(position) != null;
	}
}


