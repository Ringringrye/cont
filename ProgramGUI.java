import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.binary.Base64; 
import java.nio.*;
import java.nio.file.*; 
import java.nio.file.Files; 
import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import java.io.File; 
import java.lang.Object.*; 
import javax.swing.*; 
import javax.swing.JComboBox; 
import java.util.ArrayList; 
import java.time.LocalDateTime; 

public class ProgramGUI extends JFrame{

	private static File selectedFile; 
	private static String saveLocation; 
	private static int level; 
	private static String figure1Loc; 
	private static String figure2Loc; 
	private static String figure3Loc; 
	private static String figure4Loc; 

	public Container createContentPane(){

		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane); 
		pane.setLayout(layout); 

		layout.setAutoCreateGaps(true); 
		layout.setAutoCreateContainerGaps(true); 

		JLabel selectFileL = new JLabel("Selected File: "); 
		JLabel saveLocationL = new JLabel("Save as: "); 
		JLabel levelL = new JLabel("Select Blast Level: "); 

		JTextField selectFileTF = new JTextField(); 
		selectFileTF.setEditable(false); 
		JTextField saveLocationTF = new JTextField();            



		JButton btnA = new JButton("Upload File..."); 
		btnA.addActionListener(
        	new ActionListener(){
        		public void actionPerformed(ActionEvent e){
        			JFileChooser upFile = new JFileChooser(); 
        			int returnValue = upFile.showOpenDialog(null);
        			if(returnValue == JFileChooser.APPROVE_OPTION){
        				selectedFile = upFile.getSelectedFile(); 
        				selectFileTF.setText(selectedFile.getName()); 

        			}

        		}
        	}
        );  

		JButton btnB = new JButton("Choose Location"); 
		btnB.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
						JFileChooser saveLoc = new JFileChooser(); 
						saveLoc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
						int returnValue = saveLoc.showOpenDialog(null); 
						if(returnValue == JFileChooser.APPROVE_OPTION){
							
							saveLocation = saveLoc.getCurrentDirectory().getAbsolutePath().toString(); 
							saveLocation = saveLocation + "/"+saveLoc.getSelectedFile().getName();
							saveLocationTF.setText(saveLocation); 

							saveLocation = saveLocation + "/" + selectedFile.getName() + "_report.html"; 
							
							Path p1 = Paths.get(saveLocation); 

							if(Files.exists(p1)){
								JOptionPane.showMessageDialog(new JFrame(),"A file with this name already exists at the current location. Program will overwrite current file.", "Warning", JOptionPane.ERROR_MESSAGE);							
							}

						}
					}
				}
			);

		Integer[] boxLevels = {1,2,3,4,5,6,7,8,9}; 
		JComboBox boxA = new JComboBox(boxLevels); 
		boxA.setSelectedIndex(0); 
		boxA.addActionListener(	
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					level = (int) boxA.getSelectedItem(); 
					System.out.println(level); 
				}
			}
		);  

		JButton btnC = new JButton("Run"); 
		btnC.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					Path p1 = Paths.get(saveLocation); 

					if(Files.exists(p1)){
						int n = JOptionPane.showConfirmDialog(new JFrame(), "A file with this name already exists at the current location. Press OK to overwrite.", "Warning", JOptionPane.OK_CANCEL_OPTION); 
						if( n == JOptionPane.OK_OPTION){
							runReport(); 
							reportGenerator(); 
						}
						else{
							//do nothingg
						}
					}
					else{
						runReport(); 
						reportGenerator(); 
					}	
				}
			}
		);

		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(selectFileL)
					.addComponent(saveLocationL)
					.addComponent(levelL))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(selectFileTF)
					.addComponent(saveLocationTF)
					.addComponent(boxA))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(btnA)
					.addComponent(btnB)
					.addComponent(btnC))
				
			); 

		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(selectFileL)
					.addComponent(selectFileTF)
					.addComponent(btnA))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(saveLocationL)
					.addComponent(saveLocationTF)
					.addComponent(btnB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(levelL)
					.addComponent(boxA)
					.addComponent(btnC)) 
			);

		return pane; 

	}

	public JMenuBar createMenuBar(){

		JMenuBar menuBar = new JMenuBar(); 

		JMenu menuB = new JMenu("Help"); 
		menuBar.add(menuB); 

		return menuBar; 
	}

	private static void runReport(){

		try{
		Process p = Runtime.getRuntime().exec("python test1.py " + selectedFile.getName()); 
		BufferedReader pyInput = new BufferedReader(new InputStreamReader(p.getInputStream())); 

		String s; 
		ArrayList<String> figures = new ArrayList<String>(); 

		while( (s = pyInput.readLine()) !=null ){
			figure1Loc = s;  
		}  

		//figure1Loc = figures.get(0); 
		//figure2Loc = figures.get(1); 
		//figure3Loc = figures.get(2); 
		//figure4Loc = figures.get(3); 

		}catch(Exception e){
		
			JOptionPane.showMessageDialog(new JFrame(),"Python script has failed", "Error", JOptionPane.ERROR_MESSAGE);

		}	
	}

	private static void reportGenerator(){

		String htmlString = "";
		
		File htmlTemplate = new File("Template.html"); 
		
		try{
			htmlString = FileUtils.readFileToString(htmlTemplate); 
		}catch(IOException e){
			System.out.println("Warning: no file found"); 
		}


		File image1 = new File(figure1Loc); 
		String figure1 = imageEncode(image1); 

		File image2 = new File("Images/duplication_levels.png");
		String figure2 = imageEncode(image2); 


		htmlString = htmlString.replace("$FIGURE1", figure1); 
		htmlString = htmlString.replace("$FIGURE2", figure2);
		
		String dateTimeGenerated = LocalDateTime.now().toString(); 
		String projectName = selectedFile.getName(); 

		htmlString = htmlString.replace("$DATETIME", dateTimeGenerated); 
		htmlString = htmlString.replace("$PROJECTNAME", projectName); 



		File report = new File(saveLocation); 

		try{
			FileUtils.writeStringToFile(report, htmlString); 
		}catch(IOException g){
			System.out.println("Report failed to write."); 
		}
	}


	private static String imageEncode(File filename){
                                         
			byte[] image = new byte[0]; 

		try{
			image = Files.readAllBytes(filename.toPath());
		}catch(IOException h){
			System.out.println("Image failed to encode"); 
		}

		String htmlencode =  "data:image/png;base64, " + new String(Base64.encodeBase64String(image)); 

		return htmlencode; 
	}

	public static void createWindow(){

		JFrame frame = new JFrame("TITLE HOLDER"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		ProgramGUI generator = new ProgramGUI(); 
		frame.setJMenuBar(generator.createMenuBar()); 
		frame.setContentPane(generator.createContentPane());

		frame.setSize(600,150); 
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


