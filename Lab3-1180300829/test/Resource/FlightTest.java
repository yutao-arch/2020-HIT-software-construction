package Resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlightTest {

	/* Testing strategy
	 * 测试getflightnumber方法
     * 测试飞机编号的返回值即可
     */
	@Test
	public void getflightnumbertest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		assertEquals("A898",temp.getflightnumber());
	}
	
	/* Testing strategy
	 * 测试getflighttype方法
     * 测试飞机机型号的返回值即可
     */
	@Test
	public void getflighttypetest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		assertEquals("C88",temp.getflighttype());
	}
	
	/* Testing strategy
	 * 测试getflightallseat方法
     * 测试飞机座位数的返回值即可
     */
	@Test
	public void getflightallseattest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		assertEquals(100,temp.getflightallseat());
	}
	
	/* Testing strategy
	 * 测试getflightage方法
     * 测试飞机机龄的返回值即可
     */
	@Test
	public void getflightagetest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		assertEquals(Double.doubleToLongBits(2.5),Double.doubleToLongBits(temp.getflightage()));
	}
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的飞机类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		Flight temp1=new Flight("A898","C88",100,2.5);
		assertEquals(temp.hashCode(),temp1.hashCode());
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个飞机类是否相同划分等价类：相同，不同
     */
	@Test
	public void equalstest() {
		Flight temp=new Flight("A898","C88",100,2.5);
		Flight temp1=new Flight("A898","C88",100,2.5);
		Flight temp2=new Flight("A222","C88",100,2.5);
		assertEquals(true,temp.equals(temp1));
		assertEquals(false,temp.equals(temp2));
	}

}
