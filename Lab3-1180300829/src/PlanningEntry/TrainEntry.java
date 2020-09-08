package PlanningEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import Location.Location;
import Resource.Carriage;
import Timeslot.Timeslot;

public class TrainEntry<Carriage> extends CommonPlanningEntry implements TrainPlanningEntry<Carriage>,Comparable<TrainEntry<Carriage>> {

	private MultipleLacationEntryImpl a;
	private MultipleSortedResourceEntryImpl<Carriage> b;
	private BlockableEntryImpl c;
	
	// mutability类
	// Abstraction function:
	// AF(a)=多个位置
	// AF(b)=多个车厢资源
	// AF(c)=能阻塞的时间对
	// Safety from rep exposure:
	// 将a,b,c设置为private
	
	/**
	 * 构造方法
	 * @param a 多个位置的类
	 * @param b 多个车厢资源的类
	 * @param c 能阻塞的时间对的类
	 */
	public TrainEntry(MultipleLacationEntryImpl a,MultipleSortedResourceEntryImpl<Carriage> b,BlockableEntryImpl c) {
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
	 * 设置该高铁资源
	 * @param train 车厢资源集合
	 * @return 是否成功设置该高铁资源
	 */
	@Override
	public boolean setresource(List<Carriage> train) {
		return b.setresource(train);
	}

	/**
	 * 得到该高铁资源
	 * @return 该高铁资源
	 */
	@Override
	public List<Carriage> getresource() {
		return b.getresource();
	}

	/**
	 * 更改某车厢资源
	 * @param precarriage 待更改的车厢资源
	 * @param aftercarriage 更改后的车厢资源
	 * @return 是否成功更改车厢资源
	 */
	@Override
	public boolean changeresource(Carriage mycarriage,Carriage aftercarriage) {
		return b.changeresource(mycarriage,aftercarriage);
	}

	/**
	 * 向高铁资源里面加入一节车厢
	 * @param mycarriage 待加入的车厢
	 * @param temp 加入车厢的位置
	 * @return 是否成功加入车厢
	 */
	@Override
	public boolean addresource(Carriage mycarriage,int temp) {
		return b.addresource(mycarriage,temp);
	}

	/**
	 * 删除高铁资源中的某车厢
	 * @param mycarriage 待删除的车厢
	 * @return 是否成功删除车厢
	 */
	@Override
	public boolean deleteresource(Carriage mycarriage) {
		return b.deleteresource(mycarriage);
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
	public int compareTo(TrainEntry<Carriage> o) {
		if(c.gettimeslot().get(0).getbegintime().compareTo(o.gettimeslot().get(0).getbegintime())>0) {
			return 1;
		}
		else if(c.gettimeslot().get(0).getbegintime().compareTo(o.gettimeslot().get(0).getbegintime())==0) {
			return 0;
		}
		return -1;
	}

}
