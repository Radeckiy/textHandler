package com.testtasks.texthandler.models;

import java.util.ArrayList;

public class Section {
    private Integer sectionId, sectionTitleStringIndex;
    private ArrayList<Integer> stringsBodyIndices, nestedSectionsIndices;

    public Section(Integer sectionId, Integer sectionTitleStringIndex, ArrayList<Integer> stringsBodyIndices, ArrayList<Integer> nestedSectionsIndices) {
        this.sectionId = sectionId;
        this.sectionTitleStringIndex = sectionTitleStringIndex;
        this.stringsBodyIndices = stringsBodyIndices;
        this.nestedSectionsIndices = nestedSectionsIndices;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getSectionTitleStringIndex() {
        return sectionTitleStringIndex;
    }

    public void setSectionTitleStringIndex(Integer sectionTitleStringIndex) {
        this.sectionTitleStringIndex = sectionTitleStringIndex;
    }

    public ArrayList<Integer> getNestedSectionsIndices() {
        return nestedSectionsIndices;
    }

    public void setNestedSectionsIndices(ArrayList<Integer> nestedSectionsIndices) {
        this.nestedSectionsIndices = nestedSectionsIndices;
    }

    public ArrayList<Integer> getStringsBodyIndices() {
        return stringsBodyIndices;
    }

    public void setStringsBodyIndices(ArrayList<Integer> stringsBodyIndices) {
        this.stringsBodyIndices = stringsBodyIndices;
    }

    @Override
    public String toString() {
        return "{ sectionId: " + sectionId + " sectionTitleStringIndex: " + sectionTitleStringIndex + " stringsBodyIndices: " + stringsBodyIndices.toString() + " nestedSectionId: " + nestedSectionsIndices.toString() + " }";
    }
}
