/**
 * 
 */
package brain.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import brain.model.Neuron;
import brain.model.NeuronLayer;
import brain.ui.NeuronLevelUi;

/**
 * @author Adrian
 *
 */
public class MainUi {

	/**
	 * enter a word to see which of the neurons match is the best
	 */
	public MainUi() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final NeuronLayer nb= new NeuronLayer(0);
		Neuron best;
		String searchString;

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuronLevelUi ui = new NeuronLevelUi();
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
		
		

		nb.addNeuron(new Neuron("TRUE"));
		nb.addNeuron(new Neuron("TR"));
		nb.addNeuron(new Neuron("T"));
		nb.addNeuron(new Neuron("AID"));
		nb.addNeuron(new Neuron("BID"));
		nb.addNeuron(new Neuron("CROW"));
	}


	

}
