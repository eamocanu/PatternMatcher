/**
 * 
 */
package brain.model.images;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brain.model.Neuron;
import brain.model.generic.interfaces.NeuronInterface;
import brain.model.generic.interfaces.NeuronLayerInterface;

/**
 * @author Adrian
 *
 */
public class ImageNeuronLayer<N extends NeuronInterface<BufferedImage>> implements NeuronLayerInterface<N, BufferedImage> {
	/** Neurons in this layer */
	private List<N> neurons;
	private Map<Double, N> bestMatch;
	private double crtMaxMatchStrength=-1;
	private String currentInput="";
	
	/** Maximum match strength in percent (1==100%) */
	final double MAX_MATCH_STRENGTH=1;
	
	/** What this layer considers to be confidence in percent (0.8==80%) */
	final double HIGH_CONFIDENCE=0.8;
	
//	
//	/** Create a layer of neurons with random data of random sizes 
//	 * @param numNeuron		amount of neurons to create
//	 * @return 
//	 * */
//	public ImageNeuronLayer(int numNeuron, Class<N> neuronType) {
//		bestMatch= new HashMap<Double, N>();
//		neurons= new LinkedList<N>();
//		
//		try {
//			for (int i=0; i<numNeuron; i++){
//				neurons.add(neuronType.newInstance());
//			}
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//	
	/** Create a neuron layer with neurons containing random expected data of specified length
	 * 
	 * @param numNeuron		amount of neurons to create
	 * @param inputLength	neuron data length for the entire layer
	 */
//	public ImageNeuronLayer(Collection<? extends BufferedImage> neuronsData) {
//		bestMatch= new HashMap<Double, N>();
//		neurons= new LinkedList<N>();
//		
//		try {
//			for (int i=0; i<neurons.size(); i++){
//				GenericNeuron neuron = neuronType.newInstance();
//				Constructor<N> ct=neuronType.getConstructor(BufferedImage.class);
//				ct.newInstance(initargs)
//				neurons.add(neuron);
//			}
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
	/** Create a neuron layer with neurons containing random expected data of specified length
	 * 
	 * @param numNeuron		amount of neurons to create
	 * @param inputLength	neuron data length for the entire layer
	 */
//	public ImageNeuronLayer(Collection<N> neurons, Class<N> neuronType) {
//		bestMatch= new HashMap<Double, N>();
//		neurons= new LinkedList<N>();
//		
//		try {
//			for (int i=0; i<neurons.size(); i++){
//				N neuron = neuronType.newInstance();
//				neurons.add(neuron);
//			}
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


	@Override
	public void NeuronLayer(Collection<N> data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addNeuron(N neuron) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Collection<N> getNeurons() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public N getBestMatch(BufferedImage input) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public N getBestMatch() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BufferedImage getCurrentInput() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void printAllNeurons() {
		// TODO Auto-generated method stub
		
	}
	
	
}
