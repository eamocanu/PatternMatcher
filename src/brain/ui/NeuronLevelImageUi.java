/**
 * 
 */
package brain.ui;

/**
 * @author Adrian
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.tc33.jheatchart.HeatChart;

import brain.image.ImageUtils;
import brain.model.generic.interfaces.NeuronLayerInterface;
import brain.model.images.ImageNeuron;

public class NeuronLevelImageUi extends JFrame {

	private JPanel contentPane;

	
	JPanel mainPanel;
	JPanel neuronsPanel;
	JPanel neuronPhotoPanel;
	
	JLabel info1Label;
	private JLabel neuronText;
	private JTextField inputField;
	private JButton learnButton;
	private JPanel rightPanel;
	
	//final String neuronImagePath="C:\\delme\\drug.photos\\drug3.png";
	final String neuronImagePath="C:\\delme\\drug.photos\\drug1.png";
	
	
	/**
	 * Create the frame.
	 */
	public NeuronLevelImageUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		rightPanel= new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		contentPane.add(rightPanel, BorderLayout.EAST);
		mainPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		neuronsPanel = new JPanel();
		mainPanel.add(neuronsPanel);
//		neuronsPanel.setLayout(new GridLayout(5, 5, 2, 2)/*new BoxLayout(neuronsPanel, BoxLayout.Y_AXIS)*/);
		
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		learnButton = new JButton("Learn-Submit Input");
		inputField= new JTextField("",9);
		
		rightPanel.add(inputField);
		rightPanel.add(learnButton);
		
		
		learnButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				    try {
						brain.getBestMatch(ImageUtils.openImage(neuronImagePath));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    neuronsPanel.removeAll();
				    populateFields();
				  }
				});
	}
	
	
	NeuronLayerInterface<ImageNeuron, BufferedImage> brain;
	public void setBrain(NeuronLayerInterface<ImageNeuron, BufferedImage> nb){
		this.brain=nb;
	}
	
	
	double [][] dendriteHeatValues = new double[1][101];
	public void populateFields(){
		for (int i=0; i<40; i++){
			dendriteHeatValues[0][i]=i;
		}
		for (ImageNeuron neuron: brain.getNeurons()){
			JPanel neuronPanel= new JPanel();
//			neuronPanel.setSize(300, 300);
			neuronsPanel.add(neuronPanel);
			
			if (neuron == brain.getBestMatch()){
				neuronPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			
			neuronPanel.setLayout(new BoxLayout(neuronPanel, BoxLayout.Y_AXIS));
			
			JLabel neuronDataLabel= new JLabel();
			neuronPhotoPanel = new JPanel();
			
			neuronPanel.add(neuronPhotoPanel);
			neuronPhotoPanel.setLayout(new BoxLayout(neuronPhotoPanel, BoxLayout.Y_AXIS));
			
			double [][] neuronValue = new double[1][1];
			neuronValue[0][0]= (int)(100- neuron.getMatchStrength()*100);
			
			HeatChart neuronHeatMap= new HeatChart(neuronValue,0,100);
			neuronHeatMap.setAxisColour(Color.RED);
			neuronHeatMap.setAxisValuesColour(Color.green);
			neuronHeatMap.setColourScale(1);
			neuronHeatMap.setXValues(neuronValue);
			neuronHeatMap.setShowXAxisValues(false);
			neuronHeatMap.setShowYAxisValues(false);
			neuronHeatMap.setChartMargin(2);
			
			JLabel neuronHeatMapLabel = new JLabel(new ImageIcon( neuronHeatMap.getChartImage(true) ));
			neuronPhotoPanel.add(neuronHeatMapLabel);
			neuronPhotoPanel.add(neuronDataLabel);
			String neuronData = " | c:"+String.format("%.11g%n", neuron.getConfidence()) +" | s:"+String.format("%.5g%n", neuron.getMatchStrength());
			if (!neuron.isFree()) neuronData += " | *";
			neuronDataLabel.setText( neuronData );
			
//			double [][] dendriteValues = new double[1][neuron.getDendrites().size()];
//			int i=-1;
//			for (Dendrite dendrite: neuron.getDendrites()){
//				i++;
//				JPanel dendritePanel= new JPanel();
//				JLabel dendriteLabel = new JLabel();
//				dendritePanel.setBorder(BorderFactory.createLineBorder(Color.blue));
//				
//				dendritePanel.setLayout(new BoxLayout(dendritePanel, BoxLayout.X_AXIS));
//				dendriteLabel.setText(""+dendrite.getExpectation() +"->"+dendrite.getLastInputWanted() +" "+String.format("%.15g%n", dendrite.getMatchStrength(dendrite.getLastInputWanted()) ));
//				dendritePanel.add(dendriteLabel);
//				neuronPanel.add(dendritePanel);
//				dendriteValues[0][i]= (int)( Math.abs( dendrite.getLastInputWanted()- dendrite.getExpectation()));
//			}
//			
////			for (int j=0; j<dendriteValues[0].length; j++){
////				System.out.print(dendriteValues[0][j]+ " ");
////			}
////			System.out.println();
//			
//			HeatChart dendriteHeatMap= new HeatChart(dendriteValues,0,100);
//			dendriteHeatMap.setYValues(dendriteHeatValues);
//			dendriteHeatMap.setAxisColour(Color.RED);
//			dendriteHeatMap.setAxisValuesColour(Color.green);
//			dendriteHeatMap.setColourScale(1);
//			dendriteHeatMap.setXValues(dendriteValues);
//			dendriteHeatMap.setShowXAxisValues(false);
//			dendriteHeatMap.setShowYAxisValues(false);
//			dendriteHeatMap.setChartMargin(2);
//			
//			JLabel heatLabel = new JLabel(new ImageIcon( dendriteHeatMap.getChartImage(true) ));
////			neuronPanel.add(heatLabel);
//			
//			neuronPhotoPanel.add(heatLabel);
////			neuronPanel.add(neuronPhotoPanel);
			
//			JPanel neuronImage = new JPanel();
			BufferedImage origImage=null;
			try {
				origImage= ImageIO.read(new File(neuronImagePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JLabel neuronImage  = new JLabel(new ImageIcon( neuron.getData() ));
			neuronPhotoPanel.add(neuronImage);

			JLabel neuronOrigImage  = new JLabel(new ImageIcon( origImage ));
			neuronPhotoPanel.add(neuronOrigImage);
			
			neuronPanel.invalidate();
			neuronPanel.setSize(300, 500);
		}
		
		neuronsPanel.revalidate();
	}
	
}

