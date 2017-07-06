import java.util.ArrayList;

public class sequenceBuilder {

	private ArrayList<String> masterKey; 
	private ArrayList<String> sequence; 
	private RnaNode<String> node; 
	
	public sequenceBuilder(RnaNode<String> current, ArrayList<String> key){
		masterKey = key;
		node = current; 
		
		sequence = new ArrayList<String>();
	}
	
	//rebuilds the sequence from tree nodes
	public ArrayList<String> build(){
		
		//running list of mutations that have already happened
		ArrayList<Integer> previousMutations = new ArrayList<Integer>();
		
		sequence.addAll(masterKey);
		
		while(node.hasParent()){
						
			String data = node.getData(); 
			int position = node.getPosition(); 
			

			//makes change only if a change has not already happened at the particular position
			if(!previousMutations.contains(position)){
				//System.out.println(position);
				sequence.set(position-1, data); 
				previousMutations.add(position);
			}
			
			node = node.getParent();
			
		}
				
		return sequence;
	}
}
