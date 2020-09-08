package P1;

import static org.junit.Assert.*;

import org.junit.Test;

public class MagicSquareTest {
	
	/*
	 * Testing strategy
	 * 按照读入文件划分：只需要将五个文件都读入即可
	 */
	@Test
	public void isLegalMagicSquaretest() {
		assertEquals(true, MagicSquare.isLegalMagicSquare("src/P1/txt/1.txt"));
		assertEquals(true, MagicSquare.isLegalMagicSquare("src/P1/txt/2.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/3.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/4.txt"));
		assertEquals(false, MagicSquare.isLegalMagicSquare("src/P1/txt/4.txt"));
	}
	
	/*
	 * Testing strategy
	 * 按照输入数的正负划分：正数，负数
	 * 按照输入数的奇偶划分：奇数，偶数
	 * 按照笛卡尔乘积编写测试如下：
	 */
	@Test
	public void generateMagicSquaretest() {
		assertEquals(false, MagicSquare.generateMagicSquare(2));
		assertEquals(false, MagicSquare.generateMagicSquare(-3));
		assertEquals(true, MagicSquare.generateMagicSquare(5));
		assertEquals(false, MagicSquare.generateMagicSquare(-4));
		
	}
}
