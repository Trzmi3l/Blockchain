import models.Block;

public class App {
    public static void main(String[] args) throws Exception {
        
        Blockchain BLOCKCHAIN = new Blockchain();
        BLOCKCHAIN.addBlock(new Block(BLOCKCHAIN.getNewHeight(), Params.DIFFICULTY, null));
        BLOCKCHAIN.addBlock(new Block(BLOCKCHAIN.getNewHeight(), Params.DIFFICULTY, null));
        BLOCKCHAIN.addBlock(new Block(BLOCKCHAIN.getNewHeight(), Params.DIFFICULTY, null));
        BLOCKCHAIN.addBlock(new Block(BLOCKCHAIN.getNewHeight(), Params.DIFFICULTY, null));

            System.out.println(BLOCKCHAIN.getStringified());
           // System.out.println(BLOCKCHAIN.isValid(BLOCKCHAIN));
            
    }













}
