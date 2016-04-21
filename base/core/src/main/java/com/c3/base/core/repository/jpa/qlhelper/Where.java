package com.c3.base.core.repository.jpa.qlhelper;

/**
 * 
 * description:jpql where语句的封装
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:39:21
 * @see modify content------------author------------date
 */
public class Where extends ParamHelper {

   @Override
   public Where addQl(String ql) {
      this.ql.append(ql);
      return this;
   }

   @Override
   public Where addParams(final String name, final Object value) {
      return (Where) super.addParams(name, value);
   }

   @Override
   public String toString() {
      if (ql.length() <= 0) {
         return "";
      } else {
         return " where " + ql.toString();
      }
   }

}
