package com.dongao.oa.utils;


import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fengjifei on 2016/8/13.
 */

public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private List<T> list;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int[] navigatepageNums;
    private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
    private String funcParam = ""; // 函数的附加参数，第三个参数值。
    private String message = ""; // 设置提示消息，显示在“共n条”之后
    private int length = 8;// 显示页面长度
    private int slider = 1;// 前后显示页面长度
    public PageInfo() {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
    }

    public PageInfo(List<T> list) {
        this(list, 8);
    }

    public PageInfo(List<T> list, int navigatePages) {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        if(list instanceof Page) {
            Page page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
            if(this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                this.endRow = this.startRow - 1 + this.size;
            }

            this.navigatePages = navigatePages;
            this.calcNavigatepageNums();
            this.calcPage();
            this.judgePageBoudary();
        }

    }

    private void calcNavigatepageNums() {
        int startNum;
        if(this.pages <= this.navigatePages) {
            this.navigatepageNums = new int[this.pages];

            for(startNum = 0; startNum < this.pages; ++startNum) {
                this.navigatepageNums[startNum] = startNum + 1;
            }
        } else {
            this.navigatepageNums = new int[this.navigatePages];
            startNum = this.pageNum - this.navigatePages / 2;
            int endNum = this.pageNum + this.navigatePages / 2;
            int i;
            if(startNum < 1) {
                startNum = 1;

                for(i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = startNum++;
                }
            } else if(endNum > this.pages) {
                endNum = this.pages;

                for(i = this.navigatePages - 1; i >= 0; --i) {
                    this.navigatepageNums[i] = endNum--;
                }
            } else {
                for(i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = startNum++;
                }
            }
        }

    }

    private void calcPage() {
        if(this.navigatepageNums != null && this.navigatepageNums.length > 0) {
            this.firstPage = this.navigatepageNums[0];
            this.lastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
            if(this.pageNum > 1) {
                this.prePage = this.pageNum - 1;
            }

            if(this.pageNum < this.pages) {
                this.nextPage = this.pageNum + 1;
            }
        }

    }

    private void judgePageBoudary() {
        this.isFirstPage = this.pageNum == 1;
        this.isLastPage = this.pageNum == this.pages;
        this.hasPreviousPage = this.pageNum > 1;
        this.hasNextPage = this.pageNum < this.pages;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return this.lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return this.navigatePages;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSlider() {
        return slider;
    }

    public void setSlider(int slider) {
        this.slider = slider;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return this.navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (getPageNum() == getFirstPage()) {// 如果是首页
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "("
                    + getPrePage() + "," + getPageSize() + ",'" + funcParam
                    + "');\">&#171; 上一页</a></li>\n");
        }

        int begin = getPageNum() - (length / 2);

        if (begin < getFirstPage()) {
            begin = getFirstPage();
        }

        int end = begin + length - 1;

        if (end >= getLastPage()) {
            end = getLastPage();
            begin = end - length + 1;
            if (begin < getFirstPage()) {
                begin = getFirstPage();
            }
        }

        if (begin > getFirstPage()) {
            int i = 0;
            for (i = getFirstPage(); i < getFirstPage() + slider && i < begin; i++) {
                sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName
                        + "(" + i + "," + getPageSize() + ",'" + funcParam
                        + "');\">" + (i + 1 - getFirstPage()) + "</a></li>\n");
            }
            if (i < begin) {
                sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            }
        }

        for (int i = begin; i <= end; i++) {
            if (i == getPageNum()) {
                sb.append("<li class=\"active\"><a href=\"javascript:\">"
                        + (i + 1 - getFirstPage()) + "</a></li>\n");
            } else {
                sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName
                        + "(" + i + "," + getPageSize() + ",'" + funcParam
                        + "');\">" + (i + 1 - getFirstPage()) + "</a></li>\n");
            }
        }

        if (getLastPage() - end > slider) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            end = getLastPage() - slider;
        }

        for (int i = end + 1; i <= getLastPage(); i++) {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "("
                    + i + "," + getPageSize() + ",'" + funcParam + "');\">"
                    + (i + 1 - getFirstPage()) + "</a></li>\n");
        }

        if (getPageNum() == getLastPage()) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "("
                    + getNextPage() + "," + getPageSize() + ",'" + funcParam + "');\">"
                    + "下一页 &#187;</a></li>\n");
        }

        sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
        sb.append("<input type=\"text\" value=\""
                + getPageNum()
                + "\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName + "(this.value," + getPageSize() + ",'" + funcParam
                + "');\" onclick=\"this.select();\"/> / ");
        sb.append("<input type=\"text\" value=\""
                + getPageSize()
                + "\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName + "(" + getPageNum() + ",this.value,'" + funcParam
                + "');\" onclick=\"this.select();\"/> 条，");
        sb.append("共 " + getTotal() + " 条" + (message != null ? message : "")
                + "</a></li>\n");

        sb.insert(0, "<ul>\n").append("</ul>\n");

        sb.append("<div style=\"clear:both;\"></div>");

        // sb.insert(0,"<div class=\"page\">\n").append("</div>\n");

        return sb.toString();
    }
}
