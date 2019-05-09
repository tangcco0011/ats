package cn.kgc.tangcco.lihaozhe.action;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import cn.kgc.tangcco.lihaozhe.common.BeanFactory;
import cn.kgc.tangcco.lihaozhe.common.LocalDateTimeUtil;
import cn.kgc.tangcco.lihaozhe.entity.Flight;
import cn.kgc.tangcco.lihaozhe.service.FlightService;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: FlightAction
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月9日 下午1:59:35 <br>
 *          类描述:
 */
public class FlightAction {
	/**
	 * 是否有增加和删除操作的计数器，如果没有执行过操作则计数器为0，如果执行过增加或者删除操作则计数器的值大于0
	 */
	private static int status;
	/**
	 * 初始化业务层
	 */
	private FlightService flightService = (FlightService) BeanFactory.newInstance("flightService");
	/**
	 * 初始化输入设备
	 */
	Scanner scanner = new Scanner(System.in);

	/**
	 * 初始化数据并初始化页面
	 */
	public FlightAction() {
		super();
		flightService.importFromXml("flights.xml");
		option();
	}

	/**
	 * 初始化页面
	 */
	private void print() {
		System.out.println("1 显示所有航班信息（起飞时间升序显示）");
		System.out.println("2 查询航班（起飞时间升序显示）");
		System.out.println("3 添加航班");
		System.out.println("4 删除航班");
		System.out.println("0 退出");
		System.out.print("请输入>>>");
	}

	/**
	 * 页面动作
	 */
	private void option() {
		print();
		switch (scanner.next().trim()) {
		case "1":
			getAllFlight();
			option();
			break;
		case "2":
			queryFlightByCity();
			option();
			break;
		case "3":
			addFlight();
			option();
			break;
		case "4":
			deleteFlight();
			option();
			break;
		case "0":
			exit();
			break;

		default:
			System.out.println("输入有误，系统重置。");
			option();
			break;
		}
	}

	/**
	 * 添加航班
	 */
	private void addFlight() {
		System.out.print("请输入航班编号：");
		String flightNo = scanner.next().trim();
		System.out.print("起飞城市：");
		String departureCity = scanner.next().trim();
		System.out.print("起飞时间（ 格式为：2019-06-06 20:30:00 ）：");
		String dDay = scanner.next();
		String dtime = dDay + scanner.nextLine();
		LocalDateTime departureTime = LocalDateTimeUtil.parse(dtime);
		System.out.print("到达城市：");
		String arrivalCity = scanner.next().trim();
		System.out.print("到达时间（ 格式为：2019-06-06 21:00:00 ）：");
		String aDay = scanner.next();
		String atime = aDay + scanner.nextLine();
		LocalDateTime arrivalTime = LocalDateTimeUtil.parse(atime);
		if (departureTime.isAfter(arrivalTime)) {
			System.out.println("错误的到达时间：" + arrivalTime);
			System.out.println("添加失败");
			return;
		}
		if (flightService.addFlight(new Flight(flightNo, departureCity, departureTime, arrivalCity, arrivalTime)) > 0) {
			status++;
			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}
	}

	/**
	 * 从业务层获取数据 获取所有航班（根据起飞时间升序）
	 */
	private void getAllFlight() {
		SortedSet<Flight> ss = flightService.getAllFlight();
		Iterator<Flight> iterator = ss.iterator();
		Flight flight = null;
		while (iterator.hasNext()) {
			flight = iterator.next();
			System.out.println(flight.getFlightNo() + "," + flight.getDepartureCity() + " - " + flight.getArrivalCity()
					+ "," + LocalDateTimeUtil.toFormatString(flight.getDepartureTime()) + " - "
					+ LocalDateTimeUtil.toFormatString(flight.getArrivalTime()));
		}
	}

	/**
	 * 从业务层获取数据 根据起飞城市和到达城市查询航班（根据起飞时间升序）
	 */
	private void queryFlightByCity() {
		System.out.print("请输入起飞城市：");
		String departureCity = scanner.next().trim();
		System.out.print("请输入到达城市：");
		String arrivalCity = scanner.next().trim();
		SortedSet<Flight> ss = flightService.queryFlightByCity(departureCity, arrivalCity);
		if (ss.size() == 0) {
			System.out.println("没有找到符合条件的航班");
		} else {
			Iterator<Flight> iterator = ss.iterator();
			Flight flight = null;
			System.out.println("查询结果如下：");
			List<Flight> list = new ArrayList<Flight>();
			while (iterator.hasNext()) {
				flight = iterator.next();
				list.add(flight);
				System.out.println(flight.getFlightNo() + "," + flight.getDepartureCity() + " - "
						+ flight.getArrivalCity() + "," + LocalDateTimeUtil.toFormatString(flight.getDepartureTime())
						+ " - " + LocalDateTimeUtil.toFormatString(flight.getArrivalTime()));
			}
			System.out.print("按任意建继续...");
			scanner.next();
			System.out.print("请问是否要将查询结果导出到txt文件，存储请输入y或Y，不存储请按任意键返回：");
			if ("y".equalsIgnoreCase(scanner.next().trim())) {
				System.out.println("正在导出，请稍后...");
				exportToTxt(list);
				System.out.println("导出成功。");
			}
		}
	}

	/**
	 * 通过业务层 将查询结果导出到txt文件
	 */
	private void exportToTxt(List<Flight> list) {
		flightService.exportToTxt(list, "src/main/resources/files/flights.txt");
	}

	/**
	 * 删除的航班编号
	 */
	private void deleteFlight() {
		getAllFlight();
		System.out.print("请输入要删除的航班编号：");
		if (flightService.deleteFlight(scanner.next().trim()) > 0) {
			status++;
			System.out.println("删除成功");
			getAllFlight();
		} else {
			System.out.println("删除失败，航班号不存在");
		}
	}

	/**
	 * 退出系统
	 */
	private void exit() {
		if (status > 0) {
			System.out.println("航班信息有 所调整 是否将调整后的航班信息存储到文件中");
			System.out.println("保存请输入y或者Y，不保存按任意键退出系统");
			System.out.print("请输入>>>");
			if ("y".equalsIgnoreCase(scanner.next().trim())) {
				System.out.println("文件正在保存，请稍后...");
				flightService.saveXml("src/main/resources/flights.xml");
				System.out.println("文件保存成功");
			}
		}
		System.out.println("已退出系统");
		System.exit(0);
	}
}
