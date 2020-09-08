package Factory;

import PlanningEntry.*;
import Resource.*;

public class CourseEntryFactory implements PlanningEntryFactory{

	/**
	 * 写成空方法
	 */
	@Override
	public PlanningEntry getFlightEntry(TwoLocationEntryImpl a, OneDistinguishResourceEntryImpl<Flight> b,
			NoBlockableEntryImpl c) {
		// TODO 自动生成的方法存根
		return null;
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
	 * 创建CourseEntry对象
	 * @param a 一个位置的类
	 * @param b 一个老师资源的类
	 * @param c 不能阻塞的时间对的类
	 * @return 创建的CourseEntry对象
	 */
	@Override
	public PlanningEntry getCourseEntry(OneLocationEntryImpl a, OneDistinguishResourceEntryImpl<Teacher> b,
			NoBlockableEntryImpl c) {
		// TODO 自动生成的方法存根
		return new CourseEntry<Teacher>(a,b,c);
	}

}
