package P3;

public class Player {
	private String name; //棋手姓名
	private Integer myturn; //棋手出手顺序

	// Abstraction function:
    // AF(name)=棋手姓名
	// AF(myturn)=棋手出手顺序
    // Representation invariant:
    // 棋手姓名必须是字符串，出手顺序必须是0或者1
    // Safety from rep exposure:
    // 将name,myturn设置为private
    // 由于name,myturn是inmutable，所以在返回时不需要进行defensive copies
	
	/**
	 * 设置棋手出手顺序
	 * @param turn 棋手出手顺序
	 */
	public void setmyturn(Integer turn) {
		this.myturn = turn;
	}

	/**
	 * 设置棋手名字
	 * @param playerName 棋手名字
	 */
	public void setname(String Name) {
		this.name = Name;
	}
	
	/**
	 * 返回棋手出手顺序
	 * @return 棋手出手顺序
	 */
	public Integer getmyturn() {
		return myturn;
	}
	
	/**
	 * 返回棋手名字
	 * @return 棋手名字
	 */
	public String getname() {
		return name;
	}

	
}
