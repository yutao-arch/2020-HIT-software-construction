package debug;

import java.util.Calendar;

public class Flight {

	private String flightNo; // 航班号
	private Calendar flightDate; // 航班日期，例如2020-01-01
	private String departAirport; // 航班出发机场
	private String arrivalAirport;// 航班降落机场
	private Calendar departTime;// 航班起飞时间
	private Calendar arrivalTime;// 航班降落时间
	private Plane plane;// 执飞的飞机

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Calendar getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	public String getDepartAirport() {
		return departAirport;
	}

	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Calendar getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Calendar departTime) {
		this.departTime = departTime;
	}

	public Calendar getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Calendar arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getPlaneNo() {
		return this.plane.getPlaneNo();
	}

	public String getPlaneType() {
		return this.plane.getPlaneType();
	}

	public int getSeatsNum() {
		return this.plane.getSeatsNum();
	}

	public double getPlaneAge() {
		return this.plane.getPlaneAge();
	}

	@Override
	public boolean equals(Object another) {
		if (another == null)
			return false;
		if (!(another instanceof Flight))
			return false;
		Flight anotherFlight = (Flight) another;
		if (anotherFlight.getFlightNo().equals(this.getFlightNo())
				&& anotherFlight.getFlightDate().equals(this.getFlightDate()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return this.flightNo.hashCode();
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}
}
