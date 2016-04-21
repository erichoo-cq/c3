package com.c3.base.core.repository.jpa.qlhelper;

import java.util.HashMap;
import java.util.Map;

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
