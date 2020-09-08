package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventManagerTest {

	/* Testing strategy
	 * 测试book方法
	 * 按照加入的时间段原先存在的方式划分：只有开始时间，只有结束时间，两个时间都没有，两个时间都有
	 * 按照是否具有重叠时间段划分：具有重叠时间段，没有重叠时间段
     * 覆盖每个取值如下：
     */
	@Test
	public void booktest() {
		assertEquals(1,EventManager.book(1, 1, 2));
		assertEquals(2,EventManager.book(1, 1, 2));
		assertEquals(2,EventManager.book(1, 3, 4));
		assertEquals(3,EventManager.book(1, 1, 6));
		assertEquals(3,EventManager.book(1, 5, 6));
	}

}
