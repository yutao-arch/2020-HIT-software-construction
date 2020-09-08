package PlanningEntry;


public class OneDistinguishResourceEntryImpl<R> implements OneDistinguishResourceEntry<R>{

	private R mycource; //资源
	
	// mutability类
	// Abstraction function:
	// AF(mycource)=该资源
	// Representation invariant:
	// 输入的资源不能为空
	// Safety from rep exposure:
	// 将mycource设置为private
	
	/**
	 * 设置该资源
	 * @param a 该资源
	 * @return 是否成功设置该资源
	 */
	@Override
	public boolean setresource(R a) {
		if(mycource==null) {
		    this.mycource=a;	
		    System.out.println("资源设置成功");
		    return true;
		}
		System.out.println("资源只能设置一次");
		return false;	
	}

	/**
	 * 得到该教师资源
	 * @return 该教师资源
	 */
	@Override
	public R getresource() {
		return mycource;
	}

	/**
	 * 更改该资源
	 * @param a 更改后的资源
	 * @return 是都成功更改该资源
	 */
	@Override
	public boolean changeresource(R a) {
		if(a.equals(mycource)) {
			System.out.println("与原资源重复");
			return false;
		}
		this.mycource=a;
		System.out.println("资源更改成功");
		return true;
	}

}
