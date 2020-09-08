package Resource;

public class Flight {

	private final String flightnumber;  //飞机编号
	private final String flighttype;    //飞机机型号
	private final int flightallseat;    //座位数
	private final double flightage;     //机龄
	
	    // immutability类
		// Abstraction function:
	    // AF(flightnumber)=飞机编号
		// AF(flighttype)=飞机机型号
		// AF(flightallseat)=座位数
		// AF(flightage)=机龄
		// Representation invariant:
	    // 机龄是小数
		// Safety from rep exposure:
		// 将flightnumber,flighttype,flightallseat,flightage设置为private final
	
	/**
	 * 初始化构造方法
	 * @param a 飞机编号
	 * @param b 飞机机型号
	 * @param c 座位数
	 * @param d 机龄
	 */
	public Flight(String a,String b,int c,double d) {
		this.flightnumber=a;
		this.flighttype=b;
		this.flightallseat=c;
		this.flightage=d;
	}
	
	/**
	 * 返回飞机编号
	 * @return 飞机编号
	 */
	public String getflightnumber() {
		return flightnumber;
	}
	
	/**
	 * 返回飞机机型号
	 * @return 飞机机型号
	 */
	public String getflighttype() {
		return flighttype;
	}
	
	/**
	 * 返回飞机座位数
	 * @return 飞机座位数
	 */
	public int getflightallseat() {
		return flightallseat;
	}
	
	/**
	 * 返回飞机机龄
	 * @return 飞机机龄
	 */
	public double getflightage() {
		return flightage;
	}

	/**
	 * 重写hashcode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(flightage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + flightallseat;
		result = prime * result + ((flightnumber == null) ? 0 : flightnumber.hashCode());
		result = prime * result + ((flighttype == null) ? 0 : flighttype.hashCode());
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
		Flight other = (Flight) obj;
		if (Double.doubleToLongBits(flightage) != Double.doubleToLongBits(other.flightage))
			return false;
		if (flightallseat != other.flightallseat)
			return false;
		if (flightnumber == null) {
			if (other.flightnumber != null)
				return false;
		} else if (!flightnumber.equals(other.flightnumber))
			return false;
		if (flighttype == null) {
			if (other.flighttype != null)
				return false;
		} else if (!flighttype.equals(other.flighttype))
			return false;
		return true;
	}

}
