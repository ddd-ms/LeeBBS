package com.NJU.SWI.LeeBBS.entity;

public class Page {
    private int current = 1;
    private int limit = 10;
    private int rows;
    private String path;
    public int getOffset() {
        return (current - 1) * limit;
    }
    public int getTotalRow() {
        return rows / limit + (rows % limit == 0 ? 0 : 1);
    }
    public int getFrom() {
        int from = current - 3;
        return from <= 1 ? 1 : from;
    }
    public int getTo() {
        int to = current + 3;
        return to >= getTotalRow() ? getTotalRow() : to;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current <= 1){
            current = 1;
        }
        this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit <= 0 && limit > 100){
            limit = 10;
        }
        this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
