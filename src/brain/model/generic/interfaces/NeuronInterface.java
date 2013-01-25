/**
 * 
 */
package brain.model.generic.interfaces;

/**
 * 
 */

/**
 * @author Adrian
 * Abstraction of a neuron. 
 * Neurons get input which they store in dendrites. Each dendrite corresponds to one input.
 * 
 * If neurons are allowed to learn, their dendrites will start adjusting themselves to the 
 * input neurons see if that input is close to what the neurons already know. In other words,
 * the neuron best matching the input will become closer to the input. If the same input is seen
 * multiple times (around 10), the neuron best matching that input will learn it and be locked
 * in, meaning that it will not be able to learn new patterns.
 * 
 * Neurons forget their data slowly. 
 */
public interface NeuronInterface<T> {
	final double MAX_SIGNAL  = 1; //100%
	//final static Neuron EMPTY = new Neuron();
	final double HIGH_CONFIDENCE = 0.66;
	final int MAX_NEURON_DATA_LENGTH = 20;
//	final static NeuronInterface EMPTY;
	
	
	/**Calculate match strength for given input in order to determine how well
	 * this neuron matches the input.
	 * 
	 * If input is longer that this neuron, it uses a 0 match in 
	 * places (input[i]) where this is shorter that input
	 * 
	 * If neuron data is longer that input, it uses a value of 0 in 
	 * places where data[i] exists but input[i] does not exist
	 * 
	 * @param input		input to calculate match strength against
	 */
	public void calculateMatchStrength(T input);
	
	
	
	/** Calculate confidence of neuron and allow it to get closer to given 
	 * input (if neuron is allowed to learn).
	 * 
	 * @param input			input to check against
	 * @param bestMatch		neuron which best matches the input (highest match strength of all neurons)
	 */
	public void calculateMatchPreciseness(T input, NeuronInterface<T> bestMatch);
	
	
	/** Check if this neuron's confidence is high */
	public boolean hasHighConfidence();

	
	/** Retrieve neuron's match strength against last given input */
	public double getMatchStrength();

	
	/** Retrieve neuron's overall confidence */
	public double getConfidence() ;
 

	//public List<DendriteInterface<T>> getDendrites();
	public <V> V getDendrites();
	
	
	/** Retrieve current data stored in this neuron. Traverses all dendrites to retrieve it.
	 * Thus runtime is O(dendrites)
	 * 
	 * @return	neuron expected data
	 */
	public T getData();
	
	public void setData(T newData);
	

	/** Override neuron confidence
	 * When confidence is low neuron is able to learn new patterns faster
	 * When confidence is high, neuron stops learning and is locked remembering the learned pattern
	 * 
	 * @param confidence	in percent; interval [0,1]
	 */
	public void setConfidence(double confidence);
	

	/** Retrieves ability of neuron to learn. If it is free, it can learn new patterns.
	 * 
	 * @return	true is this neuron is free to learn patterns
	 * 			false if it already learned a pattern well (with 90% confidence)
	 */
	public boolean isFree();
	
}

