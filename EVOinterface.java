import java.lang.StringBuilder; 
import java.nio.*;
import java.nio.file.*; 
import java.nio.file.Files; 
import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import java.io.File; 
import java.lang.Object.*; 
import javax.swing.*; 
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton; 
import javax.swing.GroupLayout; 
import javax.swing.JFileChooser; 
import javax.swing.JComboBox; 
import javax.swing.filechooser.FileNameExtensionFilter; 
import java.util.*; 
import java.util.ArrayList; 
import java.time.LocalDateTime; 

public class EVOinterface extends JFrame{


	public static Container createContent(Container pane){

		final ButtonGroup group = new ButtonGroup(); 
 
		GroupLayout layout = new GroupLayout(pane); 
		pane.setLayout(layout); 

		//LABELS

		JLabel modelTypeL = new JLabel("Select Model Type:"); 
		JLabel selectFileL = new JLabel("Selected File:"); 
		JLabel saveLocationL = new JLabel("Save Location:"); 
		JLabel fitnessL = new JLabel("Desired Fitness (decimal):"); 

		//RADIO BUTTONS

		JRadioButton btnA1 = new JRadioButton("");
		btnA1.setText("Simple"); 
		group.add(btnA1);  
		btnA1.setSelected(true); 

		JRadioButton btnA2 = new JRadioButton("");
		btnA2.setText("Complex"); 
		group.add(btnA2); 

		JRadioButton btnA3 = new JRadioButton("");
		btnA3.setText("Model 3"); 
		group.add(btnA3); 

		//TEXTFIELDS

		JTextField selectFileTF = new JTextField(); 
		JTextField saveLocationTF = new JTextField(); 
		JTextField fitnessTF = new JTextField(); 


		//BUTTONS

		JButton selectFileB = new JButton("Upload"); 
		JButton saveLocationB = new JButton("Select"); 
		JButton runB = new JButton("Run"); 


		layout.setAutoCreateGaps(true); 
		layout.setAutoCreateContainerGaps(true); 


		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(modelTypeL)
				.addComponent(selectFileL)
				.addComponent(saveLocationL)
				.addComponent(fitnessL))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addComponent(btnA1)
					.addComponent(btnA2)
					.addComponent(btnA3))
				.addComponent(selectFileTF)
				.addComponent(saveLocationTF)
				.addComponent(fitnessTF))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(selectFileB)
				.addComponent(saveLocationB)
				.addComponent(runB))
			); 

		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(modelTypeL)
				.addComponent(btnA1)
				.addComponent(btnA2)
				.addComponent(btnA3))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(selectFileL)
				.addComponent(selectFileTF)
				.addComponent(selectFileB))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(saveLocationL)
				.addComponent(saveLocationTF)
				.addComponent(saveLocationB))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(fitnessL)
				.addComponent(fitnessTF))
			.addComponent(runB)
			); 


		return pane; 
	}

	public static JMenuBar createMenuBar(JMenuBar menu){

		JMenu itemA = new JMenu("File"); 

		menu.add(itemA); 
		return menu; 
	}


	public static void createWindow(){

		JFrame frame = new JFrame("EVOLUTION OF RNA SECONDARY STRUCTURE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		Container pane = frame.getContentPane();
		frame.setContentPane(createContent(pane));

		JMenuBar menu = new JMenuBar(); 
		frame.setJMenuBar(createMenuBar(menu)); 

		frame.setSize(525,215); 
		frame.setVisible(true);  
	}
	
 
	public static void main(String[] args){

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createWindow();
            }
        });
	}	
}