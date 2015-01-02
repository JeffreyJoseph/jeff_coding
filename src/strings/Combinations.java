package strings;

public class Combinations {

	String output = null;
	StringBuilder sb = new StringBuilder();
	
	public void permutations(char[] c, int index, int len){
		if(index==len){
			output = new String(c);
			System.out.println(output);
			return;
		}
		for(int i=index;i<len;i++){
			if((i+1)<len && c[i+1]==c[i]) continue;
			swap(c, index, i);
			permutations(c, index+1, len);
			swap(c, index, i);
		}
	}
	
	public void combinations(String str, int index){
		for(int i=index;i<str.length();i++){
			sb.append(str.charAt(i));
			System.out.println(sb);
			if(i<str.length()-1)
				combinations(str, i+1);
			sb.setLength(sb.length()-1);
		}
	}
	
	public void swap(char[] c, int x, int y){
		//System.out.println("X = "+ x + " Y = "+y);
		char temp = c[x];
		c[x] = c[y];
		c[y] = temp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "jeff";
		char[] c = s.toCharArray();
		
		Combinations combinations = new Combinations();
		//combinations.permutations(c, 0, s.length());
		combinations.combinations(s, 0);
	}

}
