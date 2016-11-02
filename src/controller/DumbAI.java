package controller;

import model.*;

/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {

	public DumbAI(Player me) {
		super(me);

	}

	protected @Override Location nextMove(Game g) {
		// Note: Calling delay here will make the CLUI work a little more
		// nicely when competing different AIs against each other.
		
		System.out.print("O");
		delay();

		for (Location move : Board.LOCATIONS){
			if(g.getBoard().get(move) == null){
				return move;
			}
		}

		return null;
	}
}
