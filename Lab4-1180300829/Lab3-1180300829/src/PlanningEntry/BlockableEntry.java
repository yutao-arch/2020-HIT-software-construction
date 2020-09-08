package PlanningEntry;

import java.util.List;

import Exception.ConflictTimeException;
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
     * @throws ConflictTimeException 某个站抵达时间晚于出发时间
     */
	public boolean settimeslot(List<Timeslot> alltime) throws ConflictTimeException;
	
	/**
	 * 得到这一组时间对
	 * @return 这一组时间对
	 */
	public List<Timeslot> gettimeslot();
	
	
}
