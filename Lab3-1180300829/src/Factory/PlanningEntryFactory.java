package Factory;

import PlanningEntry.BlockableEntryImpl;

import PlanningEntry.MultipleLacationEntryImpl;
import PlanningEntry.MultipleSortedResourceEntryImpl;
import PlanningEntry.NoBlockableEntryImpl;
import PlanningEntry.OneDistinguishResourceEntryImpl;
import PlanningEntry.OneLocationEntryImpl;
import PlanningEntry.PlanningEntry;
import PlanningEntry.TwoLocationEntryImpl;
import Resource.Carriage;
import Resource.Flight;
import Resource.Teacher;

public interface PlanningEntryFactory {

	/**
	 * 创建FlightEntry对象
	 * @param a 两个位置的类
	 * @param b 一个飞机资源的类
	 * @param c 不能阻塞的时间对的类
	 * @return 创建的FlightEntry对象
	 */
	public PlanningEntry getFlightEntry(TwoLocationEntryImpl a,OneDistinguishResourceEntryImpl<Flight> b,NoBlockableEntryImpl c);
	
	/**
	 * 创建TrainEntry对象
	 * @param a 多个位置的类
	 * @param b 多个车厢资源的类
	 * @param c 能阻塞的时间对的类
	 * @return 创建的TrainEntry对象
	 */
	public PlanningEntry getTrainEntry(MultipleLacationEntryImpl a,MultipleSortedResourceEntryImpl<Carriage> b,BlockableEntryImpl c);
	
	/**
	 * 创建CourseEntry对象
	  * @param a 一个位置的类
	 * @param b 一个老师资源的类
	 * @param c 不能阻塞的时间对的类
	 * @return 创建的CourseEntry对象
	 */
	public PlanningEntry getCourseEntry(OneLocationEntryImpl a,OneDistinguishResourceEntryImpl<Teacher> b,NoBlockableEntryImpl c);
}
