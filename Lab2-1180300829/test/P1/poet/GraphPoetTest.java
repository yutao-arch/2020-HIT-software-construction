/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    
    /* Testing strategy
     * 按需要读入的文件划分：空文件，一行输入，多行输入
     * 覆盖每个取值如下：
     */
    @Test
    public void testGraphPoet() throws IOException{
    	 final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/empty.txt"));
         final String input = "Test the system.";
         assertEquals("Test the system.",nimoy.poem(input));
         final GraphPoet nimoy1 = new GraphPoet(new File("test/P1/poet/oneline.txt"));
         final String input1 = "Test the system.";
         assertEquals("Test of the system.",nimoy1.poem(input1));
         final GraphPoet nimoy2 = new GraphPoet(new File("test/P1/poet/mutiline.txt"));
         final String input2 = "Theater system am";
         assertEquals("Theater sound system i am",nimoy2.poem(input2));  
    }
    
    /* Testing strategy
     * 按图中边权值划分：权值全为1，两点之间权值有不为1且有几条边
     * 覆盖每个取值如下：
     */
    @Test
    public void testPoem() throws IOException{
    	 final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/oneweight.txt"));
         final String input = "Test the system.";
         assertEquals("Test of the system.",nimoy.poem(input));
         final GraphPoet nimoy1 = new GraphPoet(new File("test/P1/poet/mutiweight.txt"));
         final String input1 = "Test the system";
         assertEquals("Test a the system",nimoy1.poem(input1));  
    }
    
    /* Testing strategy
     * 按需要读入的文件划分：空文件，不是空文件
     * 覆盖每个取值如下：
     */
    @Test
    public void testtoString() throws IOException{
    	 final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/empty.txt"));
         assertEquals("",nimoy.toString());
         final GraphPoet nimoy1 = new GraphPoet(new File("test/P1/poet/oneline.txt"));
         assertEquals("this->is权重为1\n" + "is->a权重为1\n" + "a->test权重为1\n" + "test->of权重为1\n" + "of->the权重为1\n" + "the->mugar权重为1\n" + 
         		      "mugar->omni权重为1\n" + "omni->theater权重为1\n" + "theater->sound权重为1\n" + "sound->system.权重为1\n",nimoy1.toString());
    }
}
