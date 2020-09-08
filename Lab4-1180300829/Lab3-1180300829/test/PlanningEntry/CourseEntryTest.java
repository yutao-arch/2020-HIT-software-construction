package PlanningEntry;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import Exception.BeginEndTimeException;
import Factory.CourseEntryFactory;
import Location.CourseLocation;
import Resource.Teacher;
import Timeslot.Timeslot;

public class CourseEntryTest {
	
	/**
	 * 在测试该类的过程中已经完成了对其委托方法的测试
	 */

	/* Testing strategy
	 * 测试setlocations方法
	 * 按照位置是否已被设置划分：位置已被设置，位置未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void setlocationstest() {
		String weidu="北纬40度",jingdu="东经112度";
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		assertEquals(true,course1.setlocations(new CourseLocation(weidu,jingdu,"正心楼32")));
		assertEquals(false,course1.setlocations(new CourseLocation(weidu,jingdu,"正心楼32")));
	}

	/* Testing strategy
	 * 测试changelocations方法
	 * 按照位置是否和原位置重复划分：重复，不重复
     * 覆盖每个取值如下：
     */
	@Test
	public void changelocationstest() {
		String weidu="北纬40度",jingdu="东经112度";
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.setlocations(new CourseLocation(weidu,jingdu,"正心楼32"));
		assertEquals(true,course1.changelocations(new CourseLocation(weidu,jingdu,"明德楼32")));	
		assertEquals(false,course1.changelocations(new CourseLocation(weidu,jingdu,"明德楼32")));
	}
	
	/* Testing strategy
	 * 测试deletelocations方法
	 * 按照待删除的位置是否存在划分：存在，不存在
     * 覆盖每个取值如下：
     */
	@Test
	public void deletelocationstest() {
		String weidu="北纬40度",jingdu="东经112度";
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.setlocations(new CourseLocation(weidu,jingdu,"正心楼32"));
		assertEquals(false,course1.deletelocations(new CourseLocation(weidu,jingdu,"明德楼32")));	
		assertEquals(true,course1.deletelocations(new CourseLocation(weidu,jingdu,"正心楼32")));
	}
	
	/* Testing strategy
	 * 测试getlocations方法
     * 测试返回值即可
     */
	@Test
	public void getlocationstest() {
		String weidu="北纬40度",jingdu="东经112度";
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.setlocations(new CourseLocation(weidu,jingdu,"正心楼32"));
		assertEquals(new CourseLocation(weidu,jingdu,"正心楼32"),course1.getlocations());	
	}
	
	/* Testing strategy
	 * 测试setresource方法
	 * 按照资源是否已被设置划分：资源已被设置，资源未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void setresourcetest() {
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		assertEquals(true,course1.setresource(new Teacher("422823199812254452","余涛","男","讲师")));
		assertEquals(false,course1.setresource(new Teacher("422823199812254452","余涛","男","讲师")));
	}
	
	/* Testing strategy
	 * 测试getresource方法
	 * 测试返回值即可
     */
	@Test
	public void getresourcetest() {
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.setresource(new Teacher("422823199812254452","余涛","男","讲师"));
		assertEquals(new Teacher("422823199812254452","余涛","男","讲师"),course1.getresource());
	}
	
	/* Testing strategy
	 * 测试changeresource方法
	 * 按照是否与原教师重复划分：重复，不重复
	 * 覆盖每个取值如下：
     */
	@Test
	public void changeresourcetest() {
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.setresource(new Teacher("422823199812254452","余涛","男","讲师"));
		assertEquals(false,course1.changeresource(new Teacher("422823199812254452","余涛","男","讲师")));
		assertEquals(true,course1.changeresource(new Teacher("422823199812211111","王宁","男","讲师")));
	}
	
	/* Testing strategy
	 * 测试whetherblockable方法
	 * 测试返回值即可
     */
	@Test
	public void whetherblockabletest() {
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		assertEquals(false,course1.whetherblockable());
	}
	
	/* Testing strategy
	 * 测试settimeslot方法
	 * 按照时间是否已被设置划分：已被设置，未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void settimeslottest() throws ParseException, BeginEndTimeException{
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		assertEquals(true,course1.settimeslot(new Timeslot("2020-01-01 15:45","2020-01-01 17:30")));
		assertEquals(false,course1.settimeslot(new Timeslot("2020-01-01 15:45","2020-01-01 17:30")));
	}
	
	/* Testing strategy
	 * 测试gettimeslot方法
	 * 测试返回值即可
     */
	@Test
	public void gettimeslottest() throws ParseException, BeginEndTimeException{
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		
		course1.settimeslot(new Timeslot("2020-01-01 15:45","2020-01-01 17:30"));
		assertEquals(new Timeslot("2020-01-01 15:45","2020-01-01 17:30"),course1.gettimeslot());
	}
	
	/* Testing strategy
	 * 测试compareTo方法
	 * 按照起始时间大小划分：大于，等于，小于
     * 覆盖每个取值如下：
     */
	@Test
	public void compareTotest() throws ParseException, BeginEndTimeException{
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		PlanningEntry origincourse1=new CourseEntryFactory().getCourseEntry(a1, b1, c1); //用工厂方法创建对象
		CourseEntry<Teacher> course1=(CourseEntry<Teacher>)origincourse1;
		course1.settimeslot(new Timeslot("2020-01-01 15:45","2020-01-01 17:30"));

		OneLocationEntryImpl a2;
		OneDistinguishResourceEntryImpl<Teacher> b2;
		NoBlockableEntryImpl c2;
		a2=new OneLocationEntryImpl();
		b2=new OneDistinguishResourceEntryImpl<Teacher>();
		c2=new NoBlockableEntryImpl();
		PlanningEntry origincourse2=new CourseEntryFactory().getCourseEntry(a2, b2, c2); //用工厂方法创建对象
		CourseEntry<Teacher> course2=(CourseEntry<Teacher>)origincourse2;
		course2.settimeslot(new Timeslot("2020-01-01 15:45","2020-01-01 17:30"));

		
		OneLocationEntryImpl a3;
		OneDistinguishResourceEntryImpl<Teacher> b3;
		NoBlockableEntryImpl c3;
		a3=new OneLocationEntryImpl();
		b3=new OneDistinguishResourceEntryImpl<Teacher>();
		c3=new NoBlockableEntryImpl();
		PlanningEntry origincourse3=new CourseEntryFactory().getCourseEntry(a3, b3, c3); //用工厂方法创建对象
		CourseEntry<Teacher> course3=(CourseEntry<Teacher>)origincourse3;
		course3.settimeslot(new Timeslot("2020-02-01 16:45","2020-02-01 18:30"));
		
		assertEquals(1,course3.compareTo(course1));
		assertEquals(0,course1.compareTo(course2));
		assertEquals(-1,course1.compareTo(course3));
	
	}
}
