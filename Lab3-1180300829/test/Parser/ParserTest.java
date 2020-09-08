package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

	/* Testing strategy
	 * 测试checkwhethercorrect方法
	 * 按照字符串是否符合要求划分：符合，不符合(测试所有情况)
     * 覆盖每个取值如下：
     */
	@Test
	public void checkwhethercorrecttest() {
		Parser temp=new Parser();
		assertEquals(true,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
		"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
				+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
		+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
				));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,A018"+"\n"+
		"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
				+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
		+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018111"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-1-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-1-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-1-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:A6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A 340"+"\n"+"Seats:332"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:700"
				+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:02.0"+"\n"+"}"+"\n"+"}"+"\n"
						));
		
		assertEquals(false,temp.checkwhethercorrect("Flight:2020-01-16,AA018"+"\n"+
				"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
						+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
				+"\n"+"Age:2.12"+"\n"+"}"+"\n"+"}"+"\n"
						));
	}
	
	/* Testing strategy
	 * 测试getAllinformation方法
	 * 测试某个字符串的返回值即可
     */
	@Test
	public void getAllinformationtest() {
		Parser temp=new Parser();
		assertEquals("2020-01-16,AA018",temp.getAllinformation("Flight:", "Flight:2020-01-16,AA018"+"\n"+
		"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
				+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
		+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"));
	}
}
