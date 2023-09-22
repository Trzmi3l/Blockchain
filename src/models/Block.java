package models;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Block {
    public long height;
    public String hash;
    public String prevHash;
    public long epochCreated;
    public long epochMined;
    public long difficulty;
    public long nonce;

    public List<Transaction> transactions;

    

    public Block(long height, long difficulty, List<Transaction> transactions) {
        this.height = height;
        this.epochCreated = System.currentTimeMillis();
        this.difficulty = difficulty;
        this.transactions = transactions;
        this.hash = this.getHash();
        this.prevHash = "";
        
    }
    // for initial
    public Block(long height, long difficulty, List<Transaction> transactions, String hash) {
        this.height = height;
        this.epochCreated = System.currentTimeMillis();
        this.difficulty = difficulty;
        this.transactions = transactions;
        
    }

    

    public String getHash() {
        Gson s = new Gson();
        return Utility.hashSHA256(s.toJson(this));
    }


    // Mainly for debug 
    public String getStringified() {
        Gson s = new GsonBuilder().setPrettyPrinting().create();
        return s.toJson(this);
    }

}
