package org.tree;

public class BinaryTreeNode<T> {
	
	private T data;
	
	private BinaryTreeNode<T> left;
	
	private BinaryTreeNode<T> right;
	
	public BinaryTreeNode(T data, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		this.data=data;
		this.left=left;
		this.right=right;
	}
	public BinaryTreeNode(T data)
	{
		this(data, null, null);
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public BinaryTreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(BinaryTreeNode<T> left) {
		this.left = left;
	}

	public BinaryTreeNode<T> getRight() {
		return right;
	}

	public void setRight(BinaryTreeNode<T> right) {
		this.right = right;
	}
}
