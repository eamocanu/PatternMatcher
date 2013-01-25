package brain.model.generic.interfaces;

import java.util.Collection;


/**
 * @author Adrian
 * 
 */
public interface NeuronLayerInterface <N extends NeuronInterface<I>, I> {
	
	
	/** Create a neuron layer with neurons containing created from data container 
	 * 
	 * @param data	container with data to create neurons from
	 * @return 
	 */
	public void NeuronLayer(Collection<N> data);
	
	
	/** Add externally created neurons to this layer */
	public void addNeuron(N neuron);
	
	public Collection<N> getNeurons();
	

	/** Retrieve best matching neuron given current input
	 * First check all neurons and calculate match strength preciseness for them: O(n).
	 * Then, calculate match preciseness (based on neurons confidence): O(n).
	 * O(n)+O(n) = O(n) runtime 
	 * 
	 * @param 	input string to try to match with a neuron
	 * 			it will be converted to upper case bc neurons are in upper case
	 * @return  best matching neuron for the input
	 */
	public N getBestMatch(I input);
	

	/** Retrieves best matching neuron */
	public N getBestMatch();
	
	
	/** Retrieve current input for this layer of neurons */
	public I getCurrentInput();
	
	
	public void printAllNeurons();
	
	
}