/**
 * 
 */
package brain.model.images;

import brain.model.generic.interfaces.DendriteInterface;

/**
 * @author Adrian
 *
 */
public class ImageDendrite implements DendriteInterface<Integer> {
	/** Save last input */
	private Integer lastInputWanted=0;//black
	
	/** Expected input to this dendrite */
	private Integer expectation;
	
	/** How well this dendrite's expected input matches current input */
	private double matchStrength=0;

	//range of values for dendrite expectations
	final int MIN_SIGNAL  = 0;
	final int MAX_SIGNAL  = 0xFFFFFF;
	
	
	
	
	/** Limits RGB value to max of 0xFFFFFF specified in MAX_SIGNAL
	 * 
	 * @param rgbValue
	 */
	public ImageDendrite(int rgbValue) {
		setExpectation(rgbValue);
	}
	
	
	/** Limits RGB value to max of 0xFFFFFF specified in MAX_SIGNAL and min of 0 MIN_SIGNAL
	 */
	@Override
	public void setExpectation(Integer newExpectation) {
		expectation= newExpectation;///('Z'-'A');
//		if (expectation<MIN_SIGNAL) expectation = MIN_SIGNAL;
////		if (expectation>MAX_SIGNAL) expectation = MAX_SIGNAL;
//		this.expectation= newExpectation & MAX_SIGNAL;
	}

	@Override
	public Integer getLastInputWanted() {
		return lastInputWanted;
	}

	@Override
	public Integer getExpectation() {
		return expectation;
	}

	@Override
	public double getMatchStrength(Integer input) {
		//lastInputWanted= MAX_SIGNAL & input;//save this input
		lastInputWanted= input;//save this input
		double strength= (double)(MAX_SIGNAL - Math.abs( lastInputWanted - expectation))/MAX_SIGNAL; //div by MAX_SIGNAL to get %strength
		return strength;
	}
	

	public int getRed(){ return expectation & 0xff0000;}
	public int getGreen(){ return expectation & 0x00ff00;}
	public int getBlue(){ return expectation & 0x0000ff;}
	
	
	public String toString(){
		return ""+expectation + " s: "+matchStrength;
	}
}
