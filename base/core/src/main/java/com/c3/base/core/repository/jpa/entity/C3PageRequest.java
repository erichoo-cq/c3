package com.c3.base.core.repository.jpa.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * description:对Pageable的扩展
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:35:59
 * @see Pageable
 */
public class C3PageRequest implements Pageable {

   private final Sort sort;

   public static final int DEFAULT_PAGE_NUMS = 15;

   /** 总记录数，默认为0 */
   private long totalRecords = 0;

   /** 总页数，默认为0 */
   private int totalPages = 0;

   /** 每页显示记录数量，默认为10 */
   private int pageSize = DEFAULT_PAGE_NUMS;

   /** 当前页数，默认为1 */
   private int pageNumber = 1;

   /** 页码个数 */
   private int pageCount = 5;

   private int beginPageNumber = 1;

   private int endPageNumber = 1;

   public C3PageRequest(int pageNumber, int pageSize) {
      this(pageNumber, pageSize, null);
   }

   public C3PageRequest(int pageNumber, int pageSize, Sort sort) {
      if (pageNumber < 0) {
         throw new IllegalArgumentException("Page number must not be less than zero!");
      }
      if (pageSize < 1) {
         throw new IllegalArgumentException("Page size must not be less than one!");
      }
      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
      this.sort = sort;
   }

   public long getTotalRecords() {
      return totalRecords;
   }

   public void setTotalRecords(long totalRecords) {
      this.totalRecords = totalRecords;
   }

   public int getTotalPages() {
      long ceilRecords = this.totalRecords + (long) this.getPageSize() - 1;
      return (int) (ceilRecords / (long) this.getPageSize());
   }

   public void setPageSize(int pageSize) {
      this.pageSize = (pageSize <= 0 ? DEFAULT_PAGE_NUMS : pageSize);
   }

   @Override
   public int getPageSize() {
      return this.pageSize;
   }

   @Override
   public int getOffset() {
      return pageNumber * pageSize;
   }

   public Sort getSort() {
      return sort;
   }

   public C3PageRequest next() {
      return new C3PageRequest(getPageNumber() + 1, getPageSize(), getSort());
   }

   public C3PageRequest previous() {
      return getPageNumber() == 0 ? this : new C3PageRequest(getPageNumber() - 1, getPageSize(), getSort());
   }

   @Override
   public C3PageRequest first() {
      return new C3PageRequest(0, getPageSize(), getSort());
   }

   @Override
   public C3PageRequest previousOrFirst() {
      return hasPrevious() ? previous() : first();
   }

   @Override
   public boolean hasPrevious() {
      return pageNumber > 0;
   }

   @Override
   public int getPageNumber() {
      if (pageNumber > getTotalPages()) {
         pageNumber = getTotalPages();
      }
      if (pageNumber < 0) {
         pageNumber = 0;
      }
      return pageNumber;
   }

   public void setPageNumber(int pageNumber) {
      this.pageNumber = pageNumber;
   }

   public int getPageCount() {
      return pageCount;
   }

   public void setPageCount(int pageCount) {
      this.pageCount = pageCount;
   }

   public int getBeginPageNumber() {
      int limit = this.getPageCount() / 2 + 1;
      if (this.getPageNumber() < limit) {
         return 1;
      } else {
         return this.getPageNumber() - (this.getPageCount() / 2);
      }
   }

   public int getEndPageNumber() {
      int endPageNumber = getBeginPageNumber() + getPageCount() - 1;
      if (endPageNumber > getTotalPages()) {
         endPageNumber = getTotalPages();
      }
      if (endPageNumber <= 0) {
         endPageNumber = 1;
      }
      return endPageNumber;
   }

   @Override
   public String toString() {
      return "Page [totalRecords=" + totalRecords + ", totalPages=" + totalPages + ", pageRecords=" + pageSize
            + ", pageNumber=" + pageNumber + ", pageCount=" + pageCount + ", beginPageNumber=" + beginPageNumber
            + ", endPageNumber=" + endPageNumber + "]";
   }

}
