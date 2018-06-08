package connectFour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import sac.StateFunction;
import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.GameState;
import sac.game.GameStateImpl;
import sac.game.MinMax;
import sac.graph.GraphState;

public class Connect4State extends GameStateImpl{

	
	//X is maximazing player, IS AI
	//O is minimizing player
	
	private int Lar = 0;
	private int Lon = 0; //hauteur
	public static enum Token { X, O,_ };
	private Token[][] Board = null;

	
	public Connect4State(int n, int m) {
		Lar = m;
		Lon = n;
		Board = new Token[Lon][Lar];
		for (int i=0; i <Lon; i++)
			for (int j=0; j<Lar; j++)
				Board[i][j] = Token._;
	}
	
	public Connect4State(Connect4State father) {
		Lar = father.getLar();
		Lon = father.getLon();
		setMaximizingTurnNow(father.isMaximizingTurnNow());
		Token[][] tableau = father.getBoard();
		Board = new Token[Lon][Lar];
		for (int i=0; i <Lon; i++) {
			for (int j=0; j<Lar; j++) {					
				this.Board[i][j] = tableau[i][j];
			}
		}
	}

	public int hashCode() {
		Token[] copy = new Token[Lon*Lar];
		int k = 0;
		for (int i = 0; i<Lon; i++)
			for (int j=0; j<Lar; j++)
				copy[k++]=Board[i][j];				//++k exist aussi, go checker
		
		return Arrays.hashCode(copy);			//fais le hashcode sur le contenu, pas sur l'adresse mémoire comme le reste des objets
	}
	
	@Override
	public String toString() {
		String str = "|";
		for (int i=0; i<Lon; i++ ) {
			for (int j=0; j<Lar; j++) {
				str += Board[i][j] + "|";
			}
			str = str + "\n|";
		}
		for (int l=0; l<Lar-1; l++ ) {
			str +=  "=|";
		}
		str = str + "=|\n|";
		for (int k=0; k<Lar-1; k++ ) {
			str += k + "|";
		}
		str += Lar-1 + "|";
		return str;
	}

	public void makeMove(int col) {
		for (int i=Lon-1; i>=0; i--){
			if (Board[i][col] == Token._) {
				Board[i][col] = (isMaximizingTurnNow())? Token.X : Token.O;
				break;
			}
		}
		setMaximizingTurnNow(!isMaximizingTurnNow());
	}
	

	@Override
	public List<GameState> generateChildren() {
		List<GameState> liste = new ArrayList<GameState>();
		for (int i=0; i<Lar; i++) {
			if (Board[0][i] == Token._) {
				Connect4State child = new Connect4State(this);
				child.makeMove(i);
				child.setMoveName(String.valueOf(i));
				liste.add(child);
			}
		}
		return liste;
	}
	
	
	public static void main(String[] args) {
				
		
		StateFunction heuristic = new HardHeuristics();
		int n = 6;
		int m = 7;
		
		
		Connect4State bob = new Connect4State(n,m);
		bob.setHFunction(heuristic);
		GameSearchAlgorithm a = new MinMax(bob);
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		while (true)
		{	

			a.execute();
			System.out.println(a.getMovesScores());
			String stock = "";
			stock = a.getFirstBestMove();
			//System.out.println(stock);
			bob.makeMove(Integer.valueOf(stock));
			System.out.println(bob);
			//check if win state
			if (heuristic.calculate(bob) == Double.POSITIVE_INFINITY) {
				System.out.println("COMPUTER WINS, BETTER LUCK NEXT TIME !");
				break;
			}
			System.out.println("=====================================================================================");

			System.out.println("Your move.");
			int move_user = reader.nextInt(); // Scans the next token of the input as an int.
			if (move_user < 0) {
				move_user = 0;
			}
			else if (move_user > bob.getLar()) {
				move_user = bob.getLar()-1;
			}
			bob.makeMove(move_user);
			System.out.println(bob);
			
			//check if win state
			if (heuristic.calculate(bob) == Double.NEGATIVE_INFINITY) {
				System.out.println("HUMAN PLAYER WINS, CONGRATS !");
				break;
			}
			
			
			
		}


	}

	public int getLar() {
		return Lar;
	}

	public void setLar(int lar) {
		Lar = lar;
	}

	public int getLon() {
		return Lon;
	}

	public void setLon(int lon) {
		Lon = lon;
	}

	public Token[][] getBoard() {
		return Board;
	}

	public void setBoard(Token[][] board) {
		Board = board;
	}



}
