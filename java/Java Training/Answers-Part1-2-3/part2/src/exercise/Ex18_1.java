package exercise;

public class Ex18_1 {

	public static void main(String[] args) {
		
		var account = new BankAccount("12345678", "田中宏", 1000);
		System.out.println(account);
		account.deposit(500);
		account.withdraw(250);
		System.out.println("残高: " + account.getBalance());
	}

}
