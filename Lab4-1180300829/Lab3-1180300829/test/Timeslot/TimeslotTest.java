package Timeslot;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import Exception.BeginEndTimeException;
import Exception.ComponentsNumberException;
import Exception.DateException;
import Exception.FlightNumberException;
import Exception.FromTimeException;
import Exception.PlaneAgeException;
import Exception.PlaneIdException;
import Exception.PlaneSeatsException;
import Exception.PlaneTypeException;
import Exception.ToTimeException;
import Parser.Parser;
import Resource.Teacher;

public class TimeslotTest {
	
	

	/* Testing strategy
	 * 测试getbegintime方法
     * 测试起始时间的返回值即可
     */
	@Test
	public void getbegintimetest() throws ParseException, BeginEndTimeException {
		Timeslot temp=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		Calendar begintime= Calendar.getInstance(); 
		begintime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-01-01 15:45"));
		assertEquals(begintime,temp.getbegintime());
	}
	
	/* Testing strategy
	 * 测试getendtime方法
     * 测试结束时间的返回值即可
     */
	@Test
	public void getendtimetest() throws ParseException, BeginEndTimeException {
		Timeslot temp=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		Calendar endtime= Calendar.getInstance(); 
		endtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-01-01 17:30"));
		assertEquals(endtime,temp.getendtime());
	}
	
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的时间类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() throws ParseException, BeginEndTimeException {
		Timeslot temp=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		Timeslot temp1=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		assertEquals(temp.hashCode(),temp1.hashCode());
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个时间是否相同划分等价类：时间相同，时间不同
     */
	@Test
	public void equalstest() throws ParseException, BeginEndTimeException {
		Timeslot temp=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		Timeslot temp1=new Timeslot("2020-01-01 12:45","2020-01-01 17:30");
		Timeslot temp2=new Timeslot("2020-01-01 15:45","2020-01-01 17:30");
		assertEquals(false,temp.equals(temp1));
		assertEquals(true,temp.equals(temp2));
	}
	
	/* Testing strategy
	 * 测试创建时的异常
     */
	 @Test(expected = BeginEndTimeException.class)
	 public void shouldGetBeginEndTimeException() throws ParseException, BeginEndTimeException {
		 Timeslot temp=new Timeslot("2020-01-02 15:45","2020-01-01 17:30");	 
		 temp.hashCode();
	 }

}
