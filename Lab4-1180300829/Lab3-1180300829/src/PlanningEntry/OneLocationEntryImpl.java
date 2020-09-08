package PlanningEntry;

import Location.*;

public class OneLocationEntryImpl implements Cloneable,OneLocationEntry {

	private Location onlylocation; //该位置
	
	    // mutability类
		// Abstraction function:
		// AF(onlylocation)=该位置
		// Representation invariant:
		// 输入的位置不能为空
		// Safety from rep exposure:
		// 将onlylocation设置为private
	
	// TODO checkRep
    private void checkRep() {  //保证位置不为空
    	assert onlylocation!=null:"教室位置为空";
    }
	
	/**
	 * 设置该位置
	 * @param only 该位置
	 * @return 该位置是否设置成功
	 */
	@Override
	public boolean setlocations(Location only) {
		if(onlylocation==null&&only!=null) {
			this.onlylocation=only;	
			System.out.println("位置设置成功");
			checkRep();
			return true;
		}
		System.out.println("位置只能设置一次");
		return false;
	}

	/**
	 * 更改该位置
	 * @param only 更改后的位置
	 * @return 位置是否更改成功
	 */
	@Override
	public boolean changelocations(Location only) {
		    checkRep();
		    if(only.equals(onlylocation)) {
		    	System.out.println("与原位置重复");
		    	return false;
		    }
			this.onlylocation=only;
			System.out.println("教室位置修改(重新设置)成功");
			return true;
	}

	/**
	 * 删除该位置
	 * @param waittodelete 待删除的位置
	 * @return 位置是否删除成功
	 */
	@Override
	public boolean deletelocations(Location waittodelete) {
		if(onlylocation.getlocationname().equals(waittodelete.getlocationname())) {
			onlylocation=null;
			System.out.println("教室位置删除成功");
			return true;
		}
		System.out.println("没有该教室");
		return false;
	}

	/**
	 * 得到该位置
	 * @return 该位置
	 */
	@Override
	public Location getlocations() {
		checkRep();
		return this.onlylocation;
	}

	 @Override
	  public OneLocationEntryImpl clone() { 
		 OneLocationEntryImpl stu = null; 
	    try{ 
	      stu = (OneLocationEntryImpl)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 
}
