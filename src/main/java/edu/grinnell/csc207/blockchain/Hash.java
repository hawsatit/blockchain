package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    
    private final byte[] hash;
    
    /**
     * Creates a new Hash object with the data as the hash.
     * 
     * @param data the byte array representing the hash value
     */
    public Hash(byte[] data) {
        this.hash = data;
    }
    
    /**
     * Returns the hash value as a byte array.
     * 
     * @return the hash value as a byte[]
     */
    public byte[] getData() {
        return this.hash;
    }
    
    /**
     * Checks if the hash is valid according to specific conditions.
     * 
     * @return true if the conditions for hash validity are met, false otherwise
     */
    public boolean isValid() {
        for (int i = 0; i < 3; i++) {
            if (this.hash[i] != 0) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < this.hash.length; i++) {
            hashString.append(String.format("%02x", Byte.toUnsignedInt(this.hash[i])));
        }
        return hashString.toString();
    }
    
    /**
     * Compares this hash with another object for structural equality.
     * 
     * @param other the object to compare to
     * @return true if this hash is structurally equal to the argument, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash otherHash = (Hash) other;     
            return Arrays.equals(this.hash, otherHash.hash);
        } else {
            return false;
        }
    }
}
