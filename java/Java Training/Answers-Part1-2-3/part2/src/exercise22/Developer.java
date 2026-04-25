package exercise22;

//サブクラス2: 開発者
public class Developer extends Employee {
	private String mainLanguage; // 主な開発言語（追加フィールド）

	public Developer(String name, String department, double salary, String mainLanguage) {
		super(name, department, salary);
		this.mainLanguage = mainLanguage;
	}

	@Override
	public void work() {
		System.out.println(getName() + "さんの部署は" + getDepartment() + "で、" + mainLanguage + "で開発します");
	}

	public String getMainLanguage() {
		return mainLanguage;
	}
}
