package Resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarriageTest {

	/* Testing strategy
	 * 测试getcarriagenumber方法
     * 测试车厢编号的返回值即可
     */
	@Test
	public void getcarriagenumbertest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		assertEquals("AS02",temp.getcarriagenumber());
	}
	
	/* Testing strategy
	 * 测试getcarriagetype方法
     * 测试车厢类型的返回值即可
     */
	@Test
	public void getcarriagetypetest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		assertEquals("二等座",temp.getcarriagetype());
	}
	
	/* Testing strategy
	 * 测试getcarriageallseat方法
     * 测试定员数的返回值即可
     */
	@Test
	public void getcarriageallseattest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		assertEquals(100,temp.getcarriageallseat());
	}
	
	/* Testing strategy
	 * 测试getcarriagbirth方法
     * 测试车厢出厂年份的返回值即可
     */
	@Test
	public void getcarriagbirthtest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		assertEquals("2011",temp.getcarriagbirth());
	}
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的车厢类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		Carriage temp1=new Carriage("AS02","二等座",100,"2011");
		assertEquals(temp.hashCode(),temp1.hashCode());
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个车厢类是否相同划分等价类：相同，不同
     */
	@Test
	public void equalstest() {
		Carriage temp=new Carriage("AS02","二等座",100,"2011");
		Carriage temp1=new Carriage("AS02","二等座",100,"2011");
		Carriage temp2=new Carriage("A02","一等座",100,"2011");
		assertEquals(true,temp.equals(temp1));
		assertEquals(false,temp.equals(temp2));
	}

}
