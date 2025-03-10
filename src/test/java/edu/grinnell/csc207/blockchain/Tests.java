package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    
    
    @Test
    @DisplayName("Check if newly mined block has a nonce")
    public void validNonce() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(1000);
        Block minedBlock = chain.mine(-300);
        assertEquals(true, minedBlock.getHash().isValid());
        assertEquals(true, minedBlock.getNonce() >= 0);
    }
    
    @Test
    @DisplayName("Valid Blockchain and Append mined nonce")
    public void validBlockchainAppendMined() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(250);
        chain.append(chain.mine(-50));
        assertEquals(true, chain.isValidBlockChain());
        assertEquals(true, chain.getHash().isValid());
        assertEquals(2, chain.getSize());
        assertEquals(-50, chain.last.block.getAmount());
    }
    
    @Test
    @DisplayName("Removing the last block should update size correctly")
    public void removeBlock() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(300);
        chain.append(chain.mine(-100));
        assertEquals(2, chain.getSize());
        chain.removeLast();
        assertEquals(1, chain.getSize());
        assertEquals(true, chain.isValidBlockChain());
    }
    
    @Test
    @DisplayName("Removing last block when only one block exists should return false")
    public void removeInitialBlock() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(100);
        assertEquals(false,chain.removeLast());
        assertEquals(1, chain.getSize());
    }
    
    @Test
    @DisplayName("Check balances after multiple transactions")
    public void balanceCheck() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(500);
        chain.append(chain.mine(-150));
        chain.append(chain.mine(100));
        chain.append(chain.mine(-50));
        assertEquals(true, chain.isValidBlockChain());
        assertEquals(400, chain.first.block.getAmount() + chain.first.next.block.getAmount() +
                chain.first.next.next.block.getAmount() + chain.first.next.next.next.block.getAmount());
    }
    
    
    @Test
    @DisplayName("Appending an invalid block should throw exception")
    public void invalidAppend() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(200);
        Block invalidBlock = new Block(1, -50, new Hash(new byte[32]));
        assertThrows(IllegalArgumentException.class, () -> chain.append(invalidBlock));
    }
    
}
