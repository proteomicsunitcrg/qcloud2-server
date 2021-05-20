package eu.qcloud.helper;

import java.util.List;

import eu.qcloud.data.Data;
import eu.qcloud.troubleshooting.annotation.Annotation;
import eu.qcloud.troubleshooting.annotation.AnnotationRepository.AnnotationForPlot;

public class DataAndAnnotation {

    private List <Data> data;

    private AnnotationForPlot annotation;

    public DataAndAnnotation() {
    }

    public DataAndAnnotation(List <Data> data, AnnotationForPlot annotation) {
        this.data = data;
        this.annotation = annotation;
    }

    public List <Data> getData() {
        return data;
    }

    public void setData(List <Data> data) {
        this.data = data;
    }

    public AnnotationForPlot getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationForPlot annotation) {
        this.annotation = annotation;
    }


}
