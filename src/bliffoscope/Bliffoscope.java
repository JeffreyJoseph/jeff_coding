package bliffoscope;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Bliffoscope {

	public Bliffoscope() {
		// TODO Auto-generated constructor stub
	}
	
	//Function to read target file list paths from properties file
	public ArrayList<String> getTargetsList(){
		ArrayList<String> targetList = new ArrayList<String>();
		try{
			String target_files = ReadProperties.getValue("targets");
			if(target_files==null || target_files.isEmpty())
				throw new FileNotSpecified("Target files not specified");
			String currPath = new File("").getAbsolutePath();
			StringTokenizer fileslist = new StringTokenizer(target_files, ";");
			while(fileslist.hasMoreElements())
				targetList.add(currPath + ((String)fileslist.nextElement()));
			return targetList;
		}
		catch(FileNotSpecified e){
			e.printStackTrace();
			return targetList;
		}
	}
	
	//Function to read image file path from properties file
	public String getImageFile(){
		String image_file = new String();
		try{
			image_file = ReadProperties.getValue("image");
			if(image_file==null || image_file.isEmpty())
				throw new FileNotSpecified("Image file not specified");
			String currPath = new File("").getAbsolutePath();
			return currPath+image_file;
		}
		catch(FileNotSpecified e){
			e.printStackTrace();
			return image_file;
		}
	}
	
	//Function to find the target
	/*
	 * The function matches the target against the image file as a window which
	 * is moved to right and bottom in every iteration
	 */
	public int findTarget(char data[][], char target[][], String print_target){
		int data_rows = data.length;
		int data_cols = data[0].length;
		int targ_rows = target.length;
		int targ_cols = target[0].length;
		
		/*
		 * If target is bigger than the image, then it can be concluded that given
		 * image is not sufficient to find the target
		 */
		if(targ_rows>data_rows || targ_cols>data_cols)
			return -1;
		
		int target_count = 0;
		
		boolean result = false;
		
		for(int x=0; x<=data_rows-targ_rows; x++){
			for(int y=0; y<=data_cols-targ_cols; y++){
				result = findTargetInWindow(x, y, targ_rows, targ_cols, data, target, print_target);
				//If target found, the co-ordinates are printed
				if(result){
					target_count++;
					System.out.println("Target co-ordinates are");
					System.out.println("Top left ("+x+", "+y+")");
					System.out.println("Top right ("+x+", "+(y+targ_cols-1)+")");
					System.out.println("Bottom right ("+(x+targ_rows-1)+" "+(y+targ_cols-1)+")");
					System.out.println("Bottom left ("+(x+targ_rows-1)+" "+y+")");
					System.out.println();
					x = x+targ_rows-1;
					if(x>=(data_rows-targ_rows)) break;
					y = y+targ_cols-1;
				}
			}
		}
		return target_count;
	}
	
	//Function to match the target in the image for the specified window
	/* 
	 * Checks if the pixel set in target is set in the image
	 * If set, it counts as hit, else its a miss
	 * 
	 * Hit percentage is computed to find whether target is found in that window
	 */
	public boolean findTargetInWindow(int data_row, int data_col, int targ_max_row, int targ_max_col, char data[][], char target[][],  String print_target){
		int hit = 0, miss = 0;
		char result[][] = new char[targ_max_row][targ_max_col];
		for(int x=0; x<targ_max_row; x++){
			for(int y=0; y<targ_max_col; y++){
				result[x][y] = data[data_row+x][data_col+y];
				if(target[x][y] == data[data_row+x][data_col+y])
					hit++;
				else
					miss++;
			}
		}
		//System.out.println("Hit = "+hit+"\t"+"Miss = "+miss);
		
		//Match percentage is read from properties file
		int match_percentage = Integer.parseInt(ReadProperties.getValue("match_percentage"));
		
		int match_percent_found = (hit*100)/(hit+miss);
		
		if(match_percent_found>match_percentage){
			//If print_target is set as Y in properties file, target scanned will be printed
			if(print_target.equalsIgnoreCase("Y")){
				System.out.println("Target image found");
				for(int x=0; x<targ_max_row; x++){
					for(int y=0; y<targ_max_col; y++){
						System.out.print(result[x][y]);
					}
					System.out.println();
				}
			}
			return true;
		}
		else
			return false;
	}
	
	//Main Function
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bliffoscope bliffoscope = new Bliffoscope();
		
		String image_file_path = bliffoscope.getImageFile();
		ArrayList<String> target_file_list = bliffoscope.getTargetsList();
		if(image_file_path==null || target_file_list.isEmpty())
			return;
		
		//Image file is scanned
		ReadFiles readFiles = new ReadFiles();
		ArrayList<ArrayList<Character>> image_list = readFiles.scanTemplate(image_file_path, true);
		char[][] image_array = readFiles.createImageArray(image_list);
		
		String print_target = ReadProperties.getValue("print_target");
		
		//List of target files are scanned and mapped against the image
		Iterator<String> itr_target_file_list = target_file_list.iterator();
		while(itr_target_file_list.hasNext()){
			String target_path = itr_target_file_list.next();
			ArrayList<ArrayList<Character>> target_list = readFiles.scanTemplate(target_path, false);
			char[][] target_array = readFiles.createTargetArray(target_list, print_target);
			
			int target_count = bliffoscope.findTarget(image_array, target_array, print_target);
			System.out.println("Target = "+target_path);
			System.out.println("Target count = "+target_count+"\n");
			
		}
	}

}
