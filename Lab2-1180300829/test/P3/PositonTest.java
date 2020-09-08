package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositonTest {

	/* Testing strategy
     * 测试横坐标的返回值即可
     */
	@Test
	public void testGetx() {
		Position m=new Position(3,3);
		assertEquals(3, m.getx());
	}
	
	/* Testing strategy
     * 测试纵坐标的返回值即可
     */
	@Test
	public void testGety() {
		Position m=new Position(3,3);
		assertEquals(3, m.gety());
	}
	
	/* Testing strategy
	 * 检查两个Position是否相同
     * 按照两个点是否相同划分：坐标相同，坐标不同
     * 覆盖每个取值如下：
     */
	@Test
	public void testEquals() {
		Position m=new Position(3,3);
		Position n=new Position(3,3);
		Position p=new Position(4,4);
		assertEquals(true, m.equals(n));
		assertEquals(false, m.equals(p));
	}

}
