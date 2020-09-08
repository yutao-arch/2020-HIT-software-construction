package PlanningEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import Location.Location;
import Resource.Flight;
import Timeslot.Timeslot;

public class FlightEntry<Flight> extends CommonPlanningEntry implements FlightPlanningEntry<Flight>,Comparable<FlightEntry<Flight>>{

	private MultipleLacationEntryImpl a;
	private OneDistinguishResourceEntryImpl<Flight> b;
	private BlockableEntryImpl c;
	
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
	public FlightEntry(MultipleLacationEntryImpl a,OneDistinguishResourceEntryImpl<Flight> b,BlockableEntryImpl c) {
		this.a=a;
		this.b=b;
		this.c=c;
	}
	

	/**
	 * 设置一组位置
	 * @param locations 待设置的一组位置
	 * @return 是否成功设置这一组位置
	 */
	@Override
	public boolean setlocations(List<Location> mylocations) {
		return a.setlocations(mylocations);
	}

	/**
	 * 更改这一组位置
	 * @param mylocations 更改后的位置
	 * @return 是都成功更改该位置
	 */
	@Override
	public boolean changelocations(List<Location> mylocations) {
		return a.changelocations(mylocations);
	}
	
	/**
	 * 得到这一组位置
	 * @return 这一组该位置
	 */
	@Override
	public List<Location> getlocations() {
		return a.getlocations();
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
	 * 判断是否可阻塞
	 * @return 是否可阻塞
	 */
	@Override
	public boolean whetherblockable() {
		return c.whetherblockable();
	}

	/**
     * 设置这一组时间对
     * @param alltime 一组时间对 
     * @return 是否成功设置这一组时间对
     */
	@Override
	public boolean settimeslot(List<Timeslot> alltime) {
		return c.settimeslot(alltime);
	}

	/**
	 * 得到这一组时间对
	 * @return 这一组时间对
	 */
	@Override
	public List<Timeslot> gettimeslot() {
		return c.gettimeslot();
	}
	
	/**
	 * 对某个站点进行阻塞
	 * @param toblocklocation 待阻塞的站点名称
	 * @return 阻塞站点在所有站点中的位置
	 */
	public int trainblock(String toblocklocation) {
		int i;
		if(a.getlocations().size()<=2) {
			System.out.println("没有中间站点可供阻塞\n");
			return -1;
		}
		else {
			for(i=0;i<a.getlocations().size();i++) {
				if(a.getlocations().get(i).getlocationname().equals(toblocklocation) ){
					break;
				}
			}
			if(i>0&&i<a.getlocations().size()-1) {
				Calendar nowtime = Calendar.getInstance();  //当前时间
				String kpr = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(nowtime.getTime()); 
				System.out.println("在"+toblocklocation+"发生了阻塞，现在时间为"+kpr);	
				return i;
			}
			System.out.println("不能在起点站或终点站阻塞\n");
			return -1;
		} 
	}

    /**
     * 重写compareTo方法，完成按照时间对起始时间的升序对计划项集合排序
     */
	@Override
	public int compareTo(FlightEntry<Flight> o) {
		if(c.gettimeslot().get(0).getbegintime().compareTo(o.gettimeslot().get(0).getbegintime())>0) {
			return 1;
		}
		else if(c.gettimeslot().get(0).getbegintime().compareTo(o.gettimeslot().get(0).getbegintime())==0) {
			return 0;
		}
		return -1;
	}
		

	
}
