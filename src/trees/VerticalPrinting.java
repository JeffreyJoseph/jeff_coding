package trees;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class VerticalPrinting {

	public static void verticalPrint(Node root){
		Queue<Map.Entry<Node, Integer>> queue = new LinkedList<Map.Entry<Node, Integer>>();
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>();
		
		if(root==null)
			return;
		
		Entry<Node, Integer> pair = new AbstractMap.SimpleEntry(root, 0);
		queue.add(pair);
		Entry<Node, Integer> temp_pair = null;
		Node temp_node = null;
		Integer temp_width = null;
		ArrayList<Integer> temp_list = null;
		
		while(!queue.isEmpty()){
			temp_pair = queue.remove();
			temp_node = temp_pair.getKey();
			temp_width = temp_pair.getValue();
			
			if(map.containsKey(temp_width)){
				temp_list = map.get(temp_width);
				temp_list.add(temp_node.data);
				map.put(temp_width, temp_list);
			}
			else{
				temp_list = new ArrayList<Integer>();
				temp_list.add(temp_node.data);
				map.put(temp_width, temp_list);
			}
			
			if(temp_node.left!=null)
				queue.add(new AbstractMap.SimpleEntry(temp_node.left, temp_width-1));
			if(temp_node.right!=null)
				queue.add(new AbstractMap.SimpleEntry(temp_node.right, temp_width+1));
		}
		
		Set<Map.Entry<Integer, ArrayList<Integer>>> set = new TreeSet<Map.Entry<Integer, ArrayList<Integer>>>(new Comparator<Map.Entry<Integer, ArrayList<Integer>>>() {

			@Override
			public int compare(Entry<Integer, ArrayList<Integer>> o1,
					Entry<Integer, ArrayList<Integer>> o2) {
				// TODO Auto-generated method stub
				return o1.getKey() - o2.getKey();
			}
			
		});
		set.addAll(map.entrySet());
		
		Iterator<Map.Entry<Integer, ArrayList<Integer>>> itr = set.iterator();
		while(itr.hasNext()){
			Map.Entry<Integer, ArrayList<Integer>> temp_map = itr.next();
			temp_list = temp_map.getValue();
			for(int i=0;i<temp_list.size();i++)
				System.out.print(temp_list.get(i)+"\t");
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		/* Tree structure
		 * 			1
		 * 		2		3
		 * 	4		5 6		7
		 * 		8		9
		 * 			10		11
		 */
		
		Node root = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		Node eleven = new Node(11);
		
		root.left = two;
		root.right = three;
		
		two.left = four;
		two.right = five;
		three.left = six;
		three.right = seven;
		
		five.left = eight;
		five.right = nine;
		nine.left = ten;
		nine.right = eleven;
		
		verticalPrint(root);
	}
}
