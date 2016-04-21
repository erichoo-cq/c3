package com.c3.base.core.repository.jpa.qlhelper;

/**
 * 
 * description:jpql set语句的封装
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:38:27
 * @see modify content------------author------------date
 */
public class Set extends ParamHelper {

   @Override
   public Set addQl(String ql) {
      this.ql.append(ql).append(",");
      return this;
   }

   @Override
   public Set addParams(final String name, final Object value) {
      return (Set) super.addParams(name, value);
   }

   @Override
   public String toString() {
      if (ql.length() <= 0) {
         return "";
      } else {
         ql.deleteCharAt(ql.length() - 1);
         return " set " + ql.toString();
      }
   }

}
