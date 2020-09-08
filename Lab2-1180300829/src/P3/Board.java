package P3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Board {
	private int sizeofboard;
	private final Set<Piece> myboard=new HashSet<>(); 
	public String playerone;
	public String playertwo;
	
	// Abstraction function:
    // AF(sizeofboard)=棋盘的宽度
	// AF(myboard)=棋盘的所有棋子
	// AF(playerone)=第一个棋手名字
	// AF(playertwo)=第二个棋手名字
    // Representation invariant:
    // 棋子的名字必须是字符串
    // Safety from rep exposure:
    // 将sizeofboard,myboard设置为private
    // 由于myboard是mutable，所以在返回时需要进行defensive copies
	

	/**
	 * 初始化构造方法，设置棋盘大小
	 *  @param type 棋盘类型
	 */
	public Board(String type) {
		if (type.equals("chess")) {
			sizeofboard = 7; 
		} 
		if (type.equals("go")) {
			sizeofboard = 18;
		}
	}
	
	/**
	 * 返回myboard
	 * @return myboard
	 */
	public Set<Piece> getmyboard() {
		return new HashSet<Piece>(myboard);
	}
	
	/**
	 * 判断某位置P是否越界
	 * @param P 要判断的位置
	 * @return 未越界返回true 否则 false
	 */
	public boolean whethervalidposition(Position P) {
		if (P.getx() < 0 || P.gety() < 0 || P.getx() > sizeofboard || P.gety() > sizeofboard) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 得到描述某个位置的状态的字符串 
	 * @param P 待描述位置
	 * @return 描述某个位置状态的字符串
	 */
	public String whethervalidplayer(Position P) {
		if (!whethervalidposition(P)) {
			return "非法操作：指定位置超出棋盘范围\n";
		}
		Iterator<Piece> temp = myboard.iterator();
		while (temp.hasNext()) {
			Piece a = temp.next();
			if (a.getpieceposition().getx() == P.getx() && a.getpieceposition().gety() == P.gety()) {
				if (a.getwhohave() == 0) {
					return "此位置已被棋手" + playerone + "的棋子：" + a.getpiecename()+ "占用\n";
				} else {
					return "此位置已被棋手" + playertwo + "的棋子：" + a.getpiecename() + "占用\n";
				}
			}
		}
		return "该位置空闲\n";
	}
	
	/**
	 * 统计两个棋手含有的棋子数量
	 * @return 两个棋手含有的棋子数量的字符串
	 */
	public String countplayerpiece() {
		Iterator<Piece> temp = myboard.iterator();
		int countone = 0;
		int counttwo = 0;
		while (temp.hasNext()) {
			Piece a = temp.next();
			if (a.getwhohave() == 0) {
				countone++;
			}
			if (a.getwhohave() == 1) {
				counttwo++;
			}
		}
		return playerone + "拥有" + countone + "个棋子\n" + playertwo + "拥有" + counttwo + "个棋子\n";
	}

	
	/**
	 * 检查某个位置是否是空闲
	 * @param P 待描述位置
	 * @return 该位置空闲则返回true，不是空闲返回false
	 */
	public boolean whetherempty(Position P) {
		Iterator<Piece> temp = myboard.iterator();
		while (temp.hasNext()) {
			Piece a = temp.next();
			if (a.getpieceposition().getx() == P.getx() && a.getpieceposition().gety() == P.gety()) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * 放置某颗棋子在棋盘上
	 * @param mypiece 待放置的棋子
	 * @return 放置成功返回true ，指定位置超出棋盘范围、指定位置已有棋子返回false
	 */
	public boolean putpiece(Piece mypiece) {
		Position temp=mypiece.getpieceposition();
		int x = temp.getx();
		int y = temp.gety();
		if(x<0||x>sizeofboard||y<0||y>sizeofboard) {
			System.out.println("非法操作：指定位置超出棋盘范围\n");
			return false;
		}
		Iterator<Piece> tempone=myboard.iterator();
		while(tempone.hasNext()) {
			Piece a=tempone.next();
			if(a.getpieceposition().getx()==x&&a.getpieceposition().gety()==y) {
				System.out.println("非法操作：指定位置已有棋子\n");
				return false;
			}
		}
		return myboard.add(mypiece);
	}
	
	/**
	 * 移除某位置的棋子(提子)
	 * @param P 待移除的位置
	 * @param m 棋手自己
	 * @return 移除成功返回true ，指定位置超出棋盘范围、所提棋子不是对方棋子、初始位置尚无可移动的棋子返回false
	 */
	public boolean removepiece(Position P,Player m) {
		if(!whethervalidposition(P)) {
			System.out.println("非法操作：指定位置超出棋盘范围\n");
			return false;
		}
		Iterator<Piece> tempone = myboard.iterator();
		while (tempone.hasNext()) {
			Piece a = tempone.next();
			if (a.getpieceposition().getx() == P.getx() && a.getpieceposition().gety() == P.gety()) {
				if(m.getmyturn()==a.getwhohave()) {
					System.out.println("非法操作：所提棋子不是对方棋子\n");
					return false;
				}
				tempone.remove();
				System.out.println("该位置点已被移除成功\n");
				return true;
			}
		}
		System.out.println("非法操作：该位置无棋子可提\n");
		return false;
	}
	
	/**
	 * 移动棋子
	 * @param player 棋手
	 * @param p1     起始点
	 * @param p2     终止点
	 * @return 移动成功返回true，p1位置超出棋盘范围、p2位置超出棋盘范围、目的地已有其他棋子、
	 * 初始位置尚无可移动的棋子、两个位置相同、初始位置的棋子并非该棋手所有返回false
	 */
	public boolean movepiece(Player player,Position p1,Position p2) {
		if(!whethervalidposition(p1)) {
			System.out.println("非法操作：p1位置超出棋盘范围\n");
			return false;
		}
		if(!whethervalidposition(p2)) {
			System.out.println("非法操作：p2位置超出棋盘范围\n");
			return false;
		}
		if(!whetherempty(p2)&&!(p1.equals(p2))) {
			System.out.println("非法操作：目的地已有其他棋子\n");
			return false;
		}
		if(p1.equals(p2)) {
			System.out.println("非法操作：两个位置相同\n");
			return false;
		}
		Iterator<Piece> temp= myboard.iterator();
		while (temp.hasNext()) {
			Piece a = temp.next();
			if (a.getpieceposition().getx() == p1.getx() && a.getpieceposition().gety() == p1.gety()) {
				if (a.getwhohave() != player.getmyturn()) {
					System.out.println("非法操作：初始位置的棋子并非该棋手所有\n");
					return false;
				} else {
					a.createnewposition(p2.getx(),p2.gety());
					System.out.println("棋子移动成功\n");
					return true;
				}
			}
		}
		System.out.println("非法操作：初始位置尚无可移动的棋子\n");
		return false;
	}
	
	
	/**
	 * 吃子
	 * @param player 棋手
	 * @param p1 第一个位置
	 * @param p2 第二个位置
	 * @return 吃子成功返回true，p1位置超出棋盘范围、p2位置超出棋盘范围、两个位置相同、
	 * 第二个位置上的棋子不是对方棋子、第一个位置上的棋子不是自己棋子、第一个位置上无棋子、
	 * 第二位置上无棋子返回false
	 */
	public boolean eatpiece(Player player, Position p1, Position p2) {
		Piece from=null;
		Piece to=null;
		if(!whethervalidposition(p1)) {
			System.out.println("非法操作：p1位置超出棋盘范围\n");
			return false;
		}
		if(!whethervalidposition(p2)) {
			System.out.println("非法操作：p2位置超出棋盘范围\n");
			return false;
		}
		if(p1.equals(p2)) {
			System.out.println("非法操作：两个位置相同\n");
			return false;
		}
		Iterator<Piece> temp= myboard.iterator();
		while (temp.hasNext()) {
			Piece a = temp.next();
			if (a.getpieceposition().getx() == p2.getx() && a.getpieceposition().gety() == p2.gety()) {
				if (a.getwhohave() == player.getmyturn()) {
					System.out.println("非法操作：第二个位置上的棋子不是对方棋子\n");
					return false;
				} else {
					to=a;
				}
			}
			if (a.getpieceposition().getx() == p1.getx() && a.getpieceposition().gety() == p1.gety()) {
				if (a.getwhohave() != player.getmyturn()) {
					System.out.println("非法操作：第一个位置上的棋子不是自己棋子\n");
					return false;
				} else {
					from=a;
				}
			}
		}
		if (from == null) {
			System.out.println("非法操作：第一个位置上无棋子\n");
			return false;
		}
		if (to == null) {
			System.out.println("非法操作：第二位置上无棋子\n");
			return false;
		}
		myboard.remove(to);
		from.createnewposition(p2.getx(),p2.gety());
		System.out.println("吃子成功\n");
		return true;
	}
	
}
