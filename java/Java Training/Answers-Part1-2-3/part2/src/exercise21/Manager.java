package exercise21;

//サブクラス1: マネージャー
public class Manager extends Employee {
	private int teamSize; // チームの人数（追加フィールド）

	public Manager(String name, String department, double salary, int teamSize) {
		super(name, department, salary);
		this.teamSize = teamSize;
	}

	public int getTeamSize() {
		return teamSize;
	}
}
