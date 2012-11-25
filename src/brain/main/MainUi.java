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
	 * 
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

	
	static void runExperiment1(NeuronLayer nb){
		Neuron best;
		String searchString;
		
		int min=Integer.MAX_VALUE, max=Integer.MIN_VALUE, avg=0, numTimes=500, sum=0;
		List<Integer> means = new ArrayList<Integer>();
		
//			nb.addNeuron(new Neuron("XXX"));
//			nb.addNeuron(new Neuron("HLJ"));

//			searchString="NUP";
//			best=nb.getBestMatch(searchString);
//			int i=-1;
//			while(!searchString.equals(best.getData())){
//				i++;
//				best= nb.getBestMatch(searchString);
////				System.out.println(i+": best match for "+searchString +" is "+ best.getData());
//			}
//		
		System.out.println(" ======================== ======================== ======================== ");
	}
	

}
