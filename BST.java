import java.util.ArrayList;

/* A Binary search tree that manipulates a (x, y) relation. It is possible to insert and delete
 * pairs from the tree. Pairs can be deleted or extracted providing only one of the elements.
 * The tree can be emptied. The essential time complexities are O(log n) assuming that the tree
 * is balanced. No methods for actually balancing it are implemented. This could possibly be done
 * by using an AVL or a Red-black tree. */
public class BST <X extends Comparable<X>, Y extends Comparable<Y>> implements Relation <X, Y> {

	
	private Node<X, Y> root;

	/* BST constructor. */
	public BST() {
		root = null;
	}
	
	/* Searches for a specific pair in the tree. Returns true 
	 * if the pair is available and false otherwise. It 
	 * prioritizes the comparison based on the first element 
	 * of the pair. Then it considers the second element. */
	public boolean contains(X elem1, Y elem2) {
		/* position1 states if the first element is in the
		 * tree while position2 does the same for the
		 * second element. */
		int position1 = 0, position2 = 0;
		BST.Node<X, Y> curr = root;
		
		for (;;) {
			if (curr == null)
				return false;
			
			position1 = elem1.compareTo(curr.elemX);
			position2 = elem2.compareTo(curr.elemY);
			
			if (position1 == 0 && position2 == 0) {
				return true;
			} else if (position1 < 0) {
				curr = curr.left; 
			} else if (position1 > 0) {
				curr = curr.right;
			} else if (position1 == 0 && position2 < 0) {
				curr = curr.left;
			} else if (position1 == 0 && position2 > 0) {
				curr = curr.right;
			}
		}
	}
	
	/* Returns an ArrayList of all Y values corresponding
	 * to a X value. */
	public ArrayList<Y> determineViaX(X elem1) {
		/* Did not work with (Y[]) new Object[numNodes]; */
		ArrayList<Y> list = new ArrayList<Y>();
		determineViaX(root, elem1, list);
		
		return list;
	}
	
	/* Compares elem1 to elemX in the top node. If elem1 is smaller or larger,
	 * the method is used recursively. Otherwise, if elem1 matches elemX,
	 * elemY is added to list. The method is then again used recursively on the
	 * current node's children. */
	private void determineViaX(BST.Node<X, Y> top, X elem1, ArrayList<Y> list) {
		if (top != null) {
			int position1 = elem1.compareTo(top.elemX);
			if (position1 < 0)
				determineViaX(top.left, elem1, list);
			else if (position1 > 0)
				determineViaX(top.right, elem1, list);
			else {
				list.add(top.elemY);
				
				if ((top.left != null) && (elem1.compareTo(top.left.elemX) == 0))
					determineViaX(top.left, elem1, list);
				
				if ((top.right != null) && (elem1.compareTo(top.right.elemX) == 0))	
					determineViaX(top.right, elem1, list);
			}
		}
	}
	
	/* Returns an ArrayList of all X values corresponding
	 * to a Y value. */
	public ArrayList<X> determineViaY(Y elem2) {
		ArrayList<X> list = new ArrayList<X>();
		determineViaY(root, elem2, list);
		
		return list;
	}
	
	/* Compares elem2 to elemY. If it matches, elemX is added to list. Otherwise,
	 * the method is used recursively on the matching node's children. */
	private void determineViaY(BST.Node<X, Y> top, Y elem2, ArrayList<X> list) {
		if (top != null) {
			int position2 = elem2.compareTo(top.elemY);

			if (position2 == 0) {
				list.add(top.elemX);
			}	
			
			determineViaY(top.left, elem2, list);	
			determineViaY(top.right, elem2, list);
		}
	}
	
	/* Empties the BST. */
	public void empty() {
		this.root = null;
	}
	
	/* Inserts a pair of elements in the BST. */
	public void insert(X elem1, Y elem2) {
		int position1 = 0, position2 = 0;
		BST.Node<X, Y> parent = null, curr = root;
		
		for (;;) {
			if (curr == null) {
				/* Sets the parent to the new element added. */
				BST.Node<X, Y> ins = new BST.Node<X, Y>(elem1, elem2);
				if (root == null)
					root = ins;
				else if (position1 < 0)
					parent.left = ins;
				else if (position1 > 0)
					parent.right = ins;
				else if (position1 == 0 && position2 < 0)
					parent.left = ins;
				else if (position1 == 0 && position2 > 0)
					parent.right = ins;
				
				return;
			}
			
			position1 = elem1.compareTo(curr.elemX);
			position2 = elem2.compareTo(curr.elemY);
			
			/* The new element is already in the tree. */
			if (position1 == 0 && position2 == 0)
				return;
			/* Sets the parent to current and moves current
			 * down the tree. If the new element is smaller
			 * than the current element, curr goes to the 
			 * left and vice versa. */
			parent = curr;
			if (position1 < 0)
				curr = curr.left;
			else if (position1 > 0)
				curr = curr.right;
			else if (position1 == 0 && position2 < 0)
				curr = curr.left;
			else if (position1 == 0 && position2 > 0)
				curr = curr.right;
		}
	}
	
	/* Deletes a pair of elements from the BST. */
	public void delete(X elem1, Y elem2) {
		int position1 = 0, position2 = 0;
		BST.Node<X, Y> parent = null, curr = root;
		
		for (;;) {
			if (curr == null)
				return;
			
			position1 = elem1.compareTo(curr.elemX);
			position2 = elem2.compareTo(curr.elemY);
			
			/* The pair is found when both positions
			 * are equal to the ones in the BST. Then
			 * they are deleted. */
			if (position1 == 0 && position2 == 0) {
				BST.Node<X, Y> del = curr.deleteTopmost();
				if (curr == root)
					root = del;
				else if (curr == parent.left)
					parent.left = del;
				else if (curr == parent.right)
					parent.right = del;
				
				return;
			}
			
			/* Sets the parent to current. Moves current
			 * to the parent of the left or right tree
			 * correspondingly. */
			parent = curr;
			if (position1 < 0)
				curr = parent.left;
			else if (position1 > 0)
				curr = parent.right;
			else if (position1 == 0 && position2 < 0)
				curr = parent.left;
			else if (position1 == 0 && position2 > 0)
				curr = parent.right;
		}
	}
	
	/* Removes all (x, y) pairs from the BST containing
	 * element X. */
	public void deleteViaX(X elem1) {
		int position1 = 0;
		BST.Node<X, Y> parent = null, curr = root;
		
		for (;;) {
			if (curr == null)
				return;
			
			position1 = elem1.compareTo(curr.elemX);
			
			if (position1 == 0) {
				BST.Node<X, Y> del = curr.deleteTopmost();
				
				/* The current is set back to the deleted
				 * parent. */ 
				if (curr == root) {
					root = del;
					curr = root;
				} else if (curr == parent.left) {
					parent.left = del;
					curr = parent.left;
				} else if (curr == parent.right) {
					parent.right = del;
					curr = parent.right;
				}
			} else {
				parent = curr;
				if (position1 < 0)
					curr = parent.left;
				else
					curr = parent.right;
			}
		}
	}
	
	/* Removes all (x, y) pairs from the BST containing
	 * element Y. */
	public void deleteViaY(Y elem2) {
		deleteViaY(root, elem2);
	}
	
	/* Removes a pair given only the second element. Every time a
	 * pair is found the top node is deleted. The method is then used 
	 * recursively. */
	private void deleteViaY(BST.Node<X, Y> top, Y elem2) {
		if (top != null) {
			/* Deletes the top node of the tree if it
			 * contains Y. */
			if (top.elemY.compareTo(elem2) == 0) {
				BST.Node<X, Y> del = top.deleteTopmost();
				top = del;
				
				deleteViaY(top, elem2);
			}
			
			if (top.left != null) {
				/* Deletes the top node of the left subtree
				 * if it contains Y. */
				if (top.left.elemY.compareTo(elem2) == 0) {
					BST.Node<X, Y> del = top.left.deleteTopmost();
					top.left = del;
				}
				
				deleteViaY(top.left, elem2);					
			}
			
			if (top.right != null) {
				/* Deletes the top node of the right subtree
				 * if it contains Y. */
				if (top.right.elemY.compareTo(elem2) == 0) {
					BST.Node<X, Y> del = top.right.deleteTopmost();
					top.right = del;
				}
				
				deleteViaY(top.right, elem2);					
			}
		}
	}
	
	/* Prints the contents of the BST in order. */
	public void print() {
		printInOrder(root);
	}
	
	private void printInOrder(BST.Node<X, Y> top) {
		if (top != null) {
			printInOrder(top.left);
			System.out.println(top.elemX + " - " + top.elemY);
			printInOrder(top.right);
		}
	}
	
	///////////////////////////////////////// INNER CLASS /////////////////////////////////////////
	private static class Node<X extends Comparable<X>, Y extends Comparable<Y>> {
		
		protected X elemX;
		protected Y elemY;
		protected Node<X, Y> left, right;
		
		/* Node constructor. */
		protected Node(X elemX, Y elemY) {
			this.elemX = elemX;
			this.elemY = elemY;
			left = null;
			right = null;
		}
		
		/* Deletes the topmost element of a tree. */
		public Node<X, Y> deleteTopmost() {
			if (this.left == null)
				return this.right;
			else if (this.right == null)
				return this.left;
			else { /* The node has two children. */
				/* Replaces elemX of a node with elemX located in
				 * its right node's leftmost node. */
				this.elemX = this.right.getLeftmostNode().elemX;
				/* Replaces elemY of a node with elemY located in
				 * its right node's leftmost node. */
				this.elemY = this.right.getLeftmostNode().elemY;
				this.right = this.right.deleteLeftmostNode();
			}
			
			return this;
		}
		
		/* Returns the leftmost node of a tree. */
		private Node<X, Y> getLeftmostNode() {
			Node<X, Y> curr = this;
			while (curr.left != null)
				curr = curr.left;
			
			return curr;
		}
		
		/* Deletes the leftmost node of a tree. */
		public Node<X, Y> deleteLeftmostNode() {
			/* When there is no left child. */
			if (this.left == null)
				return this.right;
			else /* When there is left child. */
				left = left.deleteLeftmostNode();
				
			return this;
		}
		
	}
	////////////////////////////////////// END OF INNER CLASS //////////////////////////////////////
	
	
}
