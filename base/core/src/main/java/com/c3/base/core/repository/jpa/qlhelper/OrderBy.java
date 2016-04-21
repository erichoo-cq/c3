package com.c3.base.core.repository.jpa.qlhelper;

/**
 * 
 * description: jpql orderby语句排序封装
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:36:58
 * @see modify content------------author------------date
 */
public class OrderBy extends QlHelper {
   private static final String ASC = "asc";
   private static final String DESC = "desc";
   private StringBuffer ql = new StringBuffer();

   public OrderBy asc(String name) {
      ql.append(name).append(" ").append(ASC).append(",");
      return this;
   }

   public OrderBy desc(String name) {
      ql.append(name).append(" ").append(DESC).append(",");
      return this;
   }

   @Override
   public String toString() {
      if (ql.length() <= 0) {
         return "";
      } else {
         ql.deleteCharAt(ql.length() - 1);
         return " order by " + ql.toString();
      }
   }

}
