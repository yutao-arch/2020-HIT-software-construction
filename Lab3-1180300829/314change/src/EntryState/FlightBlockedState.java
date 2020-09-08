package EntryState;

public class FlightBlockedState implements FlightState{

	 static FlightBlockedState instance=new FlightBlockedState();
	 private String nowstate="航班中途阻塞(Blocked)";
	 
	 private FlightBlockedState() {
		};
		
		/**
		 * 改变当前状态
		 * @param c 改变指令
		 * @return 新的状态
		 */
		@Override
		public FlightState move(char c) {
			switch(c) {
			case 'a':
				return FlightRunningState.instance;
			case 'b':
				return FlightCancelledState.instance;
			default:
				throw new IllegalArgumentException();
			}
		}

		/**
		 * 得到当前状态
		 * @return 描述当前状态的字符串
		 */
		@Override
		public String getflightstate() {
			// TODO 自动生成的方法存根
			return this.nowstate;
		}
}
