package com.app.util;

/**
 * 分页工具类 . <br>
 * 
 * @author wangtw <br>
 */
public final class PageUtils {

    /**
     * 页数
     */
    private static ThreadLocal<Integer> pageNum = new ThreadLocal<Integer>();

    /**
     * 每页记录数
     */
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();

    /**
     * 获取页数
     * 
     * @return
     */
    public static Integer getPageNum() {
        Integer pn = pageNum.get();
        if (pn == null) {
            return 0;
        }
        return pn;
    }

    /**
     * 设置页数
     * 
     * @param pageNumValue
     */
    public static void setPageNum(int pageNumValue) {
        pageNum.set(pageNumValue);
    }

    /**
     * 清除页数
     */
    public static void removePageNum() {
        pageNum.remove();
    }

    /**
     * 获取每页记录数
     * 
     * @return
     */
    public static Integer getPageSize() {
        Integer ps = pageSize.get();
        if (ps == null) {
            return 0;
        }
        return ps;
    }

    /**
     * 设置每页记录数
     * 
     * @param pageSizeValue
     */
    public static void setPageSize(int pageSizeValue) {
        pageSize.set(pageSizeValue);
    }

    /**
     * 清除每页记录数
     */
    public static void removePageSize() {
        pageSize.remove();
    }

}
