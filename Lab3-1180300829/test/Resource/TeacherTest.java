package Resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class TeacherTest {

	/* Testing strategy
	 * 测试getidnumber方法
     * 测试老师身份证号的返回值即可
     */
	@Test
	public void getidnumbertest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		assertEquals("422823199812254452",temp.getidnumber());
	}
	
	/* Testing strategy
	 * 测试getteachername方法
     * 测试教师姓名的返回值即可
     */
	@Test
	public void getteachernametest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		assertEquals("余涛",temp.getteachername());
	}
	
	/* Testing strategy
	 * 测试getteachergender方法
     * 测试教师性别的返回值即可
     */
	@Test
	public void getteachergendertest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		assertEquals("男",temp.getteachergender());
	}
	
	/* Testing strategy
	 * 测试getteachertitle方法
     * 测试教师职称的返回值即可
     */
	@Test
	public void getteachertitletest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		assertEquals("讲师",temp.getteachertitle());
	}
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的教师类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		Teacher temp1=new Teacher("422823199812254452","余涛","男","讲师");
		assertEquals(temp.hashCode(),temp1.hashCode());
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个教师类是否相同划分等价类：相同，不同
     */
	@Test
	public void equalstest() {
		Teacher temp=new Teacher("422823199812254452","余涛","男","讲师");
		Teacher temp1=new Teacher("422823199812254452","余涛","男","讲师");
		Teacher temp2=new Teacher("422823199812222222","余涛","男","讲师");
		assertEquals(true,temp.equals(temp1));
		assertEquals(false,temp.equals(temp2));
	}

}
