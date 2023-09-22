package models;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



public class Transaction {
    public String from;
    public String addr;
    public double value;
    public String signature;

    public Transaction(String from, String addr, double value) {
        this.from = from;
        this.addr = addr;
        this.value = value;
        
    }

   public void sign(PrivateKey privateKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Tworzenie instancji obiektu Signature
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSign.initSign(privateKey);

        // Tworzenie wiadomości do podpisania
        String messageToSign = from + addr + value /* + gas */;
        byte[] messageBytes = messageToSign.getBytes("UTF-8");

        // Podpisanie wiadomości
        ecdsaSign.update(messageBytes);
        byte[] signatureBytes = ecdsaSign.sign();

        // Konwersja podpisu na base64 i zapisanie go
        signature = Utility.bytesToHex(signatureBytes);
    }
    //TODO: GAS + https://www.youtube.com/watch?v=ng1GClVAXdo
    
}
