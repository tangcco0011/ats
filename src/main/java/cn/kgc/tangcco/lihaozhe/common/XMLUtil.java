package cn.kgc.tangcco.lihaozhe.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: XMLUtil
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 下午4:38:53 <br>
 *          类描述:XML文件操作的工具类
 */
public abstract class XMLUtil {
	/**
	 * 默认初始化文件
	 */
	private static String xmlFileName;
	/**
	 * 获取xml文件
	 */
	private static InputStream xmlFilePath;
	/**
	 * 初始化解析器
	 */
	private static SAXReader saxReader = new SAXReader();

	/**
	 * @return 返回xml文件的文档对象
	 * @throws DocumentException xml文档异常
	 */
	public static Document getDocument() throws DocumentException {
		return saxReader.read(xmlFilePath);
	}

	/**
	 * @param xmlFilePath xml文件所在路径
	 * @return 返回xml文件的文档对象
	 * @throws DocumentException xml文档异常
	 */
	public static Document getDocument(InputStream xmlFilePath) throws DocumentException {
		return saxReader.read(xmlFilePath);
	}

	/**
	 * @return xml文件的根节点
	 * @throws DocumentException xml文档异常
	 */
	public static Element getrootElement() throws DocumentException {
		return getDocument().getRootElement();
	}

	/**
	 * @param document xml文件的文档对象
	 * @return xml文件的根节点
	 */
	public static Element getrootElement(Document document) {
		return document.getRootElement();
	}

	/**
	 * @param xmlFilePath xml文件所在路径
	 * @return xml文件的根节点
	 * @throws DocumentException xml文档异常
	 */
	public static Element getrootElement(InputStream xmlFilePath) throws DocumentException {
		return getDocument(xmlFilePath).getRootElement();
	}

	/**
	 * @param nodeName       节点的名称
	 * @param attributeName  属性名称
	 * @param attributevalue 属性的值
	 * @return 返回节点上的值
	 * @throws DocumentException xml文档异常
	 */
	public static String getTrimTextByAttributeNameAtNodeName(String nodeName, String attributeName,
			String attributevalue) throws DocumentException {
		return getDocument().selectSingleNode("//" + nodeName + "[@" + attributeName + "='" + attributevalue + "']")
				.getText().trim();
	}

	/**
	 * @param nodeName 节点的名称
	 * @return 返回节点上的值
	 * @throws DocumentException xml文档异常
	 */
	public static List<String> getTrimTextsByNodeName(String nodeName) throws DocumentException {
		List<String> implClassNames = new ArrayList<String>();
		Document document = getDocument();
		if (nodeName != null || nodeName.trim().length() > 0) {
			List<Node> selectNodes = document.selectNodes("//" + nodeName);
			ListIterator<Node> listIterator = selectNodes.listIterator();
			while (listIterator.hasNext()) {
				implClassNames.add(listIterator.next().getText().trim());
			}
		}
		return implClassNames;
	}

	/**
	 * @return the xmlFileName
	 */
	public static String getXmlFileName() {
		return xmlFileName;
	}

	/**
	 * @param xmlFileName the xmlFileName to set
	 */
	public static void setXmlFileName(String xmlFileName) {
		XMLUtil.xmlFileName = xmlFileName;
	}

	/**
	 * @return the xmlFilePath
	 */
	public static InputStream getXmlFilePath() {
		return xmlFilePath;
	}

	/**
	 * @param xmlFilePath the xmlFilePath to set
	 */
	public static void setXmlFilePath(InputStream xmlFilePath) {
		XMLUtil.xmlFilePath = xmlFilePath;
	}

	public static void main(String[] args) throws DocumentException {
		xmlFileName = "applicationContext.xml";
		setXmlFilePath(XMLUtil.class.getClassLoader().getResourceAsStream(xmlFileName));
		System.out.println(XMLUtil.getrootElement().getName());
	}
}
