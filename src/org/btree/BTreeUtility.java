package org.btree;

public class BTreeUtility {
    
      public static void main(String[] args) {
        BTree t = new BTree(2); 
        t.insert(10);
        t.insert(20);
        t.insert(5);
        
        
        t.insert(12);
        
        t.insert(30);
        t.insert(7);
        t.insert(17);
        
        System.out.println( "Traversal of Nodes" );
        t.printLevelWiseTraverse();
        
        /*
        
        
        t.insert(11);
        t.insert(13);
        t.insert(15);*/
        

        //
        //System.out.println(t);
        //System.out.println(t.root.childNodes[0]);
        //System.out.println(t.root.childNodes[1]);
        

    }

}
