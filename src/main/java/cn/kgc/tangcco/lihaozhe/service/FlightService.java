package cn.kgc.tangcco.lihaozhe.service;

import java.util.List;
import java.util.SortedSet;

import cn.kgc.tangcco.lihaozhe.entity.Flight;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: FlightService
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午5:44:56 <br>
 *          类描述:航班信息业务层接口
 */
public interface FlightService {
	/**
	 * 从xml文件导入航班
	 * 
	 * @param xmlPath 文件地址
	 */
	void importFromXml(String xmlPath);

	/**
	 * 获取所有航班（根据起飞时间升序）
	 * 
	 * @return 所有航班集合
	 */
	SortedSet<Flight> getAllFlight();

	/**
	 * 根据起飞城市和到达城市查询航班（根据起飞时间升序）
	 * 
	 * @param departureCity 起飞城市
	 * @param arrivalCity   到达城市
	 * @return 查询到的航班集合
	 */
	SortedSet<Flight> queryFlightByCity(String departureCity, String arrivalCity);

	/**
	 * 添加航班
	 * 
	 * @param flight 待添加的航班
	 * @return 是否添加成功1/0
	 */
	int addFlight(Flight flight);

	/**
	 * 删除航班
	 * 
	 * @param flightNo 待删除的航班编号
	 * @return 1：删除成功 / 0:删除是吧
	 */
	int deleteFlight(String flightNo);

	/**
	 * 将查询结果导出到txt文件
	 * 
	 * @param flightList 查询到的航班集合
	 * @param txtPath    txt文件路径
	 */
	void exportToTxt(List<Flight> flightList, String txtPath);

	/**
	 * 将航班信息保存到xml文件中
	 * 
	 * @param xmlPath	xml文件路径
	 */
	void saveXml(String xmlPath);
}
