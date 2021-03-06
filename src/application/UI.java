package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//Essa Classe � responsavel po imprimir o tabuleiro com as pe�as na tela
public class UI 
{
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
		
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console 
	public static void clearScreen() 
	{     
		System.out.print("\033[H\033[2J");      
		System.out.flush();   
	} 
	
	public static ChessPosition readChessPosition(Scanner sc) 
	{
		//Fun��o para ler uma posi��o do teclado
		try
		{
			String s = sc.nextLine(); //Recebe o valor informado
			char column = s.charAt(0); //column recebe o primeiro caracter
			
			//Agora vamos recortar o valor digitado
			int row = Integer.parseInt(s.substring(1)); //Recortou a String, row recebe o caracter apartir da posi��o 1 
			return new ChessPosition(column, row); //Retorna a linha e a coluna
		}
		catch(RuntimeException e)
		{
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
		//O codigo foi envolvido em um try-catch pois caso acontece erro de entrada de dados
	}
	
	public static void printMatch(ChessMatch chessMatch, List <ChessPiece> captured)
	{
		//Metodo para imprimir a partida
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: "+ chessMatch.getTurn());
		//if para testar se deu cheque mate ou n�o 
		if(!chessMatch.getCheckMate())
		{
			//Caso n�o deu cheque mate continua
			System.out.println("Waiting player: "+ chessMatch.getCurrentPlayer());
			if(chessMatch.getCheck() )
			{
				//Esse if testa se a pe�a esta em cheque, mostra a menssagem abaixo
				System.out.println("CHECK!");
			}
		}
		else
		{
			//Deu chequemate, mostra messagem e o vencedor da partida
			System.out.println("CHECKMATE!");
			System.out.println("Winner: "+ chessMatch.getCurrentPlayer() );
		}
	}
	
	public static void printBoard(ChessPiece[][] pieces) 
	{
		//Essa fun��o tem como parametro uma matriz de pe�as to tipo ChessPiece
		for (int i=0; i < pieces.length; i++) 
		{

			System.out.print((8 - i) + " ");

			for (int j=0; j < pieces.length; j++) 
			{
				printPiece(pieces[i][j],false);
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) 
	{
	/*Aqui fizemos uma sobrecarga do metodo de impress�o,
	 *o intuito desse metodo � mostrar na tela os movimentos possiveis para uma determinada pe�a 
	 *Marcando aonde os possiveis movimentos que uma pe�a pode fazer 
	 *Agora essa fun��o recebe como parametro uma matriz boolean*/
		for (int i=0; i<pieces.length; i++) 
		{
			System.out.print((8 - i) + " ");

			for (int j=0; j<pieces.length; j++) 
			{
				printPiece(pieces[i][j],possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece, boolean background) 
	{
		//Metodo auxliar para mostrar as pe�as no tabuleiro
		//Tem como parametro um objeto do tipo chessPiece, em seguida ele valida a existencia de pe�as
		//Essa logica desse metodo poderia ser no metodo acima, mas foi divido para n�o ficar muito grande
		
		/*Foi feito uma modifica��o nesse metodo, ele agora passa a ter um parametro a mais,
		 * onde vai receber um valor booleano.
		 * 
		 * Caso o valor seja true, conforme o if abaixo, o background dele fica azul mostrando o
		 * movimento possivel para pe�a*/
		if(background)
		{
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		//Pequna modifica��o na exibi��o das pe�as
		if (piece == null) 
	  	{
            //Caso a pe�a n�o exista mostra um tra�o
	  		System.out.print("-" + ANSI_RESET);
        }
	  	
        else 
        {
        //Aqui esta a modifica��o, caso a pe�a instanciada seja a WHITE, mostra ele como branca
        //Caso a pe�a instanciada seja a BLACK, mostra a pe�a em amarelo, isso porque vamos executar
        //no terminal do git bash e esse tem o fundo preto
        	if (piece.getColor() == Color.WHITE) 
            {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else 
            {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured)
	{
		/*Fun��o para listar todos as pe�as capturadas do adversario*/
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x-> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		System.out.println("Captured pieces:");
		System.out.print("White");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray())); //� uma maneira de imprimir um array
		System.out.print(ANSI_RESET);
		
		System.out.print("Black");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
	
}

