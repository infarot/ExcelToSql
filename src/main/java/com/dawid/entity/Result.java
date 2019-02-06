package com.dawid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "seamstress_result")
public class Result {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "date")
    private LocalDate date;
    @Column (name = "percentage_result")
    private Double percentageResult;
    @Column(name = "seamstress_id")
    private Integer seamstressId;
    @Column(name = "shift")
    private Character shift;

    public Result() {
    }

    public Result(Double percentageResult, LocalDate date, Integer seamstressId, Character shift, String id ){
        this.percentageResult = percentageResult;
        this.date = date;
        this.seamstressId = seamstressId;
        this.shift = shift;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPercentageResult() {
        return percentageResult;
    }

    public void setPercentageResult(Double percentageResult) {
        this.percentageResult = percentageResult;
    }

    public Integer getSeamstressId() {
        return seamstressId;
    }

    public void setSeamstressId(Integer seamstressId) {
        this.seamstressId = seamstressId;
    }

    public Character getShift() {
        return shift;
    }

    public void setShift(Character shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", date=" + date +
                ", percentageResult=" + percentageResult +
                ", seamstressId=" + seamstressId +
                ", shift=" + shift +
                '}';
    }
}
