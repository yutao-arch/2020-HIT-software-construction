package PlanningEntry;

import java.util.List;

import Exception.SameLocationException;
import Location.*;

public interface MultipleLocationEntry {

	/**
	 * 设置一组位置
	 * @param locations 待设置的一组位置
	 * @return 是否成功设置这一组位置
	 * @throws SameLocationException 存在相同位置
	 */
	public boolean setlocations(List<Location> locations) throws SameLocationException;
	
	/**
	 * 更改这一组位置
	 * @param mylocations 更改后的位置
	 * @return 是都成功更改该位置
	 */
	public boolean changelocations(List<Location> mylocations);
	
	/**
	 * 得到这一组位置
	 * @return 这一组该位置
	 */
	public List<Location> getlocations();
	
}
