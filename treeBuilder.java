import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class treeBuilder{
	
	private RnaNode<String> root; //bases of the tree, also contains the original mutation
	private ArrayList<String> key; // master sequence
	private Mutation mutator; 
	private Integer correctingP; //position of the original mutation
	private Checker check; 
	private PrintWriter output; 
	private FinalSequenceAnalysis ending;
	private int processingRNA = 0;
	private int mutationLevel = 0;
	public int levelSize = 0;

	
	public treeBuilder(ArrayList<String> mkey, Checker check, FinalSequenceAnalysis ending) throws FileNotFoundException, NullPointerException{
	
		mutator = new Mutation(); 
		
		//Checker with all the keys
		this.check = check; 
		
		this.ending = ending;
		
		//master sequence
		key = mkey; 
		
		//creates root of the tree, containing the first mutation and saves mutation data
		root = mutator.createNextNode();
		
		//checks original mutaiton is base paired, if not picks a new position
		int spot = root.getPosition();
		
		ArrayList<Integer> positions = check.possibleHelix(); 
		
		while(positions.get(spot) == 0){
			spot = ThreadLocalRandom.current().nextInt(1, constants.LENGTH_OF_BASESEQUENCE); 
		}
		
		//checks original position was actually mutated
		String nuc = root.getData(); 
		
		while(nuc.equals(key.get(spot-1))){
			String temp = "#GUAC"; 
			int count = ThreadLocalRandom.current().nextInt(1,temp.length()); 
			nuc = "" + temp.charAt(count);
		}
		
		root.setData(nuc,spot);

		
		//stores the position where the correction mutation pair is
		correctingP = root.getPosition(); 
		
		//changes the master sequence to include the original mutation
		key.set(correctingP-1, root.getData());
		
		System.out.println("Correcting position is: " + root.getData() + root.getPosition());
		
		output = new PrintWriter(constants.finalFile());
	}
	
	public void buildTree() throws FileNotFoundException, IOException{

		//list of all the nodes in the current level of the tree
		ArrayList<RnaNode<String>> currentLevel = new ArrayList<>(); 
		currentLevel.add(root); 
		
		int level = 0; 

		while(currentLevel.size() > 0 && level < constants.TREE_LEVEL){

			//output how many nodes in current level 
			output.println("Ongoing RNA for level " + level + ": " + currentLevel.size());
			levelSize = currentLevel.size();
			
			//holds all the possible nodes in the next level 
			ArrayList<RnaNode<String>> nextLevel = new ArrayList<>();

			//tracks position in current cycle
			int spot = 0; 

			while(spot < currentLevel.size()){

				RnaNode<String> currentNode = currentLevel.get(spot);

				/*if the current mutation does not lead to a bad sequence, makes 
				children for the current selected node, and places as available for nextLevel*/
				if(!this.childrenPossible(currentNode)){

					this.createLeftChild(currentNode);
					this.createRightChild(currentNode);

					nextLevel.add(currentNode.getLeft());
					nextLevel.add(currentNode.getRight());
				}
				else{
					//counter for deadRNA
				//	deadRNA[level]++;
				}

				spot++;
			}
			
			//starts work on the next possible level 
			System.out.println(level); 
			level++;
			mutationLevel = level;
			currentLevel = nextLevel; 
			printer(root);
		}
		
	}
	
	//creates left child for the current node
	private void createLeftChild(RnaNode<String> current){
		
		RnaNode<String> node = current;
		RnaNode<String> nextNode = mutator.createNextNode();
		nextNode.setParent(current); 
		
		//saves mutation data for checkers
		current.setLeft(nextNode);
	}
	
	private void createRightChild(RnaNode<String> current){
		
		RnaNode<String> node = current;
		RnaNode<String> nextNode = mutator.createNextNode(); 
		nextNode.setParent(current);
		
		//saves mutation data for checkers
		current.setRight(nextNode);
	}
	
	/**
	 * 
	 * @param current
	 * @return true if children not possible
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private boolean childrenPossible(RnaNode<String> current) throws FileNotFoundException, IOException{
	
		//builds a working sequence using the string builder to be used with checker
		sequenceBuilder builder = new sequenceBuilder(current,key); 
		ArrayList<String> sequence = builder.build(); 
		
		/***CHANGE HERE depending on model type***/
		if(constants.model() == 1){
			if(check.correctingM(sequence,correctingP)){
				current.found(); //marks current node as a correcting mutation 
				return true;
			}
			//checks for if the RNA is no longer fit
			else if(!check.simpleCheck(sequence, current.getPosition(), mutationLevel, current)){
				return true;
			}
			else{
				return false;
			}
			
		}
		else{
			if(check.correctingM(sequence,correctingP)){
				current.found(); //marks current node as a correcting mutation 
				return true;
			}
			//checks for if the RNA is no longer fit
			else if(!check.complexCheck(current.getPosition(), sequence, mutationLevel, current)){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	//prints all relevant information for correcting sequences
	private boolean printer(RnaNode<String> node){
		
		RnaNode<String> current = node; 
		
		if(!current.hasChildren()){
			return true; 
		}
		else{
			RnaNode<String> leftChild = current.getLeft(); 
			RnaNode<String> rightChild = current.getRight();

			//prints information if left child is correcting
			if(leftChild.correcting()){
				sequenceBuilder builder = new sequenceBuilder(current,key); 
				
				output.println("\nCorrecting sequence information: \nLevel: " + leftChild.distanceFromRoot(leftChild));
				writeTree(leftChild); 
				output.println("Final Sequence: " + builder.build());
				output.println("Dead RNA: " + check.getDeadRNA());
				output.println(ending.calculations(builder.build()));
//				check.closeSpecificDetail();
//				output.close();
//				System.exit(0);
			}
			
			//prints information if right child is correcting
			if(rightChild.correcting()){
				sequenceBuilder builder = new sequenceBuilder(current,key); 
				
				output.println("\nCorrecting sequence information: Level: " + rightChild.distanceFromRoot(rightChild));
				writeTree(rightChild);
				output.println("Final Sequence: " + builder.build());
				output.println("Dead RNA: " + check.getDeadRNA());
				output.println(ending.calculations(builder.build()));
//				check.closeSpecificDetail();
//				output.close();
//				System.exit(0);
			}
			
			printer(leftChild);
			printer(rightChild);
			
			return false; 
		}
	}
	
	private void writeTree(RnaNode<String> current){
		
		output.print("Mutation sequence: "); 
				
		//cycles through tree printing all mutations + locations
		while(current.hasParent()){
			output.print(current.getData() + " " + current.getPosition() + ",  ");
			current = current.getParent(); 
		}
		
		output.println(); 
	}
}

