//-------------------------------------------------------------
// DO NOT EDIT ANYTHING FOR THIS CLASS EXCEPT TO ADD JAVADOCS
//-------------------------------------------------------------
import java.io.Serializable;
/**
 * The implementation of a binary tree node with links to its children.
 * @author a given file
 *
 */
//Tree node used in a binary huffman tree

public class TreeNode implements Serializable, Comparable<TreeNode> {

	
	/**
	 * count for the character (leaf node) or total of counts from both children (internal node).
	 */
	public int count;
	/**
	 * character represented by this node internal node: keep character to be null.
	 */
	public Character character = null;
	/**
	 * children links.
	 */
	public TreeNode left, right;
	/**
	 * set the count of the tree node.
	 * @param count to set
	 */
	public TreeNode(int count){
		this.count = count;
	}
	/**
	 * set the count and character of the tree node.
	 * @param count to set
	 * @param character to set
	 */
	public TreeNode(int count, Character character){
		this.count = count;
		this.character = character;
	}
	/**
	 * set the left child.
	 * @param left child to set
	 */
	public void setLeft(TreeNode left){ this.left = left;}
	/**
	 * set the right child.
	 * @param right child to set
	 */
	public void setRight(TreeNode right){ this.right = right;}
	/**
	 * compare two tree nodes.
	 * @param otherNode to compare
	 * @return number of compareTo
	 */
	public int compareTo(TreeNode otherNode){
		if (this.count - otherNode.count!=0){
			return (this.count - otherNode.count); //compare count
		}
		else{
			if (this.character!=null && otherNode.character!=null) {//use char to break the tie
				return (this.character - otherNode.character); 
				//same character + same count would be a tie
			}
			else{
				return (this.count - otherNode.count); 
				//null + same count would be a tie				
			}
		}		
	}
	/**
	 * compare to object to check if they are equal. 
	 * @param o , object to compare 
	 * @return true if they are equal, false if not 
	 */
	@Override
	public boolean equals(Object o){
		if (!(o instanceof TreeNode)){
			return false;
		}
		TreeNode otherNode = (TreeNode) o;
		return (this.compareTo(otherNode) == 0);
	}
	/**
	 * create a string with the characters and the count.
	 * @return a string with the characters and the count
	 */
	public String toString(){ 
		return "<"+this.character+","+this.count+">";			
	}
}