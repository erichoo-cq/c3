package com.c3.base.core.repository.jpa.qlhelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * description:带参数的QlHelper
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:38:06
 * @see modify content------------author------------date
 */
public abstract class ParamHelper extends QlHelper {

   protected StringBuffer ql = new StringBuffer();

   protected final Map<String, Object> params = new HashMap<String, Object>();

   public abstract ParamHelper addQl(String ql);

   public Map<String, Object> getParams() {
      return params;
   }

   public ParamHelper addParams(final String name, final Object value) {
      this.params.put(name, value);
      return this;
   }

}
