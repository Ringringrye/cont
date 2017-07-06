import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException; 

public class Wrapper extends JFrame {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 900; 
	
	private JLabel fileName, fileSize, finalFileName, treeSize, modelType; 
	private JTextField fileNameTF, fileSizeTF, finalFileTF, treeSizeTF, modelTypeTF;
	private JButton runB, exitB; 
	
	public Wrapper(){
		setTitle("EVOLUTION OF RNA SIMULATION"); 
		
		Container pane = getContentPane(); 
		pane.setLayout(new GridLayout(6,2));
		
		//initiates labels 
		fileName = new JLabel("Enter text file name: ");
		fileSize = new JLabel("Enter desired fitness (decimal):" );
		finalFileName = new JLabel("Enter output file name: "); 
		treeSize = new JLabel("Enter number of generations: ");
		modelType = new JLabel("Which model? (1, 2, or 3): ");
		

		//initiates text fields 
		fileNameTF = new JTextField(15); 
		fileSizeTF = new JTextField(15); 
		finalFileTF = new JTextField(15);
		treeSizeTF = new JTextField(15);
		modelTypeTF = new JTextField(15);

		//Initiates the run button
		JButton runB = new JButton("Run"); 
		runBHandler rBhandle = new runBHandler(); 
		runB.addActionListener(rBhandle);
		
		pane.add(fileName);
		pane.add(fileNameTF);
		pane.add(finalFileName);
		pane.add(finalFileTF);
		pane.add(fileSize);
		pane.add(fileSizeTF);
		pane.add(treeSize);
		pane.add(treeSizeTF);
		pane.add(modelType);
		pane.add(modelTypeTF);
		pane.add(runB);
		
		setSize(WIDTH,HEIGHT); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	private class runBHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			constants usage = new constants();
			usage.setFileName(fileNameTF.getText());
			usage.setPercentage(Double.parseDouble(fileSizeTF.getText()));
			usage.setFinalFile(finalFileTF.getText());
			usage.setTreeLevel(Integer.parseInt(treeSizeTF.getText()));
			usage.setModelType(Integer.parseInt(modelTypeTF.getText()));
			
			try {
				Executor test = new Executor();
			} catch (IOException e1) {
				
			}
		}
		 
	}
}
