package cn.kgc.tangcco.lihaozhe.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.plaf.SliderUI;

import cn.kgc.tangcco.lihaozhe.common.BeanFactory;
import cn.kgc.tangcco.lihaozhe.dao.FlightDao;
import cn.kgc.tangcco.lihaozhe.entity.Flight;
import cn.kgc.tangcco.lihaozhe.service.FlightService;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: FlightServiceImpl
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午3:56:46 <br>
 *          类描述:航班信息业务层接口的实现
 */
public class FlightServiceImpl implements FlightService {
	/**
	 * 初始化持久层
	 */
	FlightDao flightDao = (FlightDao) BeanFactory.newInstance("flightDao");

	@Override
	public void importFromXml(String xmlPath) {
		flightDao.importFromXml(xmlPath);
	}

	@Override
	public SortedSet<Flight> getAllFlight() {
		List<Flight> allFlight = flightDao.getAllFlight();
		ListIterator<Flight> listIterator = allFlight.listIterator();
		SortedSet<Flight> ss = new TreeSet();
		while (listIterator.hasNext()) {
			ss.add(listIterator.next());
		}
		return ss;
	}

	@Override
	public SortedSet<Flight> queryFlightByCity(String departureCity, String arrivalCity) {
		List<Flight> allFlight = flightDao.queryFlightByCity(departureCity, arrivalCity);
		ListIterator<Flight> listIterator = allFlight.listIterator();
		SortedSet ss = new TreeSet();
		while (listIterator.hasNext()) {
			ss.add(listIterator.next());
		}
		return ss;
	}

	@Override
	public int addFlight(Flight flight) {
		return flightDao.addFlight(flight);
	}

	@Override
	public int deleteFlight(String flightNo) {
		return flightDao.deleteFlight(flightNo);
	}

	@Override
	public void exportToTxt(List<Flight> flightList, String txtPath) {
		String pathname = txtPath.substring(0, txtPath.lastIndexOf("/"));
		File path = new File(pathname);
		if(!path.exists()) {
			if(path.mkdirs()) {
				saveTxt(flightList,txtPath);
			}else {
				System.out.println("存储目录创建失败。");
			}
		}else {
			saveTxt(flightList,txtPath);
		}
		
	}

	@Override
	public void saveXml(String xmlPath) {
		flightDao.saveXml(xmlPath);

	}
	/**
	 * 存储txt文件
	 */
	public void saveTxt(List<Flight> flightList, String txtPath) {
		if (flightList.size() > 0) {
			File file = new File(txtPath);
			if (file.exists()) {
				file.delete();
			}
			flightDao.exportToTxt(flightList, txtPath);
		}
	}
}
