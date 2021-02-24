package com.libs.util.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.libs.persistence.entity.AuditTrail;
import com.libs.util.auditrail.AuditInfo;
import com.libs.util.auditrail.Auditable;
import com.libs.util.enumeration.ActionType;
import com.libs.util.exception.AppException;
import com.libs.util.formatter.Formatter;
import com.libs.util.tools.ReflectionUtil;
import com.libs.util.tools.Tools;


public class AuditTrailRepositoryImpl<T> implements AuditTrailRepository<T> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void doAudit(T t, ActionType actionType, String userName, String branchCode) throws AppException {
		this.em.clear();
		Date now = new Date();
		
		T t2 = t;
		AuditInfo info = new AuditInfo((Auditable) t2);


		String[] fields = info.getAuditFields();
		String[] dbFields = info.getAuditDbFields();
		String[] pkFields = info.getAuditPrimaryKeyFields();
		String[] pkDBFields = info.getAuditDBPrimaryKeyFields();
		String tableName = info.getAuditTableName();
		
		String pKey = Tools.implode(this.getObjectString(t, pkFields), "|");
		
		if(actionType.equals(ActionType.ADD_ACTION)) {
			Object[] newValue = this.getObject(t, fields);
			for(int i = 0;i<fields.length;i++) {
				AuditTrail audittrail = new AuditTrail();
				audittrail.setAction(ActionType.ADD_ACTION.toString());
				audittrail.setBranch(branchCode);
				audittrail.setUser(userName);
				audittrail.setTransactiondate(now);
				audittrail.setPkey(pKey);
				audittrail.setField(dbFields[i]);
				audittrail.setOldvalue(null);
				audittrail.setNewvalue(String.valueOf(newValue[i]));
				em.persist(audittrail);
			}
		}else if(actionType.equals(ActionType.EDIT_ACTION)) {
			Object[] oldValue = this.getOldValueNative(t, tableName, pkFields, pkDBFields, dbFields, fields);
			Object[] newValue = this.getObject(t, fields);
			for(int i = 0;i<fields.length;i++) {
				AuditTrail audittrail = new AuditTrail();
				audittrail.setAction(ActionType.EDIT_ACTION.toString());
				audittrail.setBranch(branchCode);
				audittrail.setUser(userName);
				audittrail.setTransactiondate(now);
				audittrail.setPkey(pKey);
				audittrail.setField(dbFields[i]);
				audittrail.setOldvalue(String.valueOf(oldValue[i]));
				audittrail.setNewvalue(String.valueOf(newValue[i]));
				em.persist(audittrail);
			}
		}else if(actionType.equals(ActionType.DELETE_ACTION)) {
			Object[] oldValue = this.getOldValue(t, tableName, pkFields, pkDBFields, dbFields, fields);
			for(int i = 0;i<fields.length;i++) {
				AuditTrail audittrail = new AuditTrail();
				audittrail.setAction(ActionType.DELETE_ACTION.toString());
				audittrail.setBranch(branchCode);
				audittrail.setUser(userName);
				audittrail.setTransactiondate(now);
				audittrail.setPkey(pKey);
				audittrail.setField(dbFields[i]);
				audittrail.setOldvalue(String.valueOf(oldValue[i]));
				audittrail.setNewvalue(null);
				em.persist(audittrail);
			}
		}else if(actionType.equals(ActionType.APPROVE_ACTION) || actionType.equals(ActionType.REJECT_ACTION)) {
			Object[] oldValue = this.getOldValue(t, tableName, pkFields, pkDBFields, dbFields, fields);
			String action = actionType.toString();
			for(int i = 0;i<fields.length;i++) {
				AuditTrail audittrail = new AuditTrail();
				audittrail.setAction(action);
				audittrail.setBranch(branchCode);
				audittrail.setUser(userName);
				audittrail.setTransactiondate(now);
				audittrail.setPkey(pKey);
				audittrail.setField(dbFields[i]);
				audittrail.setOldvalue(String.valueOf(oldValue[i]));
				audittrail.setNewvalue(null);
				em.persist(audittrail);
			}
		}else {
			System.out.println("NGACO");;
		}
	}
	
	private Object[] getOldValue(T t, String tableName, String[] pkFields, String[] pkDBFields, String[] dbFields, String[] fields) throws AppException {
		String selectClause = Tools.implode(tableName, fields, ",");

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ").append(selectClause).append(" FROM ").append(t.getClass().getName()).append(" ").append(tableName).append(" WHERE 1=1 ");

		for (int i = 0; i < pkFields.length; i++) {
			sb.append(" AND ").append(pkFields[i]).append(" = ").append("?");
		}

		Query q = em.createQuery(sb.toString());
		
		Object[] os = this.getObject(t, pkFields);

		for (int i = 0; i < os.length; i++) {
			q.setParameter(i+1, os[i]);
		}
		
		Object[] result = (Object[]) q.getSingleResult();
		return result;
	}
	
	private Object[] getOldValueNative(T t, String tableName, String[] pkFields, String[] pkDBFields, String[] dbFields, String[] fields) throws AppException {
		String selectClause = Tools.implode(tableName.toLowerCase(), dbFields, ",");

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ").append(selectClause).append(" FROM ").append(tableName).append(" ").append(tableName.toLowerCase()).append(" WHERE 1=1 ");

		for (int i = 0; i < pkFields.length; i++) {
			sb.append(" AND ").append(pkDBFields[i]).append(" = ").append("?");
		}

		Query q = em.createNativeQuery(sb.toString());
		
		Object[] os = this.getObject(t, pkFields);

		for (int i = 0; i < os.length; i++) {
			q.setParameter(i+1, os[i]);
		}
		
		Object[] result = (Object[]) q.getSingleResult();
		return result;
	}
//	private String[] getObjectString(T t, String[] fields) {
//		String[] os = new String[fields.length];
//		try {
//			Class fromClass = t.getClass();
//			Method[] methods = fromClass.getMethods();
//			for (int i = 0; i < fields.length; i++) {
//				String field = fields[i];
//				for (int j = 0; j < methods.length; j++) {
//					Method method = methods[j];
//					String methodName = method.getName();
//					if (methodName.startsWith("is")) { // for boolean
//						String tempMethodName = methodName.substring(2);
//						if (tempMethodName.equalsIgnoreCase(field)) {
//							Object o = method.invoke(fromClass, null);
//							String result = null;
//							if(null!=o) {
//								boolean isO = Boolean.valueOf(String.valueOf(o));
//								result = isO? "1" : "0";
//							}
//							os[i] = result;
//							break;
//						}
//						continue;
//					} else if (methodName.startsWith("get")) {
//						String tempMethodName = methodName.substring(3);
//						if (tempMethodName.equalsIgnoreCase(field)) {
//							String result = this.objectValueToString(method.invoke(fromClass, null));
//							os[i] = result;
//							break;
//						}
//						continue;
//					} else {
//						continue;
//					}
//				}
//			}
//
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return os;
//	}
	
	private String[] getObjectString(T audited, String[] fields) throws AppException{
		String[] fieldValues = new String[fields.length];
	      for (int i = 0; i < fields.length; i++) {
	         try {
	            String methodName = "get"
	                  + Character.toUpperCase(fields[i].charAt(0)) +
	                  fields[i].substring(1);
	            Method method = audited.getClass().getMethod(methodName, null);
	            if(null==method) {
	            	methodName = "is"
	  	                  + Character.toUpperCase(fields[i].charAt(0)) +
		                  fields[i].substring(1);
	            	method = audited.getClass().getMethod(methodName, null);
	            	if(null==method) continue;
	            }
	            
	            Object val = ReflectionUtil.invokeMethod(audited, methodName, null, null);
	            fieldValues[i] = null;
	            if (val != null) {
	            
	               fieldValues[i] = this.objectValueToString(val);
	            }
	         }
	         catch (NoSuchMethodException noe) {
	            throw new AppException(422,"Cannot find getter method for field " + fields[i]);
	         }
	         catch (IllegalAccessException iae) {
	            throw new AppException(422, "Illegal access invoking getter method for field " + fields[i]);
	         }
	         catch (java.lang.reflect.InvocationTargetException ite) {
	            throw new AppException(422, "Exception occured when invoking getter method for field " + fields[i]);
	         }
	      }
	      return fieldValues;
	}
	
	 private Object[] getObject(T audited, String[] fields) throws AppException {
	      Object[] fieldValues = new Object[fields.length];
	      for (int i = 0; i < fields.length; i++) {
	         try {
	        	 String methodName = "get"
		                  + Character.toUpperCase(fields[i].charAt(0)) +
		                  fields[i].substring(1);
		            Method method = audited.getClass().getMethod(methodName, null);
		            System.out.println(methodName);
		            if(null==method) {
		            	methodName = "is"
		  	                  + Character.toUpperCase(fields[i].charAt(0)) +
			                  fields[i].substring(1);
		            	method = audited.getClass().getMethod(methodName, null);
		            	 System.out.println(methodName);
		            	if(null==method) continue;
		            }
	            Object val = ReflectionUtil.invokeMethod(audited, methodName, null, null);
	            fieldValues[i] = null;
	            if (val != null) {
	               fieldValues[i] = val;
	               if (fieldValues[i] instanceof java.util.Date) {
	                  fieldValues[i] = new Timestamp( ( (java.util.Date) fieldValues[i]).getTime());
	               }
	            }
	         }
	         catch (NoSuchMethodException noe) {
	            throw new AppException(422,"Cannot find getter method for field " + fields[i]);
	         }
	         catch (IllegalAccessException iae) {
	            throw new AppException(422, "Illegal access invoking getter method for field " + fields[i]);
	         }
	         catch (java.lang.reflect.InvocationTargetException ite) {
	            throw new AppException(422, "Exception occured when invoking getter method for field " + fields[i]);
	         }
	      }
	      return fieldValues;
	   }

//	private void checks(String[] fields, String[] dbFields, String[] pkFields, String tableName) throws AppException {
//		if (tableName == null)
//			throw new AppException(422, "auditTableName must not be NULL");
//		if (fields == null || dbFields == null || fields.length == 0 || dbFields.length == 0)
//			throw new AppException(422, "auditFields and/or auditDbFields can not be NULL or zero-length array!");
//		if (fields.length != dbFields.length)
//			throw new AppException(422, "getAuditFields() and getAuditDBFields() mast have same length!");
//		if (pkFields == null || pkFields.length == 0)
//			throw new AppException(422, "auditPrimaryKeyFields cannot be NULL or zero-length array!");
//	}
	

	   private String objectValueToString(Object obj) {
	      String result = null;
	      if (obj instanceof java.lang.String) {
	         result = obj.toString();
	      }
	      else if (obj instanceof java.util.Date) {
	         DateFormat dFormatter = Formatter.getDateFormat(Formatter.DFORMAT_YMDHHMMSS);
	         result = dFormatter.format( (java.util.Date) obj);
	      }
	      else if (obj instanceof java.lang.Double) {
	         result = Formatter.getNumberFormat(Formatter.NFORMAT_PLAIN_AUDIT)
	               .format( ( (Double) obj).doubleValue());
	      }
	      else if (obj instanceof java.lang.Float) {
	         result = Formatter.getNumberFormat(Formatter.NFORMAT_PLAIN_AUDIT)
	               .format( ( (Float) obj).floatValue());
	      }
	      else if (obj instanceof java.math.BigDecimal) {
	         result = Formatter.getNumberFormat(Formatter.NFORMAT_PLAIN_AUDIT)
	               .format( ( (java.math.BigDecimal) obj).doubleValue());
	      }
	      else if (obj instanceof java.math.BigInteger) {
	         result = obj.toString();
	      }
	      else if (obj instanceof java.lang.Integer) {
	         int val = ( (Integer) obj).intValue();
	         result = String.valueOf(val);
	      }
	      else if (obj instanceof java.lang.Long) {
	         long val = ( (Long) obj).longValue();
	         result = String.valueOf(val);
	      }
	      else {
	         System.out.println("AuditTrailManager: Unresolve class " + obj.getClass().getName());
	         result = obj.toString();
	      }
	      return result;
	   }

}
