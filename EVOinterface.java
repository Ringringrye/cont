import java.lang.StringBuilder; 
import java.nio.*;
import java.nio.file.*; 
import java.nio.file.StandardCopyOption.*; 
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

	private static File targetBPSEQ;
	private static String saveLocation; 


	public static Container createContent(Container pane){

		final ButtonGroup groupA = new ButtonGroup(); 
		final ButtonGroup groupB = new ButtonGroup(); 

 
		GroupLayout layout = new GroupLayout(pane); 
		pane.setLayout(layout); 

		//LABELS

		JLabel modelTypeL = new JLabel("Select Model Type:"); 
		JLabel selectFileL = new JLabel("Selected File:"); 
		JLabel saveLocationL = new JLabel("Save Location:"); 
		JLabel fitnessL = new JLabel("Desired Fitness (decimal):"); 
		JLabel generationsL = new JLabel("Generation Limit:"); 
		JLabel runL = new JLabel("Run to:"); 
		JLabel mutationL = new JLabel("First mutation:"); 

		//RADIO BUTTONS GROUP 1

		JRadioButton btnA1 = new JRadioButton("Simple");
		groupA.add(btnA1);  
		btnA1.setSelected(true); 

		JRadioButton btnA2 = new JRadioButton("Complex");
		groupA.add(btnA2); 

		JRadioButton btnA3 = new JRadioButton("Model 3");
		groupA.add(btnA3); 

		//RADIO BUTTONS GROUP 2

		JRadioButton btnB1 = new JRadioButton("Random");
		groupB.add(btnB1);
		btnB1.setSelected(true); 

		JRadioButton btnB2 = new JRadioButton("Defined"); 
		groupB.add(btnB2); 

		btnB2.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String returnValue = JOptionPane.showInputDialog(new JFrame(), "Please choose a location (pos-base)"); 
					
					if(returnValue == null || returnValue.equals("")){
						btnB1.setSelected(true);
					}
				}
			});

		//TEXTFIELDS

		JTextField selectFileTF = new JTextField(); 
		JTextField saveLocationTF = new JTextField(); 
		JTextField fitnessTF = new JTextField(); 
		JTextField generationsTF = new JTextField();


		//BUTTONS

		JButton selectFileB = new JButton("Upload"); 
		selectFileB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){

					JFileChooser selector = new JFileChooser();
					int returnValue = selector.showOpenDialog(null); 
					if(returnValue == JFileChooser.APPROVE_OPTION){
						targetBPSEQ = selector.getSelectedFile(); 

						selectFileTF.setText(targetBPSEQ.getAbsolutePath());
					}
				}
			});
		
		JButton saveLocationB = new JButton("Select"); 
		saveLocationB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){

					JFileChooser selector = new JFileChooser(); 
					selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					int returnValue = selector.showOpenDialog(null); 
					if(returnValue == JFileChooser.APPROVE_OPTION){

						saveLocation = selector.getCurrentDirectory().getAbsolutePath(); 
						saveLocation = saveLocation + "/" + selector.getSelectedFile().getName(); 

						if(targetBPSEQ != null){
							saveLocation = saveLocation + targetBPSEQ;

							saveLocationTF.setText(saveLocation); 

							Path savePath = Paths.get(saveLocation);

							if(Files.exists(savePath)){
									JOptionPane.showMessageDialog(new JFrame(),"A file with this name already exists at the current location. Program will overwrite current file.", "Warning", JOptionPane.ERROR_MESSAGE);							
								}
						}else{
							JOptionPane.showMessageDialog(new JFrame(), "Please select a file first", "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}

				}

			});

		JButton runB = new JButton("Run"); 

		//DROP DOWN 
		String[] levels = {"End", "First"}; 
		JComboBox boxA = new JComboBox(levels); 
		boxA.setSelectedIndex(0);


		layout.setAutoCreateGaps(true); 
		layout.setAutoCreateContainerGaps(true); 


		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(modelTypeL)
				.addComponent(selectFileL)
				.addComponent(saveLocationL)
				.addComponent(fitnessL)
				.addComponent(generationsL))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addComponent(btnA1)
					.addComponent(btnA2)
					.addComponent(btnA3))
				.addComponent(selectFileTF)
				.addComponent(saveLocationTF)
				.addComponent(fitnessTF)
				.addComponent(generationsTF)
				.addGroup(layout.createSequentialGroup()
					.addComponent(runL)
					.addComponent(boxA)
					.addComponent(mutationL)
					.addComponent(btnB1)
					.addComponent(btnB2)))
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
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(generationsL)
				.addComponent(generationsTF))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(runL)
				.addComponent(boxA)
				.addComponent(mutationL)
				.addComponent(btnB1)
				.addComponent(btnB2))
			.addComponent(runB)
			); 


		return pane; 
	}

	public static JMenuBar createMenuBar(JMenuBar menu) throws java.io.IOException{

		JMenu menuA = new JMenu("File"); 

		JMenuItem itemA1 = new JMenuItem("Run a python script..."); 
		itemA1.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JFileChooser selector = new JFileChooser(); 
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Python", "py"); 
					selector.setFileFilter(filter); 

					int returnValue = selector.showOpenDialog(null); 

					if(returnValue == JFileChooser.APPROVE_OPTION){

						File selectedFile = selector.getSelectedFile(); 
						String path = selectedFile.getPath();
						

						//FOR MAC
						File temp = new File("temp_script.py"); 
						try{

							Files.copy(Paths.get(selectedFile.getPath()), Paths.get(temp.getPath()), StandardCopyOption.COPY_ATTRIBUTES); 
						
						}catch(IOException a){

						}


						try{
							Process proc = Runtime.getRuntime().exec("python temp_script.py");

							BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

							BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

							// read the output from the command
							System.out.println("Here is the standard output of the command: ");
							String s = null;
							while ((s = stdInput.readLine()) != null) {
							    System.out.println(s);
							}

							// read any errors from the attempted command
							System.out.println("Here is the standard error of the command (if any): ");
							while ((s = stdError.readLine()) != null) {
							    System.out.println(s);
							}

							if(!temp.delete()){
								JOptionPane.showMessageDialog(new JFrame(), "Temporary Files failed to delete", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(Exception a){
							JOptionPane.showMessageDialog(new JFrame(),"Python script has failed", "Error", JOptionPane.ERROR_MESSAGE);
						}

						/*FOR WINDOWS	
						try{
							Process proc = Runtime.getRuntime().exec("C:\\Python27\\python.exe " + path);	

							BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

							BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

							// read the output from the command
							System.out.println("Here is the standard output of the command: ");
							String s = null;
							while ((s = stdInput.readLine()) != null) {
							    System.out.println(s);
							}

							// read any errors from the attempted command
							System.out.println("Here is the standard error of the command (if any): ");
							while ((s = stdError.readLine()) != null) {
							    System.out.println(s);
							}

						}catch(IOException a){
	
						}*/

						
					}

				}
			}
		);

		menuA.add(itemA1); 

		menu.add(menuA); 
		return menu; 
	}


	public static void createWindow(){

		JFrame frame = new JFrame("EVOLUTION OF RNA SECONDARY STRUCTURE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		Container pane = frame.getContentPane();
		frame.setContentPane(createContent(pane));

		JMenuBar menu = new JMenuBar(); 
		
		try{
			frame.setJMenuBar(createMenuBar(menu));
		}catch(IOException e){

		}

		frame.setSize(700,275); 
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