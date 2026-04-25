package exercise21;

public class Ex21_2 {

	public static void main(String[] args) {
		
		var manager = new Manager("田中宏", "営業", 500000, 6);
		System.out.println(manager.getName());
		System.out.println(manager.getDepartment());
		System.out.println(manager.getSalary());
		System.out.println(manager.getTeamSize());
		manager.work();
	}

}
