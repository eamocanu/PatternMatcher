package brain.model;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @author Adrian
 * 
 */
public class NeuronLayer {
	/** Neurons in this layer */
	private List<Neuron> neurons;
	private Map<Double, Neuron> bestMatch;
	private double crtMaxMatchStrength=-1;
	private String currentInput="";
	
	/** Maximum match strength in percent (1==100%) */
	final double MAX_MATCH_STRENGTH=1;
	
	/** What this layer considers to be confidence in percent (0.8==80%) */
	final double HIGH_CONFIDENCE=0.8;
	
	
	
	/** Create a layer of neurons with random data of random sizes 
	 * @param numNeuron		amount of neurons to create
	 * */
	public NeuronLayer(int numNeuron) {
		bestMatch= new HashMap<Double, Neuron>();
		neurons= new LinkedList<Neuron>();
		
		for (int i=0; i<numNeuron; i++){
			neurons.add(new Neuron());
		}
		
	}
	
	
	/** Create a neuron layer with neurons containing random expected data of specified length
	 * 
	 * @param numNeuron		amount of neurons to create
	 * @param inputLength	neuron data length for the entire layer
	 */
	public NeuronLayer(int numNeuron, int inputLength) {
		bestMatch= new HashMap<Double, Neuron>();
		neurons= new LinkedList<Neuron>();
		
		for (int i=0; i<numNeuron; i++){
			neurons.add(new Neuron(inputLength));
		}
		
	}
	
	
	/** Create a neuron layer with neurons containing created from data container 
	 * 
	 * @param data	container with data to create neurons from
	 */
	public NeuronLayer(Collection<String> data) {
		bestMatch= new HashMap<Double, Neuron>();
		neurons= new LinkedList<Neuron>();
		
		neurons.add(new Neuron("TRUE"));
		neurons.add(new Neuron("TR"));
		neurons.add(new Neuron("T"));
		
		Iterator<String> it= data.iterator();
		while (it.hasNext()){
			neurons.add(new Neuron(it.next()));
		}
		
	}
	
	
	
	/** Add externally created neurons to this layer */
	public void addNeuron(Neuron neuron){
		neurons.add(neuron);
	}
	
	
	public List<Neuron> getNeurons(){ return neurons; }
	

	/** Retrieve best matching neuron given current input
	 * First check all neurons and calculate match strength preciseness for them: O(n).
	 * Then, calculate match preciseness (based on neurons confidence): O(n).
	 * O(n)+O(n) = O(n) runtime 
	 * 
	 * @param 	input string to try to match with a neuron
	 * 			it will be converted to upper case bc neurons are in upper case
	 * @return  best matching neuron for the input
	 */
	public Neuron getBestMatch(String input){
		if (input==null) return Neuron.EMPTY;
		if (input.trim().length()==0) return Neuron.EMPTY;
		
		currentInput= input.toUpperCase();
		
		try {
			//clear prev round best match neuron 
			bestMatch.clear();
			crtMaxMatchStrength=0;
			
			//calculate best neuron match for current input round
			for (Neuron neuron:neurons){
				neuron.calculateMatchStrength(currentInput);
				
				if (!neuron.isFree() && neuron.getMatchStrength()==MAX_MATCH_STRENGTH) {
					addNeuronMatchStrength(neuron);
					return neuron;
				}
				
				//if this neuron already knows another pattern skip it
				if (!neuron.isFree()) {
					continue;
				}
					
				addNeuronMatchStrength(neuron);
				
				//found a perfect match neuron stop right away
				if (neuron.getConfidence()>=HIGH_CONFIDENCE && neuron.getMatchStrength()==MAX_MATCH_STRENGTH) break;
			}
			
			if (getBestMatch()==Neuron.EMPTY){
				System.out.println("Brain is full. Add more neurons or mark neurons as free to learn.");
			}
			
			for (Neuron neuron:neurons){
				neuron.calculateMatchPreciseness(currentInput, getBestMatch());
			}

			return getBestMatch();
		} finally {
			//LOG("---- end of round --------");
		}
	}


	/** Finds maximum strength neuron match for current input */
	private void addNeuronMatchStrength(Neuron neuron) {
		double matchStrength= neuron.getMatchStrength();
		//TODO: keep a list of all other neurons that match the strength (or maybe within +=5%) so all of these will be returned to the UI
		//at the end as possible corrections, but the strongest match will be at top highlighted
		
//		LOG("old ? new : "+crtMaxMatchStrength +" ? "+ matchStrength + "("  + (crtMaxMatchStrength < matchStrength) +")");
		if (crtMaxMatchStrength < matchStrength){
			if (bestMatch.containsKey(crtMaxMatchStrength)){
				bestMatch.remove(crtMaxMatchStrength);
			}
			
			crtMaxMatchStrength= matchStrength;
//			LOG("Winner: " +matchStrength);
			bestMatch.put(crtMaxMatchStrength, neuron);
		}
	}
	
	
	/** Retrieves best matching neuron */
	public Neuron getBestMatch(){
		if (crtMaxMatchStrength == -1) return Neuron.EMPTY;
		
		Neuron n = bestMatch.get(crtMaxMatchStrength);
		if (n==null) {
			return Neuron.EMPTY;
		}
		
		return n;
	}
	
	
	/** Retrieve current input for this layer of neurons */
	public String getCurrentInput(){
		return currentInput;
	}
	
	
	public void printAllNeurons(){
		for (Neuron neuron:neurons){
			System.out.println("n: "+ neuron + " "+ neuron.getMatchStrength() );
		}
	}
	
	
}
