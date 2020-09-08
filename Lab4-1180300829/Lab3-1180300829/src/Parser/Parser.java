package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exception.*;

public class Parser {

	/**
	 * 检查一个文件中读入的航班字符串格式是否符合报告要求
	 * @param S 待判断的字符串
	 * @return 符合要求返回true，不符合返回false
	 * @throws ArrayIndexOutOfBoundsException 若缺少日期或航班号抛出ArrayIndexOutOfBoundsException异常
	 * @throws DateException 航班号在日期前面出现或者日期格式不符合yyyy-MM-dd 
	 * @throws FlightNumberException 航班号不符合两位大写字母和2-4位数字构成
	 * @throws FromTimeException 出发时间不符合yyyy-MM-dd HH:mm
	 * @throws ToTimeException 抵达时间不符合yyyy-MM-dd HH:mm
	 * @throws PlaneIdException 机编号不符合第一位为N或B，后面是四位数字
	 * @throws PlaneTypeException 机型不符合大小写字母或数字构成，不含有空格或其他符号
	 * @throws PlaneSeatsException 座位数不符合正整数且范围为[50,600]
	 * @throws PlaneAgeException 机龄不符合范围为[5,30]，最多为1位小数或无小数
	 */
	 public void checkwhethercorrect(String S)throws ArrayIndexOutOfBoundsException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		
		 String[] all=S.split("\n");
		 String[] temp=all[0].split(",");  //若缺少日期或航班号抛出ArrayIndexOutOfBoundsException异常
		 Pattern pattern00=Pattern.compile("(Flight):(20[012][0-9]-[01][0-9]-[0123][0-9])");  
		 Pattern pattern01=Pattern.compile("([A-Z][A-Z]((\\d{2})|(\\d{3})|(\\d{4})))");
		 Pattern pattern4=Pattern.compile("(DepatureTime):(20[012][0-9]-[01][0-9]-[0123][0-9])(\\s[012][0-9]:[0-6][0-9])");
		 Pattern pattern5=Pattern.compile("(ArrivalTime):(20[012][0-9]-[01][0-9]-[0123][0-9])(\\s[012][0-9]:[0-6][0-9])");
		 Pattern pattern6=Pattern.compile("(Plane):([BN]\\d{4})");
		 Pattern pattern8=Pattern.compile("(Type):([A-Za-z0-9]+)");
		 Pattern pattern9=Pattern.compile("(Seats):(([5-9][0-9])|([1-5][0-9][0-9])|(600))");
		 Pattern pattern10=Pattern.compile("(Age):(([0-9]|([1-2][0-9])|(30))(\\.[0-9])?)");
		 Matcher tomacher = pattern00.matcher(temp[0]);
		 if(!tomacher.matches()) {  //航班号在日期前面出现或者日期格式不符合yyyy-MM-dd 
			 throw new DateException();
		 }
		 tomacher=pattern01.matcher(temp[1]);
		 if(!tomacher.matches()) {  //航班号不符合两位大写字母和2-4位数字构成
			 throw new FlightNumberException();
		 }
		 tomacher=pattern4.matcher(all[4]);
		 if(!tomacher.matches()) {  //出发时间不符合yyyy-MM-dd HH:mm
			 throw new FromTimeException();
		 }
		 tomacher=pattern5.matcher(all[5]);
		 if(!tomacher.matches()) {  //抵达时间不符合yyyy-MM-dd HH:mm
			 throw new ToTimeException();
		 }
		 tomacher=pattern6.matcher(all[6]);
		 if(!tomacher.matches()) {  //飞机编号不符合第一位为N或B，后面是四位数字
			 throw new PlaneIdException();
		 }
		 tomacher=pattern8.matcher(all[8]);
		 if(!tomacher.matches()) {  //机型不符合大小写字母或数字构成，不含有空格或其他符号
			 throw new PlaneTypeException();
		 }
		 tomacher=pattern9.matcher(all[9]);
		 if(!tomacher.matches()) {  //座位数不符合正整数且范围为[50,600]
			 throw new PlaneSeatsException();
		 }
		 tomacher=pattern10.matcher(all[10]);
		 if(!tomacher.matches()) {  //机龄不符合范围为[0,30]，最多为1位小数或无小数
			 throw new PlaneAgeException();
		 }
	 }	 	
	 
	 /**
	  * 得到一个字符串后面的所有字符串
	  * @param name 前面的字符串
	  * @param S 整个字符串
	  * @return 整个字符串除去前面字符串后的字符串
	  */
	 public String getAllinformation(String name,String S) {
			Pattern pattern = Pattern.compile("(?<="+name+").+");
			Matcher mc = pattern.matcher(S);
			while(mc.find())
				return mc.group();
			return "";
		}
}
