package EntryState;

public class TrainContext {

    private TrainState state;
    
    // mutability类
 	// Abstraction function:
 	// AF(state)=一个状态
 	// Representation invariant:
 	// 对输入的指令进行检查，不符合要求抛出异常
 	// Safety from rep exposure:
 	// 将state设置为private
	
    /**
     * 构造方法
     * @param s 初始状态
     */
	public TrainContext(TrainState s) {
		state=s;
	}
	
	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	public TrainState move(char c) {
		state=state.move(c);
		return state;
	}
	
	/**
	 * 得到当前状态
	 * @return 当前状态
	 */
	public TrainState getstate() {
		return this.state;
	}
}
