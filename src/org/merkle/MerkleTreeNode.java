package org.merkle;


public class MerkleTreeNode {
    
    public String hash;
    
    public byte[] data;
    
    public MerkleTreeNode(int length)
    {
        data = new byte[length];
        hash = null;
    }

}
