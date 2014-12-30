package trees;

public class Node {

	int data;
	Node left;
	Node right;
	
	Node(){
		
	}
	
	Node(int data){
		this.data = data;
		this.left = this.right = null;
	}
}
