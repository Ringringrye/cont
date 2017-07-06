import java.util.concurrent.ThreadLocalRandom;

public class Mutation {

	public Mutation(){
		
	}
	
	public int mutationPositions = 0;
	public String mutationBasePair = "";
	
	public RnaNode<String> createNextNode(){
		
		RnaNode<String> mutation = new RnaNode<String>();
		
		//generates random position
		int position = ThreadLocalRandom.current().nextInt(1, constants.LENGTH_OF_BASESEQUENCE);
		mutationPositions = position;

		//generates random nucleotide (GUAC)
		String temp = "#GUAC"; 
	    int spot = ThreadLocalRandom.current().nextInt(1, temp.length());
		String nucleotide = "" + temp.charAt(spot);
		mutationBasePair = nucleotide;
		mutation.setData(nucleotide, position);
				
		return mutation;
	}
	
	//saves position and base pair of mutation to be used by other methods
	public int mutationPosition(){
		return mutationPositions;
	}
	
	public String mutationBasePair(){
		return mutationBasePair;
	}
}
