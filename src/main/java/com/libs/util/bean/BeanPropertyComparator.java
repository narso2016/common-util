package com.libs.util.bean;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class BeanPropertyComparator implements Comparator {

	private String property;
	private Comparator comparator;

	public BeanPropertyComparator(String property) {
		this(property, null);
	}

	public BeanPropertyComparator(String property, Comparator comparator) {
		this.property = property;
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object bean1, Object bean2) {
		Object value1 = BeanPropertyUtil.getProperty(property, bean1);
		Object value2 = BeanPropertyUtil.getProperty(property, bean2);
		if (this.comparator == null) {
			if (value1 instanceof Comparable) {
				return ((Comparable) value1).compareTo(value2);
			} else if (value2 instanceof Comparable) {
				return ((Comparable) value2).compareTo(value1);
			} else {
				String s1 = String.valueOf(value1);
				String s2 = String.valueOf(value2);
				return s1.compareTo(s2);
			}
		} else {
			return comparator.compare(value1, value2);
		}
	}
}
