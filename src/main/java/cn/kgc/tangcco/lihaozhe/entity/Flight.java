package cn.kgc.tangcco.lihaozhe.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import lombok.Data;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: Flight
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午5:34:58 <br>
 *          类描述: 航班实体类
 */
@Data
public class Flight implements Comparable{
	/**
	 *	 航班编号
	 */
	private String flightNo;
	/**
	 * 	起飞城市
	 */
	private String departureCity;
	/**
	 *	 起飞时间
	 */
	private LocalDateTime departureTime;
	/**
	 * 	到达城市
	 */
	private String arrivalCity;
	/**
	 * 	到达时间
	 */
	private LocalDateTime arrivalTime;
	/**
	 * 	初始化Flight
	 */
	public Flight() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 	初始化Flight 并初始化航班号,出发城市,出发时间,到达城市,到达时间
	 */
	public Flight(String flightNo, String departureCity, LocalDateTime departureTime, String arrivalCity, LocalDateTime arrivalTime) {
		super();
		this.flightNo = flightNo;
		this.departureCity = departureCity;
		this.departureTime = departureTime;
		this.arrivalCity = arrivalCity;
		this.arrivalTime = arrivalTime;
	}
	public static void main(String[] args) {
		//时间转字符串格式化
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
		 System.out.println(dateTime);
		 
		//字符串转时间
		String dateTimeStr = "2018-07-28 14:11:15";
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime1 = LocalDateTime.parse(dateTimeStr, df);
		System.out.println(dateTime1);
	}
	@Override
	public int compareTo(Object o) {
		Flight flight = (Flight) o;
		if(this.getDepartureTime().isAfter(flight.getDepartureTime())) {
			return 1;
		}else if(this.getDepartureTime().isBefore(flight.getDepartureTime())){
			return -1;
		}else {
			return 0;
		}
	}
}
