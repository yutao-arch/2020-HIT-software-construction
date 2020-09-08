package EntryState;


public class TrainEndedState implements TrainState{


	static TrainEndedState instance=new TrainEndedState();
	private String nowstate="高铁已抵达终点站(Ended)";
	
	// inmutability类
	// Abstraction function:
	// AF(instance)=该状态的类
	// AF(nowstate)=描述计划项当前状态的字符串
	// Representation invariant:
	// 对输入的指令进行检查，不符合要求抛出异常
	// Safety from rep exposure:
	// 将instance,nowstate设置为private
	
	private TrainEndedState() {
	}

	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	@Override
	public TrainState move(char c) {
		// TODO 自动生成的方法存根
		return null;
	}

	  /**
		 * 得到当前状态
		 * @return 描述当前状态的字符串
		 */
	@Override
	public String gettrainstate() {

		return this.nowstate;
	}

	
}
