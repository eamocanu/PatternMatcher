/**
 * 
 */
package brain.main.customsample;

import java.util.List;

import brain.model.generic.interfaces.NeuronInterface;

/**
 * This is a sample class. Make your own like this.
 * 
 * @author Adrian
 */
public class CustomNeuron implements NeuronInterface<List<Integer>> {

	/**
	 * 
	 */
	public CustomNeuron() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void calculateMatchStrength(List<Integer> input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateMatchPreciseness(List<Integer> input,
			NeuronInterface<List<Integer>> bestMatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasHighConfidence() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getMatchStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getConfidence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <V> V getDendrites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(List<Integer> newData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfidence(double confidence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return false;
	}

}
