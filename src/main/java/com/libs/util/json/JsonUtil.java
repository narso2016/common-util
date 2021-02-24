package com.libs.util.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonUtil {

	public static ObjectWriter generateDefaultJsonWriter() {
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_EMPTY);
		return om.writer();
	}

	public static ObjectWriter generateJsonWriterWithFilter(JsonFilter... filters) {
		ObjectWriter writer = generateDefaultJsonWriter();

		SimpleFilterProvider simpleFilter = new SimpleFilterProvider();
		for (JsonFilter filter : filters) {
			if (filter.getFieldsException() == null || filter.getFieldsException().length == 0) {
				simpleFilter = simpleFilter.addFilter(filter.getEntity(), SimpleBeanPropertyFilter.serializeAll());
			} else {
				simpleFilter = simpleFilter.addFilter(filter.getEntity(), SimpleBeanPropertyFilter.serializeAllExcept(filter.getFieldsException()));
			}
		}
		return writer.with(simpleFilter);
	}
}
