package Timeslot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Timeslot {

	private final Calendar begintime;  //一组时间对的初始时间
	private final Calendar endtime;    //一组时间对的终止时间
	
    //immutability类
	// Abstraction function:
    // AF(begintime)=一组时间对的初始时间
	// AF(endtime)=一组时间对的终止时间
	// Representation invariant:
    // 时间格式必须是yyyy-MM-dd HH:mm
    // Safety from rep exposure:
    // 将begintime,endtime设置为private final
	
	/**
	 * 构造方法
	 * @param begin 初始时间字符串
	 * @param end   终止时间字符串
	 * @throws ParseException 时间格式不为yyyy-MM-dd HH:mm
	 */
	public Timeslot(String begin,String end) throws ParseException{
		this.begintime= Calendar.getInstance(); 
		begintime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(begin));
		this.endtime= Calendar.getInstance(); 
		endtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(end));
		
	}
	
	/**
	 * 返回初始时间
	 * @return 初始时间
	 */
	public Calendar getbegintime() {
		return begintime;
	}
	
	/**
	 * 返回终止时间
	 * @return 终止时间
	 */
	public Calendar getendtime() {
		return endtime;
	}

	/**
	 * 重写hashCode方法
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begintime == null) ? 0 : begintime.hashCode());
		result = prime * result + ((endtime == null) ? 0 : endtime.hashCode());
		return result;
	}

	/**
	 * 重写equals方法
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timeslot other = (Timeslot) obj;
		if (begintime == null) {
			if (other.begintime != null)
				return false;
		} else if (!begintime.equals(other.begintime))
			return false;
		if (endtime == null) {
			if (other.endtime != null)
				return false;
		} else if (!endtime.equals(other.endtime))
			return false;
		return true;
	}

	
}
