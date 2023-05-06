import java.util.function.DoubleFunction;
import java.lang.Double;

public class DoubleFunctionProcessor {
	
	private double value;
	
	public DoubleFunctionProcessor(double initValue) {
		value = initValue;
	}
	
	public double getValue() {
		return value;
	}
	
	public void process(DoubleFunction<Double> function) {
		synchronized(this) {
			value = function.apply(this.getValue());
		}
	}
}
