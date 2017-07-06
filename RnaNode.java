

public class RnaNode<String> {

	private String mydata; 
	private boolean correctingS; 
	private Integer myPosition; 
	private RnaNode<String> parent;
	private RnaNode<String> left; 
	private RnaNode<String> right; 
	private Integer goodChanges;
	
	//creates an empty node
	public RnaNode(){
		mydata = null;
		parent = null;
		left = null; 
		right = null; 
		correctingS = false; //all new nodes should be false until checked by treeBuild
		goodChanges = 0;
	}
	
	//sets the position and mutation for this node
	public void setData(String data, Integer pos){
		mydata = data; 
		myPosition = pos; 
	}
	
	//sets the left pointer to a children node
	public void setLeft(RnaNode<String> myleft){
		left = myleft; 
	}
	
	//sets the right pointer to a children node
	public void setRight(RnaNode<String> myRight){
		right = myRight; 
	}
	
	//sets the parent
	public void setParent(RnaNode<String> previous){
		parent = previous; 
	}
	
	//sets position
	public void setPosition(int pos){
		myPosition = pos; 
	}
	
	//returns the right node
	public RnaNode<String> getRight(){
		return right; 
	}
	
	//returns the left node
	public RnaNode<String> getLeft(){
		return left;
	}
	
	//returns the data in the current node
	public String getData(){
		return mydata;
	}
	
	//returns position
	public Integer getPosition(){
		return myPosition;
	}
	
	public boolean hasChildren(){
		return left != null && right != null; 
	}
	
	public boolean hasParent(){
		return parent != null;
	}
	
	public RnaNode<String> getParent(){
		return parent; 
	}
	
	public void setGoodChanges(int change){
		goodChanges++;
	}
	
	public Integer getGoodChanges(){
		return goodChanges;
	}
	
	public void found(){
		correctingS = true; 
	}
	
	public boolean correcting(){
		return correctingS; 
	}
	
	//recursive method to determine distance from the root of tree (gives the "generation" number)
	public int distanceFromRoot(RnaNode<String> current){
	
		return distanceFromRoot(current,0);
	}
	
	private int distanceFromRoot(RnaNode<String> current, int num){
		
		if (!current.hasParent()){
			return num;
		}
		
		return distanceFromRoot(current.getParent(), ++num);
	}
}
