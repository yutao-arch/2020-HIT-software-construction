package Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	/**
	 * 检查一个文件中读入的航班字符串格式是否符合报告要求
	 * @param S 待判断的字符串
	 * @return 符合要求返回true，不符合返回false
	 */
	 public boolean checkwhethercorrect(String S){
			//检查时间的格式是否符合报告要求
		   	Pattern pattern1 = Pattern.compile("((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])"
		    		+ "|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])"
		    		+ "|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))"
		    		+ "|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|"
		    		+ "([1-2][0-9])|(3[01])))"
		    		+ "|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))"
		    		+ "|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])"
		    		+ "|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|)))");
		   	//检查总体的格式是否符合报告要求
		   	Pattern pattern2 = Pattern.compile("((Flight):(20[012][0-9]-[01][0-9]-[0123][0-9]),([A-Z][A-Z]((\\d{2})|"
		   			+ "(\\d{3})|(\\d{4})))\n\\{\n(DepartureAirport):([a-zA-z]+)\n(ArrivalAirport):([a-zA-z]+)"
		   			+ "\n(DepatureTime):(20[012][0-9]-[01][0-9]-[0123][0-9])(\\s[012][0-9]:[0-6][0-9])\n(ArrivalTime)"
		   			+ ":(20[012][0-9]-[01][0-9]-[0123][0-9])(\\s[012][0-9]:[0-6][0-9])\n(Plane):([BN]\\d{4})\n\\{\n(Type)"
		   			+ ":([A-Za-z0-9]+)\n(Seats:)(([5-9][0-9])|([1-5][0-9][0-9])|(600))\n(Age):(([0-9]|([1-2][0-9])|(30))"
		   			+ "(\\.[0-9])?)\n\\}\n\\}\n)");
		   	Matcher tomacher = pattern1.matcher(S);
		   	if(tomacher.matches()) {
		   		tomacher = pattern2.matcher(S);
		    	if(tomacher.matches())
		    		return true;
		   	}
		   	return false;
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
