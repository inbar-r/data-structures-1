package avl;


public class Tests {

	public static void main(String[] args) {
		System.out.println(empty());
		System.out.println(search());
		System.out.println(insert_and_size());
		System.out.println(delete());
		System.out.println(min());
		System.out.println(max());
		System.out.println(min_equals_max());
		System.out.println(keysToArray());
		System.out.println(size());
		System.out.println(testRemove());
		System.out.println(prefixXor());
		System.out.println(succPrefixXor());
		System.out.println("done!");
	}
	
   static boolean empty() {
        AVLTree avlTree = new AVLTree();
        if (!avlTree.empty()) {
            return false;
        }
        avlTree.insert(1, true);
        return (!avlTree.empty());
    }

    static boolean search() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.search(1) != null) {
            return false;
        }
        avlTree.insert(1, false);
        if (avlTree.search(1)== false) {
            return true;
        }
        return false;
    }

    static boolean insert_and_size() {
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 1000; i++) {
            avlTree.insert(i, i%3==0);
        }
        return (avlTree.size() == 1000);
    }

    static boolean delete() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.delete(1) != -1) {
            return false;
        }
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, i%3==0);
        }
        return true;
    }

    static boolean min() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.min() != null) {
            return false;
        }
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, i%3==0);
        }
        return (avlTree.min().equals(true));
    }

    static boolean max() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.max() != null) {
            return false;
        }
        for (int i = 0; i < 99; i++) {
            avlTree.insert(i, i%3==0);
        }
        return (avlTree.max().equals(false));
    }

    static boolean min_equals_max() {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(1, true);
        return (avlTree.min().equals(avlTree.max()));
    }

    static boolean keysToArray() {
        AVLTree avlTree = new AVLTree();
        boolean[] infoarray;
        int[] keysarray;
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, i%3==0);
        }
        keysarray = avlTree.keysToArray();
        infoarray = avlTree.infoToArray();
        for (int i = 0; i < 100; i++) {
            if ((infoarray[i] && i%3!=0) ||(!infoarray[i] && i%3==0)) {
                return false;
            }
            if(keysarray[i]!=i)
            	return false;
        }
        return true;

    }

    static boolean size() {
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, i%3==0);
        }
        for (int i = 0; i < 50; i++) {
            avlTree.delete(i);
        }
        for (int i = 0; i < 25; i++) {
            avlTree.insert(i, i%2==0);
        }
        return (avlTree.size() == 75);
    }

    static boolean checkBalanceOfTree(AVLTree.AVLNode current) {
        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;
        if (current.getRight() != null) {
            balancedRight = checkBalanceOfTree(current.getRight());
            rightHeight = getDepth(current.getRight());
        }
        if (current.getLeft() != null) {
            balancedLeft = checkBalanceOfTree(current.getLeft());
            leftHeight = getDepth(current.getLeft());
        }

        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }

   static int getDepth(AVLTree.AVLNode n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.getRight() != null)
            rightHeight = getDepth(n.getRight());
        if (n.getLeft() != null)
            leftHeight = getDepth(n.getLeft());

        return Math.max(rightHeight, leftHeight) + 1;
    }

    static boolean testRemove() {
        AVLTree tree = new AVLTree();
        if (!tree.empty()) {
            return false;
        }
        int[] values = new int[]{16, 24, 36, 19, 44, 28, 61, 74, 83, 64, 52, 65, 86, 93, 88};
        for (int val : values) {
            tree.insert(val, val%2==0);
        }
        if (tree.min()!= true) {
            return false;
        }
        if (tree.max()!=false) {
            return false;
        }
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        tree.delete(88);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(88) != null) {
            return false;
        }

        tree.delete(19);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(19) != null) {
            return false;
        }

        tree.delete(16);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(16) != null) {
            return false;
        }

        tree.delete(28);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(16) != null) {
            return false;
        }
        tree.delete(24);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(24) != null) {
            return false;
        }

        tree.delete(36);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(36) != null) {
            return false;
        }

        tree.delete(52);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(52) != null) {
            return false;
        }

        tree.delete(93);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(93) != null) {
            return false;
        }

        tree.delete(86);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(86) != null) {
            return false;
        }

        tree.delete(83);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (tree.search(83) != null) {
            return false;
        }
        return true;
    }

    static boolean prefixXor() {
    	  AVLTree avlTree = new AVLTree();
          for (int i = 0; i < 100; i++) {
              avlTree.insert(i, i%3==0);
          }
          if(!avlTree.prefixXor(0))
        	  return false;
          if(avlTree.prefixXor(99))
        	  return false;
          if(!avlTree.prefixXor(45))
        	  return false;
          if(!avlTree.prefixXor(2))
        	  return false;
          return true;
    }
    
    static boolean succPrefixXor() {
  	  AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 10; i++) {
            avlTree.insert(i, i%3==0);
        }
        for (int i = 0; i < 10; i++) {
           if(avlTree.prefixXor(i)!=avlTree.succPrefixXor(i)) {
        	   System.out.println(i);
        	   return false;
           }
        }
        return true;
  }
}
