package com.rheuijerjans.postgresbenchmarks.multicolumnindex.two;

public class TwoColumn {

    private final String one;
    private final String two;
    private final int perc;

    public TwoColumn(String one, String two, int perc) {
        this.one = one;
        this.two = two;
        this.perc = perc;
    }

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public int getPerc() {
        return perc;
    }
}
