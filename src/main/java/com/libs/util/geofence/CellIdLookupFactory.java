package com.libs.util.geofence;

import java.util.ArrayList;
import java.util.List;

public class CellIdLookupFactory {
	public static final String GOOGLE = "GEO_GOOGLE";
	public static final String OPENCELLID = "GEO_OPENCI";
	
	private CellIdLookupFactory(){}
	
	public static AbstractCellIdLookup getCellIdProvider(String provider) {		
		if (GOOGLE.equals(provider)) {
			return new GoogleCellIdLookupImpl();
		}
		else if (OPENCELLID.equals(provider)) {
			return new OpenCellIdLookupImpl();
		}
		else {
			return new GoogleCellIdLookupImpl();
		}
	}
	
	public static List<String> getListProvider(){
		List<String> list = new ArrayList<String>();
		list.add(GOOGLE);
		list.add(OPENCELLID);
		return list;
	}
	
}
