import models.Block;
import models.Transaction;
import utilities.ObservableList;

public class Blockchain extends ObservableList<Block> {

    TransactionPool<Transaction> TRANSACTION_POOL = new TransactionPool<Transaction>();

    public Blockchain() {
        this.add(new Block(0, Params.DIFFICULTY, TRANSACTION_POOL.TakeAndPurge()));
    }

    public void addBlock(Block block) {
        block.prevHash = this.lastItem().hash;
        block.hash = block.getHash();

        this.add(block);

        adjustDifficulty();
    }

    public long getNewHeight() {
        return this.lastItem().height + 1;
    }

    public boolean isValid(Blockchain bc) {
        if (bc == null) bc = this;
    
        for (int i = 1; i < bc.size(); i++) {
            Block current = bc.get(i);
            Block prevBlock = bc.get(i - 1);
    
            if (!current.prevHash.equals(prevBlock.hash)) {
                System.out.println("Invalid block at height: " + i);
                return false;
            }
        }
        return true;
    }

    public void miningRevardTrx(String rewardAddress) {
        TRANSACTION_POOL.add(new Transaction("Network", rewardAddress, Params.BLOCK_REWARD));
    }

    public void adjustDifficulty() {
        Params.DIFFICULTY += System.currentTimeMillis() - this.lastItem().epochMined < Params.BLOCKTIME ? -1 : 1;
    }
    
    public void addTransaction(Transaction trx) {
        TRANSACTION_POOL.add(trx);
    }

    public String getStringified() {
        String output = "";
        for(Block b : this) {
            output += b.getStringified();
        }
        return output;
    }


    

}
