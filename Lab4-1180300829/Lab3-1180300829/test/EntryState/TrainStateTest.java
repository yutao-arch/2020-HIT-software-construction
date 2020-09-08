package EntryState;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrainStateTest {

	/* Testing strategy
	 * 通过状态的转换中完成所有Train的state的方法的测试
     */
	@Test
	public void allmethodtest() {
		TrainState state=TrainWaitingState.instance;
	    assertEquals("高铁未分配车厢(Waiting)",state.gettrainstate());
	    
	    state=state.move('a');
	    assertEquals("高铁已分配一组车厢(Allocated)",state.gettrainstate());
	    
	    state=state.move('a');
	    assertEquals("高铁已从起始站发车(Running)",state.gettrainstate());
	    
	    state=state.move('a');
	    assertEquals("高铁已抵达终点站(Ended)",state.gettrainstate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=TrainWaitingState.instance;
	    state=state.move('b');
	    assertEquals("高铁被取消(Cancelled)",state.gettrainstate());
	    
	    state=TrainWaitingState.instance;
	    state=state.move('a');
	    state=state.move('b');
	    assertEquals("高铁被取消(Cancelled)",state.gettrainstate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=TrainWaitingState.instance;
	    state=state.move('a');
	    state=state.move('a');
	    state=state.move('b');
	    assertEquals("高铁中途阻塞(Blocked)",state.gettrainstate());
	    state=state.move('a');
	    assertEquals("高铁已从起始站发车(Running)",state.gettrainstate());
	    state=state.move('b');
	    assertEquals("高铁中途阻塞(Blocked)",state.gettrainstate());
	    state=state.move('b');
	    assertEquals("高铁被取消(Cancelled)",state.gettrainstate());
	    
	}

}
