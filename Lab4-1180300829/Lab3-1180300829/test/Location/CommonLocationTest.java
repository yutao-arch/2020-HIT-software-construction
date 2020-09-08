package Location;

import static org.junit.Assert.*;

import org.junit.Test;

import Resource.Flight;

public class CommonLocationTest {
	
	
    CommonLocation temp=new CommonLocation("北纬112","东经20","湖北");
    
	/* Testing strategy
	 * 测试getlongitude方法
     * 测试返回值即可
     */
	@Test
	public void getlongitudetest() {
		assertEquals("北纬112",temp.getlongitude());
	}
	
	/* Testing strategy
	 * 测试getlatitude方法
     * 测试返回值即可
     */
	@Test
	public void getlatitudetest() {
		assertEquals("东经20",temp.getlatitude());
	}
	
	/* Testing strategy
	 * 测试getlocationname方法
     * 测试返回值即可
     */
	@Test
	public void getlocationnametest() {
		assertEquals("湖北",temp.getlocationname());
	}
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的位置类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() {
		CommonLocation temp1=new CommonLocation("北纬112","东经20","湖北");
		assertEquals(temp.hashCode(),temp1.hashCode());
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个位置类是否相同划分等价类：相同，不同
     */
	@Test
	public void equalstest() {
		CommonLocation temp1=new CommonLocation("北纬112","东经20","湖北");
		CommonLocation temp2=new CommonLocation("北纬11","东经2","湖北");
		assertEquals(true,temp.equals(temp1));
		assertEquals(false,temp.equals(temp2));
	}

}
