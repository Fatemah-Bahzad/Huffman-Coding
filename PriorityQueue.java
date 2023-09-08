import java.util.NoSuchElementException;
import java.util.Iterator;
/**
 * The implementation of a priority queue as a sorted singly linked list.
 * @author Fatemah Bahzad
 * @param <T> hold any item to add to the PriorityQueue
 */

public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	/**
	 * head of the queue.
	 * @param head of the queue
	 */
	private Node<T> head = null;
	/**
	 * linked list node class.
	 * @author given
	 * @param <T> old any item in the node
	 */
	private static class Node<T> {
		/**
		 * value of the node.
		 * @param value of the node
		 */
		private T value;
		/**
		 * next value of the node.
		 * @param next value of the node
		 */
		private Node<T> next;
		/**
		 * set the value of the node.
		 * @param value to set
		 */
		public Node(T value) { this.value = value; }
	}
		
	/**
	* provided toString() method using the iterator.
	* @return String of values 
	*/
	public String toString(){
		StringBuilder builder = new StringBuilder("");
		for (T value : this){
			builder.append(value);
			builder.append(" ");
		}
		return builder.toString().trim();
	}
	/**
	* provided iterator.
	* @return iterator of value T
	*/
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> current = head;
			
			public T next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				T val = current.value;
				current = current.next;
				return val;
			}
			
			public boolean hasNext() {
				return (current != null);
			}
		};
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	
	/**
	* size of the queue.
	*/
	private int size;
	/**
	 * set an empty Queue.
	 */
	public PriorityQueue() {
		this.head=null;
		size=0;

	}
	/**
	 * size of the Queue.
	 * @return size of the Queue
	 */
	public int size(){
		return size; 
	}
	
	/**
	 * add value to the Priority Queue.
	 * @param value to add to the Priority Queue
	 */
	public void add(T value) {
		Node<T> temp = new Node<>(value);
		if (size==0) {//if it is empty 
			this.head=temp;
			size++;
			return;
		}
		
		Node<T> current = head;
		Node<T> previous = head;//the previous node to add
		//loop over the link list to see where the object fits 
		int l=0;
		while (current!=null) {
			if(value.compareTo(current.value)==0) {
				//if it is the first node and they have the same priority
				if (current.equals(this.head) && size==1) {
					this.head.next=temp; //if there is only one node
					size++;
					break;
				}
				else { //more then one add it after 
					temp.next=current.next;
					current.next=temp;
					size++;
					break;
				}
			}
			if (value.compareTo(current.value)<0) { //if it is less then the node in the list 
				if (current.equals(this.head)) { //if we need to insert before the head
					temp.next=this.head;
					this.head=temp;
					size++;
					break;
				}
				else {
					//insert it between current and previous
					temp.next=previous.next;
					previous.next=temp;
					size++;
					break;
				}
			}
			if (current.next==null) {//if it is the last one 
				current.next=temp;
				size++;
				break;
			}
			if (l==0) { //first loop dont move the previous node
				current=current.next;
				l++;
				continue;
			}
			previous = previous.next;//the previous node to add (it will be a step behind current)
			current=current.next;
			
		}

	}

	/**
	 * Remove and return the value with the minimal priority value.
	 * @return the removed value
	 */
	public T remove( ) {
		if (this.head==null) { //if it is empty
			throw new NoSuchElementException("Priority queue empty!");
		}
		Node<T> ans=head; //to return
		this.head=this.head.next;
		size--;
		return ans.value; 
	}
	/**
	 * return the value with the minimal priority value.
	 * @return value with the minimal priority value
	 */
	public T element( ) {
		if (this.head==null) { //if it is empty
			throw new NoSuchElementException( "Priority queue empty!");
		}
		return this.head.value; //return the value of the head

	}
	/**
	 * check if f value is present in queue.
	 * @param value to check 
	 * @return true if value is in the Priority Queue
	 */
	public boolean contains(T value){
		Node<T> current = this.head;
		while (current!=null) { //loop until we find the value
			if (current.value.equals(value)) {
				return true;
			}
			current=current.next;
		}
		
		return false; //the value it not in 
	
	}
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String[] args){
		PriorityQueue<Character> letters = new PriorityQueue<>();
				
		//add/size/element/contains
		String chars = "MASON";
		for (int i=0; i<5; i++){
			letters.add(chars.charAt(i));
		}
		
		
		
		if (letters.size() == 5 && letters.element() == 'A' 
			&& letters.contains('O') && !letters.contains('B')){
			System.out.println("Yay 1");
		}

		//remove
		if (letters.remove() == 'A' && letters.size() == 4 && letters.element() == 'M'){
			System.out.println("Yay 2");
		}
		
		//sequence of add/remove
		PriorityQueue<Integer> nums = new PriorityQueue<>();
		for (int i=0; i<10; i++){
			int val = (i*i) % 17;
			nums.add(val);
		}


		boolean ok = nums.toString().trim().equals("0 1 2 4 8 9 13 13 15 16");

		StringBuilder output = new StringBuilder();
		for (int i=0; i<10; i++){
			int val = nums.remove();
			output.append(val);
			output.append(" ");
		}
		if (ok && output.toString().trim().equals("0 1 2 4 8 9 13 13 15 16")){
			System.out.println("Yay 3");		
		}
		
		//values added with the same priority are kept in FIFO order
		PriorityQueue<String> msgs = new PriorityQueue<>();
		String msg1 = new String("Hello");
		String msg2 = new String("Hello");
		msgs.add(msg1);
		msgs.add(chars);
		msgs.add(msg2);
		if (msgs.toString().trim().equals("Hello Hello MASON") && 
			msgs.contains(msg1) && msgs.contains(msg2) &&
			msgs.element()==msg1 && msgs.remove() != msg2){  //use of "==" is intentional here
			System.out.println("Yay 4");	
		}
	
		
	}
	
}