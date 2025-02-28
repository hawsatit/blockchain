package edu.grinnell.csc207.blockchain;

import java.util.Arrays;


/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    
    private final byte[] hash;
    
    /**
     * creates a new Hash object with the data as the hash
     * @param data 
     */
    public Hash(byte[] data){
        this.hash = data;
        
    }
    
    /**
     * 
     * @return the hash value as a byte[]
     */
    public byte[] getData(){
        return this.hash;
    }
    
    /**
     * 
     * @return true if the conditions for hash validity met, false if not
     */
    public boolean isValid(){
        
        for (int i = 0; i < 3; i++){
            if (this.hash[i] != 0){
                return false;
            }
        }
        
        return true;
    }
    

    @Override
    public String toString(){
    
        String hashString = "";
        for (int i = 0; i < this.hash.length; i++) {
            hashString += String.format("%d", Byte.toUnsignedInt(this.hash[i]));
        }
        return hashString;
    }
    
    /**
     * 
     * @param other
     * @return returns true if this hash is structurally equal to the argument.
     * 
     * if other is type Hash, then cast hash onto other and compare the values of other.hash and this.hash
     */
    @Override
    public boolean equals(Object other){
        
        if (other instanceof Hash){
            
            Hash otherHash = (Hash) other;     
            return Arrays.equals(this.hash, otherHash.hash);
            
        } else {
            
            return false;
        }
    }
}
