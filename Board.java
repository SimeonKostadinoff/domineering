// An abstract two-player game with outcomes in the integers.
// We define a particular game by implementing the abstract methods.
//
// Our approach requires immutable implementations of Board.  We
// require that the only public constructor builds the initial board.
// Other constructors may be used for private purposes.

import java.util.LinkedHashMap;
import java.util.Set;

public abstract class Board<Move> {
  abstract Player nextPlayer();
  abstract Set<Move> availableMoves(); 
  abstract int value(); 
  abstract Board<Move> play(Move move);

  // Constructs the game tree of the board using the minimax algorithm
  // (without alpha-beta pruning):
  public GameTree<Move> tree() {
	  //System.out.println("Availa: " + availableMoves().isEmpty());
    if (availableMoves().isEmpty())
      return new GameTree<Move>
                    (this, 
                     new LinkedHashMap<Move,GameTree<Move>>(), 
                     value());
    else
      return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree()); 
  }

  // Two helper methods for that, which call the above method tree:
  public GameTree<Move> maxTree() {
    assert(!availableMoves().isEmpty());
    //System.out.println("Empty n max tree" + availableMoves().isEmpty());
    int optimalOutcome = Integer.MIN_VALUE;
    LinkedHashMap<Move,GameTree<Move>> children 
                 = new LinkedHashMap<Move,GameTree<Move>>(); 

    for (Move m : availableMoves()) {
    	//System.out.println("in the loop: " + availableMoves());
      GameTree<Move> subtree = play(m).tree();
      //System.out.println(optimalOutcome + " " + subtree.optimalOutcome());
      children.put(m,subtree);
      optimalOutcome = Math.max(optimalOutcome,subtree.optimalOutcome());
    }

    return new GameTree<Move>(this,children,optimalOutcome); 
  }

  public GameTree<Move> minTree() {
    assert(!availableMoves().isEmpty());
    //System.out.println("Empty n min tree" + availableMoves().isEmpty());
    int optimalOutcome = Integer.MAX_VALUE;
    LinkedHashMap<Move,GameTree<Move>> children 
                 = new LinkedHashMap<Move,GameTree<Move>>(); 

    for (Move m : availableMoves()) {
      GameTree<Move> subtree = play(m).tree();
      children.put(m,subtree);
      optimalOutcome = Math.min(optimalOutcome,subtree.optimalOutcome());
    }

    return new GameTree<Move>(this,children,optimalOutcome); 
  }
}
