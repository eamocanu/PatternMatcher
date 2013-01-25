package brain.main;

import java.util.ArrayList;
import java.util.List;

import brain.model.Neuron;
import brain.model.NeuronLayer;
/**
 * 
 */

/**
 * @author Adrian
 * This is a batch test class
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		oneTimeRunExperiment();
		runExperiment1();
		runExperiment2();
		
	}

	
	
	static void runExperiment2(){
		NeuronLayer nb;
		Neuron best;
		String searchString;
		
		int min=Integer.MAX_VALUE, max=Integer.MIN_VALUE, avg=0, numTimes=5, sum=0;
		nb= new NeuronLayer(0);
		
			
			nb.addNeuron(new Neuron("XXX"));
			nb.addNeuron(new Neuron("HLJ"));

			searchString="NUP";
			best=nb.getBestMatch(searchString);
			int i=-1;
			for (int j=0; j<12; j++){
				i++;
				best= nb.getBestMatch(searchString);
//				System.out.println(i+": best match for "+searchString +" is "+ best.getData());
			}
		
		//TODO need to punish neurons here to lower their confidence otherwise they can't learn new shit
		System.out.println("PART 2 ======================== ======================== ========================");
			searchString="XYZ";
			best=nb.getBestMatch(searchString);
			i=-1;
			for (int j=0; j<numTimes; j++){
				i++;
				best= nb.getBestMatch(searchString);
				System.out.println(i+": best match for "+searchString +" is "+ best.getData());
			}
			
			sum+=i;
			min=Math.min(min, i);
			max=Math.max(max, i);
		
		
		avg=sum/numTimes;
		System.out.println("Min: "+min);
		System.out.println("Max: "+max);
		System.out.println("Avg: "+avg +" over " +numTimes +" times");
		System.out.println(" ======================== ======================== ======================== ");
	}
	
	
	static void runExperiment1(){
		NeuronLayer nb;
		Neuron best;
		String searchString;
		
		int min=Integer.MAX_VALUE, max=Integer.MIN_VALUE, avg=0, numTimes=500, sum=0;
		List<Integer> means = new ArrayList<Integer>();
		
		
		for (int j=0; j<numTimes; j++){
			nb= new NeuronLayer(0);
			nb.addNeuron(new Neuron("XXX"));
			nb.addNeuron(new Neuron("HLJ"));

			searchString="NUP";
			best=nb.getBestMatch(searchString);
			int i=-1;
			while(!searchString.equals(best.getData())){
				i++;
				best= nb.getBestMatch(searchString);
//				System.out.println(i+": best match for "+searchString +" is "+ best.getData());
			}
			means.add(i);
			sum+=i;
			min=Math.min(min, i);
			max=Math.max(max, i);
			
		}
		avg=sum/numTimes;
		System.out.println("Min: "+min);
		System.out.println("Max: "+max);
		System.out.println("Avg: "+avg +" over " +numTimes +" times");
//		
//		for (int i=0;i<means.size(); i++){
////			System.out.print(means.get(i) + " ");
//			if (means.get(i)==1){
//				System.out.print("1 "); 
//			}
//		}
		System.out.println(" ======================== ======================== ======================== ");
	}
	
	
	static void oneTimeRunExperiment(){
		NeuronLayer nb;
		Neuron best;
		String searchString;
		
		int min=Integer.MAX_VALUE, max=Integer.MIN_VALUE, avg=0, numTimes=1, sum=0;
		
		for (int j=0; j<numTimes; j++){
			nb= new NeuronLayer(0);			nb.addNeuron(new Neuron("XXX"));
			nb.addNeuron(new Neuron("HLJ"));

			searchString="NUP";
			best=nb.getBestMatch(searchString);
			int i=-1;
			while(!searchString.equals(best.getData())){
				i++;
				best= nb.getBestMatch(searchString);
				System.out.println(i+": best match for "+searchString +" is "+ best.getData());
			}
			
			sum+=i;
			min=Math.min(min, i);
			max=Math.max(max, i);
			System.out.println(" ======================== ======================== ======================== ");
		}
		
	}
}
