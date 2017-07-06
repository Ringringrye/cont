import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Checker{
	
	private static HashMap<Integer, ArrayList<Integer>> key; //stores the dictionary for the complex model
	private static ArrayList<Integer> positionV; //stores universal master position key
	parseUnpaired unpair = new parseUnpaired();
	PrintWriter specificDetail = new PrintWriter("RunDetails.txt");
	int[] deadRNA = new int[30];

	public Checker(HashMap<Integer, ArrayList<Integer>> dictionary, ArrayList<Integer> position) throws FileNotFoundException{
		
		key = dictionary; 
		positionV = position; 
		
	}
	
	//returns if the correcting mutation has been found
	public boolean correctingM(ArrayList<String> sequence, int pos){
	
		String bp1 = sequence.get(pos-1); 
		String bp2 = sequence.get(positionV.get(pos)-1);
		
		if(constants.model() == 1){
			return simpleLocCheck(bp1, bp2);
		}
		else{
			return complexLocCheck(pos, bp1, bp2);
		}
	}
	
	//checker for the simple model 
	public boolean simpleCheck(ArrayList<String> sequence, int spot, int level, RnaNode<String> current) throws FileNotFoundException, IOException{
		
		RnaNode<String> treeCounter = current;
		int spot2 = positionV.get(spot); //position of pairing
		int increment = 0;
		while(treeCounter.hasParent()){
			increment = treeCounter.getGoodChanges() + increment;
			treeCounter = treeCounter.getParent();
		}
		
		int control = level - increment; //gauge for molecule fitness, mutations are automatically bad unless counted as good
		int total = constants.LENGTH_OF_BASESEQUENCE;
		int state = 0; //keeps track of type of mutation
		String result = ""; //keeps track of molecule death
			
		//if position at the location is paired to another location
		if(spot2 != 0){
			
			String bp1 = sequence.get(spot-1);  //base pair of mutation
			String bp2 = sequence.get(spot2-1); //base pair of pairing
			
			//checks if the pairing at this position is good
			if(!this.simpleLocCheck(bp1,bp2)){
				control++;
				state = 1;
			}
			else{
				state = 2;
				current.setGoodChanges(increment++);
			}
 		}
		
		if(control > (total * constants.percentageModel())){
			deadRNA[level]++;
			result = "Dead";
		}
		else{
			result = "Alive";
		}
		
		//prints relevant information
		if(state == 0){
			current.setGoodChanges(increment++);
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Neutral Result: %s%n" , increment, control, spot, level, result);
		}
		else if(state == 1){
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Bad Result: %s%n" , increment, control, spot, level, result);
		}
		else if(state == 2){
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Good Result: %s%n" , increment, control, spot, level, result);
		}
		
		return control < (total * constants.percentageModel());
	}
	
	//checker for the complex model, almost identical to the simple model except for the locchecker
	public boolean complexCheck(int spot, ArrayList<String> sequence, int level, RnaNode<String> current) throws FileNotFoundException, IOException{
		
		RnaNode<String> treeCounter = current;
		int spot2 = positionV.get(spot); //position of pairing
		int increment = 0;
		while(treeCounter.hasParent()){
			increment = treeCounter.getGoodChanges() + increment;
			treeCounter = treeCounter.getParent();
			
		}
		
		int control = level - increment; //gauge for fitness
		int total = constants.LENGTH_OF_BASESEQUENCE;
		int state = 0; //keeps track of what type of mutation has occured
		String result = ""; //keeps track of whether or not the molecule is alive
			
		//if position at the location is paired to another location
		if(spot2 != 0){
			
			String bp1 = sequence.get(spot-1);  //base pair of mutation
			String bp2 = sequence.get(spot2-1); //base pair of pairing
			
			//checks if the pairing at this position is good
			if(!this.complexLocCheck(spot,bp1,bp2)){
				control++;
				state = 1;
			}
			else{
				state = 2;
				current.setGoodChanges(increment++);
			}
 		}
		
		//implements model 3 if necessary
		if(constants.model() == 3 && spot2 == 0){
			if(!this.unpairLocCheck(sequence, spot)){
				control++;
				state = 1;
			}
			else{
				state = 0;
			}
		}
		
		if(control > (total * constants.percentageModel())){
			deadRNA[level]++;
			result = "Dead";
		}
		else{
			result = "Alive";
		}
		
		//prints relevant information
		if(state == 0){
			current.setGoodChanges(increment++);
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Neutral Result: %s%n" , increment, control, spot, level, result);
		}
		else if(state == 1){
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Bad Result: %s%n" , increment, control, spot, level, result);
		}
		else if(state == 2){
			specificDetail.printf("good: %d bad: %d position: %d level: %d state: Good Result: %s%n" , increment, control, spot, level, result);
		}
		
		return control < (total * constants.percentageModel());
	}
	
	public int getDeadRNA(){
		
		int sum = 0;
		for(int i = 0; i < deadRNA.length; i++){
		    sum += deadRNA[i];
		}
		
		return sum;
	}
	
	public boolean unpairLocCheck(ArrayList<String> sequence, int spot) throws FileNotFoundException, IOException{
		
			String bp1 = sequence.get(spot-1); 
			int base = this.unpairedNumberChecker(bp1);
			int rando = (int)(Math.random()*100);
			double index = unpair.getFrequency(spot-1, base); 
			
			if(rando < index){
				return true; 
			}
			else{
				return false;
			}
	
	}
	
	//individual location checker for the simple model
	public boolean simpleLocCheck(String bp1, String bp2){
		
		//allows for Watson-Crick and GU base pairs
		if(bp1.equals("C") && bp2.equals("G")){
            return true; 
        }
        else if(bp1.equals("G") && (bp2.equals("C") || bp2.equals("U"))){
        	return true; 
        }
        else if(bp1.equals("A") && bp2.equals("U")){
        	return true; 
        }
        else if(bp1.equals("U") && (bp2.equals("A") || bp2.equals("G"))){
        	return true;
        }
        else{
        	return false;
        }
	}
	
	//individual location checker for the complex model
	public boolean complexLocCheck(int location, String bp1, String bp2){
	
		//Checks the dictionary first for possibilities
		if(key.containsKey(location)){
			
			ArrayList<Integer> possible = (ArrayList<Integer>) key.get(location);
			
			int spot = (this.numberChecker(bp1)*4) + this.numberChecker(bp2); //finds the correct position for the base pairing
			
			if(possible.get(spot) > 0){
				int random = (int)(Math.random()*100);
				
				//like rolling a dice. 
				if(random < possible.get(spot)){
					return true;
				}
			}
			
		}
		else{
			if(bp1.equals("C") && bp2.equals("G")){
				return true; 
			}
			else if(bp1.equals("G") && (bp2.equals("C") || bp2.equals("U"))){
				return true; 
			}
			else if(bp1.equals("A") && bp2.equals("U")){
				return true; 
			}
			else if(bp1.equals("U") && (bp2.equals("A") || bp2.equals("G"))){
				return true;
			}
		}
		
		return false; 
	}
	
	//returns appropriate numbers to find the correction position in the complex check dictionary
	private Integer numberChecker(String nuc){
		
		if(nuc.equals("A")){
			return 0;
		}
		else if(nuc.equals("C")){
			return 1;
		}
		else if(nuc.equals("G")){
			return 2;
		}
		else{
			return 3; 
		}
	}
	
	private Integer unpairedNumberChecker(String nuc){
		
		if(nuc.equals("A")){
			return 0;
		}
		else if(nuc.equals("G")){
			return 1;
		}
		else if(nuc.equals("C")){
			return 2;
		}
		else{
			return 3; 
		}
	}	
	
	public ArrayList<Integer> possibleHelix(){
		return positionV;
	}
	
	public void closeSpecificDetail(){
		specificDetail.close();
	}

}