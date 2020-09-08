package PlanningEntry;

import Location.*;

public interface OneLocationEntry {

	/**
	 * 设置该位置
	 * @param only 该位置
	 * @return 该位置是否设置成功
	 */
	public boolean setlocations(Location only);
	
	/**
	 * 更改该位置
	 * @param only 更改后的位置
	 * @return 位置是否更改成功
	 */
	public boolean changelocations(Location only);
	
	/**
	 * 删除该位置
	 * @param waittodelete 待删除的位置
	 * @return 位置是否删除成功
	 */
	public boolean deletelocations(Location waittodelete);
	
	/**
	 * 得到该位置
	 * @return 该位置
	 */
	public Location getlocations();
}
