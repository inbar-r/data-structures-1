package avl;
// Name: Inbar Reshilovsky, ID: 318879863, Username: reshilovsky
// Name: Tomer Yihya, ID:203596192 , Username: tomeryihya


/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access
 * modifiers, return type, function name and arguments. Changing these would
 * break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and
 * can add functions of your own according to your needs.
 */

public class AVLTree {

	private AVLNode root;
	AVLNode min;
	AVLNode max;

	/**
	 * This constructor creates an empty AVLTree
	 * 
	 * Complexity: O(1)
	 */
	public AVLTree() {
		this.min = new AVLNode(true);
		this.max = new AVLNode(true);
		this.root = new AVLNode(true);
	}

	/**
	 * public boolean empty()
	 * <p>
	 * returns true if and only if the tree is empty
	 * 
	 * Complexity: O(1)
	 */
	public boolean empty() {
		return this.root.isVirtualNode;
	}

	/**
	 * public boolean search(int k)
	 * <p>
	 * returns the info of an item with key k if it exists in the tree otherwise,
	 * returns null
	 * 
	 * Complexity: O(logn)
	 */
	public Boolean search(int k) {
		if (this.empty()) {
			return null;
		}
		AVLNode currentNode = this.root;
		while (currentNode.isRealNode()) {
			if (currentNode.getKey() == k) {
				return currentNode.info;
			}
			if (currentNode.getKey() > k) {
				currentNode = currentNode.left;
			} else {
				currentNode = currentNode.right;
			}
		}
		return null;

	}

	/**
	 * public int insert(int k, boolean i)
	 * <p>
	 * inserts an item with key k and info i to the AVL tree. the tree must remain
	 * valid (keep its invariants). returns the number of nodes which require
	 * rebalancing operations (i.e. promotions or rotations). This always includes
	 * the newly-created node. returns -1 if an item with key k already exists in
	 * the tree.
	 * 
	 * Complexity: O(logn) (Using search (O(logn)) and balanceAfterInsertion(O(logn)
	 */
/*	
	public int insert(int k, boolean i) {
		int counter = 0;

		if (this.search(k) != null) {
			return -1; // key k already in tree
		} else {
			AVLNode insertionNode = new AVLNode(k, i);

			// tree is empty, insert as root
			if (this.empty()) {
				this.root = insertionNode;
				this.max = insertionNode;
				this.min = insertionNode;
				return counter;

			}

			if (k > this.max.getKey()) {
				this.max = insertionNode;
			}
			if (k < this.min.getKey()) {
				this.min = insertionNode;
			}

			AVLNode searchNode = (AVLNode) this.getRoot();
			AVLNode insertPlace = (AVLNode) this.getRoot();

			// find place to insert O(logn)
			while (searchNode.isRealNode()) {
				if (k > searchNode.getKey()) {
					insertPlace = searchNode;
					searchNode = (AVLNode) searchNode.getRight();
				} else if (k < searchNode.getKey()) {
					insertPlace = searchNode;
					searchNode = (AVLNode) searchNode.getLeft();
				}
			}
			// insertion
			if (k > insertPlace.getKey()) {
				insertPlace.setRight(insertionNode);
			} else {
				insertPlace.setLeft(insertionNode);
			}
			insertionNode.setParent(insertPlace);

			// rebalancing
			return balanceAfterInsertion(insertPlace, counter);
		}
	}
*/
	
	
	
	
	
	/**
	 * private AVLNode findInsertPosition(int k)
	 * <p>
	 * search for an node with k key in the AVL tree. 
	 * returns the node with the k key or the node that in the future 
	 * a node with the k key will be inserted under it
	 * 
	 * Complexity: O(logn) (just a simple search in AVL tree (O(logn))
	 */
	private AVLNode findInsertPosition(int k) {
        AVLNode node = (AVLNode)root;
        while(node.height!=-1) 
        {
            if(node.getKey() == k)
                return node;
            else if(node.getKey()>k) 
            {
            	if(node.getLeft().isRealNode())
            		node = (AVLNode)node.getLeft();
            	else
            		break;
            }
            else //(node.getKey()<k)
            {
            	if(node.getRight().isRealNode())
            		node = (AVLNode)node.getRight();
            	else
            		break;
            }
        }

        return node;
    }
	public int insert(int k, boolean i) {
		int counter = 0;
			if (this.empty()) {
				AVLNode insertionNode = new AVLNode(k, i);
				this.root = insertionNode;
				this.max = insertionNode;
				this.min = insertionNode;
				return counter;
			}
			else {
				AVLNode insertionNode = new AVLNode(k, i);
				//update max if k>max
				if (k > this.max.getKey()) {
					this.max = insertionNode;
				}
				//update min if k<min
				if (k < this.min.getKey()) {
					this.min = insertionNode;
				}
				// find place to insert O(logn)
				AVLNode kFather = (AVLNode)findInsertPosition(k);
				// k is already in the tree
				if(kFather.key==k) {
					return -1;
				}
				// k isn't in the tree
				// insertion
				if (k > kFather.getKey()) {
					kFather.setRight(insertionNode);
				} else {
					kFather.setLeft(insertionNode);
				}
				insertionNode.setParent(kFather);

				// rebalancing
				return balanceAfterInsertion(kFather, counter);
			}
		}
	
	
	
	
	
	
	/**
	 * this function get a node, and moves up the tree while balancing it and
	 * updating the size and xor of each node's sub tree.
	 * 
	 * @param node
	 * @param counter - number of actions required to balance he tree). actions :
	 *                change height, rotate left, rotate right.
	 * 
	 * @return counter
	 * 
	 *         Complexity: O(logn)
	 */
	public int balanceAfterInsertion(AVLNode node, int counter) {
		while (node != null) {
			AVLNode nextNodeToCheck = (AVLNode) node.getParent();
			node.updateSize();
			boolean flag = false; // if rotating root
			int heightBefore = node.getHeight();
			node.calculateNewHeight();
			node.calculateLeftXor();
			int heightAfter = node.getHeight();

			boolean bf = node.balanceFactorLegal(); // check if balance factor is legal
			boolean heightChanged = ((heightBefore - heightAfter) != 0);
			int balanceFactor = node.balanceFactor();
			if (bf && !heightChanged) {
				node = nextNodeToCheck;
			} else if (bf && heightChanged) {
				node = nextNodeToCheck;
				counter++; // promote or demote.
			} else {
				if (this.getRoot().getKey() == node.getKey()) {
					flag = true;
				}
				if (balanceFactor == 2) { // balance factor is 2.
					AVLNode leftSon = (AVLNode) node.getLeft();
					if (leftSon.balanceFactor() == 1) { // left son balance factor is 1.
						node.rotateR(); // rotate right
						counter += 2; // height changed
						if (flag) { // if rotating root, new root is new parent.
							this.setRoot(node.parent);
						}
					} else { // left son balance factor is -1.
						leftSon.rotateL();
						node.rotateR();// rotate left then right
						counter += 5;
						if (flag) { // if rotating root, new root is new parent.
							this.setRoot(node.parent);
						}
					}
					// rotate
				} else { // balance factor is -2.
					AVLNode rightSon = (AVLNode) node.getRight();
					if ((rightSon.balanceFactor() == 1)) { // right son balance factor is 1.
						rightSon.rotateR();
						node.rotateL(); // rotate right then left.
						counter += 5;
						if (flag) { // if rotating root, new root is new parent.
							this.setRoot(node.parent);
						}
					} else { // right son balance factor is -1.
						node.rotateL(); // rotate left
						// node.calculateLeftXor();
						counter += 2;
						if (flag) { // if rotating root, new root is new parent.
							this.setRoot(node.parent);

						}
					}
				}
				node = nextNodeToCheck;
			}

		}
		return counter;
	}

	/**
	 * public int delete(int k)
	 * <p>
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of nodes which
	 * required rebalancing operations (i.e. demotions or rotations). returns -1 if
	 * an item with key k was not found in the tree.
	 * 
	 * Complexity: O(logn). (using search (O(logn)), getNode (O(logn)) ,
	 * balanceAfterDeletion (O(logn)) , getSuccessor (O(logn))
	 */
	public int delete(int k) {
		// tree is empty or key not in tree
		if (this.empty() || this.search(k) == null) {
			return -1;
		}
		// key is in the tree
		AVLNode node = getNode((AVLNode) this.getRoot(), k); // finds the node to be deleted
		if (node.getKey() == this.max.getKey()) {
			this.max = (AVLNode) predecessor(node);
		}
		if (node.getKey() == this.min.getKey()) {
			this.min = (AVLNode) successor(node);
		}

		if (node.isLeaf()) { // node to delete is leaf
			if ((AVLNode) this.getRoot() == node) { // only root in tree
				this.setRoot(new AVLNode(true));
				return 0;
			} else {
				AVLNode parent = (AVLNode) node.getParent();
				if (node.isLeftSon()) {
					node.getParent().setLeft(new AVLNode(true));
				} else {
					parent.setRight(new AVLNode(true));
				}
				node.setParent(new AVLNode(true));
				parent.updateSize();
				return balanceAfterDeletion(parent);
			}
		}
		if (node.getLeft().isRealNode() && node.getRight().isRealNode()) { // node to delete is binary
			AVLNode successor = successor(node);
			successor.setLeft(node.getLeft());
			node.getLeft().setParent(successor);
			node.setLeft(new AVLNode(true));
			AVLNode rebalancNode = null;
			if (successor.isLeaf()) {
				if (successor.isLeftSon()) {
					rebalancNode = (AVLNode) successor.getParent();
					rebalancNode.setLeft(new AVLNode(true));
					successor.setRight(node.getRight());
					node.getRight().setParent(successor);
					node.setRight(new AVLNode(true));
				} else { // successor is right child
					successor.getParent().setRight(new AVLNode(true));
					rebalancNode = successor;
				}
			} else { // successor has only right child
				if (successor.isLeftSon()) {
					rebalancNode = (AVLNode) successor.getParent();
					successor.getParent().setLeft(successor.getRight());
					successor.getRight().setParent(successor.getParent());
					node.getRight().setParent(successor);
					successor.setRight(node.getRight());
					node.setRight(new AVLNode(true));
				} else {
					node.setRight(new AVLNode(true));
					rebalancNode = successor;
				}
			}
			if (node == (AVLNode) this.getRoot()) { // successor replaces root
				successor.setParent(new AVLNode(true));
				this.setRoot(successor);
			} else {
				if (node.isLeftSon()) {
					node.getParent().setLeft(successor);
				} else {
					node.getParent().setRight(successor);
				}
				successor.setParent(node.getParent());
				node.setParent(new AVLNode(true));
			}
			rebalancNode.updateSize();
			successor.updateSize();
			return balanceAfterDeletion(rebalancNode);
		} else { // node to delete has only one child
			AVLNode nodeParent = (AVLNode) node.getParent();
			if (node.getRight().isRealNode()) { // node to delete is right unary
				if (node == (AVLNode) this.getRoot()) {
					node.getRight().setParent(new AVLNode(true));
					this.setRoot((AVLNode) node.getRight());
					this.root.updateSize();
					this.root.calculateNewHeight();
					this.root.calculateLeftXor();
					return balanceAfterDeletion((AVLNode) this.getRoot());
				} else {
					node.getRight().setParent(node.getParent());
					if (node.isLeftSon()) {
						node.getParent().setLeft(node.getRight());
					} else {
						node.getParent().setRight(node.getRight());
					}
					node.setParent(new AVLNode(true));
				}
				node.setRight(new AVLNode(true));
			} else { // node to delete is left unary
				if (node == (AVLNode) this.getRoot()) {
					node.getLeft().setParent(new AVLNode(true));
					this.setRoot((AVLNode) node.getLeft());
					this.root.updateSize();
					this.root.calculateNewHeight();
					this.root.calculateLeftXor();
					return balanceAfterDeletion((AVLNode) this.getRoot());
				} else {
					node.getLeft().setParent(node.getParent());
					if (node.isLeftSon()) {
						node.getParent().setLeft(node.getLeft());
					} else {
						node.getParent().setRight(node.getLeft());
					}
					node.setParent(new AVLNode(true));
					node.setLeft(new AVLNode(true));
				}
			}
			nodeParent.updateSize();
			return balanceAfterDeletion(nodeParent);
		}

	}

	/**
	 * /**
	 *
	 * @param node - node where to start move up the tree for rebalancing
	 * @return counter - the number for actions required to balance the tree actions
	 *         : change height, rotate left, rotate right.
	 *
	 *         the function get a node, and move up the tree while balancing it.
	 *         also update the size and xor of each node's subtree.
	 *
	 *         Complexity: O(logn).
	 */
	public int balanceAfterDeletion(AVLNode node) {
		AVLNode currentNode = node;
		int counter = 0;
		int heightBefore = currentNode.getHeight();
		currentNode.calculateNewHeight();
		int heightAfter = currentNode.getHeight();
		if (heightAfter != heightBefore) {
			counter++;
		}
		while (currentNode.isRealNode()) {
			int[] balance = currentNode.getBalance();
			if (balance[0] == 2 && balance[1] == 2) { // node is 2,2
				currentNode.calculateNewHeight(); // demote current node
				currentNode.updateSize();
				currentNode.calculateLeftXor();
				counter++; // cost of 1 demote
			} else {
				boolean replaceRoot = (currentNode == (AVLNode) this.getRoot());
				if (balance[0] == 3 && balance[1] == 1) { // node is 3,1
					AVLNode rightSon = (AVLNode) currentNode.getRight();
					int rightSonRightDiff = rightSon.getHeight() - rightSon.getRight().getHeight();
					if (rightSonRightDiff == 1) { // requires a single rotation left
						currentNode.rotateL();
						currentNode.updateSize();
						rightSon.updateSize();
						currentNode = rightSon;
						counter += 3; // cost of a single rotation left
						if (replaceRoot) {
							this.setRoot(currentNode);
						}
					}
					if (rightSonRightDiff == 2) { // requires double rotation
						rightSon.rotateR();
						currentNode.rotateL();
						rightSon.updateSize();
						currentNode.updateSize();
						currentNode = (AVLNode) currentNode.getParent();
						currentNode.updateSize();
						counter += 6; // cost of double rotation
						if (replaceRoot) {
							this.setRoot(currentNode);
						}
					}
				}
				if (balance[0] == 1 && balance[1] == 3) { // node is 1,3
					AVLNode leftSon = (AVLNode) currentNode.getLeft();
					int leftSonLeftDiff = leftSon.getHeight() - leftSon.getLeft().getHeight();
					if (leftSonLeftDiff == 1) { // requires a single rotation right
						currentNode.rotateR();
						currentNode.updateSize();
						leftSon.updateSize();
						currentNode = leftSon;
						counter += 3; // cost of a single rotation right
						if (replaceRoot) {
							this.setRoot(currentNode);
						}
					}
					if (leftSonLeftDiff == 2) { // requires double rotation
						leftSon.rotateL();
						currentNode.rotateR();
						leftSon.updateSize();
						currentNode.updateSize();
						currentNode = (AVLNode) currentNode.getParent();
						currentNode.updateSize();
						counter += 6;
						if (replaceRoot) {
							this.setRoot(currentNode);
						}
					}
				}
			}
			currentNode = (AVLNode) currentNode.getParent();
			currentNode.updateSize();
		}
		return counter;
	}

	/**
	 * look for the node with the maximal key of all the nodes that key < node.key
	 * 
	 * @param node
	 * @return pointer to AVLNode predecessor of node
	 * 
	 *         Complexity: O(logn)
	 */
	public AVLNode predecessor(AVLNode node) {
		if (node.getLeft().isRealNode()) {
			AVLNode predeccessor = (AVLNode) node.getLeft();
			while (predeccessor.getRight().isRealNode()) {
				predeccessor = (AVLNode) predeccessor.getRight();
			}
			return predeccessor;
		} else if (node.getParent().isRealNode() && !node.isLeftSon()) {
			AVLNode predeccessor = (AVLNode) node.getParent();
			return predeccessor;
		} else {
			return new AVLNode(true);
		}

	}

	/**
	 * public Boolean min()
	 * <p>
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 * 
	 * Complexity: O(1)
	 */
	public Boolean min() {
		if (this.empty()) {
			return null;
		} else {
			return this.min.getValue();
		}
	}

	/**
	 * public Boolean max()
	 * <p>
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 * 
	 * Complexity: O(1)
	 */
	public Boolean max() {
		if (this.empty()) {
			return null;
		} else {
			return this.max.getValue();
		}
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 * 
	 * Complexity: O(n)
	 */
	public int[] keysToArray() {
		int[] arr = new int[this.size()];
		keysToArrayRec((AVLNode) this.getRoot(), 0, arr);
		return arr;
	}

	/**
	 *
	 * @param root  - root of the scanned tree
	 * @param index - for recursion
	 * @param array - the sorted array that contains the keys of the tree nodes
	 * @return index for recursion.
	 *
	 *         Complexity: O(n).
	 */
	private int keysToArrayRec(AVLNode root, int index, int[] array) {
		if (!root.isRealNode()) {
			return index;
		} else {
			if (root.getLeft().isRealNode()) {
				index = keysToArrayRec((AVLNode) root.getLeft(), index, array);
			}
			array[index++] = (root.getKey());
			if (root.getRight().isRealNode()) {
				index = keysToArrayRec((AVLNode) root.getRight(), index, array);
			}
			return index;
		}
	}

	/**
	 * public boolean[] infoToArray()
	 * <p>
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 * 
	 * Complexity: O(n)
	 */
	public boolean[] infoToArray() {
		boolean[] arr = new boolean[this.size()];
		infoToArrayRec((AVLNode) this.getRoot(), 0, arr);
		return arr;
	}

	/**
	 * @param node  - root of the scanned tree.
	 * @param index - for recursion.
	 * @param array - the sorted array (based on keys) that contains the info of the
	 *              tree nodes.
	 * @return index for recursion.
	 *
	 *         Complexity: O(n).
	 */
	private int infoToArrayRec(AVLNode node, int index, boolean[] array) {
		if (!node.isRealNode()) {
			return index;
		} else {
			if (node.getLeft().isRealNode()) {
				index = infoToArrayRec((AVLNode) node.getLeft(), index, array);
			}
			array[index++] = (node.getValue());
			if (node.getRight().isRealNode()) {
				index = infoToArrayRec((AVLNode) node.getRight(), index, array);
			}
			return index;
		}
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 * 
	 * Complexity: O(1)
	 */
	public int size() {
		return this.root.size; // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty
	 * 
	 * Complexity: O(1)
	 */
	public AVLNode getRoot() {
		return this.root;
	}

	/**
	 * 
	 * @precondition: this.search(k) != null.
	 *
	 * @param node (the node to start the search at)
	 * @param k    (key of the node we search for).
	 * @return pointer to AVLNode with Key == k.
	 * 
	 *         Complexity: O(logn)
	 */
	public AVLNode getNode(AVLNode node, int k) {
		AVLNode nodeToDelete = node;
		while (nodeToDelete.getKey() != k) {
			if (k < nodeToDelete.key) {
				nodeToDelete = (AVLNode) nodeToDelete.getLeft();
			} else {
				nodeToDelete = (AVLNode) nodeToDelete.getRight();
			}
		}
		return nodeToDelete;
	}

	/**
	 * public boolean prefixXor(int k)
	 *
	 * Given an argument k which is a key in the tree, calculate the xor of the
	 * values of nodes whose keys are smaller or equal to k.
	 *
	 * @precondition: this.search(k) != null
	 * @param k Complexity: O(logn) using prefixXorRec(O(logn))
	 */
	public boolean prefixXor(int k) {
		if (this.root.getKey() == k)
			return this.root.getLeftXor();
		AVLNode node = getNode(this.root, k);
		boolean info = node.getLeftXor();
		return prefixXorRec(node, info);

	}

	/**
	 * @param node - the current node
	 * @param xor  - for recursion. the xor calculated so far
	 * @return the calculated xor
	 * 
	 *         Complexity: O(logn)
	 */
	public boolean prefixXorRec(AVLNode node, boolean xor) {
		if (node.parent.getKey() == this.root.getKey()) {
			if (!node.isLeftSon())
				return node.parent.getLeftXor() ^ xor;
			else
				return xor;
		}
		if (node.isLeftSon()) {
			return prefixXorRec(node.parent, xor);
		} else {
			return prefixXorRec(node.parent, node.parent.getLeftXor() ^ xor);
		}

	}

	/**
	 * public AVLNode successor
	 *
	 * given a node 'node' in the tree, return the successor of 'node' in the tree
	 * (or null if successor doesn't exist)
	 *
	 * @param node - the node whose successor should be returned
	 * @return the successor of 'node' if exists, null otherwise
	 * 
	 *         Complexity: O(logn)
	 */
	public AVLNode successor(AVLNode node) {
		if (node.getRight().isRealNode()) {
			AVLNode successor = (AVLNode) node.getRight();
			while (successor.getLeft().isRealNode()) {
				successor = (AVLNode) successor.getLeft();
			}
			return successor;
		} else if (node.getParent().isRealNode() && node.isLeftSon()) {
			AVLNode successor = ((AVLNode) node.getParent());
			return successor;
		} else {
			if (node.getParent().isRealNode()) {
				AVLNode p = (AVLNode) node.getParent();
				while (p.parent.isRealNode() && !p.isLeftSon()) {
					p = p.parent;
				}
				return (AVLNode) p.getParent();
			} else
				return new AVLNode(true);
		}
	}

	/**
	 * public boolean succPrefixXor(int k)
	 *
	 * This function is identical to prefixXor(int k) in terms of input/output.
	 * However, the implementation of succPrefixXor should be the following:
	 * starting from the minimum-key node, iteratively call successor until you
	 * reach the node of key k. Return the xor of all visited nodes.
	 *
	 * precondition: this.search(k) != null
	 * 
	 * Complexity: O(n)
	 */
	public boolean succPrefixXor(int k) {
		if (min == null || !min.isRealNode())
			return false;
		AVLNode node = this.min;
		boolean result = node.info;
		while (node.getKey() < k) { // until k
			node = successor(node);
			result = result ^ node.info;
		}
		return result;
	}

	/**
	 * Set node as tree root
	 * 
	 * @param node
	 * 
	 *             Complexity: O(1)
	 */
	public void setRoot(AVLNode node) {
		AVLNode newRoot = node;
		newRoot.setParent(new AVLNode(true));
		this.root = newRoot;
	}

	/**
	 * public class AVLNode
	 * <p>
	 * This class represents a node in the AVL tree.
	 * <p>
	 * IMPORTANT: do not change the signatures of any function (i.e. access
	 * modifiers, return type, function name and arguments. Changing these would
	 * break the automatic tester, and would result in worse grade.
	 * <p>
	 * However, you are allowed (and required) to implement the given functions, and
	 * can add functions of your own according to your needs.
	 */
	public class AVLNode {

		private boolean info;
		private int key;
		private int size; // maintained in insertion and deletion.
		private int height;
		private boolean isVirtualNode;

		private AVLNode parent;
		private AVLNode left;
		private AVLNode right;
		private boolean xorLeft;

		/**
		 * constructor for AVLNodes.
		 * 
		 * @return Non-Virtual ALVNode
		 *
		 *         create a Non-Virtual Node. Compelxity: O(1).
		 */
		public AVLNode(int key, boolean info) {
			this.key = key;
			this.info = info;
			this.size = 1;
			this.height = 0;
			this.parent = new AVLNode(true);
			this.left = new AVLNode(true);
			this.right = new AVLNode(true);
			this.isVirtualNode = false;
			this.xorLeft = info;
		}

		/**
		 * constructor for AVLNodes.
		 * 
		 * @return Virtual AVLNode where AVLNode.key is -1, ALVNode.info is false.
		 *
		 *         create a Virtual Node. Complexity: O(1)
		 */
		public AVLNode(boolean IsVirtual) {

			this.key = -1;
			this.info = false;
			this.size = 0;
			this.height = -1;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.isVirtualNode = true;

		}

		// returns node's key (for virtual node return -1)
		public int getKey() {
			if (this.isRealNode()) {
				return this.key;
			} else {
				return -1;
			}
		}

		// returns node's value [info] (for virtual node return null)
		public Boolean getValue() {
			if (this.isRealNode()) {
				return this.info;
			} else {
				return null;
			}
		}

		// sets left child
		public void setLeft(AVLNode node) {
			this.left = (AVLNode) node;
		}

		// returns left child (if there is no left child return null)
		public AVLNode getLeft() {
			return this.left;
		}

		// sets right child
		public void setRight(AVLNode node) {
			this.right = (AVLNode) node;
		}

		// returns right child (if there is no right child return null)
		public AVLNode getRight() {
			return this.right;
		}

		// sets parent
		public void setParent(AVLNode node) {
			this.parent = (AVLNode) node;
		}

		// returns the parent (if there is no parent return null)
		public AVLNode getParent() {
			return this.parent;
		}

		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode() {
			return !this.isVirtualNode;
		}

		// sets the height of the node
		public void setHeight(int height) {
			this.height = height;
		}

		// Returns the height of the node (-1 for virtual nodes)
		public int getHeight() {
			if (this.isRealNode()) {
				return this.height;
			} else {
				return -1;
			}
		}

		// returns the balance factor of the node (left.height - right.height)
		public int balanceFactor() {
			if (this.isRealNode()) {
				int rightHeight = this.getRight().getHeight();
				int leftHeight = this.getLeft().getHeight();

				return leftHeight - rightHeight;
			}
			return 0;
		}

		// returns -2 < balanceFacter < 2
		public boolean balanceFactorLegal() {
			if (this.balanceFactor() < 2 && this.balanceFactor() > -2)
				return true;
			return false;
		}

		// set the size of the node
		public void setSize(int size) {
			this.size = size;
		}

		// get the size of the node
		public int getSize() {
			return this.size;
		}

		// update size of node according to the current size of the children
		public void updateSize() {
			if (this.isRealNode()) {
				this.setSize(this.left.getSize() + this.right.getSize() + 1);
			}
		}

		// calculate and save the xorLeft of node according to the current info in the
		// left subtree of the node
		public void calculateLeftXor() {
			if (this.isRealNode()) {
				if (this.isLeaf()) {
					this.xorLeft = this.info;
				} else {
					if (this.left.isVirtualNode)
						this.xorLeft = this.info;
					else {
						if (!this.left.right.isVirtualNode)
							this.xorLeft = this.info ^ this.left.right.xorLeft ^ this.left.xorLeft;
						else
							this.xorLeft = this.info ^ this.left.xorLeft;
					}
				}
			}
		}

		boolean getLeftXor() {
			return this.xorLeft;
		}

		// if this node is left son, return true. else return false
		boolean isLeftSon() {
			AVLNode p = this.parent;
			if (this.equals(p.left))
				return true;
			return false;
		}

		// calculate new height and return true if height changed, false if not
		public void calculateNewHeight() {
			if (this.isRealNode()) {
				int rightHeight = this.getRight().getHeight();
				int leftHeight = this.getLeft().getHeight();
				this.setHeight(1 + Math.max(rightHeight, leftHeight));
			}
		}

		public boolean isLeaf() {
			return this.height == 0; // leaf height is 0
		}

		/**
		 * make a right rotation
		 *
		 * Complexity: O(1).
		 */
		public void rotateR() {

			AVLNode temp = (AVLNode) this.left; // A
			AVLNode newPointer = (AVLNode) this.left.right; // Ar
			AVLNode parent = (AVLNode) this.parent;

			this.setLeft(newPointer); // Ar left to B
			newPointer.setParent(this); // B parent to Ar

			temp.setRight(this); // B right to A
			temp.setParent(parent);

			if (!parent.isVirtualNode) {
				if (parent.right.getKey() == this.getKey()) { // B is right son
					parent.setRight(temp);
				} else {
					parent.setLeft(temp);
				}
			}
			this.setParent(temp);

			this.calculateNewHeight();
			this.updateSize();
			this.calculateLeftXor();
			temp.calculateNewHeight();
			temp.updateSize();
			temp.calculateLeftXor();
		}

		/**
		 * make a left rotation
		 *
		 * Complexity: O(1).
		 */
		public void rotateL() {

			AVLNode temp = (AVLNode) this.right; // A
			AVLNode newPointer = (AVLNode) this.right.left; // Ar
			AVLNode parent = (AVLNode) this.parent;

			this.setRight(newPointer); // Ar left to B
			newPointer.setParent(this); // B parent to Ar

			temp.setLeft(this); // B right to A
			temp.setParent(this.parent);

			if (!parent.isVirtualNode) { // change sons for parent
				if (parent.right.getKey() == this.getKey()) { // B is right son
					parent.setRight(temp);
				} else {
					parent.setLeft(temp);
				}
			}
			this.setParent(temp);

			this.calculateNewHeight();
			this.updateSize();
			this.calculateLeftXor();
			temp.calculateNewHeight();
			temp.updateSize();
			temp.calculateLeftXor();
		}

		/**
		 *
		 * @return 2 elements array where array[0] is the height differences between
		 *         this node and his left son, and array[1] is the height differences
		 *         between this node and his right son.
		 *
		 *         Complexity: O(1).
		 */
		public int[] getBalance() {
			int rightDiff = (this.getHeight() - this.right.getHeight());
			int leftDiff = (this.getHeight() - this.left.getHeight());
			return new int[] { leftDiff, rightDiff };

		}

		@Override
		public String toString() {
			if (this.isVirtualNode)
				return "virtual";
			return "Node [info=" + info + ", key=" + key + ", xorLeft=" + xorLeft + "]";
		}

	}

	@Override
	public String toString() {
		return "AVLTree [root=" + root + "]";
	}

}
