import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class parseFile {
	
	private ArrayList<Integer> position; 
	private ArrayList<String> sequence;
	
	/**Creates a parsed file to be accepted by the main method
	 * @throws FileNotFoundException **/
	public parseFile() throws FileNotFoundException{
		
		position = new ArrayList<Integer>(); 
		position.add(0); //fixes indexing 
		sequence = new ArrayList<String>();
		ArrayList<String> initArray = new ArrayList<String>(); 
		
		String filename = constants.fileName(); 
		Scanner reader = new Scanner(new File(filename));
		
		//eliminates initial 4 lines of non-sequence related data
		reader.nextLine();
		reader.nextLine();
		reader.nextLine();
		reader.nextLine();
		
		//Begins sorting through sequence data
		//this gets rid of whitespace (ex spaces, tabs, etc.) - Vaishnavi
		while(reader.hasNext()){
			initArray.add(reader.nextLine());
		}
		
		int count = 0; 
		while(count < initArray.size()){
			StringBuilder temp = new StringBuilder(initArray.get(count));
			
			//deletes bp position and all spaces in each line
			int inCount = 0; 
			while(temp.charAt(inCount) != ' '){
				temp.deleteCharAt(inCount);
			}
			temp.deleteCharAt(0);
		
			sequence.add(""+temp.charAt(0));
			
			//deletes nucleotide and space after
			temp.delete(0, 2);
			
			//puts matching location sequence into a new arraylist called position
			position.add(new Integer(temp.toString()));

			count++;
		}
		
		reader.close();
		
	}
	
	public ArrayList<String> sequenceVector(){
		return sequence;
	}
	
	public ArrayList<Integer> positionVector(){
		return position;
	}
	
	public ArrayList<String> copySequence(){
		
		int count = 0; 
		ArrayList<String> temp = new ArrayList<String>();
		
		while(count < sequence.size()){
			temp.add(sequence.get(count));
			count++;
		}
		
		return temp;
	}
	
}
