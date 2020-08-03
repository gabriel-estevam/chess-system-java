package boardgame;

//Classe Pe�a de xadrez comum
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
		/*Esse metodo vai varrer a matriz de pe�a, buscando posi��es possiveis (livres)
		 * para movimentar;
	      */
		boolean mat[][] =  possibleMove(); //Variavel auxiliar para percorrer a matriz
		for(int i = 0; i < mat.length;i++)
		{
			for(int j=0; j < mat.length; j++)
			{
				if(mat[i][j])
				{
					/*Funcionamento: Essa opera��o vai percorrer linha a linha buscando pe�as
					 * caso exista == true ele retorna true, sinal que tem uma pe�a em determinada posi��o do tabuleiro;
					 * Caso n�o exista retorna false, sinal que n�o tem pe�a ocupando o determinada posi��o*/
					return true;
				}
			}
		}
		return false; //Caso n�o tenha pe�a em determinada op��o
	}
}
