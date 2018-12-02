package com.pushertest.www.budgetcatcher.Model;

public class AccountItem {

    private String col1;
    private String col2;
    private String col3;

    private String id;

    public AccountItem(String col1, String col3, String id) {

        this.col1 = col1;
        this.col3 = col3;
        this.id = id;

    }

    public AccountItem(String col1, String col2, String col3, String id) {

        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }
}
