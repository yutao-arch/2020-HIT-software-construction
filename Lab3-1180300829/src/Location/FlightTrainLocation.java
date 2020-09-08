package Location;

public class FlightTrainLocation extends CommonLocation{

	/**
	 * 构造方法
	 * @param longitude 纬度
	 * @param latitude 经度
	 * @param locationname 该位置名称
	 */
	public FlightTrainLocation(String longitude, String latitude, String locationname) {
		super(longitude, latitude, locationname);
	}

	/**
	 * 判断该位置是否可共享
	 * @return 可共享返回true，不可共享返回false
	 */
	public boolean whethershare() {
		System.out.println("位置可共享");
		return true;
	}
}
