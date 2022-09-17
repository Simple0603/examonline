package com.duyi.examonline.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PageVO implements Serializable {
    private int curr ;  //当前页数
    private int rows ;  //每页数据条数
    private long total ;    //数据总条数
    private int max ;   //最大页数
    private int start ; //limit子句的第一个参数
    private int end ;   //limit子句的第二个参数
    private List<?> data ;  //查询到的所有数据组成的集合
    private Map<String,Object> condition ;  //查询条件组成的集合,目前只有tname一个条件

    public PageVO() {
    }

    public PageVO(int curr, int rows, long total, int max, int start, int end, List<?> data, Map<String, Object> condition) {
        this.curr = curr;
        this.rows = rows;
        this.total = total;
        this.max = max;
        this.start = start;
        this.end = end;
        this.data = data;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "curr=" + curr +
                ", rows=" + rows +
                ", total=" + total +
                ", max=" + max +
                ", start=" + start +
                ", end=" + end +
                ", data=" + data +
                ", condition=" + condition +
                '}';
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }
}
