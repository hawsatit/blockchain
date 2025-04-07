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
     *
     * @param num the block number
     * @param amount the amount of change in the block
     * @param prevHash the hash of the previous block
     * @throws NoSuchAlgorithmException if the hashing algorithm is unavailable
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.data = amount;
        this.prev = prevHash;
        this.hash = convertHash();
        this.nonce = mine();
    }

    /**
     * Initializes a new block with a given nonce
     *
     * @param num the block number
     * @param amount the amount of change in the block
     * @param prevHash the hash of the previous block
     * @param nonce the nonce used for mining
     * @throws NoSuchAlgorithmException if the hashing algorithm is unavailable
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.data = amount;
        this.prev = prevHash;
        this.hash = convertHash();
        this.nonce = nonce;
    }

    /**
     * Mines a nonce that satisfies the validity condition for the block's hash
     *
     * @return the nonce value that satisfies the validity condition
     * @throws NoSuchAlgorithmException if the hashing algorithm is unavailable
     */
    public long mine() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long testNonce = 0;

        while (!this.hash.isValid()) {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.putInt(this.num).putInt(this.data).putLong(testNonce);

            if (this.prev != null) {
                buffer.put(this.prev.getData());
            }

            md.update(buffer.array());
            this.hash = new Hash(md.digest());
            testNonce++;
        }

        return testNonce;
    }

    /**
     * Converts the block's data into a hash
     *
     * @return a Hash with the characteristics of the block (num, data, prev,
     * nonce, hash)
     * @throws NoSuchAlgorithmException if the hashing algorithm is unavailable
     */
    public Hash convertHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putInt(this.num).putInt(this.data).putLong(this.nonce);

        if (this.prev != null) {
            buffer.put(this.prev.getData());
        }

        md.update(buffer.array());
        byte[] hashBytes = md.digest();
        return new Hash(hashBytes);
    }

    /**
     * @return the number of the block
     */
    public int getNum() {
        return this.num;
    }

    /**
     * @return the amount of change of the block (data)
     */
    public int getAmount() {
        return this.data;
    }

    /**
     * @return the nonce long value
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * @return the hash value of the previous block
     */
    public Hash getPrevHash() {
        return this.prev;
    }

    /**
     * @return the hash value of the block
     */
    public Hash getHash() {
        return this.hash;
    }

    /**
     * @return the string representation of the block
     */
    @Override
    public String toString() {
        String blockString = "Block " + this.num + "(Amount: " + this.data + ", Nonce: "
                + this.nonce + ", prevHash: " + this.prev + ", hash: " + this.hash + ")";

        return blockString;
    }
}
