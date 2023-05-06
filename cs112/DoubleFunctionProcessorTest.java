import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DoubleFunctionProcessorTest {
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ArrayList<Double> values = new ArrayList<Double>();
		final int NUM_VALUES = 100;
		for (int i = 0; i < NUM_VALUES; i++)
			values.add((double) ((int) (NUM_VALUES * Math.random()) - (NUM_VALUES / 2))); 
		Collections.shuffle(values);
		final DoubleFunctionProcessor processor = new DoubleFunctionProcessor(0);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (double value : values) 
			executor.execute(() -> {
				try {
					processor.process(x -> {
						sleep(5);
						return x + value;
					});
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		Collections.shuffle(values);
		for (double value : values) 
			executor.execute(() -> {
				try {
					processor.process(x -> {
						sleep(5);
						return x - value;
					});
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		executor.shutdown();
		while (!executor.isTerminated())
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("Final value should be zero: " + processor.getValue());
	}

}
