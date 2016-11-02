package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.eclipse.jdt.annotation.NonNull;

import controller.*;
import model.*;
import model.Board.State;

/**
 * A MinMaxAI is a controller that uses the minimax algorithm to select the next
 * move.  The minimax algorithm searches for the best possible next move, under
 * the assumption that your opponent will also always select the best possible
 * move.
 *
 * <p>The minimax algorithm assigns a score to each possible game configuration
 * g.  The score is assigned recursively as follows: if the game g is over and
 * the player has won, then the score is infinity.  If the game g is over and
 * the player has lost, then the score is negative infinity.  If the game is a
 * draw, then the score is 0.
 *
 * <p>If the game is not over, then there are many possible moves that could be
 * made; each of these leads to a new game configuration g'.  We can
 * recursively find the score for each of these configurations.
 *
 * <p>If it is the player's turn, then they will choose the action that
 * maximizes their score, so the score for g is the maximum of all the scores
 * of the g's.  However, if it is the opponent's turn, then the opponent will
 * try to minimize the score for the player, so the score for g is the
 * <em>minimum</em> of all of the scores of the g'.
 *
 * <p>You can think of the game as defining a tree, where each node in the tree
 * represents a game configuration, and the children of g are all of the g'
 * reachable from g by taking a turn.  The minimax algorithm is then a
 * particular traversal of this tree.
 *
 * <p>In practice, game trees can become very large, so we apply a few
 * strategies to narrow the set of paths that we search.  First, we can decide
 * to only consider certain kinds of moves.  For five-in-a-row, there are
 * typically at least 70 moves available at each step; but it's (usually) not
 * sensible to go on the opposite side of the board from where all of the other
 * pieces are; by restricting our search to only part of the board, we can
 * reduce the space considerably.
 *
 * <p>A second strategy is that we can look only a few moves ahead instead of
 * planning all the way to the end of the game.  This requires us to be able to
 * estimate how "good" a given board looks for a player.
 *
 * <p>This class implements the minimax algorithm with support for these two
 * strategies for reducing the search space.  The abstract method {@link
 * #moves(Board)} is used to list all of the moves that the AI is willing to
 * consider, while the abstract method {@link #estimate(Board)} returns
 * the estimation of how good the board is for the given player.
 */
public abstract class MinMaxAI extends Controller {

	protected Player p;
	protected int pDepth;
	/**
	 * Return an estimate of how good the given board is for me.
	 * A result of infinity means I have won.  A result of negative infinity
	 * means that I have lost.
	 */
	protected abstract int estimate(Board b);

	/**
	 * Return the set of moves that the AI will consider when planning ahead.
	 * Must contain at least one move if there are any valid moves to make.
	 */
	protected abstract Iterable<Location> moves(Board b);

	/**
	 * Create an AI that will recursively search for the next move using the
	 * minimax algorithm.  When searching for a move, the algorithm will look
	 * depth moves into the future.
	 *
	 * <p>choosing a higher value for depth makes the AI smarter, but requires
	 * more time to select moves.
	 */
	protected MinMaxAI(Player me, int depth) {
		super(me);
		p = me;
		pDepth = depth;
	}

	/**
	 * Return the move that maximizes the score according to the minimax
	 * algorithm described above.
	 */
	protected @Override Location nextMove(Game g) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Location> location = new ArrayList<Location>();

		for (Location l : moves(g.getBoard())) {
			if (pDepth <= 1){
				result.add(this.estimate(g.getBoard().update(p, l)));
			}
			else{
				result.add(this.scoreBoard(p.opponent(), g.getBoard().update(p, l), pDepth-1));
			}
			location.add(l);
		}
		return location.get(result.indexOf(Collections.max(result)));
	}

	protected int scoreBoard(Player pTemp, Board b, int dep) {
		if (b.getState() == State.DRAW)
			return 0;
		else if (b.getState() != State.NOT_OVER) {
			if (b.getWinner().winner == p)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		}


		HashMap<Integer, Location> mainMap = new HashMap<>();
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (Location l : this.moves(b)) {
			if(dep == 1){
				result.add(estimate(b.update(pTemp, l)));
			}else {
				result.add(scoreBoard(pTemp.opponent(), b.update(pTemp, l), dep-1));
			}

		}

		if (pTemp != p) {
			return result.get(result.indexOf(Collections.min(result)));
		}
		else {
			return result.get(result.indexOf(Collections.max(result)));
		}
	}
}









