package matrices;

import java.util.ArrayList;

public class Crossword {

	ArrayList<String> list = new ArrayList<String>();
	
	public void findWord(char matrix[][], int rows, int cols, int r, int c, String word, int index){
		if(r<0 || r>rows || c<0 || c>cols)
			return;
		//System.out.println("matrix[r][c] = "+matrix[r][c]);
		//System.out.println("word.charAt(index) = "+word.charAt(index));
		if(matrix[r][c] == (word.charAt(index))){
			//System.out.println("Char match");
			String char_index = matrix[r][c]+"-[" + r + "," + c +"]";
			list.add(char_index);
			
			if(list.size()==word.length()){
				for(int i=0;i<word.length();i++)
					System.out.print(list.get(i));
				System.out.println();
			}
			
			else{
				findWord(matrix, rows, cols, r-1, c-1, word, index+1);
				findWord(matrix, rows, cols, r, c-1, word, index+1);
				findWord(matrix, rows, cols, r+1, c-1, word, index+1);
				findWord(matrix, rows, cols, r+1, c, word, index+1);
				findWord(matrix, rows, cols, r+1, c+1, word, index+1);
				findWord(matrix, rows, cols, r, c+1, word, index+1);
				findWord(matrix, rows, cols, r-1, c+1, word, index+1);
				findWord(matrix, rows, cols, r-1, c, word, index+1);
				
			}

			list.remove(index);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char [] [] matrix = {{'A','N', 'L', 'Y', 'S'},
				               {'I', 'S', 'D', 'E', 'S'},
				               {'I', 'G', 'N', 'D', 'E'}};
		
		int row = matrix.length;
		int col = matrix[0].length;
		
		String word = "DES";
		
		Crossword crossword = new Crossword();
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				crossword.findWord(matrix, row-1, col-1, i, j, word, 0);
			}
		}
	}
}
