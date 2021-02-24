package com.libs.util.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassMatcher {
	
	public ClassMatcher() {}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static boolean isMatchClass(Object[] params, Class[] classes) {
		if (params == null)if (classes == null || classes.length == 0) return true;else return false;
		if (params.length != classes.length)return false;
		boolean result = true;
		
		for (int i = 0; i < classes.length && result; i++) {
			
			if (params[i] == null) {
				result = !classes[i].isPrimitive();
				continue;
			}
			
			if (classes[i].isPrimitive()) {
				if (classes[i] == Boolean.TYPE) {
					result = params[i].getClass() == Boolean.class;
				} else if (classes[i] == Character.TYPE) {
					result = params[i].getClass() == Character.class;
				} else if (classes[i] == Byte.TYPE) {
					result = params[i].getClass() == Byte.class;
				} else if (classes[i] == Short.TYPE) {
					result = params[i].getClass() == Short.class;
				} else if (classes[i] == Integer.TYPE) {
					result = params[i].getClass() == Integer.class;
				} else if (classes[i] == Long.TYPE) {
					result = params[i].getClass() == Long.class;
				} else if (classes[i] == Float.TYPE) {
					result = params[i].getClass() == Float.class;
				} else if (classes[i] == Double.TYPE) {
					result = params[i].getClass() == Double.class;
				}
			} else
				result = classes[i].isAssignableFrom(params[i].getClass());
		}
		return result;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static Object invokeMethod(Object obj, String methodName,
			Class[] paramTypes, Object[] paramValues)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		Object result = null;
		Class classes = obj.getClass();
		Method method = classes.getMethod(methodName, paramTypes);
		result = method.invoke(obj, paramValues);
		return result;
		
	}
}
