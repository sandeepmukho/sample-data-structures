package org.lru;


/**
 * A Doubly Linked List Node
 * @author sandy
 *
 */
public class Node {

	public Node prev;
	public Node next;
	public String key;
	public String data;

	
	public Node(String key, String data) {
		this.key = key;
		this.data = data;
	}

}
