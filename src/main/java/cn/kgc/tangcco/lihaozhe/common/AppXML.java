package cn.kgc.tangcco.lihaozhe.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

/**
 * <p>
 * 项目名称: ats
 * </p>
 * <p>
 * 类名称: AppXML
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月9日 上午9:12:37 <br>
 *          类描述: 系统接口与实现类关系xml文件操作
 */
public abstract class AppXML extends XMLUtil {
	/**
	 * 存储已经读取过的接口和实现类
	 */
	private static Map<String, String> map = new HashMap<String, String>();
	private static void init() {
		/**
		 * 初始化xml名称
		 */
		setXmlFileName("applicationContext.xml");
		/**
		 * 通过类加载器将xml文件加载到输入流
		 */
		setXmlFilePath(AppXML.class.getClassLoader().getResourceAsStream(getXmlFileName()));
	}

	/**
	 * @param key 接口所在节点的属性名称
	 * @return 实现类的完全限定名
	 * @throws DocumentException xml文档异常
	 */
	public static String getImplClassNameByKey(String key) throws DocumentException {
		init();
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			String value = getTrimTextByAttributeNameAtNodeName("interface", "name", key);
			map.put(key, value);
			return value;
		}
	}

	/**
	 * @return 所有接口名称的集合
	 * @throws DocumentException xml文档异常
	 */
	public static List<String> getAttributeNames() throws DocumentException {
		init();
		return null;
	}

	/**
	 * @return 所有接口对应实现类完全限定名的集合
	 * @throws DocumentException xml文档异常
	 */
	public static List<String> getImplClassNames() throws DocumentException {
		init();
		return getTrimTextsByNodeName("interface");
	}

	public static void main(String[] args) throws DocumentException {
		init();
		System.out.println(getrootElement().getName());
	}
}
