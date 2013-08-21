package org.lru;

public class LRUCacheMain {
	
	public static void main(String[] args) {
		LRUCache lr =new LRUCache(5);
		lr.put("a", "1");
		lr.put("p", "3");
		lr.put("q", "5");
		lr.put("r", "6");
		lr.put("x", "7");
		lr.put("y", "8");
		
		System.out.println(lr.get("a"));
		System.out.println(lr.get("q"));
	}

}
