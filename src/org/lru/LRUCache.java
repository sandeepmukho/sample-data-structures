package org.lru;

import java.util.HashMap;

/**
 * LRUCache Implementation HashMap for O(1) Search and Doubly Linked List for
 * maintaining LRU
 * 
 * @author sandy
 * 
 */
public class LRUCache {
	private Node head;
	private Node tail;
	private int maxSize;
	private HashMap<String, Node> m = new HashMap<String, Node>();

	/**
	 * maxSize is the maximum capacity of the cache
	 * @param maxSize
	 */
	public LRUCache(int maxSize) {
		this.maxSize = maxSize;
		head = new Node(null, null);
		tail = new Node(null, null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Add Element to Head of Node
	 * 
	 * @param node
	 */
	private void addElementToListAtHead(Node node) {
		node.next = head.next;
		node.prev = head;
		head.next.prev = node;
		head.next = node;
	}

	/**
	 * Remove Element from the list
	 * 
	 * @param node
	 */
	private void removeElementFromList(Node node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	/**
	 * Function to access data from the cache
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		// get Node from HashMap
		Node node = m.get(key);
		if (node == null)
			return null;
		if (m.size() == 1)
			return node.data;
		// removes element from Doubly LinkedList and add to first
		removeElementFromList(node);
		addElementToListAtHead(node);
		return node.data;
	}

	/**
	 * Function to add new data or modify existing data in the cache
	 * 
	 * @param key
	 * @param data
	 */
	public void put(String key, String data) {
		if (maxSize <= 0)
			return;
		Node node = m.get(key);

		if (node != null) {
			// removes element from Doubly LinkedList and add to head node and update the data
			removeElementFromList(node);
			addElementToListAtHead(node);
			node.data = data;
		} else {			
			node = new Node(key, data);
			m.put(key, node);
			//Add Element to Doubly Linked List in start and 
			addElementToListAtHead(node);
			//if size exceeded , remove the tail element i.e. oldest element
			if (m.size() > maxSize) {
				m.remove(tail.prev.key);
				removeElementFromList(tail.prev);
			}
		}
		return;
	}
}