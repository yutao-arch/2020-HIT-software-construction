package debug;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FlightClientTest {

	/* Testing strategy
	 * 测试planeAllocation方法
	 * 按照航班集合是否有重叠时间段划分：有，没有
	 * 按照航班数和飞机数划分：相等，不相等
     * 覆盖每个取值如下：
     */
	@Test
	public void planeAllocationtest() throws ParseException {
		FlightClient test=new FlightClient();
	    Plane a1=new Plane();
	    Plane a2=new Plane();
	    Plane a3=new Plane();
	    Flight f1=new Flight();
	    Flight f2=new Flight();
	    Flight f3=new Flight();
	    a1.setPlaneAge(1.5);
	    a2.setPlaneAge(2.5);
	    a3.setPlaneAge(3.5);
	    a1.setPlaneNo("S1");
	    a2.setPlaneNo("S2");
	    a3.setPlaneNo("S3");
	    a1.setPlaneType("A350");
	    a2.setPlaneType("B787");
	    a3.setPlaneType("C929");
	    a1.setSeatsNum(200);
	    a2.setSeatsNum(200);
	    a3.setSeatsNum(200);
	    Calendar departTime4= Calendar.getInstance(); 
	    departTime4.set(2020, 1, 1, 8, 0);
	    Calendar arrivalTime4= Calendar.getInstance(); 
	    arrivalTime4.set(2020, 1, 1, 10, 0);
	    f1.setDepartTime(departTime4);
	    f1.setArrivalTime(arrivalTime4);
	    Calendar departTime5= Calendar.getInstance(); 
	    departTime5.set(2020, 1, 1, 14, 0);
	    Calendar arrivalTime5= Calendar.getInstance(); 
	    arrivalTime5.set(2020, 1, 1, 18, 0);
	    f2.setDepartTime(departTime5);
	    f2.setArrivalTime(arrivalTime5);
	    Calendar departTime6= Calendar.getInstance(); 
	    departTime6.set(2020, 1, 1, 7, 0);
	    Calendar arrivalTime6= Calendar.getInstance(); 
	    arrivalTime6.set(2020, 1, 1, 9, 0);
	    f3.setDepartTime(departTime6);
	    f3.setArrivalTime(arrivalTime6);
	    List<Plane> planes=new ArrayList<>();
	    List<Flight> flights=new ArrayList<>();
	    planes.add(a2);
	    flights.add(f2);
	    flights.add(f3);
	    assertEquals(true,test.planeAllocation(planes, flights));
	    
	    f1=new Flight();
	    f2=new Flight();
	    f3=new Flight();
	    Calendar departTime1= Calendar.getInstance(); 
	    departTime1.set(2020, 1, 1, 8, 0);
	    Calendar  arrivalTime1= Calendar.getInstance(); 
	    arrivalTime1.set(2020, 1, 1, 10, 0);
	    f1.setDepartTime(departTime1);
	    f1.setArrivalTime(arrivalTime1);
	    Calendar  departTime2= Calendar.getInstance(); 
	    departTime2.set(2020, 1, 1, 8, 0);
	    Calendar arrivalTime2= Calendar.getInstance(); 
	    arrivalTime2.set(2020, 1, 1, 9, 0);
	    f2.setDepartTime(departTime2);
	    f2.setArrivalTime(arrivalTime2);
	    Calendar departTime3= Calendar.getInstance(); 
	    departTime3.set(2020, 1, 1, 7, 0);
	    Calendar arrivalTime3= Calendar.getInstance(); 
	    arrivalTime3.set(2020, 1, 1, 9, 0);
	    f3.setDepartTime(departTime3);
	    f3.setArrivalTime(arrivalTime3);
	    planes=new ArrayList<>();
	    flights=new ArrayList<>();
	    planes.add(a1);
	    planes.add(a2);
	    flights.add(f1);
	    flights.add(f2);
	    flights.add(f3);
	    assertEquals(false,test.planeAllocation(planes, flights));
	    
		
	}

}
