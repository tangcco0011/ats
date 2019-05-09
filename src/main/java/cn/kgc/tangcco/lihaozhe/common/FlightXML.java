package cn.kgc.tangcco.lihaozhe.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.kgc.tangcco.lihaozhe.entity.Flight;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: FlightXML
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月9日 上午9:13:08 <br>
 *          类描述:
 */
public abstract class FlightXML extends XMLUtil {
	public static Document importFromXml(String xmlPath) throws DocumentException {
		setXmlFileName(xmlPath);
		setXmlFilePath(FlightXML.class.getClassLoader().getResourceAsStream(getXmlFileName()));
		return getDocument();
	}

	public static Map<String, Flight> parseXml(Document document) {
		Map<String, Flight> map = new HashMap<String, Flight>();
		if (document != null) {
			Element rootElement = document.getRootElement();
			Iterator<Element> iter = rootElement.elementIterator("flight");
			Flight flight = null;
			Iterator<Element> inner = null;
			Element element = null;
			String flightNo = null;
			String departureCity = null;
			String departureTime = null;
			String arrivalCity = null;
			String arrivalTime = null;
			while (iter.hasNext()) {
				element = iter.next();
				flightNo = element.attributeValue("flightNo");
				departureCity = element.elementTextTrim("departureCity");
				departureTime = element.elementTextTrim("departureTime");
				arrivalCity = element.elementTextTrim("arrivalCity");
				arrivalTime = element.elementTextTrim("arrivalTime");
				flight = new Flight(flightNo, departureCity, LocalDateTimeUtil.parse(departureTime), arrivalCity,
						LocalDateTimeUtil.parse(arrivalTime));
				map.put(flightNo, flight);
			}
		}
		return map;
	}
}
