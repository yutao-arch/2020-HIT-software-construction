package PlanningEntry;

import Timeslot.Timeslot;

public interface NoBlockableEntry {

	/**
	 * 判断该类是否可阻塞
	 * @return 该类是否可阻塞
	 */
	public boolean whetherblockable();
	
	/**
	 * 设置该时间对
	 * @param mytime 时间对
	 * @return 是否成功设置该时间对
	 */
	public boolean settimeslot(Timeslot mytime);
	
	/**
	 * 得到该时间对
	 * @return 该时间对
	 */
	public Timeslot gettimeslot();
}
