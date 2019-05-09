package cn.kgc.tangcco.lihaozhe.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: Util
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午5:58:50 <br>
 *          类描述:时间字符串转换
 */
public abstract class LocalDateTimeUtil {
	/**
	 * 预定义本地时间字符串输出格式
	 */
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * @return 按照预定义本地时间字符串输出格式返回系统当前本地时间的字符串
	 */
	public static String getLocalDateTimeString() {
		/**
		 * 使用北京时间
		 */
		return LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
	}

	/**
	 * @param localDateTimeString 本地时间字符串
	 * @return 将传入按照预定义本地时间字符串输出格式的i字符串转换成系统当前本地时间并将将转换后的本地时间返回
	 */
	public static LocalDateTime parse(String localDateTimeString) {
		return LocalDateTime.parse(localDateTimeString, formatter);
	}

	/**
	 * @param localDateTime 本地时间
	 * @return 照预定义本地时间字符串输出格式返回本地时间的字符串
	 */
	public static String toFormatString(LocalDateTime localDateTime) {
		return localDateTime.format(formatter);
	}
	public static void main(String[] args) {
		LocalDateTime time = LocalDateTime.now();
		// String localTime = formatter.format(time);
		// LocalDateTime ldt = LocalDateTime.parse("2019-09-28 17:07:05",formatter);
		System.out.println("LocalDateTime转成String类型的时间："+toFormatString(time));
		String timeString = "2019-09-28 17:07:05";
		System.out.println("String类型的时间转成LocalDateTime："+parse(timeString));
		if(LocalDateTime.now().isAfter(parse(timeString))) {
			System.out.println("1");
		}else {
			System.out.println("2");
		}
	}
}
