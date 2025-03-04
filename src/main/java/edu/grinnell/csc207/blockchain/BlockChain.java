package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    Node first;
    Node last;
    int size;

    /**
     * The node object used for the BlockChain
     */
    public class Node {

        Block block;
        Node next;

        public Node(Block block, Node next) {
            this.block = block;
            this.next = next;
        }
    }

    /**
     * creates a BlockChain that possess a single block the starts with the
     * given initial amount.
     *
     * @param initial the initial balance
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        this.first = new Node(new Block(0, initial, null), null);
        this.last = this.first;
        this.size = 1;
    }

    /**
     *
     * @param amount
     * @return mines a new candidate block to be added to the list.
     * @throws NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block newBlock = new Block(this.size, amount, this.last.block.getHash());
        newBlock.mine();
        return newBlock;
    }

    /**
     *
     * @return the size of the blockchain
     */
    public int getSize() {
        return this.size;
    }

    public void append(Block blk) {
        if (blk.getPrevHash().equals(this.last.block.getHash()) && blk.getHash().isValid()){
            if (this.first == null) {
                this.first = new Node(blk, null);
                this.last = this.first;
            } else {
                this.last.next = new Node(blk, null);
                this.last = this.last.next;
            }
            this.size++;
        } else {
            throw new IllegalArgumentException("incompatible  block!");
        }

    }
    
    /**
     * removes the last block from the chain, returning true. If the chain only contains a single block, 
     * then removeLast does nothing and returns false.
     * @return 
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
     * 
     * @return the hash of the last block in the chain.
     */
    public Hash getHash() {
        if (this.first == null) {
            return null;
        }

        return this.last.block.getHash();
    }

    /**
     * walks the blockchain and ensures that its blocks are consistent and valid.
     * @return true if valid
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
     * prints Alice's and Bob's respective balances
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
     * 
     * @return  string representation of the BlockChain
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
