package com.testtasks.texthandler.models;

import java.util.ArrayList;

public class Section {
    private Integer sectionId, stringBodyIndex;
    private ArrayList<Integer> nestedSectionsIndices;

    public Section(Integer sectionId, Integer stringBodyIndex, ArrayList<Integer> nestedSectionsIndices) {
        this.sectionId = sectionId;
        this.stringBodyIndex = stringBodyIndex;
        this.nestedSectionsIndices = nestedSectionsIndices;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public ArrayList<Integer> getNestedSectionsIndices() {
        return nestedSectionsIndices;
    }

    public void setNestedSectionsIndices(ArrayList<Integer> nestedSectionsIndices) {
        this.nestedSectionsIndices = nestedSectionsIndices;
    }

    public Integer getStringBodyIndex() {
        return stringBodyIndex;
    }

    public void setStringBodyIndex(Integer stringBodyIndex) {
        this.stringBodyIndex = stringBodyIndex;
    }

    @Override
    public String toString() {
        return "{ sectionId: " + sectionId + " stringBodyIndex: " + stringBodyIndex + " nestedSectionsIndices: " + nestedSectionsIndices.toString() + " }";
    }
}
