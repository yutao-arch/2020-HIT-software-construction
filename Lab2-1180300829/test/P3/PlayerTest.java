package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	/* Testing strategy
     * 测试下棋顺序值的返回值即可
     */
	@Test
	public void testGetmyturn() {
		Player m=new Player();
		Integer a=4;
		m.setmyturn(a);
		assertEquals(a,m.getmyturn());
	}
	
	/* Testing strategy
     * 测试棋手名字的返回值即可
     */
	@Test
	public void testGetname() {
		Player m=new Player();
		m.setname("yutao");
		assertEquals("yutao",m.getname());
	}

}
