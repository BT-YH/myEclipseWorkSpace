import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Random;


public class ParallelDiceRoll {
	
	public static void assignRandomRollsParallel(int[] rolls) {
		RecursiveAction parallelAction = new ParallelAction(rolls, 0, rolls.length);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(parallelAction);
	}
	
	public static void assignRandomRollsSerial(int[] rolls) {
		Random random = new Random();
		for (int i = 0; i < rolls.length; i++) {
			int num = (random.nextInt(6)) + 1;
			rolls[i] = num;
		}
	}
	
	public static void main(java.lang.String[] args) {
		int size = 20000000;
		
		int[] rolls1 = new int[size];
		int[] rolls2 = new int[size];
 		
        long ms = System.currentTimeMillis();
        assignRandomRollsSerial(rolls1);
        System.out.printf("Serial assignment: %d ms\n", System.currentTimeMillis() - ms);
        
        
        ms = System.currentTimeMillis();
        assignRandomRollsParallel(rolls2);
        System.out.printf("Parallel assignment: %d ms\n", System.currentTimeMillis() - ms);
	}
	

	static class ParallelAction extends RecursiveAction {
		int[] rolls;
		//int SIZE;
		final int SPLIT_THRESHOLD = 50000;
		private int low;
		private int high;
		Random random = new Random();
		
		public ParallelAction(int[] rolls, int low, int high) {
			super();
			this.rolls = rolls;
			//this.SIZE = rolls.length;
			this.low = low;
			this.high = high;
		}
		
		@Override
		protected void compute() {
			if ((high - low) < SPLIT_THRESHOLD) {
				// Sequential computation for smaller blocks
				for (int i = low; i < high; i++) {
					int num = (random.nextInt(6)) + 1;
					rolls[i] = num;
				}
			}
			else {
				int mid = (low + high) / 2;
				RecursiveAction left = new ParallelAction(rolls, low, mid);
				RecursiveAction right = new ParallelAction(rolls, mid, high);
				
				invokeAll(left, right);
				
//				int[] left = new int[rolls.length / 2];
//				System.arraycopy(rolls, 0, left, 0, rolls.length / 2);
//				
//				int rightLength = rolls.length - rolls.length / 2;
//				int[] right = new int[rightLength];
//				System.arraycopy(rolls, rolls.length / 2, right, 0, rightLength);
//				
//				invokeAll(new ParallelAction(left), new ParallelAction(right));
//				
//				System.arraycopy(left, 0, rolls, 0, rolls.length / 2);
//				System.arraycopy(right, 0, rolls, rolls.length / 2, rightLength);
			}
		}
		
	}
}
