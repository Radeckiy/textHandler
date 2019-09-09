package com.testtasks.texthandler.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ProcessedText {
    @Id
    private ObjectId _id;
    private String[] text;
    //private Section[] textStructure;

    public ProcessedText(ObjectId _id, String[] text) {
        this._id = _id;
        this.text = text;
        //this.textStructure = textStructure;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

//    public Section[] getTextStructure() {
//        return textStructure;
//    }
//
//    public void setTextStructure(Section[] textStructure) {
//        this.textStructure = textStructure;
//    }
}
