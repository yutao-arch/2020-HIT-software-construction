package Location;

public class CommonLocation implements Location{

	private final String longitude;  //位置经度
	private final String latitude;   //位置纬度
	private final String locationname;  //位置名称
	
	// inmutability类
	// Abstraction function:
	// AF(longitude)=该位置经度
	// AF(longitude)=该位置纬度
	// AF(longitude)=该位置名称
	// Representation invariant:
	// 位置经度,位置纬度,位置名称不为空
	// Safety from rep exposure:
	// 将longitude,latitude,locationname设置为private final
	// 由于Sring为inmutable类型，不需要进行defensive copy
	
	// TODO checkRep
    private void checkRep() {  //位置经度,位置纬度,位置名称不为空
          assert longitude!=null;
          assert latitude!=null;
          assert locationname!=null;
    }
	
	/**
	 * 构造方法
	 * @param longitude 纬度
	 * @param latitude 经度
	 * @param locationname 该位置名称
	 */
	public CommonLocation(String longitude,String latitude,String locationname) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.locationname=locationname;
		checkRep();
	}

	/**
	 * 得到该位置的的纬度
	 * @return 该位置的纬度
	 */
	@Override
	public String getlongitude() {
		checkRep();
		return longitude;
	}

	/**
	 * 得到该位置的的经度
	 * @return 该位置的经度
	 */
	@Override
	public String getlatitude() {
		checkRep();
		return latitude;
	}

	/**
	 * 得到该位置的的名称
	 * @return 该位置的名称
	 */
	@Override
	public String getlocationname() {
		checkRep();
		return locationname;
	}

	/**
	 * 重写hashcode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((locationname == null) ? 0 : locationname.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
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
		CommonLocation other = (CommonLocation) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (locationname == null) {
			if (other.locationname != null)
				return false;
		} else if (!locationname.equals(other.locationname))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}

}
