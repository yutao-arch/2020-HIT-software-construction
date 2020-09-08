package Factory;

import static org.junit.Assert.*;

import org.junit.Test;

import PlanningEntry.BlockableEntryImpl;
import PlanningEntry.MultipleLacationEntryImpl;
import PlanningEntry.MultipleSortedResourceEntryImpl;
import PlanningEntry.NoBlockableEntryImpl;
import PlanningEntry.OneDistinguishResourceEntryImpl;
import PlanningEntry.OneLocationEntryImpl;
import PlanningEntry.TwoLocationEntryImpl;
import Resource.Carriage;
import Resource.Flight;
import Resource.Teacher;

public class FlightEntryFactoryTest {

	/* Testing strategy
	 * 测试getFlightEntry方法
     * 测试返回值即可
     */
	@Test
	public void getFlightEntrytest() {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		FlightEntryFactory temp=new FlightEntryFactory();
		assert temp.getFlightEntry(a4, b4, c4)!=null;
	}
	
	/* Testing strategy
	 * 测试getTrainEntry方法
     * 测试返回值即可
     */
	@Test
	public void getTrainEntrytest() {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		FlightEntryFactory temp=new FlightEntryFactory();
		assertEquals(null,temp.getTrainEntry(a7, b7, c7));
	}
	
	/* Testing strategy
	 * 测试getCourseEntry方法
     * 测试返回值即可
     */
	@Test
	public void getCourseEntrytest() {
		OneLocationEntryImpl a1;
		OneDistinguishResourceEntryImpl<Teacher> b1;
		NoBlockableEntryImpl c1;
		a1=new OneLocationEntryImpl();
		b1=new OneDistinguishResourceEntryImpl<Teacher>();
		c1=new NoBlockableEntryImpl();
		FlightEntryFactory temp=new FlightEntryFactory();
		assertEquals(null,temp.getCourseEntry(a1, b1, c1));
	}

}
