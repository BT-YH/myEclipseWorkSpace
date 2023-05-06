
public class BankAccountTest {
	public static void main(String[] args) {
		BankAccount bal1 = new BankAccount(100);
		BankAccount bal2 = new BankAccount(1000);
		
		System.out.println(bal1 + " " + bal2);
		System.out.println(bal1.getBalance() + " " + bal2.getBalance());
		
		bal1.deposit(2002);
		System.out.println(bal1.getBalance() + " " + bal2.getBalance());
	}
}
