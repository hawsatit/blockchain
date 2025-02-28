package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 1){
            System.out.println("invalid input! Takes in one arguement which is the initial balance!");
        } else {
            int bal = Integer.parseInt(args[0]);
            BlockChain chain = new BlockChain(bal);
            
            boolean running = true;
            while(running){
                System.out.println(chain.toString());
                Scanner scanner = new Scanner(System.in);
                System.out.print("Command? ");
                String input = scanner.nextLine();
                
                if (null != input)switch (input) {
                    case "mine" -> {
                        System.out.print("Ammount Transfered? ");
                        int amm = Integer.parseInt(scanner.nextLine());
                        long nonce = chain.mine(amm).getNonce();
                        System.out.println("ammount = " + amm + ", nonce = " + nonce);
                        }
                    case "append" -> {
                        System.out.print("Ammount Transfered? ");
                        int amm = Integer.parseInt(scanner.nextLine());
                        System.out.print("Ammount Transfered? ");
                        long nonce = Long.parseLong(scanner.nextLine());
                        chain.append(new Block(chain.getSize() + 1, amm, chain.last.block.getHash(), nonce));
                        }
                    case "remove" -> chain.removeLast();
                    case "check" -> {
                        if (chain.isValidBlockChain()){
                            System.out.println("Chain is valid!");
                        } else {
                            System.out.println("Chain is invalid!");
                        }
                    }
                    case "report" -> chain.printBalances();
                    case "help" -> {
                        System.out.println("Valid commands:");
                        System.out.println('\t' + "mine: discovers the nonce for a given transaction");
                        System.out.println('\t' + "append: appends a new block onto the end of the chain");
                        System.out.println('\t' + "remove: removes the last block from the end of the chain");
                        System.out.println('\t' + "report: reports the balances of Alice and Bob");
                        System.out.println('\t' + "help: prints this list of commands");
                        System.out.println('\t' + "quit: quits the program");
                    }
                    case "quit" -> running = false;
                    default -> {
                    }
                }
            }
        }
    }  
}
