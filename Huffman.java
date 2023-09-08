import java.io.*;
/**
 * The driver program of Huffman’s algorithm.
 * @author Fatemah Bahzad
 *
 */
class Huffman implements Serializable{
	
	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	/**
	 * default length used to create hashtables.
	 */
	public static final int DEFAULT_TABLE_LENGTH = 11;
	/**
	 * original input string to encode.
	 */
	private transient String inputContents = null;
	/**
	 * hashtable used to count the frequencies of input characters.
	 */
	private transient HashTable<Character,Integer> counts = 
		new HashTable<Character,Integer>(DEFAULT_TABLE_LENGTH);
	/**
	 * priority queue used to build huffman tree.
	 */
	private transient PriorityQueue<TreeNode> queue = new PriorityQueue<>();
	
	/**
	 * huffman tree.
	 */
	private BinaryTree huffmanTree = new BinaryTree();
	
	/**
	 * hashtable used to record the encoding for input characters.
	 */
	private HashTable<Character,String> encodings = new HashTable<>(DEFAULT_TABLE_LENGTH);
	
	/**
	 * set counts to the to hash table. 
	 * @param counts use to set this.count 
	 */
	//setters and getters to help testing 	
	public void setCounts(HashTable<Character, Integer> counts){
		this.counts = counts;
	}
	/**
	 * get Counts.
	 * @return counts which is a hash table 
	 */
	public HashTable<Character,Integer> getCounts(){
		return counts;
	}
	/**
	 * set Queue.
	 * @param queue use to set this.queue
	 */
	public void setQueue(PriorityQueue<TreeNode> queue){
		this.queue = queue;
	}
	
	/**
	 * get Queue.
	 * @return queue of tree nodes
	 */
	public PriorityQueue<TreeNode> getQueue(){
		return queue;
	}

	/**
	 * set Tree.
	 * @param huffmanTree to set this.huffmanTree
	 */
	public void setTree(BinaryTree huffmanTree){
		this.huffmanTree = huffmanTree;
	}
	/**
	 *  get Tree.
	 * @return the binary tree huffmanTree
	 */
	public BinaryTree getTree(){
		return huffmanTree;
	}
	
	/**
	 * get Encodings.
	 * @return HashTable of encodings
	 */
	public HashTable<Character,String> getEncodings(){
		return encodings;
	}
	/**
	 * generate the encoding result from the huffman tree.
	 */
	public void computeEncodings(){
		computeEncodings(huffmanTree.root,"");
	}
	/**
	 * recursive helper method for encoding.
	 * @param currentLoc in the tree
	 * @param encoding is a string to encode
	 */
	private void computeEncodings(TreeNode currentLoc, String encoding){
		if(currentLoc.character != null){
			this.encodings.put(currentLoc.character, encoding);
		}
		else{
			computeEncodings(currentLoc.left, encoding+"0");
			computeEncodings(currentLoc.right, encoding+"1");
		}
	}
	/**
	 * generate a string of 0's and 1's as the encoding of the input.
	 * @param input to encode
	 * @return String of 0's and 1's
	 */

	public String encode(String input){

		StringBuffer output = new StringBuffer();
		
		for (char ch : input.toCharArray()){
			output.append(this.encodings.get(ch));
		}
		
		return output.toString();	
	
	}
	/**
	 * After encodings are computed, encode inputContents.
	 * @return String of 0's and 1's
	 */
	public String encode(){

		StringBuffer output = new StringBuffer();
		
		for (char ch : inputContents.toCharArray()){
			output.append(this.encodings.get(ch));
		}
		
		return output.toString();	
	
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	/**
	 * set inputContents as the input.
	 * @param input to set inputContents as
	 */
	public Huffman(String input){
		inputContents=input;	
	}
	/**
	 * count the number of times a char is is the input string. 
	 */
	public void createCounts(){
		if (inputContents.length()==0) { //if it is empty
			return;
		}
		counts = new HashTable<Character,Integer>(DEFAULT_TABLE_LENGTH); 
		// new empty hash table 
		for (char ch : inputContents.toCharArray()){
			if (counts.get(ch)==null) {//if the char is not in the table
				counts.put(ch, 1);
			}
			else {
				int j=counts.get(ch);// get the count of the char and add one to it 
				counts.put(ch,j+1);//add one to the count of that char
			}
		}
		
	}
	/**
	 * create a node from the information in inputContents, then add it to a queue.
	 */
	public void initQueue(){
		queue = new PriorityQueue<>(); //start with an empty priority queue.
		for (char ch : inputContents.toCharArray()){
			int value=counts.get(ch); //get the count of each char from the hashtable
			TreeNode node1 = new TreeNode(value, ch);
			if (queue.contains(node1)) { //avoid duplicates
				continue;
			}
			else {
				queue.add(node1); //add to queue if it is not in it 
			}
		}
		
	}
	/**
	 * merge nodes together into a single Huffman encoding tree.
	 */
	public void buildTree(){
		int size=queue.size();
		for (int i=0;i<size;i++) {
			if (queue.size()==1) {
				break;
			}
			TreeNode node1=queue.remove();
			TreeNode node2=queue.remove(); //get the two nodes 
			int value=node1.count+node2.count; //new node count 
			//parent node
			TreeNode node3=new TreeNode(value, null);
			node3.setLeft(node1); //set the children 
			node3.setRight(node2);
			queue.add(node3); //add the parent to the queue
		}
		huffmanTree.setRoot(queue.remove()); //set the last thing left in queue as the root of the tree	

	}
	/**
	 * decode the input string of 1s and 0s. 
	 * @param input string of 1s and 0s. 
	 * @return String of character 
	 */
	public String decode(String input){
		StringBuffer ans1 = new StringBuffer(); //answer
		TreeNode node1=huffmanTree.root; //start from the root
		if (node1.left==null && node1.right==null ) {
			return "";
		}
		for (int j=0;j<input.length();j++) {
			if (input.charAt(j)=='1') { //right
				node1=node1.right;
				if (node1.left==null && node1.right==null ) { //if it is a leaf
					ans1.append(node1.character);
					node1=huffmanTree.root; //go back to the root
					continue;
				}
			}
			else if (input.charAt(j)=='0') { //left
				node1=node1.left;
				if (node1.left==null && node1.right==null ) { //if it is a leaf
					ans1.append(node1.character);
					node1=huffmanTree.root; //go back to the root
					continue;
				}
			}
		}
		return ans1.toString(); //default return: change or remove as needed

	}

	//-------------------------------------------------------------
	// PROVIDED TESTING CODE: FEEL FREE TO EDIT
	//-------------------------------------------------------------
	/**
	 * testing code.
	 */
	public static void testMain(){
	
		Huffman huff = new Huffman("cabbeadcdcdcdbbd");
		
		//step 1: count frequency 
		huff.createCounts();
		HashTable<Character,Integer> counts = huff.getCounts();
		//System.out.println(counts);
		//System.out.println(counts.toStringDebug());

		if (counts.size() == 5 && counts.get('a') == 2 && counts.get('e')==1
			&& counts.toString().equals("c:4\nd:5\ne:1\na:2\nb:4")){
			System.out.println("Yay 1");
		}
		
		//step 2: initialize priority queue with leaf nodes
		huff.initQueue();
		PriorityQueue<TreeNode> queue = huff.getQueue();
		//System.out.println(queue);
		if (queue.size() == 5 && queue.element().character=='e' && queue.element().count==1){
			System.out.println("Yay 2");		
		}
		
		if (queue.toString().equals("<e,1> <a,2> <b,4> <c,4> <d,5>")){
			System.out.println("Yay 3");				
		}
		
		//step 3: build huffman tree with the help of priority queue
		huff.buildTree();
		BinaryTree tree= huff.getTree();
		if (tree.root.count == 16 && tree.root.left.count == 7 & tree.root.right.count == 9){
			System.out.println("Yay 4");					
		}
		
		//System.out.println(tree.toStringPreOrder());
		if (tree.toStringPreOrder().equals("<null,16><null,7><null,3><e,1><a,2><b,4><null,9><c,4><d,5>")){
			System.out.println("Yay 5");					
		}
		
		//step 4: encoding and decoding
		//System.out.println(huff.decode("1000101"));
		huff.computeEncodings();
		//System.out.println(huff.getEncodings());
		if (huff.decode("1000101").equals("cab") && huff.encode("cab").equals("1000101")){
			System.out.println("Yay 6");							
		}
		
	}	
	//-------------------------------------------------------------
	// END OF TESTING CODE
	//-------------------------------------------------------------


	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	//--------------------------------------------------------------------------------
	// How to run:
	// - To run testMain: java Huffman
	// - To encode:  java Huffman -e fileToEncode encodedOutputFile HuffmanObjectOutputFile
	// - To decode:  java Huffman -d fileToDecode decodedOutputFile HuffmanObjectInputFile
	//--------------------------------------------------------------------------------
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String[] args)
	{
		// no command-line args: provided testing of Huffman's algorithm
		if (args.length==0){
			testMain();
			return;
		}
		
		// with command-line args: file I/O for encoding/decoding
		if(args[0].equals("-e") && (args.length < 4 || args.length > 4)){
			System.out.println("Usage: java Huffman -e fileToEncode encodedOutputFile HuffmanObjectOutputFile");
			return;
		}
		else if(args[0].equals("-d") && (args.length < 4 || args.length > 4)){
			System.out.println("Usage: java Huffman -d fileToDecode decodedOutputFile HuffmanObjectInputFile");
			return;
		}
		else if(!args[0].equals("-d") && !args[0].equals("-e")){
			System.out.println("Usage: java Huffman -[e|d]");
			return;
		}
		
		String fileAsString;
		Huffman huff;
		try{
			switch(args[0]){
				case "-e": //encoding
				
					//read in fileToEncode
					fileAsString = getFileContents(args[1]);
					//System.out.println(fileAsString);
					
					//Huffman's algorithm 
					huff = new Huffman(fileAsString);
					huff.createCounts(); //step 1
					//System.out.println(hTree.counts);
					huff.initQueue(); //step 2
					//System.out.println(hTree.queue);
					huff.buildTree(); //step 3
					
					huff.computeEncodings();
					
					//encoding
					String encoding = huff.encode();
					//System.out.println("Encoded: " + encoding);
					
					//output encoded contents as a sequence of bits into file
					writeEncodedMessage(encoding, args[2]);
					
					//output Huffman object into file
					writeEncodedObject(huff, args[3]);
					break;
					
				case "-d": //decoding
				
					//read in from file and construct Huffman object
					huff = getEncodedObject(args[3]);
					
					//read in from file the encoded bits and
					//convert into a string (with only characters '0' and '1')
					fileAsString = getFileBinaryContents(args[1]);
					//System.out.println(fileAsString);
					
					//decoding
					String decodedMessage = huff.decode(fileAsString);
					
					//output decoded contents into file
					writeDecodedMessage(decodedMessage, args[2]);
					break;
			}
		}
		catch(IOException e){
			System.out.println("Problem reading or writing to specified file");
			System.out.println(e.toString());
		}
		
	}
	/**
	 * output a Huffman Object to a file.
	 * @param huff Huffman
	 * @param filename String
	 * @throws IOException Exception 
	 */
	public static void writeEncodedObject(Huffman huff, String filename) throws IOException{
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))){
			output.writeObject(huff);
		}
	}
	/**
	 * read from a file and create a Huffman Object based on the file contents.
	 * @param filename String
	 * @return Huffman Object based on the file contents
	 * @throws IOException Exception
	 */
	public static Huffman getEncodedObject(String filename) throws IOException{
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))){
			return (Huffman)input.readObject();
		}
		catch(ClassNotFoundException e){
			throw new IOException("Can not read class from provided file.");
		}
	}
	/**
	 * read the encoding result (as a string of 0's and 1's) from a file.
	 * @param filename String
	 * @return string of 0's and 1's
	 * @throws IOException Exception
	 */
	public static String getFileBinaryContents(String filename) throws IOException{
		StringBuffer fileContents = new StringBuffer();
		try(BitInputStream bs = new BitInputStream(new FileInputStream(filename), true)){
			while(bs.hasNextBit()){
				fileContents.append(bs.readBit());
			}
		}
		return fileContents.toString();
	}
	/**
	 * utput the encoding result (a string of 0's and 1's) as a bit sequence into a file.
	 * @param message String
	 * @param filename String
	 * @throws IOException Exception
	 */
	public static void writeEncodedMessage(String message, String filename) throws IOException{
		try(BitOutputStream bs = new BitOutputStream(new FileOutputStream(filename), true)){
			bs.writeBits(message);
		}
	}

	/**
	 * read from file and return file contents as a string.
	 * @param filename String
	 * @return file contents as a string
	 * @throws IOException Exception
	 */
	public static String getFileContents(String filename) throws IOException{
		StringBuffer fileContents = new StringBuffer();
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			String input = br.readLine();
			fileContents.append(input);
			input = br.readLine();
			
			while(input != null){
				fileContents.append("\n" + input);
				input = br.readLine();
			}
		}
		
		return fileContents.toString();
	}
	/**
	 * out put message as a sequence of bits to file.
	 * @param message String
	 * @param filename String
	 * @throws IOException Exception
	 */
	public static void writeDecodedMessage(String message, String filename) throws IOException{
		try(BufferedWriter br = new BufferedWriter(new FileWriter(filename))){
			br.write(message);
		}
	}
	
	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	

}