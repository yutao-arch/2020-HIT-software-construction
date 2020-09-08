package P3;

public class Piece {
	private final String piecename; //棋子的名字
	private Position pieceposition; //棋子的位置
	private final int whohave;  //棋子属于的棋手

	// Abstraction function:
    // AF(piecename)=棋子的名字
	// AF(piecename)=棋子在棋盘的位置
	// AF(whohave)=棋子属于的棋手
    // Representation invariant:
    // 棋子的名字必须是字符串，棋子属于的棋手必须是0或者1
    // Safety from rep exposure:
    // 将piecename,pieceposition,whohave设置为private
    // 由于piecename,pieceposition,whohave是inmutable，所以在返回时不需要进行defensive copies
	
	/**
	 * 初始化构造方法
	 * @param pieceName  棋子的名字
	 * @param owner      棋子的所有者
	 */
	public Piece(String pieceName,int whoHave) {
		this.piecename = pieceName;
		this.whohave=whoHave;
	}
	
	/**
	 * 返回棋子的所有者
	 * @return 棋子的所有者
	 */
	public int getwhohave() {
		return whohave;
	}

	/**
	 * 返回棋子的名字
	 * @return 棋子的名字
	 */
	public String getpiecename() {
		return this.piecename;
	}

	/**
	 * 返回棋子的位置
	 * @return 棋子的位置
	 */
	public Position getpieceposition() {
		return this.pieceposition;
	}
	
	/**
	 * 重新设计新的棋子位置
	 * @param x 棋子新的横坐标
	 * @param y 棋子新的纵坐标
	 */
	public void createnewposition(Integer x,Integer y) {
		this.pieceposition=new Position(x,y);
	}
}
