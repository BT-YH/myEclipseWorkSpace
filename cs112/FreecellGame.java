import java.util.Scanner;
import java.util.Stack;

public class FreecellGame {
	
	private CardStack[] stacks = new CardStack[17];
	
	public FreecellGame(long seed) {
		stacks[0] = new Deck(seed);
		for (int i = 1; i < stacks.length; i++) { // change
			if (i < 5) { 
				stacks[i] = new Cell();
			} else if (i < 9) {
				stacks[i] = new Foundation();
			} else {
				stacks[i] = new Cascade();
			}
			//stacks[i] = new CardStack();
		}
		// Deal deck
		int i = 9;
		while (!stacks[0].isEmpty()) {
			stacks[i++].addCard(stacks[0].removeTopCard());
			if (i == stacks.length) {
				i = 9;
			}
		}
		
//		for (i = 1; i < 17; i++) {
//			if (i < 5) {
//				Cell cell = new Cell();
//				cell.stack = stacks[i].stack;
//				stacks[i] = cell;
//			} else if (i < 9) {
//				Foundation foundation = new Foundation();
//				foundation.stack = stacks[i].stack;
//				stacks[i] = foundation;
//			} else {
//				Cascade cascade = new Cascade();
//				cascade.stack = stacks[i].stack;
//				stacks[i] = cascade;
//			}
//		}
		
		
		
	}
	
	public boolean play(int srcStackNum, int destStackNum) throws IllegalPlayException {
		if (srcStackNum < 1 || srcStackNum > 16 || destStackNum < 1 || destStackNum > 16) {
			throw new IllegalPlayException("Illegal stack number. Stacks are numbered 1-16.");
		} else if (srcStackNum == destStackNum) {
			throw new IllegalPlayException("Source and destination stacks must be different.");
		} 
		return stacks[destStackNum].playTo(stacks[srcStackNum]);
	}
	
	public Card[] getStack(int stackNum) {
		int size = stacks[stackNum].stack.size();
		Stack<Card> stack = new Stack<>();
		stack = (Stack<Card>) stacks[stackNum].stack.clone(); // copying the stack
		
		Card[] card = new Card[size];
		for (int i = size - 1; i >= 0 ; i--) {
			card[i] = stack.pop();
		}
		return card;
	}
	
	public boolean isGameOver() {
		int sum = 0;
		for (int i=5; i <= 8; i++) {
			sum += stacks[i].size();
		}
		return sum == 52;
	}
	
	public String toString() {
		String cells = String.format("%s\n 1: %s\n 2: %s\n 3: %s\n 4: %s\n", "Cells:", 
				stacks[1].toString(), stacks[2].toString(),stacks[3].toString(),stacks[4].toString());
		String foundations = String.format("%s\n 5: %s\n 6: %s\n 7: %s\n 8: %s\n", "Foundations:", 
				stacks[5].toString(), stacks[6].toString(), stacks[7].toString(), stacks[8].toString());
		String cascades = String.format("%s\n 9: %s\n10: %s\n11: %s\n12: %s\n13: %s\n14: %s\n15: %s\n16: %s\n", "Cascades:", 
				stacks[9].toString(), stacks[10].toString(), stacks[11].toString(), stacks[12].toString(), 
				stacks[13].toString(), stacks[14].toString(), stacks[15].toString(), stacks[16].toString());
		
		return cells + foundations + cascades;
		
	}
	
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 1; i < stacks.length; i++) {
//			
//			sb.append(String.format("%2d: %s\n", i, stacks[i]));
//		}
//		return sb.toString();
//	}
//	

}
