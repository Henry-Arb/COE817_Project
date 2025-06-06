package finalproject.client;

import javax.crypto.SecretKey;

/**
 * Client-side account representation with added security fields
 */
public class Account {
    private String username;
    private String password;  // Note: Only used for initial authentication
    private double balance;
    
    // Security fields (transient to exclude from serialization)
    private transient SecretKey encryptionKey;
    private transient SecretKey macKey;
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    
    // Security Key Management
    public void setSessionKeys(SecretKey encryptionKey, SecretKey macKey) {
        this.encryptionKey = encryptionKey;
        this.macKey = macKey;
    }
    
    public SecretKey getEncryptionKey() { 
        if (encryptionKey == null) {
            throw new IllegalStateException("No session keys established");
        }
        return encryptionKey; 
    }
    
    public SecretKey getMacKey() { 
        if (macKey == null) {
            throw new IllegalStateException("No session keys established");
        }
        return macKey; 
    }

    @Override
    public String toString() {
        return username + ":" + password + ":" + balance;
    }

    public static Account fromString(String line) {
        String[] parts = line.split(":");
        return new Account(parts[0], parts[1]);
    }
}