package com.libs.util.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class QueryBuilder<T> {
	
	 static java.sql.Date oDate;

	public Specification<T> queryBuilder(Map<String, Object> whereClause) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> queryBuilderAndLike(Map<String, Object> whereClause, Map<String, Object> whereLike) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLike = whereLike.entrySet();
				Iterator<Entry<String, Object>> collSetsLike = collSetLike.iterator();

				while (collSetsLike.hasNext()) {
					Entry<String, Object> entry = collSetsLike.next();
					final Predicate predicate = cb.like(root.get(entry.getKey()), "%"+String.valueOf(entry.getValue())+"%");
					predicates.add(predicate);
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> queryBuilderAndLikeAndBetween(Map<String, Object> whereClause, 
			Map<String, Object> whereLike, Map<String, Object[]> whereClauseBeetwen) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLike = whereLike.entrySet();
				Iterator<Entry<String, Object>> collSetsLike = collSetLike.iterator();

				while (collSetsLike.hasNext()) {
					Entry<String, Object> entry = collSetsLike.next();
					final Predicate predicate = cb.like(root.get(entry.getKey()), "%"+String.valueOf(entry.getValue())+"%");
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				 
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	
	
	public Specification<T> queryBuilder(Map<String, Object> whereClause, String inSearch, List<T> values) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				predicates.add(cb.and(root.get(inSearch).in(values)));
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> queryBuilder(Map<String, Object> whereClause, String containing, Object value) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				predicates.add(cb.like(root.get(containing), "%"+String.valueOf(value)+"%"));
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> getCustomQueryEquals(Map<String, Object> whereClause, 
			Map<String, Object> whereClauseBeetwen, Map<String, Object> whereClauseBeetwenInRoot, 
			Map<String, Object> whereClauseAgregatorLess, Map<String, Object> whereClauseAgregatorGreater ) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLess = whereClauseAgregatorLess.entrySet();
				Iterator<Entry<String, Object>> collSetsLess = collSetLess.iterator();
				while (collSetsLess.hasNext()) {
					Entry<String, Object> entry = collSetsLess.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 

//						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), dateParam);
						final Predicate predicate = cb.lessThanOrEqualTo(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
				
				Set<Entry<String, Object>> collSetGreater = whereClauseAgregatorGreater.entrySet();
				Iterator<Entry<String, Object>> collSetsGreater = collSetGreater.iterator();
				while (collSetsGreater.hasNext()) {
					Entry<String, Object> entry = collSetsGreater.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 

						final Predicate predicate = cb.greaterThanOrEqualTo(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
//						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), dateParam);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
								
				Set<Entry<String, Object>> collSetBeetwenInRoot = whereClauseBeetwenInRoot.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwenInRoot = collSetBeetwenInRoot.iterator();
				
				while (collSetsBeetwenInRoot.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwenInRoot.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							final Predicate predicate = cb.between(cb.literal(dateParam), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(cb.literal(String.valueOf(os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(cb.literal(BigDecimal.valueOf((Double)os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							Date dateParamTo = (Date)os[1];
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(dateParam), cb.literal(dateParamTo));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
								
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> getCustomQueryEqualsAndNotEquals(Map<String, Object> whereClause, 
			Map<String, Object> whereClauseNot , Map<String, Object> whereClauseBeetwen,
			Map<String, Object> whereClauseBeetwenInRoot, Map<String, Object> whereClauseAgregatorLess, 
			Map<String, Object> whereClauseAgregatorGreater
			) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetNot = whereClauseNot.entrySet();
				Iterator<Entry<String, Object>> collSetsNot = collSetNot.iterator();

				while (collSetsNot.hasNext()) {
					Entry<String, Object> entry = collSetsNot.next();
					final Predicate predicate = cb.notEqual(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLess = whereClauseAgregatorLess.entrySet();
				Iterator<Entry<String, Object>> collSetsLess = collSetLess.iterator();
				while (collSetsLess.hasNext()) {
					Entry<String, Object> entry = collSetsLess.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 

//						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), dateParam);
						final Predicate predicate = cb.lessThanOrEqualTo(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.lessThanOrEqualTo(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
				
				Set<Entry<String, Object>> collSetGreater = whereClauseAgregatorGreater.entrySet();
				Iterator<Entry<String, Object>> collSetsGreater = collSetGreater.iterator();
				while (collSetsGreater.hasNext()) {
					Entry<String, Object> entry = collSetsGreater.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 

						final Predicate predicate = cb.greaterThanOrEqualTo(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
//						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), dateParam);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
								
				Set<Entry<String, Object>> collSetBeetwenInRoot = whereClauseBeetwenInRoot.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwenInRoot = collSetBeetwenInRoot.iterator();
				
				while (collSetsBeetwenInRoot.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwenInRoot.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							final Predicate predicate = cb.between(cb.literal(dateParam), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(cb.literal(String.valueOf(os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(cb.literal(BigDecimal.valueOf((Double)os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							Date dateParamTo = (Date)os[1];
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(dateParam), cb.literal(dateParamTo));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
								
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	
	public Specification<T> getCustomQuery(Map<String, Object> whereClause, 
			Map<String, Object> whereClauseBeetwen, Map<String, Object> whereClauseBeetwenInRoot, 
			Map<String, Object> whereClauseAgregatorLess, Map<String, Object> whereClauseAgregatorGreater ) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					final Predicate predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLess = whereClauseAgregatorLess.entrySet();
				Iterator<Entry<String, Object>> collSetsLess = collSetLess.iterator();
				while (collSetsLess.hasNext()) {
					Entry<String, Object> entry = collSetsLess.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date dateParam = (Date)os;
						final Predicate predicate = cb.lessThan(root.get(entry.getKey()), dateParam);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.lessThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.lessThan(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.lessThan(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
				
				Set<Entry<String, Object>> collSetGreater = whereClauseAgregatorGreater.entrySet();
				Iterator<Entry<String, Object>> collSetsGreater = collSetGreater.iterator();
				while (collSetsGreater.hasNext()) {
					Entry<String, Object> entry = collSetsGreater.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date dateParam = (Date)os;
						final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), dateParam);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.greaterThan(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
								
				Set<Entry<String, Object>> collSetBeetwenInRoot = whereClauseBeetwenInRoot.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwenInRoot = collSetBeetwenInRoot.iterator();
				
				while (collSetsBeetwenInRoot.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwenInRoot.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							final Predicate predicate = cb.between(cb.literal(dateParam), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								final Predicate predicate = cb.between(cb.literal(data), root.get(os[1].toString()), root.get(os[2].toString()));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(cb.literal(String.valueOf(os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(cb.literal(BigDecimal.valueOf((Double)os[0])), root.get(os[1].toString()), root.get(os[2].toString()));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
							Date dateParamTo = (Date)os[1];
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(dateParam), cb.literal(dateParamTo));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
								
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	
	public Specification<T> queryBuilderQustomEquals(Map<String, Object> whereClause) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.like(
		                  root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> queryBuilderCriteriaEqualsAndCriteriaBetweenOrderBy(Map<String, Object> whereClause,
			Map<String, Object[]> whereClauseBeetwen, String sortBy, String order) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.like(
		                  root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
					
				}
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				if ("asc".equals(order)) {
					query.orderBy(cb.asc(root.get(sortBy)));
				} else {
					query.orderBy(cb.desc(root.get(sortBy)));
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	
	public Specification<T> queryBuilderQustomEqualsOrderBy(Map<String, Object> whereClause, String sortBy, String order) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.like(
		                  root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
					
				}
				
				if ("asc".equals(order)) {
					query.orderBy(cb.asc(root.get(sortBy)));
				} else {
					query.orderBy(cb.desc(root.get(sortBy)));
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> queryBuilderQustomEqualsAndNot(Map<String, Object> whereClause,Map<String, Object> whereClauseNot) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.like(
		                  root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				
				Set<Entry<String, Object>> collSetNot = whereClauseNot.entrySet();
				Iterator<Entry<String, Object>> collSetsNot = collSetNot.iterator();
				while (collSetsNot.hasNext()) {
					Entry<String, Object> entry = collSetsNot.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.notLike(root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.notEqual(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.notEqual(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	public Specification<T> customQueryEqualsAndBetween(Map<String, Object> whereClause, 
			Map<String, Object[]> whereClauseBeetwen){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.like(
		                  root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
	}
	
	public Specification<T> customQueryEqualsAndBetweenAndListIn(Map<String, Object> whereClause, 
			Map<String, Object[]> whereClauseBeetwen, Map<String, Object> whereClauseIn){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.equal(
		                  root.<String>get(entry.getKey()), entry.getValue());
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
	}
	
	public Specification<T> customQueryEqualsAndBetweenAndListInAndGreater(Map<String, Object> whereClause, 
			Map<String, Object[]> whereClauseBeetwen, Map<String, Object> whereClauseIn, 
			Map<String, Object> whereClauseGreater){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.equal(
		                  root.<String>get(entry.getKey()), entry.getValue());
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetGreater = whereClauseGreater.entrySet();
				Iterator<Entry<String, Object>> collSetsGreater = collSetGreater.iterator();
				while (collSetsGreater.hasNext()) {
					Entry<String, Object> entry = collSetsGreater.next();
					Object os = entry.getValue();
					if(os.getClass().equals(Date.class)) {
						Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 

						final Predicate predicate = cb.greaterThanOrEqualTo(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
						predicates.add(predicate);
					}else if(os.getClass().isPrimitive()) {
						Object o = os;
						if(Integer.class == o.getClass()) {
							int data = (Integer) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Character.class == o.getClass()) {
							char data = (Character) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						} else if(Short.class == o.getClass()) {
							short data = (Short) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Long.class == o.getClass()) {
							long data = (Long) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Float.class == o.getClass()) {
							float data = (Float) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}else if(Double.class == o.getClass()) {
							double data = (Double) o;
							final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), data);
							predicates.add(predicate);
						}
					}else if(os.getClass().equals(String.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), String.valueOf(os));
						predicates.add(predicate);
					}else if(os.getClass().equals(BigDecimal.class)) {
						final Predicate predicate = cb.greaterThanOrEqualTo(root.get(entry.getKey()), BigDecimal.valueOf((Double)os));
						predicates.add(predicate);
					}
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
	}
	
	public Specification<T> getListIn(Map<String, Object> whereClause, Map<String, Object> whereClauseIn) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
					}else {
						predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					}
		              
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
								
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public Specification<T> getListInAndLike(Map<String, Object> whereClause, Map<String, Object> whereClauseIn, Map<String, Object> whereClauseLike) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();

				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);
					}else {
						predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
					}
		              
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetLike = whereClauseLike.entrySet();
				Iterator<Entry<String, Object>> collSetsLike = collSetLike.iterator();

				while (collSetsLike.hasNext()) {
					Entry<String, Object> entry = collSetsLike.next();
					final Predicate predicate = cb.like(root.get(entry.getKey()), "%"+String.valueOf(entry.getValue())+"%");
					predicates.add(predicate);
				}
								
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	public Specification<T> customQueryEqualsAndNotEqualsAndBetweenAndListIn(Map<String, Object> whereClause, 
			Map<String, Object> whereClauseNot, Map<String, Object[]> whereClauseBeetwen, Map<String, Object> whereClauseIn){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.equal(
		                  root.<String>get(entry.getKey()), entry.getValue());
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetNot = whereClauseNot.entrySet();
				Iterator<Entry<String, Object>> collSetsNot = collSetNot.iterator();
				while (collSetsNot.hasNext()) {
					Entry<String, Object> entry = collSetsNot.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.notLike(root.<String>get(entry.getKey()), "%" + entry.getValue() + "%");
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.notEqual(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.notEqual(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
	}
	
	public Specification<T> customQueryEqualsAndNotEqualsAndBetweenAndListInAndListNotIn(Map<String, Object> whereClause, 
			Map<String, Object> whereClauseNot, Map<String, Object[]> whereClauseBeetwen, Map<String, Object> whereClauseIn, Map<String, Object> whereClauseNotIn){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Set<Entry<String, Object>> collSet = whereClause.entrySet();
				Iterator<Entry<String, Object>> collSets = collSet.iterator();
				while (collSets.hasNext()) {
					Entry<String, Object> entry = collSets.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.equal(root.<String>get(entry.getKey()), entry.getValue());
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.equal(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.equal(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetNot = whereClauseNot.entrySet();
				Iterator<Entry<String, Object>> collSetsNot = collSetNot.iterator();
				while (collSetsNot.hasNext()) {
					Entry<String, Object> entry = collSetsNot.next();
					Predicate predicate = null;
					if (root.get(entry.getKey()).getJavaType() == String.class) {
						predicate = cb.notEqual(root.<String>get(entry.getKey()), entry.getValue());
		            } else if(root.get(entry.getKey()).getJavaType() == Date.class) {
		            	Date pDate = (Date) entry.getValue();
		            	Calendar cal = Calendar.getInstance();
		            	 cal.setTime(pDate);
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                oDate = new java.sql.Date(cal.getTime().getTime()); 
		                
		                predicate = cb.notEqual(root.<Date>get(entry.getKey()).as(java.sql.Date.class), oDate);

		            }else {
		            	predicate = cb.notEqual(root.get(entry.getKey()), entry.getValue());
		            }
					
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object[]>> collSetBeetwen = whereClauseBeetwen.entrySet();
				Iterator<Entry<String, Object[]>> collSetsBeetwen = collSetBeetwen.iterator();
				
				while (collSetsBeetwen.hasNext()) {
					Entry<String, Object[]> entry = collSetsBeetwen.next();
					if(entry.getValue().getClass().isArray()) {
						Object[] os = (Object[]) entry.getValue();
						if(os[0].getClass().equals(Date.class)) {
							Date dateParam = (Date)os[0];
			            	Calendar calStart = Calendar.getInstance();
			            	calStart.setTime(dateParam);
			            	calStart.set(Calendar.HOUR_OF_DAY, 0);
			            	calStart.set(Calendar.MINUTE, 0);
			            	calStart.set(Calendar.SECOND, 0);
			            	calStart.set(Calendar.MILLISECOND, 0);
			                Date sDate = new java.sql.Date(calStart.getTime().getTime()); 
			                
							
							Date dateParamTo = (Date)os[1];
							Calendar calEnd = Calendar.getInstance();
							calEnd.setTime(dateParamTo);
							calEnd.set(Calendar.HOUR_OF_DAY, 0);
							calEnd.set(Calendar.MINUTE, 0);
							calEnd.set(Calendar.SECOND, 0);
							calEnd.set(Calendar.MILLISECOND, 0);
							Date eDate = new java.sql.Date(calEnd.getTime().getTime());
							
							final Predicate predicate = cb.between(root.<Date>get(entry.getKey()).as(java.sql.Date.class), cb.literal(sDate), cb.literal(eDate));
							predicates.add(predicate);
						}else if(os[0].getClass().isPrimitive()) {
							Object o = os[0];
							Object oTo = os[1];
							if(Integer.class == o.getClass()) {
								int data = (Integer) o;
								int dataTo = (Integer) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Character.class == o.getClass()) {
								char data = (Character) o;
								char dataTo = (Character) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							} else if(Short.class == o.getClass()) {
								short data = (Short) o;
								short dataTo = (Short) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Long.class == o.getClass()) {
								long data = (Long) o;
								long dataTo = (Long) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Float.class == o.getClass()) {
								float data = (Float) o;
								float dataTo = (Float) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}else if(Double.class == o.getClass()) {
								double data = (Double) o;
								double dataTo = (Double) oTo;
								final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(data), cb.literal(dataTo));
								predicates.add(predicate);
							}
						}else if(os[0].getClass().equals(String.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(String.valueOf(os[0])), cb.literal(String.valueOf(os[1])));
							predicates.add(predicate);
						}else if(os[0].getClass().equals(BigDecimal.class)) {
							final Predicate predicate = cb.between(root.get(entry.getKey()), cb.literal(BigDecimal.valueOf((Double)os[0])), cb.literal(BigDecimal.valueOf((Double)os[1])));
							predicates.add(predicate);
						}
						
					}
				}
				
				Set<Entry<String, Object>> collSetIn = whereClauseIn.entrySet();
				Iterator<Entry<String, Object>> collSetsIn = collSetIn.iterator();
				
				while (collSetsIn.hasNext()) {
					Entry<String, Object> entry = collSetsIn.next();
					final Predicate predicate = root.get(entry.getKey()).in(entry.getValue());
					predicates.add(predicate);
				}
				
				Set<Entry<String, Object>> collSetNotIn = whereClauseNotIn.entrySet();
				Iterator<Entry<String, Object>> collSetsNotIn = collSetNotIn.iterator();
				
				while (collSetsNotIn.hasNext()) {
					Entry<String, Object> entry = collSetsNotIn.next();
					final Predicate predicate = cb.not(root.get(entry.getKey()).in(entry.getValue()));
					predicates.add(predicate);
				}
				
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};		
	}
	
	
	
}
