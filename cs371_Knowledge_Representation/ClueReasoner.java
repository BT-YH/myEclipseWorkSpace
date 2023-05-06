/**
 * ClueReasoner.java - project skeleton for a propositional reasoner
 * for the game of Clue.  Unimplemented portions have the comment "TO
 * BE IMPLEMENTED AS AN EXERCISE".  The reasoner does not include
 * knowledge of how many cards each player holds.  See
 * http://cs.gettysburg.edu/~tneller/nsf/clue/ for details.
 *
 * @author Todd Neller
 * @version 1.0
 *

Copyright (C) 2005 Todd Neller

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

Information about the GNU General Public License is available online at:
  http://www.gnu.org/licenses/
To receive a copy of the GNU General Public License, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
02111-1307, USA.

 */

import java.io.*;
import java.util.*;

public class ClueReasoner 
{
    private int numPlayers;
    private int playerNum;
    private int numCards;
    private SATSolver solver;    
    private String caseFile = "cf";
    private String[] players = {"sc", "mu", "wh", "gr", "pe", "pl"};
    private String[] suspects = {"mu", "pl", "gr", "pe", "sc", "wh"};
    private String[] weapons = {"kn", "ca", "re", "ro", "pi", "wr"};
    private String[] rooms = {"ha", "lo", "di", "ki", "ba", "co", "bi", "li", "st"};
    private String[] cards;

    public ClueReasoner()
    {
        numPlayers = players.length;

        // Initialize card info
        cards = new String[suspects.length + weapons.length + rooms.length];
        int i = 0;
        for (String card : suspects)
            cards[i++] = card;
        for (String card : weapons)
            cards[i++] = card;
        for (String card : rooms)
            cards[i++] = card;
        numCards = i;

        // Initialize solver
        solver = new SimpleSATSolver(10000, 0.01); //new SAT4JSolver();
        addInitialClauses();
    }

    private int getPlayerNum(String player) 
    {
        if (player.equals(caseFile))
            return numPlayers;
        for (int i = 0; i < numPlayers; i++)
            if (player.equals(players[i]))
                return i;
        System.out.println("Illegal player: " + player);
        return -1;
    }

    private int getCardNum(String card)
    {
        for (int i = 0; i < numCards; i++)
            if (card.equals(cards[i]))
                return i;
        System.out.println("Illegal card: " + card);
        return -1;
    }

    private int getPairNum(String player, String card) 
    {
        return getPairNum(getPlayerNum(player), getCardNum(card));
    }

    private int getPairNum(int playerNum, int cardNum)
    {
        return playerNum * numCards + cardNum + 1;
    }    

    public void addInitialClauses() 
    {
    	// TO BE IMPLEMENTED AS AN EXERCISE

    	// Each card is in at least one place (including case file).
    	for (int c = 0; c < numCards; c++) {
    		int[] clause = new int[numPlayers + 1];
    		for (int p = 0; p <= numPlayers; p++)
    			clause[p] = getPairNum(p, c);
    		solver.addClause(clause);
    	}    

    	// If a card is one place, it cannot be in another place.
    	for (int c = 0; c < numCards; c++)
    		for (int p1 = 0; p1 < numPlayers; p1++)
    			for (int p2 = p1+1; p2 <= numPlayers; p2++) {
    				int[] clause = { -getPairNum(p1, c),
    						-getPairNum(p2, c) };
    				solver.addClause(clause);
    			}

    	// At least one card of each category is in the case file.
    	int[] clause = new int[suspects.length];
    	for (int s1 = 0; s1 < suspects.length; ++s1) {
    		clause[s1] = getPairNum(caseFile, suspects[s1]);
    	}
    	solver.addClause(clause);

    	clause = new int[weapons.length];
    	for (int w1 = 0; w1 < weapons.length; ++w1) {
    		clause[w1] = getPairNum(caseFile, weapons[w1]);
    	}
    	solver.addClause(clause);

    	clause = new int[rooms.length];
    	for (int r1 = 0; r1 < rooms.length; ++r1) {
    		clause[r1] = getPairNum(caseFile, rooms[r1]);
    	}
    	solver.addClause(clause);

    	// No two cards in each category can both be in the case file.
    	for (int s1 = 0; s1 < suspects.length; ++s1) {
    		for (int s2 = (s1 + 1) % suspects.length; s2 != s1; ++s2, s2 %= suspects.length) {
    			clause = new int[] { -getPairNum(caseFile, suspects[s1]),
    					-getPairNum(caseFile, suspects[s2]) };
    			solver.addClause(clause);
    		}
    	}

    	for (int s1 = 0; s1 < weapons.length; ++s1) {
    		for (int s2 = (s1 + 1) % weapons.length; s2 != s1; ++s2, s2 %= weapons.length) {
    			clause = new int[] { -getPairNum(caseFile, weapons[s1]),
    					-getPairNum(caseFile, weapons[s2]) };
    			solver.addClause(clause);
    		}
    	}

    	for (int s1 = 0; s1 < rooms.length; ++s1) {
    		for (int s2 = (s1 + 1) % rooms.length; s2 != s1; ++s2, s2 %= rooms.length) {
    			clause = new int[] { -getPairNum(caseFile, rooms[s1]),
    					-getPairNum(caseFile, rooms[s2]) };
    			solver.addClause(clause);
    		}
    	}
    }
        
    public void hand(String player, String[] cards) 
    {
    	playerNum = getPlayerNum(player);

    	// TO BE IMPLEMENTED AS AN EXERCISE
    	boolean[] playerHasCard = new boolean[numCards];
    	for (String card : cards) {
    		playerHasCard[getCardNum(card)] = true;
    	} for (int i = 0; i < numCards; ++i) {
    		solver.addClause(playerHasCard[i] ?
    				new int[] {  getPairNum(playerNum, i) } :
    					new int[] { -getPairNum(playerNum, i) });
    	}
    }

    public void suggest(String suggester, String card1, String card2, 
                        String card3, String refuter, String cardShown) 
    {
    	// TO BE IMPLEMENTED AS AN EXERCISE
    	if (refuter == null) {
    		for (String player : players) {
    			if (player.equals(suggester));
    			else {
    				int[] sclause = new int[1];
    				sclause[0] = -getPairNum(player, card1);
    				solver.addClause(sclause);
    				int[] wclause = new int[1];
    				wclause[0] = -getPairNum(player, card2);
    				solver.addClause(wclause);
    				int[] rclause = new int[1];
    				rclause[0] = -getPairNum(player, card3);
    				solver.addClause(rclause);
    			}
    		}
    	} else {
    		int suggesterInt = -1, refuterInt = -1;
    		for (int i = 0; i < players.length; ++i) {
    			//get the index position of suggester and refuter
    			if (players[i].equals(suggester)) suggesterInt = i;
    			else if (players[i].equals(refuter)) refuterInt = i;
    		}
    		for (int i = (suggesterInt + 1) % players.length; i != refuterInt; ++i, i %= players.length) {
    			// non-suggesters do not have the cards
    			int[] sclause = new int[1];
    			sclause[0] = -getPairNum(players[i], card1);
    			solver.addClause(sclause);
    			int[] wclause = new int[1];
    			wclause[0] = -getPairNum(players[i], card2);
    			solver.addClause(wclause);
    			int[] rclause = new int[1];
    			rclause[0] = -getPairNum(players[i], card3);
    			solver.addClause(rclause);
    		}
    		if (cardShown != null) {
    			// refuter has the card
    			int[] shownClause = { getPairNum(refuter, cardShown) };
    			solver.addClause(shownClause);
    		} else {
    			// refuter has one of the three cards
    			int[] shownClause = { getPairNum(refuter, card1),
    					getPairNum(refuter, card2),
    					getPairNum(refuter, card3) };
    			solver.addClause(shownClause);
    		}
    	}
    	//System.out.println(suggester + " " + card1 + " " + card2 + " " + card3);
    	//printNotepad();
    }

    public void accuse(String accuser, String card1, String card2, 
    		String card3, boolean isCorrect)
    {
    	// TO BE IMPLEMENTED AS AN EXERCISE
//    	int[] sclause = new int[1];
//    	sclause[0] = -getPairNum(accuser, card1);
//    	solver.addClause(sclause);
//    	int[] wclause = new int[1];
//    	wclause[0] = -getPairNum(accuser, card2);
//    	solver.addClause(wclause);
//    	int[] rclause = new int[1];
//    	rclause[0] = -getPairNum(accuser, card3);
//    	solver.addClause(rclause);

    	if (isCorrect) {
    		int[] clause1 = new int[1];
    		clause1[0] = getPairNum(caseFile, card1);
    		solver.addClause(clause1);
    		int[] clause2 = new int[1];
    		clause2[0] = getPairNum(caseFile, card2);
    		solver.addClause(clause2);
    		int[] clause3 = new int[1];
    		clause3[0] = getPairNum(caseFile, card3);
    		solver.addClause(clause3);
    	} else {
//    		int[] clause1 = { getPairNum(caseFile, card1),
//    				-getPairNum(caseFile, card2),
//    				-getPairNum(caseFile, card3) };
//    		solver.addClause(clause1);
//
//    		int[] clause2 = { -getPairNum(caseFile, card1),
//    				getPairNum(caseFile, card2),
//    				-getPairNum(caseFile, card3) };
//    		solver.addClause(clause2);
//
//    		int[] clause3 = { -getPairNum(caseFile, card1),
//    				-getPairNum(caseFile, card2),
//    				getPairNum(caseFile, card3) };
//    		solver.addClause(clause3);
    		int[] clause = { -getPairNum(caseFile, card1),
    				-getPairNum(caseFile, card2),
    				-getPairNum(caseFile, card3) };
    		solver.addClause(clause);

    	}
    }

    public int query(String player, String card) 
    {
        return solver.testLiteral(getPairNum(player, card));
    }

    public String queryString(int returnCode) 
    {
        if (returnCode == SATSolver.TRUE)
            return "Y";
        else if (returnCode == SATSolver.FALSE)
            return "n";
        else
            return "-";
    }
        
    public void printNotepad() 
    {
        PrintStream out = System.out;
        for (String player : players)
            out.print("\t" + player);
        out.println("\t" + caseFile);
        for (String card : cards) {
            out.print(card + "\t");
            for (String player : players) 
                out.print(queryString(query(player, card)) + "\t");
            out.println(queryString(query(caseFile, card)));
        }
    }
        
    public static void main(String[] args) 
    {
    	ClueReasoner cr = new ClueReasoner();
    	String[] myCards = { "sc", "wh", "kn", "ca" };

    	cr.hand("sc", myCards);
    	cr.printNotepad();

    	cr.suggest("sc", "pl", "wr", "st", "mu", "st");
    	cr.suggest("mu", "pe", "ca", "ba", "pe", null);
    	cr.suggest("wh", "gr", "re", "co", "pe", null);
    	cr.suggest("gr", "wh", "pi", "bi", "pl", null);
    	cr.suggest("pe", "wh", "kn", "ha", "pl", null);
    	cr.suggest("pl", "wh", "pi", "st", "sc", "wh");
//    	      cr.printNotepad();

    	cr.suggest("sc", "mu", "wr", "di", "wh", "wr");
    	cr.suggest("mu", "pe", "ro", "ki", "gr", null);
    	cr.suggest("wh", "pe", "pi", "lo", "pl", null);
    	cr.suggest("gr", "sc", "re", "ki", "pe", null);
    	cr.suggest("pe", "mu", "kn", "ki", "sc", "kn");
    	cr.suggest("pl", "pe", "ca", "ba", "sc", "ca");
//    	      cr.printNotepad();

    	cr.suggest("sc", "sc", "ro", "ki", "wh", "sc");
    	cr.printNotepad();
    	cr.suggest("mu", "mu", "re", "co", "wh", null);
    	cr.suggest("wh", "pl", "re", "lo", "gr", null);
    	cr.suggest("gr", "sc", "ro", "ki", "wh", null);
    	cr.suggest("pe", "wh", "wr", "co", "sc", "wh");
    	cr.accuse("pe", "pl", "wr", "co", false);
    	cr.printNotepad();


//        String[] myCards = {"wh", "li", "st"};
//        cr.hand("sc", myCards);
//        cr.suggest("sc", "sc", "ro", "lo", "mu", "sc");
//        cr.suggest("mu", "pe", "pi", "di", "pe", null);
//        cr.suggest("wh", "mu", "re", "ba", "pe", null);
//        cr.suggest("gr", "wh", "kn", "ba", "pl", null);
//        cr.suggest("pe", "gr", "ca", "di", "wh", null);
//        cr.suggest("pl", "wh", "wr", "st", "sc", "wh");
//        cr.suggest("sc", "pl", "ro", "co", "mu", "pl");
//        cr.suggest("mu", "pe", "ro", "ba", "wh", null);
//        cr.suggest("wh", "mu", "ca", "st", "gr", null);
//        cr.suggest("gr", "pe", "kn", "di", "pe", null);
//        cr.suggest("pe", "mu", "pi", "di", "pl", null);
//        cr.suggest("pl", "gr", "kn", "co", "wh", null);
//        cr.suggest("sc", "pe", "kn", "lo", "mu", "lo");
//        cr.suggest("mu", "pe", "kn", "di", "wh", null);
//        cr.suggest("wh", "pe", "wr", "ha", "gr", null);
//        cr.suggest("gr", "wh", "pi", "co", "pl", null);
//        cr.suggest("pe", "sc", "pi", "ha", "mu", null);
//        cr.suggest("pl", "pe", "pi", "ba", null, null);
//        cr.suggest("sc", "wh", "pi", "ha", "pe", "ha");
//        cr.suggest("wh", "pe", "pi", "ha", "pe", null);
//        cr.suggest("pe", "pe", "pi", "ha", null, null);
//        cr.suggest("sc", "gr", "pi", "st", "wh", "gr");
//        cr.suggest("mu", "pe", "pi", "ba", "pl", null);
//        cr.suggest("wh", "pe", "pi", "st", "sc", "st");
//        cr.suggest("gr", "wh", "pi", "st", "sc", "wh");
//        cr.suggest("pe", "wh", "pi", "st", "sc", "wh");
//        cr.suggest("pl", "pe", "pi", "ki", "gr", null);
//        cr.printNotepad();
//        cr.accuse("sc", "pe", "pi", "bi", true);
    }           
}
