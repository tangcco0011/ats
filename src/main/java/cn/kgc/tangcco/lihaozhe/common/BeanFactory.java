package cn.kgc.tangcco.lihaozhe.common;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import lombok.Data;

/**
 * <p>
 * 项目名称: factory
 * </p>
 * <p>
 * 类名称: BeanFactory
 * </p>
 * 
 * @author 作者 : 李昊哲
 * @version 1.0 创建时间: 2019年5月8日 上午17:41:20 <br>
 *          类描述:通过配置文件和反射生成类的实例化对象
 */
public abstract class BeanFactory{
	/**
	 * 	存储对象的容器，key为接口名首字母小写，value为接口的实现类对象
	 */
	private static Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * @param key		接口名首字母小写	
	 * @return Object	类型的实例化对象
	 */
	public static Object newInstance(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			Class clazz = null;
			try {
				clazz = Class.forName(AppXML.getImplClassNameByKey(key));
				Object object = clazz.newInstance();
				map.put(key, object);
				return object;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | DocumentException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
