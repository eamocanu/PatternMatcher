/**
 * 
 */
package brain.model.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brain.model.generic.interfaces.NeuronInterface;
import brain.model.generic.interfaces.NeuronLayerInterface;



/**
 * @author Adrian
 *
 */
//N is a neuron; I is the input the neuron takes 
public class GenericNeuronLayer <N extends NeuronInterface<I>, I> implements NeuronLayerInterface<N, I> {

	/** Neurons in this layer */
	private List<N> neurons;
	private Map<Double, N> bestMatch;
	private double crtMaxMatchStrength=-1;
	private I currentInput;
	
	/** Maximum match strength in percent (1==100%) */
	final double MAX_MATCH_STRENGTH=1;
	
	/** What this layer considers to be confidence in percent (0.8==80%) */
	final double HIGH_CONFIDENCE=0.8;
	

	
	
	/** Create a neuron layer with neurons containing random expected data
	 * 
	 * @param numNeuron		amount of neurons to create
	 * @param neuronType	type of neuron contained in this neuron layer
	 */
	public GenericNeuronLayer(int numNeuron, Class<N> neuronType) {
		bestMatch= new HashMap<Double, N>();
		neurons= new LinkedList<N>();
		
		try {
			for (int i=0; i<neurons.size(); i++){
				N neuron = neuronType.newInstance();
				neurons.add(neuron);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/** Create a neuron layer with neurons containing data from neuronsInputData
	 * 
	 * @param neuronsInputData		data to give to each neuron
	 * @param neuronType	type of neuron contained in this neuron layer
	 */
	public GenericNeuronLayer(Collection<I> neuronsInputData, Class<N> neuronType) {
		bestMatch= new HashMap<Double, N>();
		neurons= new LinkedList<N>();
		
		try {
			for (I inputData: neuronsInputData){
				N neuron = neuronType.newInstance();
				neurons.add(neuron);
				neuron.setData(inputData);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void NeuronLayer(Collection<N> data) {
		for (N neuron: data){
			neurons.add(neuron);
		}
	}


	@Override
	public void addNeuron(N neuron) {
		neurons.add(neuron);
	}


	@Override
	public Collection<N> getNeurons() {
		return neurons;
	}


	@Override
	public N getBestMatch(I input) {
		if (input==null) return null;//Neuron.EMPTY;
		currentInput= input;
		
		try {
			//clear prev round best match neuron 
			bestMatch.clear();
			crtMaxMatchStrength=0;
			
			//calculate best neuron match for current input round
			for (N neuron:neurons){
				neuron.calculateMatchStrength(currentInput);
				
//				if (!neuron.isFree() && neuron.getMatchStrength()==MAX_MATCH_STRENGTH) {
//					addNeuronMatchStrength(neuron);
//					return neuron;
//				}
				
				//if this neuron already knows another pattern skip it
				if (!neuron.isFree()) {
					continue;
				}
					
				addNeuronMatchStrength(neuron);
				
				//found a perfect match neuron stop right away
				if (neuron.getConfidence()>=HIGH_CONFIDENCE && neuron.getMatchStrength()==MAX_MATCH_STRENGTH) break;
			}
			
			//if (getBestMatch()==Neuron.EMPTY){
			if (getBestMatch()==null){
				System.out.println("Brain is full. Add more neurons or mark neurons as free to learn.");
			}
			
			for (N neuron:neurons){
				neuron.calculateMatchPreciseness(currentInput, getBestMatch());
			}

			return getBestMatch();
		} finally {
			//LOG("---- end of round --------");
		}
		
	}

	
	/** Finds maximum strength neuron match for current input */
	private void addNeuronMatchStrength(N neuron) {
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
	

	@Override
	public N getBestMatch() {
		if (crtMaxMatchStrength == -1) return null;// Neuron.EMPTY;
		
		N n = bestMatch.get(crtMaxMatchStrength);
//		if (n==null) {
//			return Neuron.EMPTY;
//		}
		
		return n;
	}


	@Override
	public I getCurrentInput() {
		return currentInput;
	}


	@Override
	public void printAllNeurons(){
		for (N neuron: neurons){
			System.out.println("n: "+ neuron + " "+ neuron.getMatchStrength() );
		}
	}
	
	
	
}
