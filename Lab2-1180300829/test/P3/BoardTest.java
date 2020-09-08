package P3;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class BoardTest {

	/* Testing strategy
	 * 测试putpiece方法，在建Board时两种等价类象棋和围棋可以只测试一种即可
     * 按照加入棋子的坐标是否越界划分：坐标越界，坐标不越界
     * 按照加入棋子的坐标是否已经存在棋子划分：指定位置已有棋子，指定位置没有棋子
     * 覆盖每个取值如下：
     */
	@Test
	public void testPutpiece() {
		Board m=new Board("chess");
		Piece a=new Piece("yutao",0);
		Piece b=new Piece("yutao",0);
		Piece c=new Piece("yutao",0);
		a.createnewposition(1, 1);
		b.createnewposition(99, 99);
		c.createnewposition(1, 1);
		assertEquals(true,m.putpiece(a));
		assertEquals(false,m.putpiece(b));
		assertEquals(false,m.putpiece(c));
	}
	
	/* Testing strategy
	 * 测试removepiece方法，在建Board时两种等价类只需测试围棋即可
     * 按照待移除的坐标是否越界划分：坐标越界，坐标不越界
     * 按照待移除棋子的坐标划分：指定位置超出棋盘范围，所提棋子不是对方棋子，初始位置尚无可移动的棋子，移除成功
     * 按照棋手划分：第一个棋手移除第二个人的棋子，第二个人移除第一个人的棋子
     * 覆盖每个取值如下：
     */
	@Test
	public void testRemovepiece() {
		Board m=new Board("go");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		a.createnewposition(1, 1);
		b.createnewposition(2, 2);
		Player num1=new Player();
		Player num2=new Player();
		num1.setname("haha");
		num2.setname("lala");
		num1.setmyturn(0);
		num2.setmyturn(1);
		m.putpiece(a);
		m.putpiece(b);
		Position temp=new Position(99,99);
		Position temp1=new Position(4,4);
		assertEquals(false,m.removepiece(a.getpieceposition(),num1));
		assertEquals(false,m.removepiece(temp,num1));
		assertEquals(false,m.removepiece(temp1,num1));
		assertEquals(true,m.removepiece(b.getpieceposition(),num1));
		assertEquals(true,m.removepiece(a.getpieceposition(),num2));
	}

	/* Testing strategy
	 * 测试movepiece方法，在建Board时两种等价类象棋和围棋可以只测试一种即可
     * 按照移动前后棋子的坐标划分：p1位置超出棋盘范围,p2位置超出棋盘范围,目的地已有其他棋子,
	 * 初始位置尚无可移动的棋子,两个位置相同,初始位置的棋子并非该棋手,移动成功
     * 覆盖每个取值如下：
     */
	@Test
	public void testMovepiece() {
		Board m=new Board("chess");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		a.createnewposition(1, 1);
		b.createnewposition(2, 2);
		Player num1=new Player();
		Player num2=new Player();
		num1.setname("haha");
		num2.setname("lala");
		num1.setmyturn(0);
		num2.setmyturn(1);
		m.putpiece(a);
		m.putpiece(b);
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		Position temp3=new Position(3,3);
		Position temp4=new Position(4,4);
		assertEquals(false,m.movepiece(num1,temp,temp3));
		assertEquals(false,m.movepiece(num1,temp1,temp));
		assertEquals(false,m.movepiece(num1,temp1,temp2));
		assertEquals(false,m.movepiece(num1,temp3,temp4));
		assertEquals(false,m.movepiece(num1,temp1,temp1));
		assertEquals(false,m.movepiece(num1,temp2,temp3));
		assertEquals(true,m.movepiece(num1,temp1,temp3));	
	}
	
	/* Testing strategy
	 * 测试eatpiece方法，在建Board时两种等价类只需测试国际象棋即可
     * 按照吃子前后棋子的坐标划分：p1位置超出棋盘范围，p2位置超出棋盘范围，两个位置相同，
	 * 第二个位置上的棋子不是对方棋子，第一个位置上的棋子不是自己棋子，第一个位置上无棋子，
	 * 第二位置上无棋子，吃子成功
     * 覆盖每个取值如下：
     */
	@Test
	public void testEatpiece() {
		Board m=new Board("chess");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Piece c=new Piece("white",0);
		Piece d=new Piece("black",1);
		a.createnewposition(1, 1);
		b.createnewposition(2, 2);
		c.createnewposition(3, 3);
		d.createnewposition(4, 4);
		Player num1=new Player();
		Player num2=new Player();
		num1.setname("haha");
		num2.setname("lala");
		num1.setmyturn(0);
		num2.setmyturn(1);
		m.putpiece(a);
		m.putpiece(b);
		m.putpiece(c);
		m.putpiece(d);
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		Position temp3=new Position(3,3);
		Position temp4=new Position(4,4);
		Position temp5=new Position(5,5);
		Set<Piece> myboard=m.getmyboard();
		assertEquals(false,m.eatpiece(num1,temp,temp2));
		assertEquals(false,m.eatpiece(num1,temp1,temp));
		assertEquals(false,m.eatpiece(num1,temp1,temp1));
		assertEquals(false,m.eatpiece(num1,temp1,temp3));
		assertEquals(false,m.eatpiece(num1,temp2,temp4));
		assertEquals(false,m.eatpiece(num1,temp5,temp4));
		assertEquals(false,m.eatpiece(num1,temp1,temp5));
		assertEquals(true,m.eatpiece(num1,temp1,temp2));
	}
	
	/* Testing strategy
	 * 测试whethervalidplayer方法
     * 按照棋子的坐标划分：指定位置超出棋盘范围,此位置已经第一个棋手的棋子：white占用,此位置已经被第二个棋手的棋子：black占用，该位置空闲
     * 覆盖每个取值如下：
     */
	@Test
	public void testWhethervalidplayer() {
		Board m=new Board("chess");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		a.createnewposition(1, 1);
		b.createnewposition(2, 2);
		Player num1=new Player();
		Player num2=new Player();
		num1.setname("haha");
		num2.setname("lala");
		num1.setmyturn(0);
		num2.setmyturn(1);
		m.putpiece(a);
		m.putpiece(b);
		Position temp=new Position(1,1);
		Position temp1=new Position(2,2);
		Position temp2=new Position(3,3);
		Position temp3=new Position(99,99);
		assertEquals("非法操作：指定位置超出棋盘范围\n",m.whethervalidplayer(temp3));
		assertEquals("此位置已被棋手null的棋子：white占用\n",m.whethervalidplayer(temp));
		assertEquals("此位置已被棋手null的棋子：black占用\n",m.whethervalidplayer(temp1));
		assertEquals("该位置空闲\n",m.whethervalidplayer(temp2));
	}
	
	/* Testing strategy
	 * 测试countplayerpiece方法
     * 测试棋子数的返回字符串即可
     */
	@Test
	public void testCountplayerpiece() {
		Board m=new Board("chess");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		a.createnewposition(1, 1);
		b.createnewposition(2, 2);
		Player num1=new Player();
		Player num2=new Player();
		num1.setname("haha");
		num2.setname("lala");
		num1.setmyturn(0);
		num2.setmyturn(1);
		m.putpiece(a);
		m.putpiece(b);
		assertEquals("null拥有1个棋子\n" + "null拥有1个棋子\n",m.countplayerpiece());
	}
}
