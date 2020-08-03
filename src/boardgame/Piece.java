package boardgame;

//Classe Peça de xadrez comum
public abstract class Piece 
{
	protected Position position;
	private Board board;

	public Piece(Board board) 
	{
		this.board = board;
		position = null;
	}

	protected Board getBoard() 
	{	
		return board;
	}
	
	public abstract boolean[][] possibleMove();
	
	public boolean possibleMove(Position position) 
	{
		//Esse metodo faz um "gancho" de alguma sub-classe que extends esse metodo
		return possibleMove()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove()
	{
		/*Esse metodo vai varrer a matriz de peça, buscando posições possiveis (livres)
		 * para movimentar;
	      */
		boolean mat[][] =  possibleMove(); //Variavel auxiliar para percorrer a matriz
		for(int i = 0; i < mat.length;i++)
		{
			for(int j=0; j < mat.length; j++)
			{
				if(mat[i][j])
				{
					/*Funcionamento: Essa operação vai percorrer linha a linha buscando peças
					 * caso exista == true ele retorna true, sinal que tem uma peça em determinada posição do tabuleiro;
					 * Caso não exista retorna false, sinal que não tem peça ocupando o determinada posição*/
					return true;
				}
			}
		}
		return false; //Caso não tenha peça em determinada opção
	}
}
