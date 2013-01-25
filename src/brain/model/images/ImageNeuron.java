/**
 * 
 */
package brain.model.images;

import java.awt.image.BufferedImage;
import java.util.Random;

import brain.image.ImageUtils;
import brain.model.Dendrite;
import brain.model.generic.interfaces.NeuronInterface;


/**
 * @author Adrian
 *
 */
public class ImageNeuron implements NeuronInterface<BufferedImage> {
	final double MAX_SIGNAL  = 1; //100%
	final static NeuronInterface<BufferedImage> EMPTY = new ImageNeuron();
	final double HIGH_CONFIDENCE = 0.66;
	//final int MAX_NEURON_DATA_LENGTH = 20;
	
	
	/** Strength of match of this neuron given current input */
	private double matchStrength;
	
	/** Confidence of neuron of its match strength */
	private double confidenceInPercent=0;
	
	/** Neuron's data to match against input */
	private BufferedImage data=null;
	
	/** Indicates if neuron is allowed to learn or is already learned */ 
	private boolean isFreeToLearn=true;
	
	/** Dendrites corresponding to each character in neuron data variable */
	private ImageDendrite [][] dendrites;
	
	private BufferedImage outputData;
	
	
	
	/** Create a neuron with no dendrites. Need to call set data before use */
	public ImageNeuron() {	}
	
	
	/** Create a neuron with given data. Subsequent inputs will be matched against this data
	 * 
	 * @param data		neuron data
	 */ 
	public ImageNeuron(BufferedImage data) {
		this.data=data;
		
		setData(data);
	}
	

	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#hasHighConfidence()
	 */
	@Override
	public boolean hasHighConfidence() {
		return confidenceInPercent >= HIGH_CONFIDENCE;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#getMatchStrength()
	 */
	@Override
	public double getMatchStrength() {
		return matchStrength;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#getConfidence()
	 */
	@Override
	public double getConfidence() {
		return confidenceInPercent;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#getDendrites()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ImageDendrite [][] getDendrites() {
		return dendrites;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#getData()
	 */
	@Override
	public BufferedImage getData() {
		if (data==null) return new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
		
		if (outputData==null){
			outputData= new BufferedImage(data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		
		for (int i=0; i<data.getWidth(); i++){
			for (int j=0; j<data.getHeight(); j++){
				outputData.setRGB(i, j, 0xff000000 | dendrites[i][j].getExpectation());//need leading FF otherwise invisible-that's alfa
			}
		}
		
		return outputData;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#setConfidence(double)
	 */
	@Override
	public void setConfidence(double confidence) {
		this.confidenceInPercent= confidence;
	}

	
	/* (non-Javadoc)
	 * @see brain.model.general.GenericNeuron#isFree()
	 */
	@Override
	public boolean isFree() {
		return isFreeToLearn;
	}

	
	@Override
	public void setData(BufferedImage newData) {
//		dendrites=null;
		data= newData;//just in case u want to save the image
		dendrites= new ImageDendrite[newData.getWidth()][newData.getHeight()];
		
		for (int i=0; i<newData.getWidth(); i++){
			for (int j=0; j<newData.getHeight(); j++){
				dendrites[i][j]= new ImageDendrite(newData.getRGB(i, j));
			}
		}
	}


	@Override
	public void calculateMatchStrength(BufferedImage input) {
		BufferedImage inputResized = ImageUtils.scaleImage(input, data.getWidth(), data.getHeight());
		double dendritesStrength=0;
		
		for (int i=0; i<dendrites.length; i++){
			for (int j=0; j<dendrites[i].length; j++){
				dendritesStrength+= dendrites[i][j].getMatchStrength(inputResized.getRGB(i, j));
			}
		}
		
		//normalize on 0-1 range
		matchStrength= dendritesStrength/(data.getWidth()*data.getHeight());
	}


	@Override
	public void calculateMatchPreciseness(BufferedImage input, NeuronInterface<BufferedImage> bestMatch) {
		BufferedImage inputResized = ImageUtils.scaleImage(input, data.getWidth(), data.getHeight());
		
		double delta=0;
		
		if (this == bestMatch){
			//increase my confidence by a small fraction of matchStrength->don't want to learn all in one go
//			confidenceInPercent += 0.8 * (matchStrength/6);
			confidenceInPercent += matchStrength*0.05;
			
			if (confidenceInPercent > MAX_SIGNAL * 0.9){//cap it at 90%
				confidenceInPercent = MAX_SIGNAL * 0.9;
				
				//a neuron that is already confident and now has a 100% match is locked in to its current learned pattern
				if (matchStrength==MAX_SIGNAL){
					//this neuron is marked as knowledgeable and confident so it does not free to anything from now on
					isFreeToLearn=false;
				}
			}
			
			for (int i=0; i<dendrites.length; i++){
				for (int j=0; j<dendrites[i].length; j++){
					delta=1;//furthest apart
					
					//How far away is this dendrite's value from what's expected?
					delta = (double)(inputResized.getRGB(i, j) - dendrites[i][j].getExpectation())/('Z'-'A');//delta in %
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
					dendrites[i][j].setExpectation( (int)(dendrites[i][j].getExpectation()+ delta*100)); 
				}//for
			}

		} else {
			//learning part
			confidenceInPercent -= 0.001 * (MAX_SIGNAL - bestMatch.getMatchStrength());

			if (confidenceInPercent < 0.05) confidenceInPercent = 0.05;

			//its confidence dropped so it can try to learn other patterns
//			if (confidenceInPercent < 0.5){
//				isFreeToLearn=true;
//			}

			for (int i=0; i<dendrites.length; i++){
				for (int j=0; j<dendrites[i].length; j++){
					//if I'm close to expected signal but I'm not best, then forget it->get random; don't want 2 similar neurons
					if( Math.abs(bestMatch.getMatchStrength() - this.getMatchStrength()) <= MAX_SIGNAL * 0.11){
						//					dendrite.setExpectation((char)getRandomNumber('A','Z'));
//						int red= getRandomNumber(0,255);
//						int green= getRandomNumber(0,255);
//						int blue= getRandomNumber(0,255);
//						dendrites[i][j].setExpectation(0xFF<<6 | red<<4 | green <<2 | blue);
						dendrites[i][j].setExpectation(0xFF<<6 | getRandomNumber(0,0xFFFFFF));
						//also decr its confidence since it's not valid anymore
						//remove this dendrite's value from neuron's confidence
						confidenceInPercent = confidenceInPercent - confidenceInPercent/(dendrites.length*dendrites[0].length);
					} else {
						//don't strongly match crt input
						delta= inputResized.getRGB(i, j) - dendrites[i][j].getExpectation();
						//The more confident I am, the less I want to deviate from current expectation.
						delta= delta * (1 -confidenceInPercent);
						dendrites[i][j].setExpectation( (int)(dendrites[i][j].getExpectation()+ delta*0.2));
					}

				}//for dendrites
			}//for dendrites
			
		}//else
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


	public String toString(){
		return data +" -> "+ getData() + " | s: "+getMatchStrength() + " c: "+ getConfidence();
	}


}
