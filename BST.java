package HW7;

import java.util.LinkedList;

/**
 * Your implementation of the BST class
 *
 * @author Trien Hong
 */

import java.util.List;


public class BST<K extends Comparable<? super K>, V> {
	private BSTNode<K, V> root;
	
	/**
     * This constructor initializes an empty BST.
     *
     * There is no need to do anything for the constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }
    
    
    /**
     * Adds a new entry to the tree or updates the value of an existing key in the tree
     * 
     * Traverse the tree to find the appropriate location. If the key is
     * already in the tree, then update its value to the new value. Otherwise
     * create a new node consisting the new (key, value) pair and add it to the tree.
     * The new node becomes a leaf. Then go back up the tree from the new leaf to the root. 
     * Upon seeing an imbalanced node on the path, balance it with proper rotations. 
     * Update the height, balance factor and the new size instance variable of every affected node.
     * 
     * This method is essentially the same as its counterpart in SimpleBST. Therefore
     * you can use the code for put(), its helper method putHelper() and the helper's helpers - 
     * balance(), rotateLeft(), rotateRight(), update() and height(), from the SimpleBST class.
     * The only new items you need to incorporate are the following:
     * 		1. Modify the code properly to handle the generic types K and V for the key and value.
     * 		2. Provide the code for rotateLeft() which mirrors rotateRight(). You can get 
     * 		   lots of help from Slide 33 for the BST lectures when completing this method.
     * 		3. In update(), update the size instance variable of a BST node in addition to 
     *         height and balance factor. You need to think of the relation between the size of a node 
     *         and the sizes of its left and right children, and how to define the size of a null node.
     *      4. Handle the exceptions properly.
     * 
     * Time Complexity: O(log n)
     * 
     * @param key    the key of the entry to add or update
     * @param value  the value associated with key
     * @throws       java.lang.IllegalArgumentException if key or value is null
     */
    public void put(K key, V value) {
    	if(key == null || value == null) {
    		throw new IllegalArgumentException();
    	}

    	root = putHelper(root, key, value);
    }
    
    public BSTNode<K, V> putHelper(BSTNode<K, V> node, K key, V value) {
    	if (node == null) {
    		return new BSTNode<K, V>(key, value);
    	}
		
		if (key.compareTo(node.getKey()) == 0) {
    		node.setValue(value);
    		return node;
    	} else if (key.compareTo(node.getKey()) < 0) {
    		node.setLeft(putHelper(node.getLeft(), key, value));
    	} else {
    		node.setRight(putHelper(node.getRight(), key, value));
    	}
    	
    	return balance(node);
    }
    
	private BSTNode<K, V> balance(BSTNode<K, V> node) {
		update(node);

		if (Math.abs(node.getBalanceFactor()) <= 1) {
			return node;
		}
		
		if (node.getBalanceFactor() > 1) {
			if (node.getLeft().getBalanceFactor() >= 0) {
				return rotateRight(node);
			} else {
				node.setLeft(rotateLeft(node.getLeft()));
				return rotateRight(node);
			}
		} else {
			if (node.getRight().getBalanceFactor() <= 0) {
				return rotateLeft(node);
			} else {
				node.setRight(rotateRight(node.getRight()));
				return rotateLeft(node);
			}
		}
	}
	
	private BSTNode<K, V> rotateRight(BSTNode<K, V> node) {
		BSTNode<K, V> left = node.getLeft();
		node.setLeft(left.getRight());
		left.setRight(node);
		update(node);
		update(left);
		return left;
	}
	
	private BSTNode<K, V> rotateLeft(BSTNode<K, V> node) {
		BSTNode<K, V> right = node.getRight();
		node.setRight(right.getLeft());
		right.setLeft(node);
		update(node);
		update(right);
		return right;
		
	}
	
	private void update(BSTNode<K, V> node) {
		int leftHeight = height(node.getLeft());
		int rightHeight = height(node.getRight());
		node.setHeight(1 + Math.max(leftHeight, rightHeight));
		node.setBalanceFactor(leftHeight - rightHeight);
		node.setSize(size(node));
	}
	
	private int height(BSTNode<K, V> node) {
		if (node == null) {
			return -1;
		} else {
			return node.getHeight();
		}
	}
	
	private int size(BSTNode<K, V> node) {
		int leftSideSize = 0;
		int rightSideSize = 0;

		if (node.getLeft() != null) {
			leftSideSize = node.getLeft().getSize();
		}

		if (node.getRight() != null) {
			rightSideSize = node.getRight().getSize();
		}

		return leftSideSize + rightSideSize + 1;
	}
    
    /*
     * Returns the value associated with a given key.
     * 
     * Traverse the tree to find the appropriate location. If the key is
     * in the tree, then return its value. Otherwise return null.
     * 
     * Time Complexity: O(log n)
     * 
     * @param key  the key to search for
     * @return     the value associated with key if key is in the tree, null otherwise
     * @throws     java.lang.IllegalArgumentException if key is null
     */
    public V get(K key) {
    	if(key == null) {
    		throw new IllegalArgumentException();
    	}

    	return getHelper(root, key);
    }
    
	public V getHelper(BSTNode<K, V> node, K key) {
		if (node == null) {
			return null;
		}

		if (key.equals(node.getKey())) {
			return node.getValue();
		}

		if (key.compareTo(node.getKey()) < 0) {
			return getHelper(node.getLeft(), key);
		} else if (key.compareTo(node.getKey()) > 0) {
			return getHelper(node.getRight(), key);
		}

		return null;
	}
    
    
    /**
     * Finds and returns all keys in the tree in descending order
     * 
     * Time Complexity: O(n)
     * 
     * Note: You would NOT receive credit if you perform an in-order traversal of the tree and 
     * then reverse the returned list, as this is unnecessary. Instead your method should 
     * directly obtain the list of all keys in descending order. 
     * 
     * Hint: Modify in-order traversal by changing the order in which the nodes are visited.
     * 
     * @return  the list of all keys in the tree in descending order
     */
    public List<K> reverseOrder() {
    	LinkedList<K> rOrder = new LinkedList<>();
    	reverseOrderHelper(root, rOrder);
    	return rOrder;
    }
    
    private void reverseOrderHelper(BSTNode<K, V> node, LinkedList<K> order) {
    	if (node == null) {
    		return;
    	}
    	
    	reverseOrderHelper(node.getRight(), order);
    	order.add(node.getKey());
    	reverseOrderHelper(node.getLeft(), order);
    }
    
    
    /**
     * Finds and returns the k smallest keys in ascending order
     * 
     * Ex:
     * For the following BST
     * 
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * 
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25]
     * kSmallest(3) should return the list [10, 12, 13]
     * kSmallest(20) should cause java.lang.IllegalArgumentException to be thrown
     * 
     * Time Complexity: O(log n + k)
     * 
     * Note: The required time complexity does NOT allow you to perform an in-order traversal
     * on the entire tree and then return the k smallest keys. Instead you should only traverse the 
     * branches of the tree necessary to get the data you need.
     * 
     * @param k  the number of smallest keys to find
     * @return   the list of k smallest keys in ascending order
     * @throws   java.lang.IllegalArgumentException if k < 0 or k > the size of the tree
     */
    public List<K> kSmallest(int k) { // needs more works
		List<K> kSmallestList = new LinkedList<K>();

		if (k < 0 || k > size(root)) {
			throw new IllegalArgumentException();
		}

		kSmallestHelper(root, k, kSmallestList);
		
		return kSmallestList;
	}
	private void kSmallestHelper(BSTNode<K, V> node, int k, List<K> kSmallestList) {
		if (node == null) {
			return;
		}

		kSmallestHelper(node.getLeft(), k, kSmallestList);

		if (kSmallestList.size() < k) {
			kSmallestList.add(node.getKey());
		}
		
		if (kSmallestList.size() < k) {
			kSmallestHelper(node.getRight(), k, kSmallestList);
		}
	}
    
    
    /**
     * Finds and returns the predecessor of the given key, i.e. the largest key 
     * in the tree that is smaller than the given key.
     * 
     * Note: The given key may or may not be in the tree.
     * 
     * Time Complexity: O(log n)
     * 
     * Note: The required time complexity does NOT allow you to traverse the tree to sort the keys  
     * 
     * Hint: Start by searching for the key in the tree.
     * If the key is not in the tree, then its predecessor is the lowest key on the
     * search path that is smaller than the key. If the key is in the tree, 
     * then there are two cases:
     * 
     * 1. The left subtree of the key is empty. In this case, its predecessor is the lowest key 
     * on the search path that is smaller than the key.
     * 
     * 2. The left subtree of the key is not empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 
     * Ex:
     * For the following BST
     * 
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \     / \
     *        10  15    40  78  85
     *        
     * predecessor(82) should return 80
     * predecessor(78) should return 75
     * predecessor(50) should return 40
     * predecessor(10) should return null
     * 
     * @param key  the key to find the predecessor for
     * @return     the predecessor of key if the predecessor exists, null otherwise
     * @throws     java.lang.IllegalArgumentException if key is null
     */
	public K predecessor(K key) {
		
		if (key == null) {
			throw new IllegalArgumentException();
		}

		return predecessorHelper(root, key, null);
	}

	private K predecessorHelper(BSTNode<K, V> node, K key, K previous) {
		if (node == null) {
			return null;
		}

		if (key.equals(node.getKey())) {
			if (node.getLeft() != null) {
				return leftBiggest(node.getLeft());
			}
		} else if (key.compareTo(node.getKey()) < 0) {
			if (node.getLeft() == null) {
				return previous;
			}

			return predecessorHelper(node.getLeft(), key, previous);
		} else {
			previous = node.getKey();
			if (node.getRight() == null) {
				return previous;
			}

			return predecessorHelper(node.getRight(), key, previous);
		}

		return previous;
	}
	
	private K leftBiggest(BSTNode<K, V> node) {
		while (node.getRight() != null) {
			node = node.getRight();
		}

		return node.getKey();
	}
    
    
    /**
     * Finds and returns the successor of the given key, i.e. the smallest key 
     * in the tree that is larger than the given key.
     * 
     * This method mirrors predecessor()
     * 
     * Time Complexity: O(log n)
     * 
     * @param key  the key to find the successor for
     * @return     the successor of key if the successor exists, null otherwise
     * @throws     java.lang.IllegalArgumentException if key is null
     */
	public K successor(K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		return successorHelper(root, key, null);
	}

	private K successorHelper(BSTNode<K, V> node, K key, K previous) {
		if (node == null) {
			return null;
		}

		if (key.equals(node.getKey())) {
			if (node.getRight() != null) {
				return rightSmallest(node.getRight());
			}
		} else if (key.compareTo(node.getKey()) < 0) {
			previous = node.getKey();

			if (node.getLeft() == null) {
				return previous;
			}

			return successorHelper(node.getLeft(), key, previous);
		} else {
			if (node.getRight() == null) {
				return previous;
			}

			return successorHelper(node.getRight(), key, previous);
		}
		
		return previous;
	}

	private K rightSmallest(BSTNode<K, V> node) {
		if (node.getLeft() == null) {
			return node.getKey();
		} else {
			return rightSmallest(node.getLeft());
		}
	}
    
    /**
     * For Extra Credit ONLY
     * 
     * Removes the data whose key matches the given key and returns its associated value
     * if the key is in the tree; returns null otherwise.
     * 
     * This is a hard method and I did not show you how to do this in the lectures.
     * If you are interested and ambitious enough to attempt this, come to see me and I will
     * explain to you how to remove a node from a BST and how to balance the tree after the removal. 
     * 
     * Time Complexity: O(log n)
     * 
     * @param key  the key of the data to remove
     * @return     the value associated with key if key is in the tree, null otherwise
     * @throws     java.lang.IllegalArgumentException if key is null
     */
    public V remove(K key) {
    	// Replace this line with your return statement
    	return null;
    }
    
    
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. 
     *
     * @return the root of the tree
     */
    public BSTNode<K, V> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}