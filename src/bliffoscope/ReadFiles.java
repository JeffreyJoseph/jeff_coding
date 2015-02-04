package bliffoscope;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFiles {

	int left_trim;
	int right_trim;
	
	ReadFiles(){
		this.left_trim = Integer.MAX_VALUE;
		this.right_trim = 0;
	}
	
	/*
	 * Function to scan the image / target file
	 */
	public ArrayList<ArrayList<Character>> scanTemplate(String path, boolean data) {
		FileInputStream fis = null;
		InputStreamReader reader = null;
		try{
			fis = new FileInputStream(path);
			Charset encoding = Charset.defaultCharset();
			reader = new InputStreamReader(fis, encoding);
			
			int i_character = -1;
			left_trim = Integer.MAX_VALUE;	//Sets index of left most set bit across all input lines
			right_trim = 0;                 //Sets index of right most set bit across all input lines
			int top_trim = 0;
			int bottom_trim = 0;
			int line_no = -1;				//To keep track of line number of the input
			boolean found = false;
			int left = 0,  index = 0;
			
			ArrayList<ArrayList<Character>> list = new ArrayList<ArrayList<Character>>();
			
			while(true){
				ArrayList<Character> sub_list = new ArrayList<Character>();
				index = 0;
				left = -1;
				line_no++;
				while((i_character = reader.read()) != -1){
					char c_character = (char)i_character;
					if(c_character == '\n')
						break;
					sub_list.add(c_character);
					if(c_character == '+'){
						if(found==false)			//To trim empty lines at the bottom.
							found = true;
						if(top_trim == 0)           //To trim empty lines at the top. Finds no of blank lines at the top.
							top_trim = 1;
						if(left == -1)			    //To trim white spaces in the left. This will find left most set pixel across all lines in the input.
							left = index;
						if(index>right_trim)        //To trim white spaces in the right. This will find right most set pixel across all lines in the input.
							right_trim = index;
					}
					index++;
				}
				if(found && bottom_trim<line_no)			//To trim blank lines at the bottom. Sets the last line which has a set pixel.
					bottom_trim = line_no;
				if(data || left != -1 || top_trim == 1)		//To trim blank lines at the top. Will not trim if image file is scanned
					list.add(sub_list);
				if(left>=0 && left<left_trim)
					left_trim = left;
				if(i_character==-1)
					break;
			}
			//System.out.println("Left trim = "+left_trim+"\t"+"Right trim = "+right_trim);
			
			//If it is target image, then the empty lines at bottom will be trimmed
			if(!data){
				int lines_to_trim = list.size() - bottom_trim + 1;
				for(int i=list.size()-1, j=0; j<lines_to_trim; i--,j++)
					list.remove(i);
			}
			return list;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				fis.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Function to create a 2D Target array for the given input target
	 */
	public char[][] createTargetArray(ArrayList<ArrayList<Character>> list, String print_target){
		int rows = list.size();
		int cols = right_trim - left_trim +1;
		//System.out.println("Rows = "+rows+"\t"+"Cols = "+cols);
		char mask[][] = new char[rows][cols];

		Iterator<ArrayList<Character>> iterator = list.iterator();
		ArrayList<Character> temp = null;
		
		for(int r = 0; r<rows; r++){
			temp = iterator.next();
			Iterator<Character> itr = temp.iterator();
			for(int c = 0; c<left_trim; c++)
				itr.next();
			for(int c = 0; c<cols;c++)
				mask[r][c] = itr.next();
			
		}
		
		//Print the target scanned if print_target is set to Y in properties file
		if(print_target.equalsIgnoreCase("Y")){
			for(int r = 0; r<rows; r++){
				for(int c = 0; c<cols;c++)
					System.out.print(mask[r][c]);
				System.out.println();
			}
		}
		return mask;
	}

	/*
	 * Function to create a 2D array of the image 
	 */
	public char[][] createImageArray(ArrayList<ArrayList<Character>> list){
		int rows = list.size();
		int cols = right_trim;
		//System.out.println("Inside createImageArray");
		//System.out.println("Rows = "+rows+"\t"+"Cols = "+cols);
		
		char data[][] = new char[rows][cols];
		
		Iterator<ArrayList<Character>> iterator = list.iterator();
		ArrayList<Character> temp = null;
		
		for(int r = 0; r<rows; r++){
			temp = iterator.next();
			Iterator<Character> itr = temp.iterator();
			for(int c = 0; c<cols;c++)
				if(itr.hasNext())
					data[r][c] = itr.next();
				else
					data[r][c] = ' ';
		}
		/*for(int r = 0; r<rows; r++){
			for(int c = 0; c<cols;c++)
				System.out.print(data[r][c]);
			System.out.println();
		}*/
		return data;
	}
}
