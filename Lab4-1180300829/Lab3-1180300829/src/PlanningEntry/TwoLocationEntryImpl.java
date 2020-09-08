package PlanningEntry;


import Exception.SameLocationException;
import Location.*;

public class TwoLocationEntryImpl implements Cloneable,TwoLocationEntry{

	private Location fromlocation;  //起点站
	private Location tolocation;   //终点站
	
	    // mutability类
		// Abstraction function:
	    // AF(fromlocation)=起点站
		// AF(tolocation)=终点站
		// Representation invariant:
	    // 只能设置一次起点站和终点站，两个机场位置应该不同,位置不为空
	    // Safety from rep exposure:
	    // 将fromlocation,tolocation设置为private
	
	// TODO checkRep
    private void checkRep() {  //保证两个飞机场位置不同
    	assert fromlocation!=null;
    	assert tolocation!=null;
        assert !fromlocation.equals(tolocation):"存在重复的两个飞机场位置\n";
    }
	
	/**
	 * 设置起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 * @throws SameLocationException 存在重复的两个飞机场位置
	 */
	@Override
	public boolean setlocations(Location from, Location to) throws SameLocationException {
		if(from.equals(to)) {
			throw new SameLocationException();  //位置相同抛出异常
		}
		if(fromlocation==null&&tolocation==null) {
			this.fromlocation=from;
			this.tolocation=to;
			System.out.println("位置设置成功");
			checkRep();
			return true;
		}
		System.out.println("位置只能设置一次");
		return false;
	}

	
	/**
	 * 更改起点站和终点站的位置
	 * @param from 起点站的位置
	 * @param to 终点站的位置
	 * @return 是否设置成功
	 */
	@Override
	public boolean changelocations(Location from, Location to) {
		    checkRep();
			System.out.println("位置不可更改");
			return false;
	}

	/**
	 * 得到起点站的位置
	 * @return 起点站
	 */
	@Override
	public Location getfromlocation() {
		checkRep();
		return fromlocation;
	}

	/**
	 * 得到终点站的位置
	 * @return 起点站
	 */
	@Override
	public Location gettolocation() {
		checkRep();
		return tolocation;
	}
	
	 @Override
	  public TwoLocationEntryImpl clone() { 
		 TwoLocationEntryImpl stu = null; 
	    try{ 
	      stu = (TwoLocationEntryImpl)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 
	

}
