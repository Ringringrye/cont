import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class parseUnpaired {
		
		//returns the frequency of a single base given the position and mutation
		public double getFrequency(int position, int basepair) throws FileNotFoundException, IOException{
				
				BufferedReader reader = new BufferedReader(new FileReader("sbp_dict.txt"));
				
				//creates the matrix format of the key
				ArrayList<ArrayList<Double>> unpairedKey = new ArrayList<>();
				
				while(reader.ready()){
					
					String current = reader.readLine();
					ArrayList<Double> line = new ArrayList<>();
					
					//splits sbp_dict by values
					String[] splitting = current.split(",");
					
					//splits the arraylist by values
					for(int spot = 1; spot<splitting.length; spot++){
						
						line.add(Double.parseDouble(splitting[spot]));
						
					}
					
					unpairedKey.add(line);
		
//					unpairedKey.get(2).get(1); would give 5.78 if that was the 3rd position G
				}
				
				reader.close();
				
				return unpairedKey.get(position).get(basepair);
			}
			
		}

