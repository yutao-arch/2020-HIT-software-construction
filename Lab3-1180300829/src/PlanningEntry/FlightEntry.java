package PlanningEntry;

import Location.Location;
import Resource.Flight;
import Timeslot.Timeslot;

public class FlightEntry<Flight> extends CommonPlanningEntry implements FlightPlanningEntry<Flight>,Comparable<FlightEntry<Flight>>{

	private TwoLocationEntryImpl a;
	private OneDistinguishResourceEntryImpl<Flight> b;
	private NoBlockableEntryImpl c;
	
	// mutability类
	// Abstraction function:
	// AF(a)=两个位置
	// AF(b)=一个飞机资源
	// AF(c)=不能阻塞的时间对
	// Safety from rep exposure:
	// 将a,b,c设置为private

	/**
	 * 构造方法
	 * @param a 两个位置的类
	 * @param b 一个飞机资源的类
	 * @param c 不能阻塞的时间对的类
	 */
	public FlightEntry(TwoLocationEntryImpl a,OneDistinguishResourceEntryImpl<Flight> b,NoBlockableEntryImpl c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	

	/**
	 * 设置起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	@Override
	public boolean setlocations(Location from, Location to) {
		return a.setlocations(from, to);
	}

	/**
	 * 更改起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	@Override
	public boolean changelocations(Location from, Location to) {
		return a.changelocations(from, to);
	}
	
	/**
	 * 得到起点站的位置
	 * @return 起点站
	 */
	@Override
	public Location getfromlocation() {
		return a.getfromlocation();
	}

	/**
	 * 得到终点站的位置
	 * @return 起点站
	 */
	@Override
	public Location gettolocation() {
		return a.gettolocation();
	}


	/**
	 * 设置飞机资源
	 * @param a 待设置的飞机资源
	 * @return 飞机资源是否设置成功
	 */
	@Override
	public boolean setresource(Flight a) {
		return b.setresource(a);
	}

	/**
	 * 得到该飞机资源
	 * @return 该飞机资源
	 */
	@Override
	public Flight getresource() {
		return b.getresource();
	}

	/**
	 * 更改该飞机资源
	 * @param a 更改后的资源
	 * @return 是否更改成功
	 */
	@Override
	public boolean changeresource(Flight a) {
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
	public int compareTo(FlightEntry<Flight> o) {
		if(this.c.gettimeslot().getbegintime().compareTo(o.gettimeslot().getbegintime())>0) {
			return 1;
		}
		else if(this.c.gettimeslot().getbegintime().compareTo(o.gettimeslot().getbegintime())==0) {
			return 0;
		}
		return -1;
	}
		

	
}
