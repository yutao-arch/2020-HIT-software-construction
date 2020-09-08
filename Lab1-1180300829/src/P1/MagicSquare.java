package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class MagicSquare {
	public static void main(String[] args) {
		System.out.println("下面对五个提供的文件进行是否是MagicSquare的检测，若是返回true，不是返回false并说明原因：");
		System.out.println("文件一的结果为"+MagicSquare.isLegalMagicSquare("src/P1/txt/1.txt")+"\n");
		System.out.println("文件二的结果为"+MagicSquare.isLegalMagicSquare("src/P1/txt/2.txt")+"\n");
		System.out.println("文件三的结果为"+MagicSquare.isLegalMagicSquare("src/P1/txt/3.txt")+"\n");
		System.out.println("文件四的结果为"+MagicSquare.isLegalMagicSquare("src/P1/txt/4.txt")+"\n");
		System.out.println("文件五的结果为"+MagicSquare.isLegalMagicSquare("src/P1/txt/5.txt")+"\n");
		int a;
		while(true)
		{
			Scanner in=new Scanner(System.in);
			System.out.println("下面请你输入自己想实现的MagicSquare的宽度：");
			a=in.nextInt();
			if(a%2==0) {
				System.out.println("以偶数"+a+"为长创建不了MagicSquare，返回值为"+MagicSquare.generateMagicSquare(a)+"\n");
			}
			else if(a<0) {
				System.out.println("以负数"+a+"为长创建不了MagicSquare，返回值为"+MagicSquare.generateMagicSquare(a)+"\n");
			}
			else {
				System.out.println("以"+a+"实现的MagicSquare如下，且已存入6.txt");
				MagicSquare.generateMagicSquare(a);
				System.out.println("然后对文件六进行是否是MagicSquare的检测，结果如下：");
				System.out.println(MagicSquare.isLegalMagicSquare("src/P1/txt/6.txt")+"\n");
			}
			
		}
	}//主函数，对几种文本文件进行判断
	
	public static boolean isLegalMagicSquare(String fileName) {
		int hang=-1; //定义行数
		File myfile=new File(fileName);
		try {
	    	//得到行数
	    	BufferedReader yes=new BufferedReader(new FileReader(myfile));
			String mytemp1=null;
			do {
				hang++; //得到行数
			}while((mytemp1=yes.readLine())!=null);
			yes.close();
			
			//定义第一种错误：行列数不相等、并非矩阵
			@SuppressWarnings("resource")
			BufferedReader faultone=new BufferedReader(new FileReader(myfile));
			String mytemp2=null;
			String[] everyhang;
			int lie;
			while((mytemp2=faultone.readLine())!=null) {
				everyhang=mytemp2.split("\t");   //按照制表符把一行的字符分开装到数组里
				lie=everyhang.length;
				if(lie!=hang) {
					System.out.println(fileName+"行列数不相等，不能构成矩阵");
					return false;
				}
			}
			everyhang=null;
			faultone.close();
			
			//定义第二种错误：矩阵中某些数字不是正整数
		    @SuppressWarnings("resource")
			BufferedReader faulttwo=new BufferedReader(new FileReader(myfile));
		   	String mytemp3=null;
		    int i=0,j;
	    	while((mytemp3=faulttwo.readLine())!=null) {
			    everyhang=mytemp3.split("\t");
		    	lie=everyhang.length;
		   		for(j=0;j<lie;j++) {
		    		if(everyhang[j].contains(".")||everyhang[j].contains("-")) {
			    		System.out.println(fileName+"存在不是正整数的数");
			    		return false;
				   	}
			   	 }
		    	i++;
		   	}
		   	everyhang=null;
		   	faulttwo.close();
		   	
		    //定义第三种错误：若出现不是\t作为分隔符抛出异常	
			int[][] juzhen=new int[hang][hang];
			i=0;
		   	try {
		   	    BufferedReader faultthree=new BufferedReader(new FileReader(myfile));
		   		mytemp3=null;
		    	while((mytemp3=faultthree.readLine())!=null) {
				    everyhang=mytemp3.split("\t");
			    	lie=everyhang.length;
			   		for(j=0;j<lie;j++) {
			   			juzhen[i][j]=Integer.valueOf(everyhang[j]);
			   		}
			   		i++;
		    	}
		    	faultthree.close();
		     }catch(Exception e) {//Integer.valueOf()方法遇到空格则会触发异常，能够筛选非"\t"的情况
		   	      System.out.println(fileName+"数字之间并非使用\\t分割");
		   	      e.printStackTrace();
                  return false;
		   	    }
			
		   	lie=hang;
		   	int[] sumhang=new int[hang];
		    int[] sumlie=new int[lie];
		    int[] sumdia=new int[2];
		    int temp1,temp2,temp3;
		    for(i=0;i<hang;i++) {
		    	sumhang[i]=0;
		    }
		    for(j=0;j<lie;j++) {
		    	sumlie[j]=0;
		    }
		    sumdia[0]=0;sumdia[1]=0;
		    
		    //计算行，列，对角线的和并判断是否为MagicSquare
		    //计算每一行的和保存在数组sumhang
		    for(i=0;i<hang;i++) {
		    	for(j=0;j<lie;j++) {
		    		sumhang[i]=sumhang[i]+juzhen[i][j];
		    	}
		    }
		    //计算某一列的和保存在sumlie
		    for(i=0;i<lie;i++) {
		    	for(j=0;j<hang;j++) {
		    		sumlie[i]=sumlie[i]+juzhen[j][i];
		    	}
		    }
		    //计算对角线的和保存在sumdia
		    for(j=0;j<hang;j++) {
		    	sumdia[0]=sumdia[0]+juzhen[j][j];
		    	sumdia[1]=sumdia[1]+juzhen[j][hang-j-1];
		    	}
		    //判断是否满足MagicSquare的条件
		    temp1=sumhang[0];temp2=sumlie[0];temp3=sumdia[0];
		    if(temp1!=temp2||temp1!=temp3||temp2!=temp3) {
		    	return false;
		    }
		    else {
		    	for(i=1;i<hang;i++) {
			    	if(sumhang[i]!=temp1) {
			    		return false;
			    	}
		    	}
		    	for(i=1;i<lie;i++) {
		    		if(sumlie[i]!=temp2) {
		    			return false;
		    		}
		    	}
		    	if(sumdia[1]!=sumdia[0]) {
		    		return false;
		    	}	    	
		    }    	
		    //定义第三种错误：若出现不是\t作为分隔符抛出异常	
	    }catch(Exception e) { 
               System.out.println(fileName+"文件有问题");
               e.printStackTrace();
               return false;
	    }
		return true;  
	}
	
	@SuppressWarnings("resource")
	public static boolean generateMagicSquare(int n) {
		 if (n % 2 == 0 || n <= 0) {   //判断输入的n是否为负数，若是则返回false
	            return false;
	        }
		 int magic[][] = new int[n][n];
		 int row = 0, col = n / 2, i, j, square = n * n;
		 for (i = 1; i <= square; i++) {  //构造奇数阶MagicSquare
		 magic[row][col] = i;
		 if (i % n == 0)    //斜着填
		 row++;
		 else {
		 if (row == 0)    //上边满了往下构造
		 row = n - 1;
		 else
		 row--;
		 if (col == (n - 1))   //右边满了往左构造
		 col = 0;
		 else
		 col++;
		 }
		 }
		 for (i = 0; i < n; i++) {              
		 for (j = 0; j < n; j++)
		    System.out.print(magic[i][j] + "\t");   //输出MagicSquare
	     System.out.println();
		 }	
		 FileWriter writefile = null;
	     BufferedWriter mywriter = null;
	     try {
	    	    writefile = new FileWriter("src/P1/txt/6.txt");
	            mywriter = new BufferedWriter(writefile);
	        } catch (Exception e) {
	        	 System.out.println("写入出错1");
	        	 e.printStackTrace();
	             return false;
	        }
	        for (i = 0; i < n; i++) {
	            for (j = 0; j < n; j++) {
	                try {
	                    mywriter.write(magic[i][j] + "\t");
	                } catch (Exception e) {
	                	System.out.println("写入出错2");
	                	e.printStackTrace();
	                	 return false;
	                }
	            }
	            try {
	                mywriter.write("\n");
	            } catch (Exception e) {
	            	System.out.println("写入出错3");
	            	e.printStackTrace();
	                return false;
	            }
	        }
	        try {
	            if(mywriter!=null)
	                mywriter.close();
	        } catch (Exception e) {
	        	System.out.println("写入出错4");
	        	e.printStackTrace();
	        	return false;
	        } 
	        return true;
	}
}
	     

			
			
			
