package com.rheuijerjans.postgresbenchmarks.multicolumnindex.one;

public class OneColumn {

    private final String one;
    private final int perc;


    public OneColumn(String one, int perc) {
        this.one = one;
        this.perc = perc;
    }

    public String getOne() {
        return one;
    }

    public int getPerc() {
        return perc;
    }
}
