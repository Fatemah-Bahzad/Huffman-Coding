import java.io.Serializable;

/** The implementation of a binary tree.
 * @author Fatemah Bahzad
 */
public class BinaryTree implements Serializable {
	
	
			/** 
	 		 *Root of tree.
	 		 */
  	public TreeNode root;
	/** set the Root of tree.
 	 * @param node to set as the root
 	 */
	public void setRoot(TreeNode node){
		this.root = node;
	}

	/** get the height of the tree.
	 * @return the height of the tree
	 */
	public int height(){
		if (root==null) {
			return -1; //null tree
		}
		return height(root);

	}
	/** helper method to get the height of the tree by using recursion.
	 * @param node , the current node  
	 * @return the height of the tree
	 */
	private int height(TreeNode node) {
		int leftH=0;
		int rightH=0;
		if (node.left!=null) { //count the height of the left 
			leftH =1+height(node.left);
		}
		if (node.right!=null) {//count the height of the left 
			rightH =1+height(node.right);
		}
		if (rightH>leftH) { //return the longest
			return rightH;
		}
		return leftH;
	}
	
	/** count the number of leaves in the tree.
	 * @return the number of leaves in the tree
	 */
	public int numLeaves(){
		if (root==null) {
			return 0;
		}
		return numLeaves(root);  // call recursion helper method and return its value
	}
	
	/** helper method to get the number of leaves in the tree by using recursion.
	 * @param node , the current node  
	 * @return the number of leaves of the tree
	 */
	private int numLeaves(TreeNode node) {
		if (node.left==null) { //check if the node is one of leaves nodes 
			return 1; //count
		}
		if (node.right==null) {
			return 1;
		}
		return numLeaves(node.right)+numLeaves(node.left);
		
	}
	
	/** create a string with the order of the tree in preorder (parents,left,right).
	 * @return String of nodes in preorder 
	 */
	public String toStringPreOrder(){
		return preOrder(root); // call recursion helper method 
	}
	/** helper method to create a string with the order of.
	 * the tree in preorder (parents,left,right)
	 * @param node ,current node
	 * @return String of nodes in preorder 
	 */
	private String preOrder(TreeNode node){
		StringBuilder ans = new StringBuilder();
		if (node==null) { //if it is empty
			return"";
		}
		//add nodes in order 
		ans.append(node); //parent
		ans.append(preOrder(node.left)); //left
		ans.append(preOrder(node.right)); // right 

		return ans.toString();
	}
	
	/** create a string with the order of the tree in Inorder (left,parent,right).
	 * @return String of nodes in Inorder 
	 */
	public String toStringInOrder(){
		return inOrder(root);// call recursion helper method 
	}
	
	/** helper method to create a string with the order of.
	 * the tree in Inorder (left,parent,right)
	 * @param node ,current node
	 * @return String of nodes in Inorder 
	 */
	private String inOrder(TreeNode node){
		StringBuilder ans = new StringBuilder();
		if (node==null) { //if it is empty
			return "";
		}
		//add nodes in order 
		ans.append(inOrder(node.left)); //left
		ans.append(node);//parent
		ans.append(inOrder(node.right)); //right
		return ans.toString();
	}

	
	/** create a string with the order of the tree in Levelorder.
	 * (lever by level starting from the root)
	 * @return String of nodes in Levelorder 
	 */
	public String toStringLevelOrder(){
		StringBuilder ans = new StringBuilder();
		int hight= height();
		for(int i=hight;i>=0;i--) { //append nodes by level 
			ans.append(levelorder(root,i,hight));
		}
		return ans.toString();
	
	}
	/** helper method to create a string with the order of.
	 * the tree in LevelOrder (lever by level starting from the root)
	 * @param node ,current node
	 * @param i ,loop
	 * @param height of the tree
	 * @return String of nodes in LevelOrder 
	 */
	private String levelorder(TreeNode node,int i,int height){ //nodes at each level 
		StringBuilder ans = new StringBuilder();
		if (node==null) { //if it is empty 
			return"";
		}
		//append the nodes at each level starting from the left to right
		else if (i==height) { 
			ans.append(node); 
		}
		else if(node!=null) {
			ans.append(levelorder(node.left,i+1,height));
			ans.append(levelorder(node.right,i+1,height));
		}
		
		return ans.toString();
	}
	
	

	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String[] args){
	
		BinaryTree tree = new BinaryTree();
		
		//a single-node tree
		tree.setRoot(new TreeNode(1, 'r'));
		if (tree.height() == 0 && tree.numLeaves() == 1 
				&& tree.toStringPreOrder().equals("<r,1>")){
			System.out.println("Yay1");
		}

		//set up a tree
		//        r,1
		//       /   \
		//     a,2    e,10
		//   /     \
		// b,3     c,4
		//           \
		//           d,5
		// Note: this tree is a general binary tree but not a Huffman tree.
		TreeNode node1 = new TreeNode(2, 'a');
		TreeNode node2 = new TreeNode(3, 'b');		
		TreeNode node3 = new TreeNode(4, 'c');
		TreeNode node4 = new TreeNode(5, 'd');
		TreeNode node5 = new TreeNode(10, 'e');
		tree.root.setLeft(node1);
		tree.root.setRight(node5);
		node1.setLeft(node2);
		node1.setRight(node3);
		node3.setRight(node4);
		
		//tree basic features
		if (tree.root.left.right.count == 4 && tree.height() == 3 && tree.numLeaves() == 3){
			System.out.println("Yay2");
		}
		
		//tree traverals
		if (tree.toStringPreOrder().equals("<r,1><a,2><b,3><c,4><d,5><e,10>")){
			System.out.println("Yay3");
		}

		if (tree.toStringInOrder().equals("<b,3><a,2><c,4><d,5><r,1><e,10>")){
			System.out.println("Yay4");
		}
		
		if (tree.toStringLevelOrder().equals("<r,1><a,2><e,10><b,3><c,4><d,5>")){
			System.out.println("Yay5");
		}
	}	
}
