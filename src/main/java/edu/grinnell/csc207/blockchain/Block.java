package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public final class Block {
    
    int num;
    int data;
    Hash prev;
    long nonce;
    Hash hash;
    
    /**
     * Initializes a new block without a given nonce
     * @param num
     * @param amount
     * @param prevHash
     * @throws NoSuchAlgorithmException 
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.data = amount;
        this.prev = prevHash;
        this.nonce = mine();
        this.hash = convertHash();
    }
    
    /**
     * Initializes a new block with a given nonce
     * @param num
     * @param amount
     * @param prevHash
     * @param nonce
     * @throws NoSuchAlgorithmException 
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException{
        this.num = num;
        this.data = amount;
        this.prev = prevHash;
        this.nonce = nonce;
        this.hash = convertHash();
    }
    
    /**
     * 
     * @return returns a nonce value long whos hash satisfies the validity condition
     * @throws NoSuchAlgorithmException 
     */
    public long mine() throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long testNonce = 0;
        
        while(this.hash.isValid() == false){
            ByteBuffer buffer = ByteBuffer.allocate(4).putInt(this.num).putInt(this.data).putLong(testNonce);
            
            if (this.prev != null) {
                buffer.put(this.prev.getData());
            }
            
            md.update(buffer.array());
            this.hash = new Hash(md.digest());
        }
        
        return testNonce;
    }
    
    /**
     * 
     * @return a Hash with the characteristics of the block (num, data, prev, nonce, hash)
     * @throws NoSuchAlgorithmException 
     */
    public Hash convertHash() throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer buffer = ByteBuffer.allocate(4).putInt(this.num).putInt(this.data).putLong(this.nonce);
        
        
        if (this.prev != null) {
            buffer.put(this.prev.getData());
        }
        
        md.update(buffer.array());
        byte[] hashBytes = md.digest();
        return new Hash(hashBytes);
        
    }
    
    /**
     * 
     * @return the number of the block
     */
    public int getNum(){
        return this.num;
    }
    
    /**
     * 
     * @return the amount of change of the block (data)
     */
    public int getAmount(){
        return this.data;
    }
    
    /**
     * 
     * @return the nonce long value
     */
    public long getNonce(){
        return this.nonce;
    }
    

    /**
     * 
     * @return the hash value of the previous block
     */
    public Hash getPrevHash(){
        return this.prev;
    }
    
    /**
     * 
     * @return the hash value of the block
     */
    public Hash getHash(){
        return this.hash;
    }
    
    /**
     * 
     * @return the string representation of the block
     */
    @Override
    public String toString(){
        String blockString = "Block " + this.num + "(Amount: " + this.data + ", Nonce: " + this.nonce + ", prevHash: " + this.prev + ", hash: " + this.hash + ")";
        return blockString;
    }
    
    
}
