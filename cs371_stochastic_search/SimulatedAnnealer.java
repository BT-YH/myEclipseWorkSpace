import java.util.Random;

public class SimulatedAnnealer  {
	State state;
	double energy;
	State minState;
	double minEnergy;
	double acceptRate = 0; // allowing things to get worse so that 
						   // they can get better
	double initTemp;
	double decayRate;  	  // annealing scheule
	
	public SimulatedAnnealer(State initState, double initTemp, double decayRate) {
		state = initState;
		energy = initState.energy();
		minState = (State) state.clone();
		minEnergy = energy;
		this.initTemp = initTemp;
		this.decayRate = decayRate;
	}
	
	public State search(int iterations) {
		Random random = new Random(); //***
		double temperature = initTemp; //***
		for (int i = 0; i < iterations; i++) {
			if (i % 1000 == 0)
				System.out.println(minEnergy + "\t" + energy);
			state.step();
			double nextEnergy = state.energy();
			if (nextEnergy <= energy || random.nextDouble() < Math.exp((energy - nextEnergy) / temperature)) { //***
				energy = nextEnergy;
				if (nextEnergy < minEnergy) { 
					minState = (State) state.clone();
					minEnergy = nextEnergy;
				}
			} else
				state.undo();
			
			temperature *= decayRate; //***
		}
		return minState;
		// *** search changes from HillDescender
	}
}
