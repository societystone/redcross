package com.app.bean;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

import lombok.Data;

/**
 * 分页用的Bean . <br>
 * 
 * @author wangtw <br>
 */
@Data
public class PageResultBean<T> implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6477868212171605239L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    public PageResultBean(List<T> rows) {
        init(rows);
    }

    /**
     * 初始化
     * 
     * @param rows
     */
    private void init(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.total = page.getTotal();
            this.rows = page.getResult();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
        }
    }

}
