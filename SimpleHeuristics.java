package connectFour;

import connectFour.Connect4State.Token;
import sac.State;
import sac.StateFunction;

public class SimpleHeuristics extends StateFunction{

	public double calculate(State state) {
		Connect4State p4 = (Connect4State) state;
		
		for (int j=0; j<p4.getLar(); j++){
			if (p4.getBoard()[0][j] == Token.X ) {
				return Double.POSITIVE_INFINITY;
			}
			if (p4.getBoard()[0][j] == Token.O ) {
				return Double.NEGATIVE_INFINITY;
			}
		}
		return 0;
	}
}
