package PlanningEntry;

import Location.*;

public class TwoLocationEntryImpl implements TwoLocationEntry{

	private Location fromlocation;  //起点站
	private Location tolocation;   //终点站
	
	    // mutability类
		// Abstraction function:
	    // AF(fromlocation)=起点站
		// AF(tolocation)=终点站
		// Representation invariant:
	    // 只能设置一次起点站和终点站
	    // Safety from rep exposure:
	    // 将fromlocation,tolocation设置为private
	
	/**
	 * 设置起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	@Override
	public boolean setlocations(Location from, Location to) {
		if(fromlocation==null&&tolocation==null) {
			this.fromlocation=from;
			this.tolocation=to;
			System.out.println("位置设置成功");
			return true;
		}
		System.out.println("位置只能设置一次");
		return false;
	}

	/**
	 * 更改起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	@Override
	public boolean changelocations(Location from, Location to) {
			System.out.println("位置不可更改");
			return false;
	}

	/**
	 * 得到起点站的位置
	 * @return 起点站
	 */
	@Override
	public Location getfromlocation() {
		return fromlocation;
	}

	/**
	 * 得到终点站的位置
	 * @return 起点站
	 */
	@Override
	public Location gettolocation() {
		return tolocation;
	}
	
	

}
