package exercise;

import java.util.Objects;

public class BankAccount {
    private String number; 			// 口座番号
    private String name;   			// 口座名義
    private double balance;         // 残高

    public BankAccount(String number, String name, double balance) {
		this.number = number;
		this.name = name;
		this.balance = balance;
	}
    
    public BankAccount() {}
    	
    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BankAccount [number=" + number + ", name=" + name + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, name, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(name, other.name) && Objects.equals(number, other.number);
	}

 
}

