package Location;

public interface Location {

	/**
	 * 得到该位置的的纬度
	 * @return 该位置的纬度
	 */
	public String getlongitude();
	
	/**
	 * 得到该位置的的经度
	 * @return 该位置的经度
	 */
	public String getlatitude();
	
	/**
	 * 得到该位置的的名称
	 * @return 该位置的名称
	 */
	public String getlocationname();
	
}
