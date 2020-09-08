package Location;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlightTrainLocationTest {

	/* Testing strategy
	 * 测试whethershare方法
     * 测试返回值即可
     */
	@Test
	public void whethersharetest() {
		FlightTrainLocation temp=new FlightTrainLocation("北纬112","东经20","武汉");
		assertEquals(true,temp.whethershare());
	}

}
