package strings;

public class StringArithmetic {

	public void add(String s1, String s2){
		if(s1==null && s2==null){
			System.out.println("String inputs are null");
			return;
		}
		
		if(s1==null || s1==""){
			System.out.println("Sum = "+s2);
			return;
		}
		
		if(s2==null || s2==""){
			System.out.println("Sum = "+s1);
			return;
		}
		
		StringBuilder result = new StringBuilder();
		
		int l1 = s1.length()-1;
		int l2 = s2.length()-1;
		//System.out.println("l1 = "+l1+"\t"+"l2 = "+l2);;
		
		int min_len = Math.min(l1, l2);
		//System.out.println("Min len = "+min_len);
		
		int curr = 0, carry = 0;
		
		for(int i=0; i<=min_len; i++){
			int no1 = s1.charAt(l1)-48;
			int no2 = s2.charAt(l2)-48;
			curr = no1 + no2 + carry;
			if(curr>9){
				carry = curr/10;
				curr %= 10;
			}
			else
				carry = 0;
			result.insert(0, curr);
			l1--;
			l2--;
		}
		
		if(l1==-1 && l2==-1){
			if(carry>0)
				result.insert(0, carry);
			System.out.println("Sum = "+result);
		}
		else if(l1>=0){
			while(l1>=0){
				int no1 = s1.charAt(l1)-48;
				curr = no1 + carry;
				if(curr>9){
					carry = curr/10;
					curr %= 10;
					result.insert(0, curr);
					l1--;
				}
				else{
					result.insert(0, curr);
					l1--;
					break;
				}
			}
			if(l1>=0){
				result.insert(0, s1.substring(0, l1+1));
			}
			System.out.println("Sum = "+result);
		}
		else if(l2>=0){
			while(l2>=0){
				int no2 = s2.charAt(l2)-48;
				curr = no2 + carry;
				if(curr>9){
					carry = curr/10;
					curr %= 10;
					result.insert(0, curr);
					l2--;
				}
				else{
					result.insert(0, curr);
					l2--;
					break;
				}
			}
			if(l2>=0){
				result.insert(0, s2.substring(0, l2+1));
			}
			System.out.println("Sum = "+result);
		}
	}
	
	public void subtract(String s1, String s2){
		if(s1==null && s2==null){
			System.out.println("String inputs are null");
			return;
		}
		
		if(s1==null || s1==""){
			System.out.println("Difference = "+"-"+s2);
			return;
		}
		
		if(s2==null || s2==""){
			System.out.println("Difference = "+s1);
			return;
		}
		
		StringBuilder result = new StringBuilder();
		
		int l1 = s1.length()-1;
		int l2 = s2.length()-1;
		int s1s2_bigger = 0;
		if(l1==l2){
			if(s1.charAt(0)>s2.charAt(0))
				s1s2_bigger = 1;
			else
				s1s2_bigger = 2;
		}
		else if(l1>l2)
			s1s2_bigger = 1;
		else
			s1s2_bigger = 2;
		
		if(s1s2_bigger == 2){
			String temp = s1;
			s1 = s2;
			s2 = temp;
		}
		
		int min_len = Math.min(l1, l2);
		StringBuilder sb = new StringBuilder(s1);
		
		for(int i=0; i<=min_len; i++){
			int no1 = sb.charAt(l1)-48;
			int no2 = s2.charAt(l2)-48;
			
			if(no1<no2){
				borrow(sb,l1-1);
				System.out.println(sb);
				no1 += 10;
			}
			
			result.insert(0, no1 - no2);
			l1--;
			l2--;
		}
		
		if(l1>=0)
			result.insert(0, sb.substring(0, l1+1));
		
		if(s1s2_bigger == 2)
			result.insert(0, '-');
		
		System.out.println("Difference = "+result);
	}
	
	public void borrow(StringBuilder sb, int index){
		if(sb.charAt(index)>48){
			int temp = sb.charAt(index)-48;
			sb.deleteCharAt(index);
			sb.insert(index, temp-1);
		}
		else{
			borrow(sb, index-1);
			sb.deleteCharAt(index);
			sb.insert(index, 9);
		}		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = "163";
		String s2 = "363";
		String s3 = "999";
		String s4 = "16389";
		String s5 = "100000";
		
		StringArithmetic arithmetic = new StringArithmetic();
		/*arithmetic.add(s1, s2);
		arithmetic.add(s1, s3);
		arithmetic.add(s1, s4);
		arithmetic.add(s1, null);
		arithmetic.add(s1, "");*/
		
		arithmetic.subtract(s1, s2);
		arithmetic.subtract(s3, s1);
		arithmetic.subtract(s4, s1);
		arithmetic.subtract(s5, s1);
		arithmetic.subtract(s1, null);
		arithmetic.subtract(s1, "");
		
		/*StringBuilder sb = new StringBuilder("Jeffrey");
		System.out.println(sb.deleteCharAt(1));
		System.out.println(sb.insert(1, 'e'));*/
	}

}
