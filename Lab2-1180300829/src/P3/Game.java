package P3;

public class Game {
	private Board myboard;
	private final Action myaction=new Action();
	private final Player playerone=new Player();
	private final Player playertwo=new Player();
	
	// Abstraction function:
    // AF(myboard)=棋盘
	// AF(myaction)=棋盘的所有操作
	// AF(playerone)=第一个棋手
	// AF(playertwo)=第二个棋手
    // Representation invariant:
    // 输入的棋盘类型只能是"chess"或者"go"
    // Safety from rep exposure:
    // 将所有成员设置为private
    // 由于myboard,playerone,playertwo是inmutable，所以在返回时不需要进行defensive copies
	
	/**
	 * 构造方法，初始化棋盘
	 * @param type 待初始化的棋盘种类
	 */
	public Game(String type) {
		if(type.equals("chess")) { //国际象棋
			myboard=new Board("chess");
			//国王
			Piece kinga=new Piece("King0",0);
			Piece kingb=new Piece("King1",1);
			kinga.createnewposition(4, 0);
			kingb.createnewposition(4, 7);
			//皇后
			Piece Queena=new Piece("Queen0",0);
			Piece Queenb=new Piece("Queen1",1);
			Queena.createnewposition(3, 0);
			Queenb.createnewposition(3, 7);
			//战车
			Piece Rooka1=new Piece("Rook0",0);
			Piece Rooka2=new Piece("Rook0",0);
			Piece Rookb1=new Piece("Rook1",1);
			Piece Rookb2=new Piece("Rook1",1);
			Rooka1.createnewposition(0, 0);
			Rooka2.createnewposition(7, 0);
			Rookb1.createnewposition(0, 7);
			Rookb2.createnewposition(7, 7);
			//骑士
			Piece Knighta1=new Piece("Knight0",0);
			Piece Knighta2=new Piece("Knight0",0);
			Piece Knightb1=new Piece("Knight1",1);
			Piece Knightb2=new Piece("Knight1",1);
			Knighta1.createnewposition(1, 0);
			Knighta2.createnewposition(6, 0);
			Knightb1.createnewposition(1, 7);
			Knightb2.createnewposition(6, 7);
			//主教
			Piece Bishopa1=new Piece("Bishop0",0);
			Piece Bishopa2=new Piece("Bishop0",0);
			Piece Bishopb1=new Piece("Bishop1",1);
			Piece Bishopb2=new Piece("Bishop1",1);
			Bishopa1.createnewposition(2, 0);
			Bishopa2.createnewposition(5, 0);
			Bishopb1.createnewposition(2, 7);
			Bishopb2.createnewposition(5, 7);
			//禁卫军
			Piece Pawna1=new Piece("Pawn0",0);
			Piece Pawna2=new Piece("Pawn0",0);
			Piece Pawna3=new Piece("Pawn0",0);
			Piece Pawna4=new Piece("Pawn0",0);
			Piece Pawna5=new Piece("Pawn0",0);
			Piece Pawna6=new Piece("Pawn0",0);
			Piece Pawna7=new Piece("Pawn0",0);
			Piece Pawna8=new Piece("Pawn0",0);
			Piece Pawnb1=new Piece("Pawn1",1);
			Piece Pawnb2=new Piece("Pawn1",1);
			Piece Pawnb3=new Piece("Pawn1",1);
			Piece Pawnb4=new Piece("Pawn1",1);
			Piece Pawnb5=new Piece("Pawn1",1);
			Piece Pawnb6=new Piece("Pawn1",1);
			Piece Pawnb7=new Piece("Pawn1",1);
			Piece Pawnb8=new Piece("Pawn1",1);
			Pawna1.createnewposition(0, 1);
			Pawna2.createnewposition(1, 1);
			Pawna3.createnewposition(2, 1);
			Pawna4.createnewposition(3, 1);
			Pawna5.createnewposition(4, 1);
			Pawna6.createnewposition(5, 1);
			Pawna7.createnewposition(6, 1);
			Pawna8.createnewposition(7, 1);
			Pawnb1.createnewposition(0, 6);
			Pawnb2.createnewposition(1, 6);
			Pawnb3.createnewposition(2, 6);
			Pawnb4.createnewposition(3, 6);
			Pawnb5.createnewposition(4, 6);
			Pawnb6.createnewposition(5, 6);
			Pawnb7.createnewposition(6, 6);
			Pawnb8.createnewposition(7, 6);
			//将所有棋子放置在棋盘上
			myboard.putpiece(kinga);
			myboard.putpiece(kingb);
			
			myboard.putpiece(Queena);
			myboard.putpiece(Queenb);
			
			myboard.putpiece(Rooka1);
			myboard.putpiece(Rooka2);
			myboard.putpiece(Rookb1);
			myboard.putpiece(Rookb2);
			
			myboard.putpiece(Knighta1);
			myboard.putpiece(Knighta2);
			myboard.putpiece(Knightb1);
			myboard.putpiece(Knightb2);
			
			myboard.putpiece(Bishopa1);
			myboard.putpiece(Bishopa2);
			myboard.putpiece(Bishopb1);
			myboard.putpiece(Bishopb2);
			
			myboard.putpiece(Pawna1);
			myboard.putpiece(Pawna2);
			myboard.putpiece(Pawna3);
			myboard.putpiece(Pawna4);
			myboard.putpiece(Pawna5);
			myboard.putpiece(Pawna6);
			myboard.putpiece(Pawna7);
			myboard.putpiece(Pawna8);
			myboard.putpiece(Pawnb1);
			myboard.putpiece(Pawnb2);
			myboard.putpiece(Pawnb3);
			myboard.putpiece(Pawnb4);
			myboard.putpiece(Pawnb5);
			myboard.putpiece(Pawnb6);
			myboard.putpiece(Pawnb7);
			myboard.putpiece(Pawnb8);		
		}
		if(type.equals("go")) {  //围棋
			myboard=new Board("go");
		}
	}
	
	/**
	 * 初始化棋手
	 * @param a 棋手a的名字
	 * @param b 棋手b的名字
	 */
	public void setplayernames(String a,String b) {
		this.playerone.setname(a);
		this.playertwo.setname(b);
		this.playerone.setmyturn(0);
		this.playertwo.setmyturn(1);
		myboard.playerone=a;
		myboard.playertwo=b;
	}
	
	/**
	 * 返回棋盘
	 * @return 棋盘
	 */
	public Board getmyboard() {
		return myboard;
	}

	/**
	 * 返回棋手A
	 * @return 棋手A
	 */
	public Player getplayerone() {
		return playerone;
	}

	/**
	 * 返回棋手B
	 * @return 棋手B
	 */
	public Player getplayertwo() {
		return playertwo;
	}

	/**
	 * 得到当前回合的棋手
	 * @param turn 回合的标识符
	 * @return 当前回合应该行动的棋手
	 */
	public Player getplayer(int myturn) {
		if (myturn == 0) {
			return playerone;
		} else {
			return playertwo;
		}
	}
	
	/**
	 * 得到描述某个位置的状态的字符串 
	 * @param P 待描述位置
	 * @return 描述某个位置状态的字符串
	 */
	public String whethervalidplayer(Position P) {
		return myaction.whethervalidplayer(myboard, P);

	}

	/**
	 * 统计两个棋手含有的棋子数量
	 * @return 两个棋手含有的棋子数量的字符串
	 */
	public String countplayerpiece() {
		return myaction.countplayerpiece(myboard);
	}

	/**
	 * 输出记录操作过程的数组
	 */
	public void printhistory() {
		myaction.printhistory();
	}
	
	/**
	 * 放置棋手某颗棋子在棋盘上
	 * @param player 棋手
	 * @param piece 棋子
	 * @param P 待放置的位置
	 * @return 放置成功返回true ，指定位置超出棋盘范围、指定位置已有棋子、
	 * 该棋子并非属于该棋手返回false
	 */
	public boolean putpiece(Player player, Piece piece, Position P) {
		piece.createnewposition(P.getx(), P.gety());
		return myaction.putpiece(player, myboard, piece);
	}
	
	/**
	 * 移除某位置的棋子(提子)
	 * @param player 棋手自己
	 * @param P 待移除的位置
	 * @return 移除成功返回true ，指定位置超出棋盘范围、所提棋子不是对方棋子、初始位置尚无可移动的棋子返回false
	 */
	public boolean removepiece(Player player, Position P) {
		return myaction.removepiece(player, myboard, P);
	}
	
	/**
	 * 移动棋子
	 * @param player 棋手
	 * @param p1 起始位置
	 * @param p2 终止位置
	 * @return 移动成功返回true，p1位置超出棋盘范围、p2位置超出棋盘范围、目的地已有其他棋子、
	 * 初始位置尚无可移动的棋子、两个位置相同、初始位置的棋子并非该棋手所有返回false
	 */
	public boolean movepiece(Player player, Position p1, Position p2) {
		return myaction.movepiece(player, myboard, p1, p2);
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
		return myaction.eatpiece(player, myboard, p1, p2);
	}

	
	
}
