package com.libs.util.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

@SuppressWarnings("rawtypes")
public class BeanPropertyUtil {

	private static Map map = new WeakHashMap();

	private BeanPropertyUtil() {
	}


	public static Object getProperty(String name, Object instance)
			throws IllegalArgumentException {
		try {
			Method read = getReadMethod(name, instance);
			if (read == null)
				throw new IllegalArgumentException(
						"Cannot find instance with property '" + name + "'");
			return read.invoke(instance, null);
		} catch (Exception e) {
			throw new IllegalArgumentException("Problem accessing property '"
					+ name + "': " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private static Method getReadMethod(String name, Object instance)
			throws IllegalArgumentException, IntrospectionException {
		String id = instance.getClass() + "#" + name;
		Method read = (Method) map.get(id);
		if (read == null) {
			BeanInfo info = Introspector.getBeanInfo(instance.getClass(),
					Introspector.USE_ALL_BEANINFO);
			PropertyDescriptor pds[] = info.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				if (name.equals(pd.getName())) {
					read = pd.getReadMethod();
					map.put(id, read);
					break;
				}
			}
		}
		if (read == null)
			throw new IllegalArgumentException(
					"Cannot find instance with property '" + name + "'");
		return read;
	}

}
