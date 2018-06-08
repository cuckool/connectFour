package connectFour;

import connectFour.Connect4State.Token;
import sac.StateFunction;

public class test {

	public static void main(String[] args) {
		HardHeuristics heur = new HardHeuristics();
		Connect4State p4 = new Connect4State(6,8);
		
		
		

		p4.makeMove(7);
		p4.makeMove(2);


		p4.makeMove(2);
		
		p4.makeMove(1);
		p4.makeMove(1);
		p4.makeMove(4);
		p4.makeMove(2);

	

	   
	    
	    
	    
		System.out.println(p4);
		System.out.println(heur.countHorizontalString(p4, Token.X));
		System.out.println(heur.countVerticalString(p4, Token.X));
		System.out.println(heur.countDiagonalRightString(p4, Token.O));
		System.out.println(heur.countDiagonalLeftString(p4, Token.X));

		/*
		System.out.println(heur.checkVictory(p4));
		
		if (heur.checkVictory(p4) > 100000)
			System.out.println("X WINS");
		else if (heur.checkVictory(p4) < -100000)
			System.out.println("O WINS");
		else
			System.out.println("NO VICTORY");

		*/
	}

}
