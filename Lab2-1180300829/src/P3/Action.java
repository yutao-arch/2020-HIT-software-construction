package P3;

import java.util.ArrayList;


public class Action {
	private final ArrayList<String> allhistory=new ArrayList<>();
	int counthistory=0;

	// Abstraction function:
    // AF(allhistory)=走棋历史
	// AF(counthistory)=走棋的次数
    // Safety from rep exposure:
    // 将 allhistory设置为private
	
	/**
	 * 得到描述某个位置的状态的字符串 
	 * @param board 棋盘
	 * @param position 待描述位置
	 * @return 描述某个位置状态的字符串
	 */
	public String whethervalidplayer(Board board, Position position) {
		return board.whethervalidplayer(position);
	}
	
	/**
	 *  统计两个棋手含有的棋子数量
	 * @param board 操作的棋盘
	 * @return 两个棋手含有的棋子数量的字符串
	 */
	public String countplayerpiece(Board board) {
		return board.countplayerpiece();
	}
	
	/**
	 * 输出记录操作过程的数组
	 */
	public void printhistory() {
		for (String m : allhistory) {
			System.out.println(m);
		}
	}
	
	/**
	 * 放置棋手某颗棋子在棋盘上
	 * @param piece 待放置的棋子
	 * @param board 棋盘
	 * @param player 棋手
	 * @return 放置成功返回true ，指定位置超出棋盘范围、指定位置已有棋子、
	 * 该棋子并非属于该棋手，所指定的棋子已经在棋盘上返回false
	 */
	public boolean putpiece(Player player, Board board, Piece piece) {
		if(piece.getwhohave()!=player.getmyturn()) {
			System.out.println("非法操作：该棋子并非属于该棋手\n");
			return false;
		}
		if(board.getmyboard().contains(piece)) {
			System.out.println("非法操作：所指定的棋子已经在棋盘上\n");
			return false;
		}
		boolean flag=board.putpiece(piece);
		if(flag) {
			allhistory.add(counthistory + "." + player.getname() + "加入了一个棋子" + piece.getpiecename() + "，坐标为 ("
					+ piece.getpieceposition().getx() + "," + piece.getpieceposition().gety() + ")\n");
			counthistory++;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 移除某位置的棋子(提子)
	 * @param position 待移除的位置
	 * @param board 棋盘
	 * @param player 棋手
	 * @return 移除成功返回true ，指定位置超出棋盘范围、所提棋子不是对方棋子、初始位置尚无可移动的棋子返回false
	 */
	public boolean removepiece(Player player, Board board, Position position) {
		boolean flag=board.removepiece(position,player);
		if(flag) {
			allhistory.add(counthistory + "." + player.getname() + "移除了一个棋子(提子)"  + "，坐标为 ("
					+ position.getx() + "," + position.gety() + ")\n");
			counthistory++;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 移动棋子
	 * @param player 棋手
	 * @param board 棋盘
	 * @param p1 起始位置
	 * @param p2 终止位置
	 * @return 移动成功返回true，p1位置超出棋盘范围、p2位置超出棋盘范围、目的地已有其他棋子、
	 * 初始位置尚无可移动的棋子、两个位置相同、初始位置的棋子并非该棋手所有返回false
	 */
	public boolean movepiece(Player player, Board board, Position p1, Position p2) {
		boolean flag = board.movepiece(player, p1, p2);
		if (flag) {
			allhistory.add(counthistory + "." + player.getname() + " 移动了一个棋子位置从(" + p1.getx() + ","
					+ p1.gety() + ")到(" + p2.getx() + "," + p2.gety() + ")\n");
			counthistory++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 吃子
	 * @param player 棋手
	 * @param board 棋盘
	 * @param p1 第一个位置
	 * @param p2 第二个位置
	 * @return 吃子成功返回true，p1位置超出棋盘范围、p2位置超出棋盘范围、两个位置相同、
	 * 第二个位置上的棋子不是对方棋子、第一个位置上的棋子不是自己棋子、第一个位置上无棋子、
	 * 第二位置上无棋子返回false
	 */
	public boolean eatpiece(Player player, Board board, Position p1, Position p2) {
		boolean flag = board.eatpiece(player, p1, p2);
		if (flag) {
			allhistory.add(counthistory + "." + player.getname() + " 从一个位置(" + p1.getx() + ","
					+ p1.gety() + ")吃掉了(" + p2.getx() + "," + p2.gety() + ")位置的棋子\n");
			counthistory++;
			return true;
		} else {
			return false;
		}
	}
	
	
}
