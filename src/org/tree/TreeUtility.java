package org.tree;

public class TreeUtility {

	public static int heightofTree(Node root, int level) {
		
		if (root == null)
			return level - 1;
		else if (root.getLeft() != null || root.getRight() != null) {
			int leftLevel = heightofTree(root.getLeft(), level + 1);
			int rightLevel = heightofTree(root.getRight(), level + 1);
			level = (leftLevel > rightLevel) ? leftLevel : rightLevel;
		}
		System.out.println("Node: " + root.getData() + ", Height: " + level);
		return level;
	}

	public static void main(String[] args) {
		Node root = new Node(10, new Node(8), new Node(6, new Node(4), null));		
		//root = new Node(10, null, null);
		System.out.println(heightofTree(root, 1));
	}

}
