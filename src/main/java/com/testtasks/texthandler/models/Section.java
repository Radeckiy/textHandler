package com.testtasks.texthandler.models;

public class Section {
    private int nestedLevel = 0;
    private String str = "";

    Section(String str, int nestedLevel) {
        this.nestedLevel = nestedLevel;
        this.str = str;
    }

    public int getNestedLevel() {
        return nestedLevel;
    }

    public void setNestedLevel(int nestedLevel) {
        this.nestedLevel = nestedLevel;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "nestedLevel: " + nestedLevel + " str: " + str;
    }
}
