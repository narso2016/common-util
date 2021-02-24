package com.libs.util.geofence;

//import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;

public class CellIdLookupManager {	

	protected ILogger logger = LogManager.getDefaultLogger();
	
	public GeofenceBean geocodeByCellId(int MCC, int MNC, int LAC, int CID) {
		GeofenceBean result = null;
		
		List<String> providers = CellIdLookupFactory.getListProvider();
		if (providers != null && providers.size() > 0) {
			for (Iterator<String> iterator = providers.iterator(); iterator.hasNext();) {
				String provider = (String) iterator.next();
				AbstractCellIdLookup lookupMgr = CellIdLookupFactory.getCellIdProvider(provider);
				try {
					result = lookupMgr.getCoordinate(MCC, MNC, LAC, CID);
					if (result == null) {
						continue;
					}
					else {
						break;
					}
				}
				catch (Exception ex) {
					this.logger.printStackTrace(ex);
				}
			}
		}
		else {
			this.logger.log(ILogger.LEVEL_INFO, "No geocode service provider is active!!!");
		}
		
		return result;
	}
	
}
