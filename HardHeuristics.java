package connectFour;

import connectFour.Connect4State.Token;
import sac.State;
import sac.StateFunction;

public class HardHeuristics extends StateFunction{

public double calculate(State state) {
	Connect4State p4 = (Connect4State) state;
	//IMPROVEMENT REGARDING MEDIUMHEURISTICS
	//we are now adding point only if the extremity of the string are free

	if (checkVictory(p4) != 0) {
		return checkVictory(p4);
	}

	int totalScore = 0;
	
	int HorizontalStringScore = countHorizontalString(p4, Token.O)+countHorizontalString(p4, Token.X);
	int VerticalStringScore = countVerticalString(p4, Token.O)+countVerticalString(p4, Token.X);
	int DiagonalStringScore = countDiagonalLeftString(p4, Token.O) + 
			countDiagonalLeftString(p4, Token.X) + 
			countDiagonalRightString(p4, Token.O) + 
			countDiagonalRightString(p4, Token.X);
	int MiddleBonus = scoreMiddle(p4);
	
	
	totalScore = HorizontalStringScore + VerticalStringScore + DiagonalStringScore + MiddleBonus;
	return totalScore;
}


public int countHorizontalString(Connect4State p4, Token jeton) {
	int ScoreString = 0;
	//we are now adding point only if the extremity of the string are free
	for (int i=0; i<p4.getLon(); i++) {
		for (int j=0; j<p4.getLar()-1; j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == jeton) {
				int StringSize = 1;
				int rightLimit = 0;
				for (int k=1; k<p4.getLar()-j; k++) {
					if (p4.getBoard()[i][j+k] == ref) {
						StringSize++;
						rightLimit = j+k;
					}
					else {
						break;
					}
				}

				if (jeton == Token.O) {
						if (isUsableSlot(p4, i, rightLimit+1))
							ScoreString -= StringSize * 10;
						if (isUsableSlot(p4, i, j-1))
							ScoreString -= StringSize * 10;
				}
				else {
					if (isUsableSlot(p4, i, rightLimit+1))
						ScoreString += StringSize * 10;
					if (isUsableSlot(p4, i, j-1))
						ScoreString += StringSize * 10;
				}				
			}
		}
	}
	return ScoreString;
}

public int countVerticalString(Connect4State p4, Token jeton) {
	int ScoreString = 0;
	
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=0; j<p4.getLar(); j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == jeton) {
				int StringSize = 1;
				for (int k=1; k<p4.getLon()-i; k++) {
					if (p4.getBoard()[i+k][j] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}

				if (jeton == Token.O) {
					if (isUsableSlot(p4, i-1, j))
						ScoreString -= StringSize * 10;
				}
				else {
					if (isUsableSlot(p4, i-1, j))
						ScoreString += StringSize * 10;
				}			
				
			}
		}
	}
	return ScoreString;
}

public int countDiagonalRightString(Connect4State p4, Token jeton) {
	int ScoreString = 0;
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=0; j<p4.getLar()-1; j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == jeton) {
				int StringSize = 1;
				int BorneMax = 0;
				int iMax = 0;
				int jMax = 0;
				if (p4.getLon()-i<p4.getLar()-j) {
					BorneMax = p4.getLon()-i;
				}
				else {
					BorneMax = p4.getLar()-j;
				}
				for (int k=1; k<BorneMax; k++) {
					if (p4.getBoard()[i+k][j+k] == ref) {
						StringSize++;
						iMax = i+k;
						jMax = j+k;
					}
					else {
						break;
					}
				}

				if (jeton == Token.O) {
					if (isUsableSlot(p4, iMax+1, jMax+1))
						ScoreString -= StringSize * 10;
					if (isUsableSlot(p4, i-1, j-1))
						ScoreString -= StringSize * 10;
				}
				else {
					if (isUsableSlot(p4, iMax+1, jMax+1))
						ScoreString += StringSize * 10;
					if (isUsableSlot(p4, i-1, j-1))
						ScoreString += StringSize * 10;
				}
			}
		}
	}
	return ScoreString;
}

public int countDiagonalLeftString(Connect4State p4, Token jeton) {
	int ScoreString = 0;
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=1; j<p4.getLar(); j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == jeton) {
				int StringSize = 1;
				int BorneMax = 0;
				int iMax = 0;
				int jMax = 0;
				if (j<p4.getLon()-i) {
					BorneMax = j+1;
				}
				else {
					BorneMax = p4.getLon()-i;
				}
				for (int k=1; k<BorneMax; k++) {
					if (p4.getBoard()[i+k][j-k] == ref) {
						StringSize++;
						iMax = i+k;
						jMax = j-k;
					}
					else {
						break;
					}
				}
				if (jeton == Token.O) {
					if (isUsableSlot(p4, iMax+1, jMax-1))
						ScoreString -= StringSize * 10;
					if (isUsableSlot(p4, i-1, j+1))
						ScoreString -= StringSize * 10;
				}
				else {
					if (isUsableSlot(p4, iMax+1, jMax-1))
						ScoreString += StringSize * 10;
					if (isUsableSlot(p4, i-1, j+1))
						ScoreString += StringSize * 10;
				}
			}
		}
	}
	return ScoreString;
}

public double checkVictory(Connect4State p4) {
	for (int j=0; j<p4.getLar(); j++){
		if (p4.getBoard()[0][j] == Token.X ) {
			return Double.POSITIVE_INFINITY;
		}
		if (p4.getBoard()[0][j] == Token.O ) {
			return Double.NEGATIVE_INFINITY;
		}
	}
	//check horizontal strings
	for (int i=0; i<p4.getLon(); i++) {
		for (int j=0; j<p4.getLar()-1; j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == Token.O) {
				int StringSize = 1;
				for (int k=1; k<p4.getLar()-j; k++) {
					if (p4.getBoard()[i][j+k] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.NEGATIVE_INFINITY;
				}
			}
			if (ref == Token.X) {
				int StringSize = 1;
				for (int k=1; k<p4.getLar()-j; k++) {
					if (p4.getBoard()[i][j+k] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.POSITIVE_INFINITY;
				}
			}
		}
	}
	//check vertical strings
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=0; j<p4.getLar(); j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == Token.O) {
				int StringSize = 1;
				for (int k=1; k<p4.getLon()-i; k++) {
					if (p4.getBoard()[i+k][j] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.NEGATIVE_INFINITY;
					//System.out.println(StringSize);
				}
			}
			if (ref == Token.X) {
				int StringSize = 1;
				for (int k=1; k<p4.getLon()-i; k++) {
					if (p4.getBoard()[i+k][j] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.POSITIVE_INFINITY;
					//System.out.println(StringSize);
				}
			}
		}
	}
	
	//CHECKING DIAGONAL RIGHT
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=0; j<p4.getLar()-1; j++) {
			if (p4.getBoard()[i][j] != Token._) {
				Token ref = p4.getBoard()[i][j];
				int StringSize = 1;
				int BorneMax = 0;
				if (p4.getLon()-i<p4.getLar()-j) {
					BorneMax = p4.getLon()-i;
				}
				else {
					BorneMax = p4.getLar()-j;
				}
				for (int k=1; k<BorneMax; k++) {
					if (p4.getBoard()[i+k][j+k] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					if (ref == Token.O) {
						return Double.NEGATIVE_INFINITY;
					}
					else {
						return Double.POSITIVE_INFINITY;
					}
				}
			}
		}
	}
	//CHECKING DIAGONAL LEFT
	for (int i=0; i<p4.getLon()-1; i++) {
		for (int j=1; j<p4.getLar(); j++) {
			Token ref = p4.getBoard()[i][j];
			if (ref == Token.O) {
				int StringSize = 1;
				int BorneMax = 0;
				if (j<p4.getLon()-i) {
					BorneMax = j+1;
				}
				else {
					BorneMax = p4.getLon()-i;
				}
				for (int k=1; k<BorneMax; k++) {
					if (p4.getBoard()[i+k][j-k] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.NEGATIVE_INFINITY;
				}
			}
			else if (ref == Token.X){
				int StringSize = 1;
				int BorneMax = 0;
				if (j<p4.getLon()-i) {
					BorneMax = j+1;
				}
				else {
					BorneMax = p4.getLon()-i;
				}
				for (int k=1; k<BorneMax; k++) {
					if (p4.getBoard()[i+k][j-k] == ref) {
						StringSize++;
					}
					else {
						break;
					}
				}
				if (StringSize >= 4) {
					return Double.POSITIVE_INFINITY;
				}
			}
		}
	}
	
	
	return 0;
}

public int scoreMiddle(Connect4State p4) {
	int ScoreMiddle = 0;
	for (int i=0; i<p4.getLon(); i++) {
		for (int j=p4.getLar()/3; j<p4.getLar()-(p4.getLar()/3); j++) {
			if (p4.getBoard()[i][j] == Token.O)
				ScoreMiddle--;
			if (p4.getBoard()[i][j] == Token.X) 
				ScoreMiddle++;
		}
	}
	return ScoreMiddle;
}

public boolean isUsableSlot(Connect4State p4, int i, int j) {
	//This function will help us determine if a slot can be used directly to expand a string
	//first we check if the slot is empty
	if (0<=i && i<p4.getLon()) {
		if (0<=j && j<p4.getLar()) {
			if (p4.getBoard()[i][j] == Token._) {
				if (i == p4.getLon()-1)
					return true;
				else if (p4.getBoard()[i+1][j] == Token.O || p4.getBoard()[i+1][j] == Token.X)
					return true;
			}
		}
	}
	
	return false;
}

}