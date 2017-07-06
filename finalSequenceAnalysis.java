import java.util.ArrayList;

public class FinalSequenceAnalysis {
	
	private static ArrayList<Integer> positionV;
	
	public FinalSequenceAnalysis(ArrayList<Integer> key){
		
		 positionV = key;
	}
	
	public String calculations(ArrayList<String> sequenceList){
		

		String sequence  = "";
		int G = 0;
		int A = 0;
		int C = 0;
		int U = 0;
		int spot = 0;

		//counts all base pairs
		while(spot < (sequenceList.size())/2){
			
				if(sequenceList.get(spot).equals("G")){
					G++;
				}
				else if(sequenceList.get(spot).equals("A")){
					A++;
				}
				else if(sequenceList.get(spot).equals("C")){
					C++;
				}
				else if(sequenceList.get(spot).equals("U")){
					U++;
				}
			
			spot++;
		}
		
		String base = "";
		String pair = "";
		
		int AA = 0;
		int AC = 0;
		int AG = 0;
		int AU = 0;
		int CA = 0;
		int CC = 0;
		int CG = 0;
		int CU = 0;
		int GA = 0;
		int GC = 0;
		int GG = 0;
		int GU = 0;
		int UA = 0;
		int UC = 0;
		int UG = 0;
		int UU = 0;
		
		//counts all base pairings
		for(int counter = 0; counter < (sequenceList.size()-1)/2; counter++){
			
			base = sequenceList.get(counter);
			pair = sequenceList.get(positionV.get(counter));
			
			if(positionV.get(counter) != 0 && counter < positionV.get(counter)){
	
				if(base.equals("A") && pair.equals("A")){
					AA++;
				}
				else if(base.equals("A") && pair.equals("C")){
					AC++;
				}
				else if(base.equals("A") && pair.equals("G")){
					AG++;
				}
				else if(base.equals("A") && pair.equals("U")){
					AU++;
				}
				else if(base.equals("C") && pair.equals("A")){
					CA++;
				}
				else if(base.equals("C") && pair.equals("C")){
					CC++;
				}
				else if(base.equals("C") && pair.equals("G")){
					CG++;
				}
				else if(base.equals("C") && pair.equals("U")){
					CU++;
				}
				else if(base.equals("G") && pair.equals("A")){
					GA++;
				}
				else if(base.equals("G") && pair.equals("C")){
					GC++;
				}
				else if(base.equals("G") && pair.equals("G")){
					GG++;
				}
				else if(base.equals("G") && pair.equals("U")){
					GU++;
				}
				else if(base.equals("U") && pair.equals("A")){
					UA++;
				}
				else if(base.equals("U") && pair.equals("C")){
					UC++;
				}
				else if(base.equals("U") && pair.equals("G")){
					UG++;
				}
				else if(base.equals("U") && pair.equals("U")){
					UU++;
				}
			}
		}
		
		String baseFrequencies = "\n--Base Frequencies--\nA: " + Integer.toString(A) + " C: " + Integer.toString(C) + " G: " + Integer.toString(G) + " U: " + Integer.toString(U) + "\n";
		String pairedFrequenciesA  = "\n--Paired Frequencies--\nAA: " + Integer.toString(AA) + " AC: " + Integer.toString(AC) + " AG: " + Integer.toString(AG) + " AU: " + Integer.toString(AU) + "\n";
		String pairedFrequenciesC  = "CA: " + Integer.toString(CA) + " CC: " + Integer.toString(CC) + " CG: " + Integer.toString(CG) + " CU: " + Integer.toString(CU) + "\n";
		String pairedFrequenciesG  = "GA: " + Integer.toString(GA) + " GC: " + Integer.toString(GC) + " AG: " + Integer.toString(GG) + " GU: " + Integer.toString(GU) + "\n";
		String pairedFrequenciesU  = "UA: " + Integer.toString(UA) + " UC: " + Integer.toString(UC) + " UG: " + Integer.toString(UG) + " UU: " + Integer.toString(UU);
		
		sequence = baseFrequencies + pairedFrequenciesA + pairedFrequenciesC + pairedFrequenciesG + pairedFrequenciesU;
		
		return sequence;
		
	}
}
