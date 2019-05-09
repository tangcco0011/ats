package cn.kgc.tangcco.lihaozhe.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.kgc.tangcco.lihaozhe.common.FlightXML;
import cn.kgc.tangcco.lihaozhe.common.LocalDateTimeUtil;
import cn.kgc.tangcco.lihaozhe.dao.FlightDao;
import cn.kgc.tangcco.lihaozhe.entity.Flight;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: FlightDaoImpl
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午5:53:37 <br>
 *          类描述:航班信息数据库操作接口的具体实现
 */
@Slf4j
public class FlightDaoImpl implements FlightDao {
	/**
	 * 存储航班信息
	 */
	private Map<String, Flight> flightMap = new HashMap<String, Flight>();

	@Override
	public void importFromXml(String xmlPath) {
		Document document = null;
		try {
			document = FlightXML.importFromXml(xmlPath);
			flightMap = FlightXML.parseXml(document);
		} catch (DocumentException e) {
			log.warn("航班信息xml文件导入失败");
		}
	}

	@Override
	public List<Flight> getAllFlight() {
		List<Flight> list = new ArrayList<Flight>();
		Set<Entry<String, Flight>> entrySet = flightMap.entrySet();
		Iterator<Entry<String, Flight>> iterator = entrySet.iterator();
		Map.Entry<String, Flight> entry = null;
		while (iterator.hasNext()) {
			list.add(iterator.next().getValue());
		}
		return list;
	}

	@Override
	public List<Flight> queryFlightByCity(String departureCity, String arrivalCity) {
		List<Flight> list = new ArrayList<Flight>();
		Set<Entry<String, Flight>> entrySet = flightMap.entrySet();
		Iterator<Entry<String, Flight>> iterator = entrySet.iterator();
		Map.Entry<String, Flight> entry = null;
		Flight flight = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			flight = entry.getValue();
			if (flight.getDepartureCity().equals(departureCity) && flight.getArrivalCity().equals(arrivalCity)) {
				list.add(flight);
			}
		}
		return list;
	}

	@Override
	public int addFlight(Flight flight) {
		int startSize = flightMap.size();
		flightMap.put(flight.getFlightNo(), flight);
		int OKSize = flightMap.size();
		return OKSize - startSize;
	}

	@Override
	public int deleteFlight(String flightNo) {
		int startSize = flightMap.size();
		if (flightMap.containsKey(flightNo)) {
			flightMap.remove(flightNo);
		}
		int OKSize = flightMap.size();
		return startSize - OKSize;
	}

	@Override
	public void exportToTxt(List<Flight> flightList, String txtPath) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(txtPath, "rw");
			StringBuilder sb = new StringBuilder();
			ListIterator<Flight> listIterator = flightList.listIterator();
			Flight flight = null;
			// 为避免文件删除失败造成txt文件内数据失真，每次从文件的其实位置开始写入
			raf.seek(0);
			while (listIterator.hasNext()) {
				flight = listIterator.next();
				sb.append(flight.getFlightNo() + "\t");
				sb.append("起飞城市：" + flight.getDepartureCity() + "\t");
				sb.append("起飞时间：" + LocalDateTimeUtil.toFormatString(flight.getDepartureTime()) + "\t");
				sb.append("到达城市：" + flight.getArrivalCity() + "\t");
				sb.append("到达时间：" + LocalDateTimeUtil.toFormatString(flight.getArrivalTime()) + "\n");
				// 按照字节数组的形式存储避免乱码
				raf.write(sb.toString().getBytes());
				System.out.print(sb.toString());
				// StringBuilder清空
				sb.delete(0, sb.length());
				raf.seek(raf.length());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveXml(String xmlPath) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("flights");
		Set<Entry<String, Flight>> entrySet = flightMap.entrySet();
		Iterator<Entry<String, Flight>> iterator = entrySet.iterator();
		Map.Entry<String, Flight> entry = null;
		Element flightElement = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			flightElement = root.addElement("flight").addAttribute("flightNo", entry.getKey());
			flightElement.addElement("departureCity").addText(entry.getValue().getDepartureCity());
			flightElement.addElement("departureTime")
					.addText(LocalDateTimeUtil.toFormatString(entry.getValue().getDepartureTime()));
			flightElement.addElement("arrivalCity").addText(entry.getValue().getArrivalCity());
			flightElement.addElement("arrivalTime")
					.addText(LocalDateTimeUtil.toFormatString(entry.getValue().getArrivalTime()));
		}
		FileWriter fileWriter;
		XMLWriter writer = null;
		try {
			fileWriter = new FileWriter(xmlPath);
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new XMLWriter(fileWriter, format);
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
