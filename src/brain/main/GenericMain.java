/**
 * 
 */
package brain.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import brain.main.customsample.CustomNeuron;
import brain.model.generic.GenericNeuronLayer;
import brain.model.generic.interfaces.NeuronLayerInterface;
import brain.model.images.ImageNeuron;

/**
 * @author Adrian
 * 
 * Example showing how to use custom neurons with generic neuron layer 
 */
public class GenericMain {

	/**
	 * 
	 */
	public GenericMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<BufferedImage>images= new ArrayList<BufferedImage>();
		NeuronLayerInterface<ImageNeuron, BufferedImage> neuronLayer = new GenericNeuronLayer<ImageNeuron, BufferedImage>(4,ImageNeuron.class);
		NeuronLayerInterface<ImageNeuron, BufferedImage> neuronLayer2 =new GenericNeuronLayer<ImageNeuron, BufferedImage>(images ,ImageNeuron.class);
		
		List<List<Integer>> integers=new ArrayList<List<Integer>>();
		NeuronLayerInterface<CustomNeuron,  List<Integer>> neuronLayer3 =new GenericNeuronLayer<CustomNeuron, List<Integer>>(integers, CustomNeuron.class);
		NeuronLayerInterface<CustomNeuron,  List<Integer>> neuronLayer4 =new GenericNeuronLayer<CustomNeuron, List<Integer>>(1, CustomNeuron.class);
	}

}
