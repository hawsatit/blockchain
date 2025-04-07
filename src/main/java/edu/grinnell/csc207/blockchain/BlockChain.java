package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary transactions.
 */
public class BlockChain {

    Node first;
    Node last;
    int size;

    /**
     * The node object used for the BlockChain.
     */
    public class Node {

        Block block;
        Node next;

        /**
         * Constructs a new node with a block and a pointer to the next node.
         * @param block the block stored in this node
         * @param next the next node in the chain
         */
        public Node(Block block, Node next) {
            this.block = block;
            this.next = next;
        }
    }

    /**
     * Creates a BlockChain that possesses a single block starting with the given initial amount.
     * @param initial the initial balance
     * @throws NoSuchAlgorithmException if the algorithm for block creation is unavailable
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        this.first = new Node(new Block(0, initial, null), null);
        this.last = this.first;
        this.size = 1;
    }

    /**
     * Mines a new candidate block to be added to the list.
     * @param amount the amount to be added in the new block
     * @return the mined block
     * @throws NoSuchAlgorithmException if the algorithm for block creation is unavailable
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block newBlock = new Block(this.size, amount, this.last.block.getHash());
        newBlock.mine();
        return newBlock;
    }

    /**
     * Returns the size of the blockchain.
     * @return the size of the blockchain
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Appends a block to the blockchain.
     * @param blk the block to be added
     * @throws IllegalArgumentException if the block is incompatible with the chain
     */
    public void append(Block blk) {
        if (blk.getPrevHash().equals(this.last.block.getHash()) && blk.getHash().isValid()) {
            if (this.first == null) {
                this.first = new Node(blk, null);
                this.last = this.first;
            } else {
                this.last.next = new Node(blk, null);
                this.last = this.last.next;
            }
            this.size++;
        } else {
            throw new IllegalArgumentException("incompatible block!");
        }
    }

    /**
     * Removes the last block from the chain and returns true. 
     * If the chain only contains a single block, then removeLast does nothing and returns false.
     * @return true if the block was removed, false if no block was removed
     */
    public boolean removeLast() {
        if (this.first == null || this.size == 1) {
            return false;
        } else {
            Node cur = this.first;
            while (cur.next != null && cur.next.next != null) {
                cur = cur.next;
            }
            cur.next = null;
            this.last = cur;
            size--;
            return true;
        }
    }

    /**
     * Returns the hash of the last block in the chain.
     * @return the hash of the last block, or null if the chain is empty
     */
    public Hash getHash() {
        if (this.first == null) {
            return null;
        }
        return this.last.block.getHash();
    }

    /**
     * Validates the blockchain by ensuring that all blocks are consistent and valid.
     * @return true if the blockchain is valid, false otherwise
     */
    public boolean isValidBlockChain() {
        if (this.first == null) {
            return false;
        }

        Node cur = this.first;
        int sum = 0;
        Hash previousHash = null;

        while (cur != null) {
            sum += cur.block.getAmount();

            if (sum < 0) {
                return false;
            }

            if (!cur.block.getHash().isValid()) {
                return false;
            }

            if (previousHash != null && !cur.block.getPrevHash().equals(previousHash)) {
                return false;
            }

            previousHash = cur.block.getHash();
            cur = cur.next;
        }

        return true;
    }

    /**
     * Prints the balances of Alice and Bob based on the transactions in the blockchain.
     */
    public void printBalances() {
        if (this.first == null) {
            System.out.println("Alice: 0, Bob: 0");
        } else {
            int sum = 0;
            int paid = 0;
            Node cur = this.first;

            while (cur != null) {
                int transaction = cur.block.getAmount();
                sum += transaction;

                if (transaction < 0) {
                    paid += -transaction;
                }

                cur = cur.next;
            }

            System.out.println("Alice: " + sum + ", Bob: " + paid);
        }
    }

    /**
     * Returns the string representation of the BlockChain.
     * @return the string representation of the blockchain
     */
    @Override
    public String toString() {
        if (this.first == null) {
            return "The blockchain is empty";
        }

        StringBuilder s = new StringBuilder();
        Node cur = this.first;

        while (cur != null) {
            s.append(cur.block.toString()).append('\n');
            cur = cur.next;
        }

        return s.toString();
    }
}
