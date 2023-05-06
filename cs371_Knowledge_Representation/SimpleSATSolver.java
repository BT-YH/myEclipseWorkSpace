import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * SimpleSATSolver.java - a simple variation of the WalkSAT algorithm.
 * See http://cs.gettysburg.edu/~tneller/nsf/clue/ for details.
 *
 * @author Todd Neller
 *

Copyright (C) 2019 Todd Neller

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


public class SimpleSATSolver extends SATSolver
{
	private Random random = new Random();
	private int maxVar = 0;
	private boolean[] assignment;
	private int iterations;
	private double probRandomFlip;

	/**
	 * @param iterations
	 * @param probRandomFlip
	 */
	public SimpleSATSolver(int iterations, double probRandomFlip) {
		this.iterations = iterations;
		this.probRandomFlip = probRandomFlip;
	}

	public boolean makeQuery() 
	{
		
		// get all KB and query clauses together and determine the maxVar
		ArrayList<int[]> allClauses = new ArrayList<>(clauses);
		allClauses.addAll(queryClauses);
		for (int[] clause : allClauses) {
			for (int literal : clause) {
				maxVar = Math.max(maxVar, Math.abs(literal));
			}
		}
		
		// initialize a random truth assignment
		assignment = new boolean[maxVar + 1]; // we won't use index 0
		for (int i = 1; i <= maxVar; i++) {
			assignment[i] = random.nextBoolean();
		}
		
		// iteratively search for a model (satisfying truth assignment)
		ArrayList<Integer> unsatClauseIndices = getUnsatClauseIndices(allClauses);
		for (int i = 0; i < iterations && !unsatClauseIndices.isEmpty(); i++) {
			
			// get a random unsatisfied clause
			int clauseNum = unsatClauseIndices.get(
					random.nextInt(unsatClauseIndices.size()));
			int[] clause = allClauses.get(clauseNum);
			
			// flip the truth assignment of a random literal of that clause
			int literal = clause[random.nextInt(clause.length)];
			int var = Math.abs(literal);
			assignment[var] = !assignment[var]; // flip
			ArrayList<Integer> newUnsatClauseIndices = getUnsatClauseIndices(allClauses);
			
			// unflip the assignment if more clauses are unsatisfied (unless small probability)
			if (newUnsatClauseIndices.size() > unsatClauseIndices.size()
				&& random.nextDouble() >= probRandomFlip) {
				assignment[var] = !assignment[var];
			} 
			else {
				unsatClauseIndices = newUnsatClauseIndices;
			}
			
		}
		

		// return whether or not model was found
		return unsatClauseIndices.isEmpty();
	}
	
	/**
	 * Energy of the local search - number of unsatisfied clauses
	 * @param allClauses
	 * @return
	 */
	private ArrayList<Integer> getUnsatClauseIndices(ArrayList<int[]> allClauses) {
		ArrayList<Integer> clauseIndices = new ArrayList<>();
		for (int i = 0; i < allClauses.size(); i++) {
			int[] clause = allClauses.get(i);
			boolean satisfied = false;
			for (int j = 0; !satisfied && j < clause.length; j++) {
				int literal = clause[j];
				int varNum = Math.abs(literal);
				satisfied = (assignment[varNum] == literal > 0); // positive literal <=> true     
															     // negative literal <=> false
			}
			if (!satisfied) {
				clauseIndices.add(i);
			}
		}
		
		return clauseIndices;
	}
		
	public static void main(String[] args) {

		// Liar and truth-teller example test code:

		int[][] clauses = {{-1, -2}, {2, 1}, {-2, -3}, {3, 2}, {-3, -1}, {-3, -2}, {1, 2, 3}};
		SimpleSATSolver s = new SimpleSATSolver(10000, .01);
		for (int i = 0; i < clauses.length; i++)
			s.addClause(clauses[i]);
		System.out.println("Knowledge base is satisfiable: " + s.makeQuery());
		String[] names = {"", "Amy", "Bob", "Cal"};
		for (int i = 1; i <= 3; i++) {
			System.out.print("Is " + names[i] + " a truth-teller? ");
			int result = s.testLiteral(i);
			if (result == FALSE)
				System.out.println("No.");
			else if (result == TRUE)
				System.out.println("Yes.");
			else
				System.out.println("Unknown.");
		}

		// Reading from standard input:
		// Example usage: java SimpleSATSolver < liar-truthteller-example.txt
		s = new SimpleSATSolver(10000, .01);
		System.out.println("Enter clauses one-per-line with space-separated integer notation:");
		s.readClauses(System.in);
		System.out.println("Var\tTrue?");
		for (int i = 1; i <= s.maxVar; i++) {
			int result = s.testLiteral(i);
			System.out.printf("%d\t%s\n", i, 
					result == FALSE ? "No." : (result == TRUE ? "Yes." : "Unknown."));
		}
	}

	public void readClauses(InputStream inputStream) {
		Scanner in = new Scanner(inputStream);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			Scanner lineIn = new Scanner(line);
			ArrayList<Integer> clause = new ArrayList<Integer>();
			while (lineIn.hasNextInt()) {
				int var = lineIn.nextInt();
				if (Math.abs(var) > maxVar)
					maxVar = Math.abs(var);
				clause.add(var);
			}
			lineIn.close();
			if (!clause.isEmpty()) {
				int[] clauseArr = new int[clause.size()];
				for (int i = 0; i < clause.size(); i++)
					clauseArr[i] = clause.get(i);
				clauses.add(clauseArr);
			}
		}
		in.close();
	}
}
