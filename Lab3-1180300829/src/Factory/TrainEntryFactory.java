package Factory;

import PlanningEntry.*;
import Resource.*;


public class TrainEntryFactory implements PlanningEntryFactory{

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
	 * 创建TrainEntry对象
	 * @param a 多个位置的类
	 * @param b 多个车厢资源的类
	 * @param c 能阻塞的时间对的类
	 * @return 创建的TrainEntry对象
	 */
	@Override
	public PlanningEntry getTrainEntry(MultipleLacationEntryImpl a, MultipleSortedResourceEntryImpl<Carriage> b,
			BlockableEntryImpl c) {
		return new TrainEntry(a,b,c);
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
