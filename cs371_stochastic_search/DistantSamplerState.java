import java.util.Arrays;
import java.util.Random;

public class DistantSamplerState implements State {

	private static double[][] data;
	private int numSamples, rowIndex, prevRowIndex, prevSampleIndex;
	private int[] indices;

	DistantSamplerState(double[][] data, int numSamples) {
		DistantSamplerState.data = data;
		this.numSamples = numSamples;
		
		indices = new int[data.length];
		for (int i = 0; i < data.length; ++i) {
			indices[i] = i;
		}
//		sample = new int[numSamples];
//		for (int i = 0; i < sample.length; ++i) {
//			sample[i] = -1;
//		}
//		for (int i = 0; i < sample.length; ++i) {
//			boolean repeated = false;
//
//			do {
//				rowIndex = (int)(Math.random() * data.length);
//				repeated = false;
//				for (int f = 0; !repeated && f < sample.length; ++f)
//					if (sample[i] == rowIndex)
//						repeated = true;
//			} while (repeated);
//
//			sample[i] = rowIndex;
//		}
	}

	@Override
	public void step() {
		prevRowIndex = (int)(Math.random() * numSamples);
		rowIndex = (int)(Math.random() * (data.length - numSamples)) + numSamples;
		
		int temp = indices[prevRowIndex];
		indices[prevRowIndex] = indices[rowIndex];
		indices[rowIndex] = temp;
//		boolean repeated = false;
//
//		do {
//			rowIndex = (int)(Math.random() * data.length);
//			repeated = false;
//			for (int i = 0; !repeated && i < sample.length; ++i)
//				if (sample[i] == rowIndex)
//					repeated = true;
//		} while (repeated);
//
//		prevSampleIndex = (int)(Math.random() * sample.length);
//		prevRowIndex = sample[prevSampleIndex];
//		sample[prevSampleIndex] = rowIndex;
	}

	@Override
	public void undo() {
		int temp = indices[prevRowIndex];
		indices[prevRowIndex] = indices[rowIndex];
		indices[rowIndex] = temp;
	}

	@Override
	public double energy() {
		double e = 0;
		for (int i = 0; i < numSamples; ++i) {
			for (int j = i + 1; j < numSamples; ++j) {
				double sum = 0;
				for (int f = 0; f < data[indices[i]].length; ++f) {
					sum += (data[indices[i]][f] - data[indices[j]][f]) * (data[indices[i]][f] - data[indices[j]][f]);
				}
				e += 1 / sum;
//					e += Math.sqrt((1 / sum));
			}
		}
		if (Double.isInfinite(e)) {
			System.out.println(prevSampleIndex + "\t" + prevRowIndex + "\t" + rowIndex);
			System.out.println(this);
		}
		return e;
	}
	
	public DistantSamplerState search(int iterations, double temperature, double decayRate) {
		Random random = new Random(); //***
		double minEnergy = Integer.MAX_VALUE;
		double energy = energy();
		DistantSamplerState minState = null;
		for (int i = 0; i < iterations; i++) {
//			if (i % 1000 == 0)
//				System.out.println(minEnergy + "\t" + energy);
			step();
			double nextEnergy = energy();
			if (nextEnergy <= energy || random.nextDouble() < Math.exp((energy - nextEnergy) / temperature)) { //***
				energy = nextEnergy;
				if (nextEnergy < minEnergy) { 
					minState = (DistantSamplerState) clone();
					minEnergy = nextEnergy;
				}
			} else
				undo();
			
			temperature *= decayRate; //***
		}
		return minState;
	}
	
	public int[] getSampleIndices() {
		int[] sample = new int[numSamples];
		System.arraycopy(indices, 0, sample, 0, numSamples);
		Arrays.sort(sample);
		return sample;
	}

	public DistantSamplerState clone() {
		try {
			DistantSamplerState copy = (DistantSamplerState)super.clone();
			copy.indices = (int[])indices.clone();
			return copy;
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	public String toString() {
		return Arrays.toString(getSampleIndices());
	}
}
