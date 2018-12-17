package tms.dao;
//
import tms.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

	private final static int BATCH_COMMIT_SIZE = 100;

	protected abstract Session getCurrentSession();

	protected String create(Object o) {
		Session ss = getCurrentSession();
        String id = ss.save(o).toString();
        return id;
    }
	protected void batchCreate(List<?> objList) {
		Session ss = getCurrentSession();
		Class<?> clazz = null;
		for (int i = 0 ; i < objList.size() ; i++ ) {
			Object obj = objList.get(i);
			ss.save(obj);

			if ((i+1) % BATCH_COMMIT_SIZE == 0) {
				ss.flush();
				ss.clear();
			}

			if(clazz == null) clazz = obj.getClass();
		}
	}
	protected void batchCreate(Object[] objs) {
		Session ss = getCurrentSession();
		Class<?> clazz = null;
		for (int i = 0 ; i < objs.length; i++ ) {
			Object obj = objs[i];
			ss.save(obj);

			if ((i+1) % BATCH_COMMIT_SIZE == 0) {
				ss.flush();
				ss.clear();
			}

			if(clazz == null) clazz = obj.getClass();
		}
	}

	protected void updateByID(Class<?> entityClazz, Object transientPO, Serializable id, String... ignorePropertyNames){
		Object persistentO = getByID(entityClazz, id);
    	if(persistentO == null) return;
		
    	BeanUtils.copyProperties(transientPO, persistentO, ignorePropertyNames);
    }
	protected int updateByHQL(Class<?> entityClazz, String hql, List<Object> params){
		return this.updateByHQL(entityClazz, hql, params.toArray());
	}
	protected int updateByHQL(Class<?> entityClazz, String hql, Object[] params){
		Session ss = getCurrentSession();
        Query query = ss.createQuery(hql);
    	
        for(int i = 0 , len = params.length ; i < len ; i++) {
		    query.setParameter(i , params[i]);
		}

    	return query.executeUpdate();
    }

	protected void deleteByID(Class<?> entityClazz, Serializable id) {
    	Object persistentO = getByID(entityClazz, id);
    	if(persistentO == null) return;
    	
    	Session ss = getCurrentSession();
        ss.delete(persistentO);
    }
    protected int deleteByHQL(Class<?> entityClazz, String hql, Object[] params) {
    	Session ss = getCurrentSession();
    	Query query = ss.createQuery(hql);
    	
    	for(int i = 0 , len = params.length ; i < len ; i++) {
			query.setParameter(i , params[i]);
		}

    	int i = query.executeUpdate();
    	return i;
    }
    protected void deleteByHQL(Class<?> entityClazz, String hql, Object[] params1, Object[] namedParams) {
    	Session ss = getCurrentSession();
    	Query query = ss.createQuery(hql);
    	if(params1 != null){
    		for(int i = 0 , len = params1.length ; i < len ; i++) {
    			query.setParameter(i , params1[i]);
    		}
    	}
    	
    	if(namedParams != null){
    		query.setParameterList("Array", namedParams);
    	}
    	
    	query.executeUpdate();
    }
    
    protected <T> T getByID(Class<T> entityClazz, Serializable id) {
        return (T) getCurrentSession().get(entityClazz, id);
    }
    protected <T> T getByHQL(String hql, Object... params) {
        Query query = getCurrentSession().createQuery(hql);
		
		for(int i = 0 , len = params.length ; i < len ; i++) {
			query.setParameter(i , params[i]);
		}

		List<T> resultList = query.list();
		if(resultList != null && resultList.size() == 1) {
	    	return (T) resultList.get(0);
	    } else {
	    	return null;
	    }
    }
    protected <T> T getByEQ(Class<T> entityClazz, Object... params) {
    	Criteria c = getCurrentSession().createCriteria(entityClazz);

	    for(int i = 0 , len = params.length ; i < len ; i++) {
	    	Object[] param = (Object[]) params[i];
	    	c.add(Restrictions.eq((String)param[0], param[1]));
		}

	    List<?> resultList = c.list();
	    if(resultList != null && resultList.size() == 1) {
	    	return (T) resultList.get(0);
	    } else {
	    	return null;
	    }
    }

    protected <T> T getBySQL(String sql, Object... params) {
        Query query = getCurrentSession().createSQLQuery(sql);
		
		for(int i = 0 , len = params.length ; i < len ; i++) {
			query.setParameter(i , params[i]);
		}

		List<T> resultList = query.list();
		if(resultList != null && resultList.size() == 1) {
	    	return (T) resultList.get(0);
	    } else {
	    	return null;
	    }
    }
    protected <T> T getBySQL(String sql, Class<?> entityClazz, Object... params) {
        Query query = getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(entityClazz));
		
		for(int i = 0 , len = params.length ; i < len ; i++) {
			query.setParameter(i , params[i]);
		}

		List<T> resultList = query.list();
		if(resultList != null && resultList.size() == 1) {
	    	return (T) resultList.get(0);
	    } else {
	    	return null;
	    }
    }
    
    protected List<?> listByHQL(final String hql, final Map<String, ?> paramMap){
	    return (List<?>) createQuery(hql, paramMap).list();
	}
	protected <T>List<T> listBySQL(Class<?> entityClazz, String sql, List<?> filterParams) {
		return this.listBySQL(entityClazz, sql, filterParams.toArray());
	}
    protected <T>List<T> listBySQL(Class<?> entityClazz, String sql, Object... filterParams) {
		Query query = getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(entityClazz));
		
		for(int i = 0 , len = filterParams.length ; i < len ; i++) {
			query.setParameter(i , filterParams[i]);
		}
		return query.list();
	}
    
    protected List<?> listBySQL(ResultTransformer transformer, String sql, Object... filterParams) {
		Query query = getCurrentSession().createSQLQuery(sql).setResultTransformer(transformer);
		
		for(int i = 0 , len = filterParams.length ; i < len ; i++) {
			query.setParameter(i , filterParams[i]);
		}
		return (List<?>)query.list();
	}
    
    protected List<?> listBySQL(String sql, List<?> filterParams) {
    	return listBySQL(sql, filterParams.toArray());
    }
    protected List<?> listBySQL(String sql, Object... filterParams) {
		Query query = getCurrentSession().createSQLQuery(sql);
		
		for(int i = 0 , len = filterParams.length ; i < len ; i++) {
			query.setParameter(i , filterParams[i]);
		}
		return (List<?>)query.list();
	}
    protected <T>List<T> listByHQL(String hql, List<?> params) {
		Query query = getCurrentSession().createQuery(hql);
		
		for(int i = 0 , len = params.size() ; i < len ; i++) {
			query.setParameter(i , params.get(i));
		}
		
		return query.list();
	}
	protected <T>List<T> listByHQL(String hql, Object... params) {
		Query query = getCurrentSession().createQuery(hql);
		
		for(int i = 0 , len = params.length ; i < len ; i++) {
			query.setParameter(i , params[i]);
		}
		
		return query.list();
	}

	protected <T>List<T> listByEQ(Class<?> entityClazz, Object[] params) {
    	Criteria c = getCurrentSession().createCriteria(entityClazz);

	    for(int i = 0 , len = params.length ; i < len ; i++) {
	    	Object[] param = (Object[]) params[i];
	    	c.add(Restrictions.eq((String)param[0], param[1]));
		}
	    
	    return c.list();
    }

	protected List<?> listByIDs(Class<?> entityClazz, String idName, List<?> ids) {
        if(ids == null || ids.size() == 0) return null;
		
    	Criteria c = getCurrentSession().createCriteria(entityClazz);
	    c.add(Restrictions.in(idName, ids.toArray()));

	    return c.list();
	}

	protected <T>List<T> pageListBySQL(Class entityClazz, String sql, int pageNo, int pageSize, Object[] filterParams) {
		pageSize = checkPageSize(pageSize);

		Query query = getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(entityClazz));
		for(int i = 0, len = filterParams.length ; i < len ; i++) {
			query.setParameter(i, filterParams[i]);
		}

		return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}


	
	protected long countByEQ(Class<?> entityClazz, String countPropertyName, Object... filterParams) {
		Criteria c = getCurrentSession().createCriteria(entityClazz);
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count(countPropertyName));
		c.setProjection(projList); 
		
		for(int i = 0 , len = filterParams.length ; i < len ; i++) {
	    	Object[] param = (Object[]) filterParams[i];
	    	c.add(Restrictions.eq((String)param[0], param[1]));
		}
        
		List<?> resultList = c.list(); 
		if (resultList != null && resultList.size() == 1 ) {
			return (Long) resultList.get(0);
		}
		
		return 0;
	}



    private Query createQuerySql(final String queryString, final Object... values) {
		Query query = getCurrentSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if(values[i] != null)
					query.setParameter(i, values[i]);
			}
		}
		return query;
	}

    private Query createSQLQuery(final String queryString, final Object... values) {
		Query query = getCurrentSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if(values[i] != null)
					query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	private Query createQuery(final String hql, final Object... values) {
		Query query = getCurrentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if(values[i] != null)
					query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	protected long count(Class<?> entityClazz, String countPropertyName, Object... filterParams) {
		Criteria c = getCurrentSession().createCriteria(entityClazz);

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count(countPropertyName));
		c.setProjection(projList);

		setRestrictions(c, filterParams);

		List<?> resultList = c.list();
		if (resultList != null && resultList.size() == 1 ) {
			return (Long) resultList.get(0);
		}

		return 0;
	}
	protected long sumSQLResult(final String sql, final List<?> filterParams) {
		return sumSQLResult(sql, filterParams.toArray());
	}
	protected long sumSQLResult(final String sql, final Object... filterParams) {
		java.math.BigDecimal cntResult = (java.math.BigDecimal) createSQLQuery(sql, filterParams).uniqueResult();
		return cntResult.longValue();
	}
	protected long countSQLResult(final String sql, final List<?> filterParams) {
		return countSQLResult(sql, filterParams.toArray());
	}
	protected long countSQLResult(final String sql, final Object... filterParams) {
		java.math.BigInteger cntResult = (java.math.BigInteger) createSQLQuery(sql, filterParams).uniqueResult();
		if(cntResult != null){
			return cntResult.longValue();
		}else{
			return 0;
		}
	}
	protected long countHQLResult(final String hql, final Object... filterParams) {
		String countHql = prepareCountHql(hql);
		return (Long) createQuery(countHql, filterParams).uniqueResult();
	}
	protected long countHQLResult(final String hql, final List<Object> filterParams) {
		String countHql = prepareCountHql(hql);
		return (Long) createQuery(countHql, filterParams.toArray()).uniqueResult();
	}

	/**
	 * 指定获取持久化对象(PO)的部分字段数据
	 * @param entityClazz
	 * @param gottenProperties
	 * @param pageNo
	 * @param pageSize
	 * @param sortParams
	 * @param filterParams
	 * @return
	 */
	protected List<?> pageList(Class<?> returnClazz, Class<?> entityClazz, String[] gottenProperties, int pageNo, int pageSize, Object[] sortParams, Object[] filterParams) {
		pageSize = checkPageSize(pageSize);

		DetachedCriteria dc = DetachedCriteria.forClass(entityClazz);

		//限定查询字段
		ProjectionList pList = Projections.projectionList();
		for(String propertyName : gottenProperties) {
			pList.add(Projections.property(propertyName).as(propertyName));
		}
		dc.setProjection(pList);

		//指定返回的对象的类型
		dc.setResultTransformer(Transformers.aliasToBean(returnClazz));

		//设定过滤条件
		setRestrictions(dc, filterParams);

		//设定排序
		setOrders(dc, sortParams);

		return dc.getExecutableCriteria(getCurrentSession()).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	protected <T>List<T> pageList(Class<?> entityClazz, int pageNo, int pageSize, Object[] sortParams) {
		pageSize = checkPageSize(pageSize);

		return this.pageList(entityClazz, pageNo, pageSize, sortParams, null);
	}
	protected <T>List<T> pageList(Class<?> entityClazz, int pageNo, int pageSize, Object[] sortParams, Object[] filterParams) {
		pageSize = checkPageSize(pageSize);

		Criteria c = getCurrentSession().createCriteria(entityClazz);

		setOrders(c, sortParams);

		if(filterParams != null) setRestrictions(c, filterParams);

		return c.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	protected void setRestrictions(DetachedCriteria c, Object[] filterParamsForAnd, Object[] filterParamsForOR) {
		this.setRestrictions(c, filterParamsForAnd);

		Criterion[] criterionsForOR = new Criterion[filterParamsForOR.length];
		for(int i = 0, len = filterParamsForOR.length ; i < len ; i++) {
			criterionsForOR[i] = getCriterionByFilterParam((Object[])filterParamsForOR[i]);
		}
		c.add(Restrictions.or(criterionsForOR));
	}
	protected void setRestrictionsOR(Criteria c, Object[] filterParamsForAnd, Object[] filterParamsForOR) {
		if(filterParamsForAnd != null && filterParamsForAnd.length > 0) this.setRestrictions(c, filterParamsForAnd);

		Criterion[] criterionsForOR = new Criterion[filterParamsForOR.length];
		for(int i = 0, len = filterParamsForOR.length ; i < len ; i++) {
			criterionsForOR[i] = getCriterionByFilterParam((Object[])filterParamsForOR[i]);
		}
		c.add(Restrictions.or(criterionsForOR));
	}
	private Criterion getCriterionByFilterParam(Object[] filterParam) {
		String filterType = filterParam[1].toString();
		if(Constants.FILTERBY_TYPE.EQ.toString().equals(filterType)) {
			return Restrictions.eq((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.GT.toString().equals(filterType)) {
			return Restrictions.gt((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.GE.toString().equals(filterType)) {
			return  Restrictions.ge((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.LT.toString().equals(filterType)) {
			return  Restrictions.lt((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.LE.toString().equals(filterType)) {
			return  Restrictions.le((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.LIKE.toString().equals(filterType)) {
			return Restrictions.like((String) filterParam[0], escapeSQLLike((String)filterParam[2]), MatchMode.ANYWHERE);
		} else if(Constants.FILTERBY_TYPE.START.toString().equals(filterType)) {
			return Restrictions.like((String) filterParam[0], escapeSQLLike((String)filterParam[2]), MatchMode.START);
		} else if(Constants.FILTERBY_TYPE.NE.toString().equals(filterType)) {
			return  Restrictions.ne((String)filterParam[0], filterParam[2]);
		} else if(Constants.FILTERBY_TYPE.IN.toString().equals(filterType)) {
			return  Restrictions.in((String)filterParam[0], (Object[]) filterParam[2]);
		} else {
			logger.error("getCriterionByFilterParam", filterParam.toString(), "Invalid FilterParam. Default To Use EQ");
			return  Restrictions.eq((String)filterParam[0], filterParam[2]);
		}
	}

	protected String escapeSQLLike(String likeStr) {
		String str = StringUtils.replace(likeStr, "_", "\\_");
		str = StringUtils.replace(str, "%",    "\\%");
		return str;
	}


	protected void setRestrictions(Criteria c, Object[] filterParams) {
		for(int i = 0, len = filterParams.length ; i < len ; i++) {
			Object[] filterParam = (Object[]) filterParams[i];

			c.add(getCriterionByFilterParam(filterParam));
		}
	}
	protected void setOrders(Criteria c, Object[] sortParams) {
		if(sortParams != null){
			for(int i=0; i < sortParams.length; i++) {
				Object[] sortParam = (Object[]) sortParams[i];

				if((Boolean) sortParam[1]) {
					c.addOrder(Order.asc((String) sortParam[0]));
				} else {
					c.addOrder(Order.desc((String) sortParam[0]));
				}
			}
		}
	}
	protected void setRestrictions(DetachedCriteria c, Object[] filterParams) {
		if(filterParams == null) return;

		for(int i = 0, len = filterParams.length ; i < len ; i++) {
			Object[] filterParam = (Object[]) filterParams[i];

			c.add(getCriterionByFilterParam(filterParam));
		}
	}
	private void setOrders(DetachedCriteria c, Object[] sortParams) {
		if(sortParams != null){
			for(int i=0; i < sortParams.length; i++) {
				Object[] sortParam = (Object[]) sortParams[i];

				if((Boolean) sortParam[1]) {
					c.addOrder(Order.asc((String) sortParam[0]));
				} else {
					c.addOrder(Order.desc((String) sortParam[0]));
				}
			}
		}
	}
	private String prepareCountHql(String originalHql) {
		if(originalHql.contains("distinct"))
			return "select count(1) from ("+ originalHql +") o";

		String fromHql = "";
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(originalHql, "from");
		if(fromHql.equals("from "))
			fromHql = "from " + StringUtils.substringAfter(originalHql, "from".toUpperCase());

		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(1) " + fromHql;
		return countHql;
	}

	private int checkPageSize(int pageSize) {
		if(pageSize > Constants.MAX_PAGESIZE) {
			return 1;
		} else {
			return pageSize;
		}
	}

	/**
	 *获取当天的最后时间 yyyy-MM-dd 23:59:59
	 * @param today 日期为字符串格式：yyyy-MM-dd
	 * @return 当天的最后时间
	 */
	protected String getTodayLastTime(String today){
		String regex = "^([0-9]{4})-([0-9]{2})-([0-9]{2})$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(today);
		if(StringUtils.isNotBlank(today)&& m.matches()){
			today+=" 23:59:59";
		}
		return today;
	}
}
