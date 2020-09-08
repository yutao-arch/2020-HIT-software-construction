package Location;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseLocationTest {

	/* Testing strategy
	 * 测试whethershare方法
     * 测试返回值即可
     */
	@Test
	public void whethersharetest() {
		CourseLocation temp=new CourseLocation("北纬112","东经20","正心32");
		assertEquals(false,temp.whethershare());
	}

}
