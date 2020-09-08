package P3;

public class Position {
	private int x;  //棋子的横坐标
	private int y;  //棋子的纵坐标
	
	// Abstraction function:
    // AF(x)=棋子横坐标
	// AF(y)=棋子纵坐标
    // Representation invariant:
    // 坐标必须是整数
    // Safety from rep exposure:
    // 将x,y设置为private
    // 由于x,y是inmutable，所以在返回时不需要进行defensive copies
	
	/**
	 * 初始化构造方法
	 * @param ix x坐标
	 * @param iy y坐标
	 */
	public Position(int ix,int iy) {
		this.x=ix;
		this.y=iy;
	}
	
	/**
	 * 返回当前点的横坐标
	 * @return 横坐标x
	 */
	public int getx() {
		return x;
	}
	
	/**
	 * 返回当前点的纵坐标
	 * @return 纵坐标y
	 */
	public int gety() {
		return y;
	}
	
	/**
	 * 重写euqals，判断两个Position是否相等
	 * @param m 待判断位置
	 * @return 是否相等的判断
	 */
	public boolean equals(Position m) {
		if(m.x==this.x&&m.y==this.y) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
