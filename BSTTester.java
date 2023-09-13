package HW7;

public class BSTTester {
	public static void main(String[] args) {
		BST bst = new BST();
		
		bst.put(50, 10);
		bst.put(25, 11);
		bst.put(75, 5);
		bst.put(12, 5);
		bst.put(37, 123);
		bst.put(70, 2);
		bst.put(80, 3);
		bst.put(10, 2);
		bst.put(15, 51);
		bst.put(40, 4);
		bst.put(78, 3);
		bst.put(85, 5);
		
		System.out.println(bst.get(50));
		System.out.println(bst.get(25));
		System.out.println(bst.get(75));
		System.out.println(bst.get(12));
		System.out.println(bst.get(37));
		System.out.println(bst.get(70));
		System.out.println(bst.get(80));
		System.out.println(bst.get(10));
		System.out.println(bst.get(15));
		System.out.println(bst.get(40));
		System.out.println(bst.get(78));
		System.out.println(bst.get(85));
		System.out.println(bst.get(59));
	}
}