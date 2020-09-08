package EntryState;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlightStateTest {

	/* Testing strategy
	 * 通过状态的转换中完成所有Flight的state的方法的测试
     */
	@Test
	public void allmethodtest() {
		FlightState state=FlightWaitingState.instance;
	    assertEquals("航班未分配飞机(Waiting)",state.getflightstate());
	    
	    state=state.move('a');
	    assertEquals("航班已分配飞机(Allocated)",state.getflightstate());
	    
	    state=state.move('a');
	    assertEquals("航班已起飞(Running)",state.getflightstate());
	    
	    state=state.move('a');
	    assertEquals("航班已降落(Ended)",state.getflightstate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=FlightWaitingState.instance;
	    state=state.move('b');
	    assertEquals("航班已取消(Cancelled)",state.getflightstate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=FlightWaitingState.instance;
	    state=state.move('a');
	    state=state.move('b');
	    assertEquals("航班已取消(Cancelled)",state.getflightstate());
	}
}
