import java.io.IOException;
import java.util.ArrayList;

//Combines all other programs
public class Executor {
	
	private static ArrayList<Integer> position;
	private ArrayList<String> masterKey; //working sequence 
		
	public Executor() throws IOException{
		
		//file turns the bpseq file into a position vector and the "masterkey" for the entire sequence 
		parseFile newFile = new parseFile();
		
		position = newFile.positionVector();
		masterKey = newFile.sequenceVector();

		//creates the complex model dictionary file
		parseDictionary newDictionary = new parseDictionary();
		
		finalSequenceAnalysis ending = new finalSequenceAnalysis(position);
		
		Checker check = new Checker(newDictionary.dictionary(), position);
		
		treeBuilder tree = new treeBuilder(masterKey, check, ending);
		tree.buildTree();

	}
	
}
	

