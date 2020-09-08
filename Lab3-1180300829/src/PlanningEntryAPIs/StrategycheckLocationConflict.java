package PlanningEntryAPIs;

import java.util.List;

import PlanningEntry.PlanningEntry;

public interface StrategycheckLocationConflict {
	
	/**
	 * 检测一组计划项之间是否存在位置独占冲突
	 * @param entries 计划项集合
	 * @return 是否存在位置独占冲突
	 */
	public boolean checkLocationConflict(List<PlanningEntry> entries);

}
