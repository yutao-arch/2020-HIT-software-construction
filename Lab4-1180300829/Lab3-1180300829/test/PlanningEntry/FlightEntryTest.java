package PlanningEntry;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import Exception.BeginEndTimeException;
import Exception.LessThanZeroException;
import Exception.SameLocationException;
import Factory.FlightEntryFactory;
import Location.FlightTrainLocation;
import Resource.Flight;
import Timeslot.Timeslot;

public class FlightEntryTest {
	
	/**
	 * 在测试该类的过程中已经完成了对其委托方法的测试
	 * @throws SameLocationException 
	 */

	/* Testing strategy
	 * 测试setlocations方法
	 * 按照位置是否已被设置划分：位置已被设置，位置未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void setlocationstest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		assertEquals(true,flight.setlocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉")));
		assertEquals(false,flight.setlocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉")));
	}
	
	/* Testing strategy
	 * 测试changelocations方法
     * 测试返回值即可
     */
	@Test
	public void changelocationstest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.setlocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉"));
		assertEquals(false,flight.changelocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉")));
	}
	
	/* Testing strategy
	 * 测试getfromlocation方法
     * 测试返回值即可
     */
	@Test
	public void getfromlocationtest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.setlocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉"));
		assertEquals(new FlightTrainLocation(weidu,jingdu,"北京"),flight.getfromlocation());
	}
	
	/* Testing strategy
	 * 测试gettolocation方法
     * 测试返回值即可
     */
	@Test
	public void gettolocationtest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.setlocations(new FlightTrainLocation(weidu,jingdu,"北京"), new FlightTrainLocation(weidu,jingdu,"武汉"));
		assertEquals(new FlightTrainLocation(weidu,jingdu,"武汉"),flight.gettolocation());
	}
	
	/* Testing strategy
	 * 测试setresource方法
	 * 按照资源是否已被设置划分：资源已被设置，资源未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void setresourcetest() throws LessThanZeroException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		assertEquals(true,flight.setresource(new Flight("A678","A28",100,2.5)));
		assertEquals(false,flight.setresource(new Flight("A678","A28",100,2.5)));
	}
	
	/* Testing strategy
	 * 测试getresource方法
	 * 测试返回值即可
     */
	@Test
	public void getresourcetest() throws LessThanZeroException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.setresource(new Flight("A678","A28",100,2.5));
		assertEquals(new Flight("A678","A28",100,2.5),flight.getresource());
	}
	
	/* Testing strategy
	 * 测试changeresource方法
	 * 按照是否与原飞机重复划分：重复，不重复
	 * 覆盖每个取值如下：
     */
	@Test
	public void changeresourcetest() throws LessThanZeroException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.setresource(new Flight("A678","A28",100,2.5));
		assertEquals(false,flight.changeresource(new Flight("A678","A28",100,2.5)));
		assertEquals(true,flight.changeresource(new Flight("A424","A11",100,2.5)));
	}
	
	/* Testing strategy
	 * 测试whetherblockable方法
	 * 测试返回值即可
     */
	@Test
	public void whetherblockabletest() {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		assertEquals(false,flight.whetherblockable());
	}
	
	/* Testing strategy
	 * 测试settimeslot方法
	 * 按照时间是否已被设置划分：已被设置，未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void settimeslottest() throws ParseException, BeginEndTimeException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		assertEquals(true,flight.settimeslot(new Timeslot("2020-01-02 16:45","2020-01-02 18:30")));
		assertEquals(false,flight.settimeslot(new Timeslot("2020-01-02 16:45","2020-01-02 18:30")));
	}
	
	/* Testing strategy
	 * 测试gettimeslot方法
	 * 测试返回值即可
     */
	@Test
	public void gettimeslottest() throws ParseException, BeginEndTimeException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
		flight.settimeslot(new Timeslot("2020-01-02 16:45","2020-01-02 18:30"));
		assertEquals(new Timeslot("2020-01-02 16:45","2020-01-02 18:30"),flight.gettimeslot());
	}
	
	/* Testing strategy
	 * 测试compareTo方法
	 * 按照起始时间大小划分：大于，等于，小于
     * 覆盖每个取值如下：
     */
	@Test
	public void compareTotest() throws ParseException, BeginEndTimeException {
		TwoLocationEntryImpl a4;
		OneDistinguishResourceEntryImpl<Flight> b4;
		NoBlockableEntryImpl c4;
		a4=new TwoLocationEntryImpl();
		b4=new OneDistinguishResourceEntryImpl<Flight>();
		c4=new NoBlockableEntryImpl();
		PlanningEntry originflight1=new FlightEntryFactory().getFlightEntry(a4, b4, c4); //用工厂方法创建对象
		FlightEntry<Flight> flight1=(FlightEntry<Flight>)originflight1;
		flight1.settimeslot(new Timeslot("2020-01-01 16:45","2020-01-01 18:30"));
		
		TwoLocationEntryImpl a5;
		OneDistinguishResourceEntryImpl<Flight> b5;
		NoBlockableEntryImpl c5;
		a5=new TwoLocationEntryImpl();
		b5=new OneDistinguishResourceEntryImpl<Flight>();
		c5=new NoBlockableEntryImpl();
		PlanningEntry originflight2=new FlightEntryFactory().getFlightEntry(a5, b5, c5); //用工厂方法创建对象
		FlightEntry<Flight> flight2=(FlightEntry<Flight>)originflight2;	
		flight2.settimeslot(new Timeslot("2020-01-01 17:45","2020-01-01 19:30"));
		
		TwoLocationEntryImpl a6;
		OneDistinguishResourceEntryImpl<Flight> b6;
		NoBlockableEntryImpl c6;
		a6=new TwoLocationEntryImpl();
		b6=new OneDistinguishResourceEntryImpl<Flight>();
		c6=new NoBlockableEntryImpl();
		PlanningEntry originflight3=new FlightEntryFactory().getFlightEntry(a6, b6, c6); //用工厂方法创建对象
		FlightEntry<Flight> flight3=(FlightEntry<Flight>)originflight3;
		flight3.settimeslot(new Timeslot("2020-01-01 16:45","2020-01-01 18:30"));
		
		assertEquals(1,flight2.compareTo(flight1));
		assertEquals(0,flight3.compareTo(flight1));
		assertEquals(-1,flight1.compareTo(flight2));
	}
}
