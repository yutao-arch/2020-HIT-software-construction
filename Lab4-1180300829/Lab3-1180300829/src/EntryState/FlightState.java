package EntryState;



public interface FlightState {

	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	public FlightState move(char c);
	
	/**
	 * 得到描述当前状态的字符串
	 * @return 描述当前状态的字符串
	 */
	public String getflightstate();
	
}