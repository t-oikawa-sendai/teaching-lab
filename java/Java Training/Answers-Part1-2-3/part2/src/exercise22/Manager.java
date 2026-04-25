package exercise22;

//サブクラス1: マネージャー
public class Manager extends Employee {
	private int teamSize; // チームの人数（追加フィールド）

	public Manager(String name, String department, double salary, int teamSize) {
		super(name, department, salary);
		this.teamSize = teamSize;
	}

	@Override
	public void work() {
		System.out.println(getName() + "さんの部署は" + getDepartment() + "で、" + teamSize + "人のチームです");
	}

	public int getTeamSize() {
		return teamSize;
	}
}
