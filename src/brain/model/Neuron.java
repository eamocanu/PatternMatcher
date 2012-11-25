package brain.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
public class Neuron {
	final double MAX_SIGNAL  = 1; //100%
	final static Neuron EMPTY = new Neuron();
	final double HIGH_CONFIDENCE = 0.66;
	final int MAX_NEURON_DATA_LENGTH = 20;
	
	
	/** Strength of match of this neuron given current input */
	private double matchStrength;
	
	/** Confidence of neuron of its match strength */
	private double confidenceInPercent=0;
	
	/** Neuron's data to match against input */
	private String data="";
	
	/** Indicates if neuron is allowed to learn or is already learned */ 
	private boolean isFreeToLearn=true;
	
	/** Dendrites corresponding to each character in neuron data variable */
	private List<Dendrite> dendrites;
	
	
	
	/** Create a neuron with random expected data of specified length
	 * 
	 * @param inputLength	expected data length
	 */
	public Neuron(int inputLength) {
		dendrites= new LinkedList<Dendrite>();
		
		for (int i=0; i<inputLength; i++){
			dendrites.add(new Dendrite());
		}
	}
	
	
	/** Create a neuron with a random number of dendrites and random data */
	public Neuron() {
		dendrites= new LinkedList<Dendrite>();
		
		for (int i=0; i<getRandomNumber(1, MAX_NEURON_DATA_LENGTH); i++){
			dendrites.add(new Dendrite());
		}
	}
	
	
	/** Create a neuron with given data. Subsequent inputs will be matched against this data
	 * 
	 * @param data		neuron data
	 */ 
	public Neuron(String data) {
		this.data=data;
		
		dendrites= new LinkedList<Dendrite>();
		
		for (int i=0; i<data.length(); i++){
			dendrites.add(new Dendrite(data.charAt(i)));
		}
	}
	
	
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
	public void calculateMatchStrength(String input) {
		double dendritesStrength=0;
		int i=-1;

		//go thru all dendrites to find match strength
		for (Dendrite dendrite: dendrites){
			i++;
			//take care of shorter input
			if (i>=input.length()){
				break;
			}
			dendritesStrength+= dendrite.getMatchStrength(input.charAt(i));
		}
		
		//average signal strength over all dendrites
		//1: dividing by input size averages neuron match strength over each dendrite, if the number
		//of dendrites match the size of the input
		//
		//2: if #dendrites > input, the dendrites which are outside the input.length will drag the
		//match strength of the neuron down bc their match strength is not accounted for in this
		//neurons match strength, but when calculating it, we divide by the # of dendrites so
		//this will have a minimizing effect on match strength value
		//
		//3: if #dendrites < input, then match strength is penalized and it is lower bc we divide by a larger #
		dendritesStrength = dendritesStrength/Math.max(input.length(), dendrites.size());
		matchStrength= dendritesStrength;
	}
	
	
	
	/** Calculate confidence of neuron and allow it to get closer to given 
	 * input (if neuron is allowed to learn).
	 * 
	 * @param input			input to check against
	 * @param bestMatch		neuron which best matches the input (highest match strength of all neurons)
	 */
	public void calculateMatchPreciseness(String input, Neuron bestMatch) {
		double delta=0;
		int i=-1;
		
		if (this == bestMatch){
			//increase my confidence by a small fraction of matchStrength->don't want to learn all in one go
			confidenceInPercent += 0.8 * (matchStrength/6);
			
			if (confidenceInPercent > MAX_SIGNAL * 0.9){//cap it at 90%
				confidenceInPercent = MAX_SIGNAL * 0.9;
				
				//a neuron that is already confident and now has a 100% match is locked in to its current learned pattern
				if (matchStrength==MAX_SIGNAL){
					//this neuron is marked as knowledgeable and confident so it does not free to anything from now on
					isFreeToLearn=false;
				}
			}
			
			for (Dendrite dendrite: dendrites){
				i++;
				
				delta=1;//furthest apart
				if (i>=input.length()) break;//input is of different size that this neuron's data
				
				//How far away is this dendrite's value from what's expected?
                delta = (double)(input.charAt(i) - dendrite.getExpectation())/('Z'-'A');//delta in %
                delta = delta / 2;//take half of it otherwise it grows in big chunks and never gets finer

                //The more confident we are, the less we want to deviate from current expectation.
                delta = delta * (MAX_SIGNAL - confidenceInPercent);

                //boundary cases, if delta grows too big or too small
                if (delta>0  && delta<0.01)
                	delta = 0.01; 
                if (delta<0  && delta<-0.01)
                	delta = -0.01;
                
                //LOG("best neuron new delta: "+delta);
                //LOG("best neuron new expectation: "+ (char)(dendrite.getExpectation()+ delta*100));
                //allow neuron to evolve and get closer to expectation
                dendrite.setExpectation( (char)(dendrite.getExpectation()+ delta*100)); 
			}//for
			
		} else {
			//not best match neuron -> lose confidence more when no other neuron has a strong match.
			//In other words this neurons' confidence drops less when other neuron has a strong match
            confidenceInPercent -= 0.001 * (MAX_SIGNAL - bestMatch.getMatchStrength());
			//confidenceInPercent -= 0.006 * (MAX_SIGNAL - bestMatch.getMatchStrength());
            
            //penalize neurons that are confident of right input, but in fact are not the best 
            //This will lead to them forgetting the input and definitely being less sure of what they know
            //so they can learn other patterns
//            if( getConfidence() >0.5 && this != bestMatch){//TODO add currentMatchStrength > 0.5 or maybe >0.7
//            	confidenceInPercent = confidenceInPercent/2;
//            }
			
            if (confidenceInPercent < 0.05) confidenceInPercent = 0.05;

            //its confidence dropped so it can try to learn other patterns
            if (confidenceInPercent < 0.5){
            	isFreeToLearn=true;
            }
            
            //high confidence non best match neurons don't care about current input because they
            //are already good at recognizing their own specific input so they dont want to learn
            //other inputs until their confidence drops slowly (above)
            if(this.hasHighConfidence()){
            	System.out.println(this.getData() +" has high confidence -> no change");
            	return;
            }
            		
            i=-1;
            for (Dendrite dendrite: dendrites){
            	i++;
				if (i>=input.length()) break;
            	//this neuron is a very low match -> randomize itself more ONLY IF ITS CONFIDENCE IS LOW
//            	if(bestMatch.getMatchStrength() - this.getMatchStrength() <= MAX_SIGNAL * 0.1){
            	if(confidenceInPercent < 0.16 && Math.abs(bestMatch.getMatchStrength() - this.getMatchStrength()) <= MAX_SIGNAL * 0.16){
            		//LOG("best s:"+bestMatch.getMatchStrength() + " this.s: "+this.getMatchStrength() );
            		//Get more random
            		if (getRandomNumber(1, 100) > 0.5){
            			dendrite.setExpectation((char)getRandomNumber('A','Z'));
            		} else {
            			dendrite.setExpectation((char)getRandomNumber('A','Z'));
            		}
            		confidenceInPercent=0.05 ;//reset confidence since data is being reset
            	} else {
            		//drop confidence a bit proportional to its match strength
            		confidenceInPercent-= 0.01 * (MAX_SIGNAL - this.getMatchStrength());
            		
            		//How far away is this dendrite's value from what's expected?
            		delta= input.charAt(i) - dendrite.getExpectation();
//            		LOG("delta BEFORE: "+delta);
            		delta= delta * (MAX_SIGNAL - confidenceInPercent);
//            		LOG("delta AFTER 1: "+delta);
            		delta *= 0.33;
//            		LOG("delta AFTER 2: "+delta);
//            		LOG(this);
            		
            		//boundary cases, if delta grows too big or too small
            		if (delta>0  && delta<1)
            			delta = 1; 
            		if (delta<0  && delta<-1)
            			delta = -1;
                     
//            		LOG("delta AFTER 3: "+delta);
            		dendrite.setExpectation( (char)(dendrite.getExpectation() + delta));
            	}

            }//for
        	
		}
		
	}
	
	
	/** Check if this neuron's confidence is high */
	private boolean hasHighConfidence() {
		return confidenceInPercent >= HIGH_CONFIDENCE;
	}


	/** Retrieve neuron's match strength against last given input */
	public double getMatchStrength() {
		return matchStrength;
	}

	
	/** Retrieve neuron's overall confidence */
	public double getConfidence() {
		return confidenceInPercent;
	}
 

	/** Retrieve a number in interval [low, high]
	 * 
	 * @param low	low boundary
	 * @param high	high boundary
	 * @return		a random number in boundary range
	 */
	private int getRandomNumber(int low, int high){
		Random r=new Random();
		int i=(r.nextInt(high-low) +low);
		return i;
	}
	
	
	public List<Dendrite> getDendrites(){
		return dendrites;
	}

	
	/** Retrieve current data stored in this neuron. Traverses all dendrites to retrieve it.
	 * Thus runtime is O(dendrites)
	 * 
	 * @return	neuron expected data
	 */
	public String getData(){
		String crtDendriteData="";
		
		for (Dendrite dendrite: dendrites){
			crtDendriteData += dendrite.getExpectation();
		}
		
		return crtDendriteData;
	}

	
	public String toString(){
		return data +" -> "+ getData() + " | s: "+getMatchStrength() + " c: "+ getConfidence();
	}


	/** Override neuron confidence
	 * When confidence is low neuron is able to learn new patterns faster
	 * When confidence is high, neuron stops learning and is locked remembering the learned pattern
	 * 
	 * @param confidence	in percent; interval [0,1]
	 */
	public void setConfidence(double confidence) {
		this.confidenceInPercent= confidence;
	}


	/** Retrieves ability of neuron to learn. If it is free, it can learn new patterns.
	 * 
	 * @return	true is this neuron is free to learn patterns
	 * 			false if it already learned a pattern well (with 90% confidence)
	 */
	public boolean isFree() {
		return isFreeToLearn;
	}

}
