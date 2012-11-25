package brain.model;

import java.util.Random;

/**
 * @author Adrian
 *
 * Dendrite abstraction class used to model a neuron dedrite.
 * A dendrite has an expected input which it matches against a given input (current input)
 * Based on the loseness of match it calculates a match strength variable in percent.
 */
public class Dendrite {
	/** Save last input */
	private char lastInputWanted=' ';
	
	/** Expected input to this dendrite */
	private char expectation;
	
	/** How well this dendrite's expected input matches current input */
	private double matchStrength=0;

	//range of values for dendrite expectations
	final char MIN_SIGNAL  = 0;//'A';
	final char MAX_SIGNAL  = 'Z'-'A';//'Z'; //maximum signal is given when the difference bw input vs expectation is 0
	
	
	
	/** Sets random expectation for this dendrite */ 
	public Dendrite() {
		setExpectation(getRandomExpectation());
	}
	
	
	/** Choose a random character as expectation 
	 * 
	 * @return	a randomly chosen value that is within range.
	 */
	private char getRandomExpectation() {
		Random r=new Random();
		int i=(r.nextInt('Z'-'A') +'A');
		return (char)i;
	}


	/** Sets value for this dendrite
	 * 
	 * @param expectation	expectation for this dendrite; its initial value
	 */
	public Dendrite(/*Neuron neuron,*/ char expectation) {
		setExpectation(expectation);
	}
	
	
	/** Sets expected input. 
	 * Also makes sure input is within range. Currently range is: [A,Z] 
	 * 
	 * @param newExpectation 	the input
	 */
	public void setExpectation(char newExpectation){
		expectation= newExpectation;///('Z'-'A');
		if (expectation<'A') expectation = 'A';
		if (expectation>'Z') expectation = 'Z';
	}
	
	
	/** Retrieve last expected input */ 
	public char getLastInputWanted(){
		return lastInputWanted;
	}
	
	
	public char getExpectation(){
		return expectation;
	}
	
		
	/** Retrieves match strength of this dendrite with respect to the input
	 * 
	 * @param input		a letter to match current expectation against
	 * @return	percentage match bw expectation and input [0,1]/6
	 */
	public double getMatchStrength(char input){
		lastInputWanted= input;//save this input
		double strength= (double)(MAX_SIGNAL - Math.abs( input - expectation))/MAX_SIGNAL; //div by MAX_SIGNAL to get %strength
		return strength;
	}

	
	public String toString(){
		return ""+expectation + " s: "+matchStrength;
	}
	
}
