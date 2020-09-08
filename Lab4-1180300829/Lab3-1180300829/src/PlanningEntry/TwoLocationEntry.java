package PlanningEntry;

import Exception.SameLocationException;
import Location.*;

public interface TwoLocationEntry {
	
	/**
	 * 设置起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 * @throws SameLocationException 两个机场位置相同
	 */
	public boolean setlocations(Location from,Location to) throws SameLocationException;

	/**
	 * 更改起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	public boolean changelocations(Location from,Location to);
	
	/**
	 * 得到起点站的位置
	 * @return 起点站
	 */
	public Location getfromlocation();
	
	/**
	 * 得到终点站的位置
	 * @return 起点站
	 */
	public Location gettolocation();
}
