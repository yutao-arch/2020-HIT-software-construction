package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	/* Testing strategy
	 * 测试putpiece方法，在建Board时两种等价类象棋和围棋可以只测试一种即可
     * 按照加入棋子的坐标是否越界划分：坐标越界，坐标不越界
     * 按照加入棋子的坐标是否已经存在棋子划分：指定位置已有棋子，指定位置没有棋子
     * 按照棋子划分：该棋子并非属于该棋手，所指定的棋子已经在棋盘上
     * 覆盖每个取值如下：
     */
	@Test
	public void testPutpiece() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		assertEquals(false,pp.putpiece(pp.getplayer(0),b,temp1));
		assertEquals(false,pp.putpiece(pp.getplayer(0),a,temp));
		assertEquals(true,pp.putpiece(pp.getplayer(0),a,temp1));
		assertEquals(false,pp.putpiece(pp.getplayer(1),b,temp1));
		assertEquals(false,pp.putpiece(pp.getplayer(1),b,temp1));
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
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		pp.putpiece(pp.getplayer(0),a,temp1);
		pp.putpiece(pp.getplayer(1),b,temp2);
		assertEquals(false,pp.removepiece(pp.getplayer(0),a.getpieceposition()));
		assertEquals(false,pp.removepiece(pp.getplayer(0),temp));
		assertEquals(false,pp.removepiece(pp.getplayer(0),temp1));
		assertEquals(true,pp.removepiece(pp.getplayer(0),b.getpieceposition()));
		assertEquals(true,pp.removepiece(pp.getplayer(1),a.getpieceposition()));
	}

	/* Testing strategy
	 * 测试movepiece方法，在建Board时两种等价类象棋和围棋可以只测试一种即可
     * 按照移动前后棋子的坐标划分：p1位置超出棋盘范围,p2位置超出棋盘范围,目的地已有其他棋子,
	 * 初始位置尚无可移动的棋子,两个位置相同,初始位置的棋子并非该棋手,移动成功
     * 覆盖每个取值如下：
     */
	@Test
	public void testMovepiece() {
		Game pp=new Game("chess");
		pp.setplayernames("haha", "lala");
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		Position temp3=new Position(3,3);
		Position temp4=new Position(4,4);
		Position temp5=new Position(7,7);
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp,temp3));
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp1,temp));
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp1,temp5));
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp3,temp4));
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp1,temp1));
		assertEquals(false,pp.movepiece(pp.getplayer(0),temp5,temp3));
		assertEquals(true,pp.movepiece(pp.getplayer(0),temp1,temp3));	
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
		Game pp=new Game("chess");
		pp.setplayernames("haha", "lala");
		Position temp=new Position(99,99);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		Position temp3=new Position(3,3);
		Position temp5=new Position(7,7);
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp,temp5));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp1,temp));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp1,temp1));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp1,temp2));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp5,temp2));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp3,temp5));
		assertEquals(false,pp.eatpiece(pp.getplayer(0),temp1,temp3));
		assertEquals(true,pp.eatpiece(pp.getplayer(0),temp1,temp5));
	}
	
	/* Testing strategy
	 * 测试whethervalidplayer方法
     * 按照棋子的坐标划分：指定位置超出棋盘范围,此位置已经第一个棋手的棋子：white占用,此位置已经被第二个棋手的棋子：black占用，该位置空闲
     * 覆盖每个取值如下：
     */
	@Test
	public void testWhethervalidplayer() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp=new Position(1,1);
		Position temp1=new Position(2,2);
		Position temp2=new Position(3,3);
		Position temp3=new Position(99,99);
		pp.putpiece(pp.getplayer(0),a,temp1);
		pp.putpiece(pp.getplayer(1),b,temp2);
		assertEquals("非法操作：指定位置超出棋盘范围\n",pp.whethervalidplayer(temp3));
		assertEquals("此位置已被棋手haha的棋子：white占用\n",pp.whethervalidplayer(temp1));
		assertEquals("此位置已被棋手lala的棋子：black占用\n",pp.whethervalidplayer(temp2));
		assertEquals("该位置空闲\n",pp.whethervalidplayer(temp));
	}
	
	/* Testing strategy
	 * 测试countplayerpiece方法
     * 测试棋子数的返回字符串即可
     */
	@Test
	public void testCountplayerpiece() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp1=new Position(2,2);
		Position temp2=new Position(3,3);
		pp.putpiece(pp.getplayer(0),a,temp1);
		pp.putpiece(pp.getplayer(1),b,temp2);
		assertEquals("haha拥有1个棋子\n" + "lala拥有1个棋子\n",pp.countplayerpiece());
	}
	
	/* Testing strategy
	 * 测试printhistory方法
     * 执行此方法即可
     */
	@Test
	public void testPrinthistory() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		pp.putpiece(pp.getplayer(0),a,temp1);
		pp.putpiece(pp.getplayer(1),b,temp2);
	    pp.printhistory();
	}
	
	/* Testing strategy
	 * 测试getmyboard方法
     * 测试返回值即可
     */
	@Test
	public void testGetmyoard() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Piece a=new Piece("white",0);
		Piece b=new Piece("black",1);
		Position temp1=new Position(1,1);
		Position temp2=new Position(2,2);
		pp.putpiece(pp.getplayer(0),a,temp1);
		pp.putpiece(pp.getplayer(1),b,temp2);
		assertEquals("haha",pp.getmyboard().playerone);
		assertEquals("lala",pp.getmyboard().playertwo);
		assertEquals(true,pp.getmyboard().getmyboard().contains(a));
		assertEquals(true,pp.getmyboard().getmyboard().contains(b));
	}


	/* Testing strategy
	 * 测试getplayerone方法
     * 测试返回值即可
     */
	@Test
	public void testGetplayerone() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Integer a=new Integer(0);
		assertEquals("haha",pp.getplayerone().getname());
		assertEquals(a,pp.getplayerone().getmyturn());
	}

	/* Testing strategy
	 * 测试getplayertwo方法
     * 测试返回值即可
     */
	@Test
	public void testGetplayertwo() {
		Game pp=new Game("go");
		pp.setplayernames("haha", "lala");
		Integer a=new Integer(1);
		assertEquals("lala",pp.getplayertwo().getname());
		assertEquals(a,pp.getplayertwo().getmyturn());
	}

}
