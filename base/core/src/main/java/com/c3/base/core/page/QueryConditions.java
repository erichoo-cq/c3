package com.c3.base.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.c3.base.core.util.SqlUtil;

public class QueryConditions implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String EQUAL = "=";
	public static final String NOT_EQUAL = "<>";
	public static final String GREATER_THAN = ">";
  	public static final String GREATER_EQUAL = ">=";
  	public static final String LESS_THAN = "<";
  	public static final String LESS_EQUAL = "<=";
  	public static final String INCLUDE = "%value%";
  	public static final String LEFT_INCLUDE = "value%";
  	public static final String RIGHT_INCLUDE = "%value";
  	public static final String ISNULL = "isnull";
  	public static final String ISNOTNULL = "isnotnull";
  	public static final String ISEMPTY = "isempty";
  	public static final String ISNOTEMPTY = "isnotempty";
  	private String orderBy;
  	private String groupBy;
  	private String otherHql;
  	private List<String> propertyNames;
  	private List<String> operators;
  	private List<Object> values;
  	private String statementId;
  	private String countStatementId;
  	private Object paramValue;

  	public QueryConditions( ) {
  		this.orderBy = null;
  		this.groupBy = null;
  		this.propertyNames = new ArrayList<String>();
  		this.operators = new ArrayList<String>();
  		this.values = new ArrayList<Object>();
  		this.otherHql = null;
  		this.statementId = null;
  		this.countStatementId = null;
  		this.paramValue = null;
  	}

  	public void setCondition(String propertyName, String operator, Object value) {
  		if ((propertyName == null) || ("".equals(propertyName.trim()))) {
  			return;
  		}
  		String tempOperator = operator;
  		if ((operator == null) || ("".equals(operator.trim()))) {
  			tempOperator = "=";
  		}

  		Object tempValue = value;
  		if (("isnull".equals(tempOperator)) || ("isnotnull".equals(tempOperator)) || ("isempty".equals(tempOperator)) || ("isnotempty".equals(tempOperator)))
  		{
  			tempValue = tempOperator;
  		}

  		if (tempValue == null) {
  			return;
  		}
  		if (((tempValue instanceof String)) && ("".equals(tempValue.toString()))) {
  			return;
  		}
  		this.propertyNames.add(propertyName);

  		if (tempOperator.equals("%value%")) {
  			this.operators.add("like");
  			this.values.add("%" + SqlUtil.parseLikeString(tempValue.toString()) + "%");
  		} else if (tempOperator.equals("value%")) {
  			this.operators.add("like");
  			this.values.add(SqlUtil.parseLikeString(tempValue.toString()) + "%");
  		} else if (tempOperator.equals("%value")) {
  			this.operators.add("like");
  			this.values.add("%" + SqlUtil.parseLikeString(tempValue.toString()));
  		} else {
  			this.operators.add(tempOperator);
  			this.values.add(tempValue);
  		}
  	}

  	public void setConditionEqual(String propertyName, Object value) {
  		setCondition(propertyName, "=", value);
  	}

  	public void setConditionNotEqual(String propertyName, Object value) {
  		setCondition(propertyName, "<>", value);
  	}

  	public void setConditionGreaterEqual(String propertyName, Object value) {
  		setCondition(propertyName, ">=", value);
  	}

  	public void setConditionGreaterThan(String propertyName, Object value) {
  		setCondition(propertyName, ">", value);
  	}

  	public void setConditionLessEqual(String propertyName, Object value) {
  		setCondition(propertyName, "<=", value);
  	}

  	public void setConditionLessThan(String propertyName, Object value) {
  		setCondition(propertyName, "<", value);
  	}

  	public void setConditionLeftInclude(String propertyName, Object value) {
  		setCondition(propertyName, "value%", value);
  	}

  	public void setConditionRightInclude(String propertyName, Object value) {
  		setCondition(propertyName, "%value", value);
  	}

  	public void setConditionInclude(String propertyName, Object value) {
  		setCondition(propertyName, "%value%", value);
  	}

  	public void setConditionIsNull(String propertyName) {
  		setCondition(propertyName, "isnull", "isnull");
  	}

  	public void setConditionIsNotNull(String propertyName) {
  		setCondition(propertyName, "isnotnull", "isnotnull");
  	}

  	public void setConditionIsEmpty(String propertyName) {
  		setCondition(propertyName, "isempty", "isempty");
  	}

  	public void setConditionIsNotEmpty(String propertyName) {
  		setCondition(propertyName, "isnotempty", "isnotempty");
  	}

  	public void setParameter(String statementId, String countStatementId, Object paramValue) {
  		this.statementId = statementId;
  		this.countStatementId = countStatementId;
  		this.paramValue = paramValue;
  	}

  	public String getOrderBy() {
  		return this.orderBy;
  	}

  	public void setOrderBy(String orderBy) {
  		this.orderBy = orderBy;
  	}

  	public String getGroupBy( ) {
  		return this.groupBy;
  	}

  	public void setGroupBy(String groupBy) {
  		this.groupBy = groupBy;
  	}

  	public String getOtherHql() {
  		return this.otherHql;
  	}

  	public void setOtherHql(String otherHql) {
  		this.otherHql = otherHql;
  	}

  	public List<String> getPropertyNames(){
  		return this.propertyNames;
  	}

  	public void setPropertyNames(List<String> propertyNames) {
  		this.propertyNames = propertyNames;
  	}

  	public List<String> getOperators() {
  		return this.operators;
  	}

  	public void setOperators(List<String> operators) {
  		this.operators = operators;
  	}

  	public List<Object> getValues() {
  		return this.values;
  	}

  	public void setValues(List<Object> values) {
  		this.values = values;
  }

  public String getStatementId()
  {
    return this.statementId;
  }

  public void setStatementId(String statementId)
  {
    this.statementId = statementId;
  }

  public String getCountStatementId()
  {
    return this.countStatementId;
  }

  public void setCountStatementId(String countStatementId)
  {
    this.countStatementId = countStatementId;
  }

  public Object getParamValue()
  {
    return this.paramValue;
  }

  public void setParamValue(Object paramValue)
  {
    this.paramValue = paramValue;
  }
}