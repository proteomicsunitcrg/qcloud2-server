package eu.qcloud.file;

import java.util.List;

import eu.qcloud.data.Data;

public class Summary {
    String sequence;

    List<Data> values;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Summary() {
    }

    public List<Data> getValues() {
        return values;
    }

    public void setValues(List<Data> values) {
        this.values = values;
    }

    public Summary(String sequence, List<Data> values) {
        this.sequence = sequence;
        this.values = values;
    }




}
