/**
 * 
 */
package brain.main;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import brain.model.generic.GenericNeuronLayer;
import brain.model.generic.interfaces.NeuronInterface;
import brain.model.generic.interfaces.NeuronLayerInterface;
import brain.model.images.ImageNeuron;
import brain.ui.NeuronLevelImageUi;

/**
 * @author Adrian
 * Testing image matching 
 */
public class MainImageUi {

	/**
	 * 
	 */
	public MainImageUi() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<BufferedImage>images= new ArrayList<BufferedImage>();
		//TODO load images
		BufferedImage origImage=null;
		try {
			
			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug1.png"));
			images.add(origImage);

			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug1.mod.png"));
			images.add(origImage);
			
			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug1.noise.9.png"));
			images.add(origImage);

			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug1.gaussianBlur.1px.png"));
			images.add(origImage);
			
			
			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug2.png"));
			images.add(origImage);
			
			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug3.png"));
			images.add(origImage);
			
			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug4.png"));
			images.add(origImage);

			origImage= ImageIO.read(new File("C:\\delme\\drug.photos\\drug3.noise.9.png"));
			images.add(origImage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NeuronLayerInterface<ImageNeuron, BufferedImage> neuronLayer = new GenericNeuronLayer<ImageNeuron, BufferedImage>(1,ImageNeuron.class);
		final NeuronLayerInterface<ImageNeuron, BufferedImage> nb= new GenericNeuronLayer<ImageNeuron, BufferedImage>(images ,ImageNeuron.class);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuronLevelImageUi ui = new NeuronLevelImageUi();
					ui.setVisible(true);

					ui.setBrain(nb);
//					ui.setSize(500, 600);
					
					ui.populateFields();
					ui.setVisible(true);
					ui.invalidate();
					ui.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	
	
	

}
