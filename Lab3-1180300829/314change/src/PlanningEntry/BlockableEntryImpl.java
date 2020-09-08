package PlanningEntry;

import java.util.ArrayList;
import java.util.List;

import Timeslot.Timeslot;

public class BlockableEntryImpl implements BlockableEntry {

	private List<Timeslot> alltime; //一组时间对
	
	// mutability类
	// Abstraction function:
	// AF(mytrain)=一组时间对
	// Representation invariant:
	// 输入的时间对集合不能为空
	// Safety from rep exposure:
	// 将alltime设置为private
	
	/**
	 * 判断是否可阻塞
	 * @return 是否可阻塞
	 */
	@Override
	public boolean whetherblockable() {
		System.out.println("可阻塞");
		return true;
	}

	 /**
     * 设置这一组时间对
     * @param alltime 一组时间对 
     * @return 是否成功设置这一组时间对
     */
	@Override
	public boolean settimeslot(List<Timeslot> time) {
		if(alltime==null) {
			alltime=new ArrayList<>();
			this.alltime=time;
			System.out.println("一组时间对设置成功");
			return true;
		}
		System.out.println("时间只能设置一次");
		return false;
	}

	/**
	 * 得到这一组时间对
	 * @return 这一组时间对
	 */
	@Override
	public List<Timeslot> gettimeslot() {
		return alltime;
	}
}
