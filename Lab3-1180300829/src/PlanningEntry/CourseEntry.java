package PlanningEntry;

import Location.Location;
import Resource.Teacher;
import Timeslot.Timeslot;

public class CourseEntry<Teacher> extends CommonPlanningEntry implements CoursePlanningEntry<Teacher>,Comparable<CourseEntry<Teacher>>{

	private OneLocationEntryImpl a;
	private OneDistinguishResourceEntryImpl<Teacher> b;
	private NoBlockableEntryImpl c;
	
	// mutability类
	// Abstraction function:
	// AF(a)=一个位置
	// AF(b)=一个老师资源
	// AF(c)=不能阻塞的时间对
	// Safety from rep exposure:
	// 将a,b,c设置为private
	
	/**
	 * 构造方法
	 * @param a 一个位置的类
	 * @param b 一个老师资源的类
	 * @param c 不能阻塞的时间对的类
	 */
	public CourseEntry(OneLocationEntryImpl a,OneDistinguishResourceEntryImpl<Teacher> b,NoBlockableEntryImpl c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}

	/**
	 * 设置该位置
	 * @param only 该位置
	 * @return 该位置是否设置成功
	 */
	@Override
	public boolean setlocations(Location only) {
		return a.setlocations(only);
	}

	/**
	 * 更改该位置
	 * @param only 更改后的位置
	 * @return 位置是否更改成功
	 */
	@Override
	public boolean changelocations(Location only) {
		return a.changelocations(only);
	}

	/**
	 * 删除该位置
	 * @param waittodelete 待删除的位置
	 * @return 位置是否删除成功
	 */
	@Override
	public boolean deletelocations(Location waittodelete) {
		return a.deletelocations(waittodelete);
	}
	
	/**
	 * 得到该位置
	 * @return 该位置
	 */
	@Override
	public Location getlocations() {
		return a.getlocations();
	}

	/**
	 * 设置该教师资源
	 * @param a 该教师资源
	 * @return 是否成功设置该教师资源
	 */
	@Override
	public boolean setresource(Teacher a) {
		return b.setresource(a);
	}

	/**
	 * 得到该教师资源
	 * @return 该教师资源
	 */
	@Override
	public Teacher getresource() {
		return b.getresource();
	}
	
	/**
	 * 更改该教师资源
	 * @param a 更改后的教师资源
	 * @return 是都成功更改该教师资源
	 */
	@Override
	public boolean changeresource(Teacher a) {
		return b.changeresource(a);
	}

	/**
	 * 判断该类是否可阻塞
	 * @return 该类是否可阻塞
	 */
	@Override
	public boolean whetherblockable() {
		return c.whetherblockable();
	}

	/**
	 * 设置该时间对
	 * @param mytime 时间对
	 * @return 是否成功设置该时间对
	 */
	@Override
	public boolean settimeslot(Timeslot mytime) {
		return c.settimeslot(mytime);
	}

	/**
	 * 得到该时间对
	 * @return 该时间对
	 */
	@Override
	public Timeslot gettimeslot() {
		return c.gettimeslot();
	}

    /**
     * 重写compareTo方法，完成按照时间对起始时间的升序对计划项集合排序
     */
	@Override
	public int compareTo(CourseEntry<Teacher> o) {
		if(c.gettimeslot().getbegintime().compareTo(o.gettimeslot().getbegintime())>0) {
			return 1;
		}
		else if(c.gettimeslot().getbegintime().compareTo(o.gettimeslot().getbegintime())==0) {
			return 0;
		}
		return -1;
	}
}
