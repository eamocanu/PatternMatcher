package brain.main.customsample;

import java.util.List;

import brain.model.generic.interfaces.DendriteInterface;

/**
 * This is a sample class. Make your own like this.
 * 
 * @author Adrian
 *
 */
public class CustomDendrite implements DendriteInterface<List<Integer>> {

	public CustomDendrite() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setExpectation(List<Integer> newExpectation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getLastInputWanted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getExpectation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMatchStrength(List<Integer> input) {
		// TODO Auto-generated method stub
		return 0;
	}

}
