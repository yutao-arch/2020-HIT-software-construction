package PlanningEntry;

import java.util.List;

import Timeslot.Timeslot;

public interface BlockableEntry {

	/**
	 * 判断是否可阻塞
	 * @return 是否可阻塞
	 */
    public boolean whetherblockable();
	
    /**
     * 设置这一组时间对
     * @param alltime 一组时间对 
     * @return 是否成功设置这一组时间对
     */
	public boolean settimeslot(List<Timeslot> alltime);
	
	/**
	 * 得到这一组时间对
	 * @return 这一组时间对
	 */
	public List<Timeslot> gettimeslot();
	
	
}
