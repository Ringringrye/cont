public class constants {

	//This portion must be changed whenever the file is changed - Vaishnavi
    public static int LENGTH_OF_BASESEQUENCE = 1543;
    
    private static double PERCENTAGE_MODEL = 0.01; 
    
    public static int TREE_LEVEL = 20;
    
    private static String FILE_NAME = "Test.txt";
    
    private static String NAME_OF_FINAL_FILE = "TrialKJL.txt";
    
    public static int MODEL_TYPE = 1;
    
    public static String fileName(){
    	return FILE_NAME; 
    }
    
    public void setFileName(String newName){
    	FILE_NAME = newName;
    }
    
    public void setModelType(int newName){
    	MODEL_TYPE = newName;
    }
    
    
    public int setTreeLevel(int newName){
    	TREE_LEVEL = newName;
    	return TREE_LEVEL;
    }
    
    public static String finalFile(){
    	return NAME_OF_FINAL_FILE; 
    }
    
    public void setFinalFile(String newName){
    	NAME_OF_FINAL_FILE = newName; 
    }

    
    public void setPercentage(double newPercentage){
    	PERCENTAGE_MODEL = newPercentage; 
    }
    
}
