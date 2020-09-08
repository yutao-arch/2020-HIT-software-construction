package Factory;

import PlanningEntry.*;
import Resource.Carriage;
import Resource.Flight;
import Resource.Teacher;

public class FlightEntryFactory implements PlanningEntryFactory{

	/**
	 * 创建FlightEntry对象
	 * @param a 两个位置的类
	 * @param b 一个飞机资源的类
	 * @param c 不能阻塞的时间对的类
	 * @return 创建的FlightEntry对象
	 */
	@Override
	public PlanningEntry getFlightEntry(TwoLocationEntryImpl a,OneDistinguishResourceEntryImpl<Flight> b,NoBlockableEntryImpl c) {
		return new FlightEntry<Flight>(a,b,c);
	}

	/**
	 * 写成空方法
	 */
	@Override
	public PlanningEntry getTrainEntry(MultipleLacationEntryImpl a, MultipleSortedResourceEntryImpl<Carriage> b,
			BlockableEntryImpl c) {
		// TODO 自动生成的方法存根
		return null;
	}

	/**
	 * 写成空方法
	 */
	@Override
	public PlanningEntry getCourseEntry(OneLocationEntryImpl a, OneDistinguishResourceEntryImpl<Teacher> b,
			NoBlockableEntryImpl c) {
		// TODO 自动生成的方法存根
		return null;
	}

}
