package model;

import java.util.ArrayList;

public class Blockchain {
    public static ArrayList<Block> chain = new ArrayList<>();

    public static void addBlock(String data) {
        String previousHash = chain.size() == 0 ? "0" : chain.get(chain.size()-1).hash;
        Block newBlock = new Block(data, previousHash);
        chain.add(newBlock);
    }

    public static boolean isChainValid() {
        for(int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i-1);

            if(!current.hash.equals(current.calculateHash())) return false;
            if(!current.previousHash.equals(previous.hash)) return false;
        }
        return true;
    }
}
