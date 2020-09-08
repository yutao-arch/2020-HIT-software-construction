package PlanningEntry;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Exception.BeginEndTimeException;
import Exception.ConflictTimeException;
import Exception.LessThanZeroException;
import Exception.SameLocationException;
import Exception.SameResourceException;
import Factory.CourseEntryFactory;
import Factory.TrainEntryFactory;
import Location.CourseLocation;
import Location.FlightTrainLocation;
import Location.Location;
import Resource.Carriage;
import Resource.Teacher;
import Timeslot.Timeslot;

public class TrainEntryTest {
	
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
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train=(TrainEntry<Carriage>)origintrain;
		
		List<Location> alllocation=new ArrayList<>();
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"北京"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"武汉"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"南京"));
      
		assertEquals(true,train.setlocations(alllocation));
		assertEquals(false,train.setlocations(alllocation));
	}

	/* Testing strategy
	 * 测试changelocations方法
	 * 测试返回值即可
     */
	@Test
	public void changelocationstest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train=(TrainEntry<Carriage>)origintrain;
		
		List<Location> alllocation=new ArrayList<>();
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"北京"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"武汉"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"南京"));
		
		train.setlocations(alllocation);
		assertEquals(false,train.changelocations(alllocation));	
	
	}
	
	
	/* Testing strategy
	 * 测试getlocations方法
     * 测试返回值即可
     */
	@Test
	public void getlocationstest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train=(TrainEntry<Carriage>)origintrain;
		
		List<Location> alllocation=new ArrayList<>();
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"北京"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"武汉"));
		alllocation.add(new FlightTrainLocation(weidu,jingdu,"南京"));
		
		train.setlocations(alllocation);
		assertEquals(alllocation,train.getlocations());	
	}
	
	/* Testing strategy
	 * 测试setresource方法
	 * 按照资源是否已被设置划分：资源已被设置，资源未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void setresourcetest() throws SameResourceException, LessThanZeroException {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Carriage> allcarriage1=new ArrayList<>();
		allcarriage1.add(new Carriage("AS01","二等座",100,"2011"));
		allcarriage1.add(new Carriage("AS02","二等座",100,"2011"));
		
		assertEquals(true,train1.setresource(allcarriage1));
		assertEquals(false,train1.setresource(allcarriage1));
	}
	
	/* Testing strategy
	 * 测试getresource方法
	 * 测试返回值即可
     */
	@Test
	public void getresourcetest() throws SameResourceException, LessThanZeroException {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Carriage> allcarriage1=new ArrayList<>();
		allcarriage1.add(new Carriage("AS01","二等座",100,"2011"));
		allcarriage1.add(new Carriage("AS02","二等座",100,"2011"));
		
		train1.setresource(allcarriage1);
		assertEquals(allcarriage1,train1.getresource());
	}
	
	/* Testing strategy
	 * 测试changeresource方法
	 * 按照更改后是否与原车厢重复划分：重复，不重复
	 * 按照待更改车厢是否存在划分：存在，不存在
	 * 覆盖每个取值如下：
     */
	@Test
	public void changeresourcetest() throws SameResourceException, LessThanZeroException {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Carriage> allcarriage1=new ArrayList<>();
		allcarriage1.add(new Carriage("AS01","二等座",100,"2011"));
		allcarriage1.add(new Carriage("AS02","二等座",100,"2011"));
		
		train1.setresource(allcarriage1);
		assertEquals(false,train1.changeresource(new Carriage("A01","二等座",100,"2011"), new Carriage("A02","二等座",100,"2011")));
		assertEquals(false,train1.changeresource(new Carriage("AS01","二等座",100,"2011"), new Carriage("AS02","二等座",100,"2011")));
		assertEquals(true,train1.changeresource(new Carriage("AS01","二等座",100,"2011"), new Carriage("A01","二等座",100,"2011")));
	}
	
	/* Testing strategy
	 * 测试addresource方法
	 * 增加车厢是否已经存在划分：存在，不存在
	 * 按照待增加车厢的位置：高铁中部，高铁尾部
	 * 覆盖每个取值如下：
     */
	@Test
	public void addresourcetest() throws SameResourceException, LessThanZeroException {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Carriage> allcarriage1=new ArrayList<>();
		allcarriage1.add(new Carriage("AS01","二等座",100,"2011"));
		allcarriage1.add(new Carriage("AS02","二等座",100,"2011"));
		
		train1.setresource(allcarriage1);
		assertEquals(false,train1.addresource(new Carriage("AS01","二等座",100,"2011"), 0));
		assertEquals(true,train1.addresource(new Carriage("A01","二等座",100,"2011"), 0));
		assertEquals(true,train1.addresource(new Carriage("A02","二等座",100,"2011"), 3));
	}
	
	/* Testing strategy
	 * 测试deleteresource方法
	 * 增加待移除车厢是否已经存在划分：存在，不存在
	 * 覆盖每个取值如下：
     */
	@Test
	public void deleteresourcetest() throws SameResourceException, LessThanZeroException {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Carriage> allcarriage1=new ArrayList<>();
		allcarriage1.add(new Carriage("AS01","二等座",100,"2011"));
		allcarriage1.add(new Carriage("AS02","二等座",100,"2011"));
		
		train1.setresource(allcarriage1);
		assertEquals(false,train1.deleteresource(new Carriage("A01","二等座",100,"2011")));
		assertEquals(true,train1.deleteresource(new Carriage("AS02","二等座",100,"2011")));
	}
	
	
	/* Testing strategy
	 * 测试whetherblockable方法
	 * 测试返回值即可
     */
	@Test
	public void whetherblockabletest() {
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		assertEquals(true,train1.whetherblockable());
	}
	
	/* Testing strategy
	 * 测试settimeslot方法
	 * 按照时间是否已被设置划分：已被设置，未被设置
     * 覆盖每个取值如下：
     */
	@Test
	public void settimeslottest() throws ParseException, BeginEndTimeException, ConflictTimeException{
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Timeslot> alltime1=new ArrayList<>();
		alltime1.add(new Timeslot("2020-01-01 16:45","2020-01-01 18:30"));
		alltime1.add(new Timeslot("2020-01-01 18:40","2020-01-01 20:30"));
	
		assertEquals(true,train1.settimeslot(alltime1));
		assertEquals(false,train1.settimeslot(alltime1));
	}
	
	/* Testing strategy
	 * 测试gettimeslot方法
	 * 测试返回值即可
     */
	@Test
	public void gettimeslottest() throws ParseException, BeginEndTimeException, ConflictTimeException{
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Timeslot> alltime1=new ArrayList<>();
		alltime1.add(new Timeslot("2020-01-01 16:45","2020-01-01 18:30"));
		alltime1.add(new Timeslot("2020-01-01 18:40","2020-01-01 20:30"));
		train1.settimeslot(alltime1);
	
		assertEquals(alltime1,train1.gettimeslot());
	}
	
	/* Testing strategy
	 * 测试trainblocklot方法
	 * 按照站点总数划分：不大于2，大于2
	 * 按照是否是中间站点划分：是中间站点，不是中间站点
     * 覆盖每个取值如下：
     */
	@Test
	public void trainblocktest() throws SameLocationException {
		String weidu="北纬40度",jingdu="东经112度";
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Location> alllocation1=new ArrayList<>();
		alllocation1.add(new FlightTrainLocation(weidu,jingdu,"北京"));
		alllocation1.add(new FlightTrainLocation(weidu,jingdu,"武汉"));
		train1.setlocations(alllocation1);
		
		assertEquals(-1,train1.trainblock("武汉"));
		
		MultipleLacationEntryImpl a8;
		MultipleSortedResourceEntryImpl<Carriage> b8;
		BlockableEntryImpl c8;
		a8=new MultipleLacationEntryImpl();
		b8=new MultipleSortedResourceEntryImpl<Carriage>();
		c8=new BlockableEntryImpl();
		PlanningEntry origintrain2=new TrainEntryFactory().getTrainEntry(a8, b8, c8); //用工厂方法创建对象
		TrainEntry<Carriage> train2=(TrainEntry<Carriage>)origintrain2;
		
		List<Location> alllocation2=new ArrayList<>();
		alllocation2.add(new FlightTrainLocation(weidu,jingdu,"北京"));
		alllocation2.add(new FlightTrainLocation(weidu,jingdu,"武汉"));
		alllocation2.add(new FlightTrainLocation(weidu,jingdu,"南京"));
		train2.setlocations(alllocation2);
		
		assertEquals(-1,train2.trainblock("北京"));
		assertEquals(-1,train2.trainblock("南京"));
		assertEquals(1,train2.trainblock("武汉"));
	}
	
	/* Testing strategy
	 * 测试compareTo方法
	 * 按照起始时间大小划分：大于，等于，小于
     * 覆盖每个取值如下：
     */
	@Test
	public void compareTotest() throws ParseException, BeginEndTimeException, ConflictTimeException{
		MultipleLacationEntryImpl a7;
		MultipleSortedResourceEntryImpl<Carriage> b7;
		BlockableEntryImpl c7;
		a7=new MultipleLacationEntryImpl();
		b7=new MultipleSortedResourceEntryImpl<Carriage>();
		c7=new BlockableEntryImpl();
		PlanningEntry origintrain1=new TrainEntryFactory().getTrainEntry(a7, b7, c7); //用工厂方法创建对象
		TrainEntry<Carriage> train1=(TrainEntry<Carriage>)origintrain1;
		
		List<Timeslot> alltime1=new ArrayList<>();
		alltime1.add(new Timeslot("2020-01-01 16:45","2020-01-01 18:30"));
		alltime1.add(new Timeslot("2020-01-01 18:40","2020-01-01 20:30"));
		train1.settimeslot(alltime1);
		
		MultipleLacationEntryImpl a8;
		MultipleSortedResourceEntryImpl<Carriage> b8;
		BlockableEntryImpl c8;
		a8=new MultipleLacationEntryImpl();
		b8=new MultipleSortedResourceEntryImpl<Carriage>();
		c8=new BlockableEntryImpl();
		PlanningEntry origintrain2=new TrainEntryFactory().getTrainEntry(a8, b8, c8); //用工厂方法创建对象
		TrainEntry<Carriage> train2=(TrainEntry<Carriage>)origintrain2;
		
		List<Timeslot> alltime2=new ArrayList<>();
		alltime2.add(new Timeslot("2020-01-01 14:45","2020-01-01 18:30"));
		alltime2.add(new Timeslot("2020-01-01 18:40","2020-01-01 20:30"));
		train2.settimeslot(alltime2);
		
		MultipleLacationEntryImpl a9;
		MultipleSortedResourceEntryImpl<Carriage> b9;
		BlockableEntryImpl c9;
		a9=new MultipleLacationEntryImpl();
		b9=new MultipleSortedResourceEntryImpl<Carriage>();
		c9=new BlockableEntryImpl();
		PlanningEntry origintrain3=new TrainEntryFactory().getTrainEntry(a9, b9, c9); //用工厂方法创建对象
		TrainEntry<Carriage> train3=(TrainEntry<Carriage>)origintrain3;

		List<Timeslot> alltime3=new ArrayList<>();
		alltime3.add(new Timeslot("2020-01-01 14:45","2020-01-01 18:30"));
		alltime3.add(new Timeslot("2020-01-01 18:40","2020-01-01 20:30"));
		train3.settimeslot(alltime3);
		
		assertEquals(1,train1.compareTo(train2));
		assertEquals(0,train2.compareTo(train3));
		assertEquals(-1,train2.compareTo(train1));
	}

}
