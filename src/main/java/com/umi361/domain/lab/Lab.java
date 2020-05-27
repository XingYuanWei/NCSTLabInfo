package com.umi361.domain.lab;

public class Lab {

    private Long lab_id;
    private String lab_name;
    private String lab_label;
    private String lab_descr;

    public Lab(Long lab_id, String lab_name, String lab_label, String lab_descr) {
        this.lab_id = lab_id;
        this.lab_name = lab_name;
        this.lab_label = lab_label;
        this.lab_descr = lab_descr;
    }

    public Lab() {}

    public Long getLab_id() {
        return lab_id;
    }

    public void setLab_id(Long lab_id) {
        this.lab_id = lab_id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getLab_label() {
        return lab_label;
    }

    public void setLab_label(String lab_label) {
        this.lab_label = lab_label;
    }

    public String getLab_descr() {
        return lab_descr;
    }

    public void setLab_descr(String lab_descr) {
        this.lab_descr = lab_descr;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "lab_id=" + lab_id +
                ", lab_name='" + lab_name + '\'' +
                ", lab_label='" + lab_label + '\'' +
                ", lab_descr='" + lab_descr + '\'' +
                '}';
    }
}
