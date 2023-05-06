import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GroupingProblem implements State {

	private static Random rand = new Random();
	private int classSize, numGroups;
	private int lastIndex, currIndex;
	private int[][] prefs;
	private int[] groups;

	public GroupingProblem(int classSize, int numGroups, int prefs[][]) {
		this.classSize = classSize;
		this.numGroups = numGroups;
		this.prefs = prefs;

		groups = new int[classSize];
		for (int i = 0; i < classSize; ++i)
			groups[i] = i;
	}

	@Override
	public void step() {
		int i = rand.nextInt(classSize);
		int f = rand.nextInt(classSize);

		while (i % numGroups == f % numGroups) {
			f = rand.nextInt(classSize);
		}

		lastIndex = f;
		currIndex = i;
		int temp = groups[i];
		groups[i] = groups[f];
		groups[f] = temp;
	}

	@Override
	public void undo() {
		int temp = groups[lastIndex];
		groups[lastIndex] = groups[currIndex];
		groups[currIndex] = temp;
	}

	@Override
	public double energy() {
		double e = 0;
		for (int i = 0; i < groups.length; ++i) {
			double c = 0;
			int size = 0;
			for (int f = 0; f < groups.length; ++f) {
				if (i != f && i % numGroups == f % numGroups) {
					c += prefs[groups[i]][groups[f]];
					++size;
				}
			}
			e += (size * 10) - c;
		}
		return e;
	}

	@Override
	public Object clone() {
		try {
			GroupingProblem copy = (GroupingProblem)super.clone();
			copy.groups = (int[])groups.clone();
			return copy;
		} catch (Exception e) {}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int f = 0; f < numGroups; ++f) {
			sb.append(String.format("Group: %d\t", f));
			for (int i = 0; i < classSize; ++i) {
				if (i % numGroups == f) {
					sb.append(String.format("%d ", groups[i]));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		final int ITERATIONS = 10000000;
		File file = new File("group-input.txt");
		Scanner scan = new Scanner(file);
		int classSize = scan.nextInt();
		int numGroups = scan.nextInt();
		int[][] prefs = new int[classSize][classSize];
		for (int i = 0; i < classSize; ++i) {
			for (int f = 0; f < classSize; ++f) {
				prefs[i][f] = scan.nextInt();
			}
		}
		GroupingProblem state = new GroupingProblem(classSize, numGroups, prefs);
		State minState = new SimulatedAnnealer(state, 10000, 0.999999).search(ITERATIONS);
		System.out.println(minState);
		System.out.println("Energy: " + minState.energy());
	}
}
