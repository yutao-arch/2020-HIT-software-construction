package PlanningEntry;

import java.util.ArrayList;
import java.util.List;

import Location.*;

public class MultipleLacationEntryImpl implements MultipleLocationEntry {

	private List<Location> locations; //一组位置
	
	// mutability类
	// Abstraction function:
	// AF(mytrain)=一组位置
	// Representation invariant:
	// 输入的位置集合不能为空
	// Safety from rep exposure:
	// 将locations设置为private
	
	/**
	 * 设置一组位置
	 * @param locations 待设置的一组位置
	 * @return 是否成功设置这一组位置
	 */
	@Override
	public boolean setlocations(List<Location> mylocations) {
		if(locations==null&&mylocations!=null) {
			this.locations=new ArrayList<>(mylocations);
			System.out.println("位置设置成功");
			return true;
		}
		System.out.println("位置只能设置一次");
		return false;
	}

	/**
	 * 更改这一组位置
	 * @param mylocations 更改后的位置
	 * @return 是都成功更改该位置
	 */
	@Override
	public boolean changelocations(List<Location> mylocations) {
			System.out.println("位置不可更改");
			return false;
	}

	/**
	 * 得到这一组位置
	 * @return 这一组该位置
	 */
	@Override
	public List<Location> getlocations() {
		return this.locations;
	}
}
