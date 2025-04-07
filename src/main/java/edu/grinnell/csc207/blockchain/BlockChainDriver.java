package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the blockchain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException if an algorithm is not found during mining
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Check for correct input
        if (args.length != 1) {
            System.out.println("Invalid input! Takes in one argument which is the initial balance!");
        } else {
            // Initialize blockchain with the given balance
            int balance = Integer.parseInt(args[0]);
            BlockChain chain = new BlockChain(balance);

            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            // Main loop for processing user commands
            while (running) {
                System.out.println();
                System.out.println(chain.toString());
                System.out.print("Command? ");
                String input = scanner.nextLine();

                if (input != null) {
                    switch (input) {
                        case "mine":
                            System.out.print("Amount Transferred? ");
                            int amount = Integer.parseInt(scanner.nextLine());
                            long nonce = chain.mine(amount).getNonce();
                            System.out.println("Amount = " + amount + ", Nonce = " + nonce);
                            break;

                        case "append":
                            System.out.print("Amount Transferred? ");
                            amount = Integer.parseInt(scanner.nextLine());
                            System.out.print("Nonce? ");
                            nonce = Long.parseLong(scanner.nextLine());
                            Block newBlock = new Block(chain.getSize(), amount, chain.last.block.getHash(), nonce);
                            newBlock.mine();
                            chain.append(newBlock);
                            break;

                        case "remove":
                            chain.removeLast();
                            break;

                        case "check":
                            if (chain.isValidBlockChain()) {
                                System.out.println("Chain is valid!");
                            } else {
                                System.out.println("Chain is invalid!");
                            }
                            break;

                        case "report":
                            chain.printBalances();
                            break;

                        case "help":
                            printHelp();
                            break;

                        case "quit":
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid command. Type 'help' for a list of valid commands.");
                            break;
                    }
                }
            }

            scanner.close();
        }
    }

    /**
     * Prints a list of valid commands to the user.
     */
    private static void printHelp() {
        System.out.println("Valid commands:");
        System.out.println("\tmine: discovers the nonce for a given transaction");
        System.out.println("\tappend: appends a new block onto the end of the chain");
        System.out.println("\tremove: removes the last block from the end of the chain");
        System.out.println("\treport: reports the balances of Alice and Bob");
        System.out.println("\thelp: prints this list of commands");
        System.out.println("\tquit: quits the program");
    }
}
