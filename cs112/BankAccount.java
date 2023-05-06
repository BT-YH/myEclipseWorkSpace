
public class BankAccount {
	
	// 1. Define your fields.
	private int balance;
	
	// 2. Define your constructors.
	public BankAccount() { 
		// This is the "default constructor".
	}
	
	public BankAccount(int balance) {
		this.balance = balance;
	}
	
	// 3. Create (override) a toString methods
	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
	
	// 4. Create setters and/or getters
	public int getBalance() {
		return balance;
	}

	// 5. Create and test other methods incrementally
	public void deposit(int amount) {
		balance += amount;
	}
	
	
}
