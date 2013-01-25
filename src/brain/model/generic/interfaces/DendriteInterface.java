package brain.model.generic.interfaces;


public interface DendriteInterface<T> {
	
	/** Sets expected input. 
	 * Also makes sure input is within range. Currently range is: [A,Z] 
	 * 
	 * @param newExpectation 	the input
	 */
	public void setExpectation(T newExpectation);
	
	
	/** Retrieve last expected input */ 
	public T getLastInputWanted();
	
	
	public T getExpectation();
	
	
	/** Retrieves match strength of this dendrite with respect to the input
	 * 
	 * @param input		a letter to match current expectation against
	 * @return	percentage match bw expectation and input [0,1]/6
	 */
	public double getMatchStrength(T input);

}
