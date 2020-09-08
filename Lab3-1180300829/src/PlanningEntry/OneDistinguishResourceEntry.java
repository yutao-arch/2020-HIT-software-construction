package PlanningEntry;

public interface OneDistinguishResourceEntry<R> {

	/**
	 * 设置该资源
	 * @param a 该资源
	 * @return 是否成功设置该资源
	 */
	public boolean setresource(R a);
	
	/**
	 * 得到该资源
	 * @return 该资源
	 */
	public R getresource();
	
	/**
	 * 更改该资源
	 * @param a 更改后的资源
	 * @return 是都成功更改该资源
	 */
	public boolean changeresource(R a);


}
