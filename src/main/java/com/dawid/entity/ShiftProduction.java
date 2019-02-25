package com.dawid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="shift_production")
public class ShiftProduction {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "shift")
    private Character shift;
    @Column(name = "per_seamstress")
    private Double perSeamstress;
    @Column(name = "per_seamstress_qc")
    private Double perSeamstressQc;
    @Column(name = "per_employee")
    private Double perEmployee;
    @Column (name = "result")
    private Double result;
    @Column (name = "potential_utilization")
    private Double potentialUtilization;
    @Column(name = "work_organization")
    private Double workOrganization;

    public ShiftProduction(String id, LocalDate date, Character shift, Double perSeamstress, Double perSeamstressQc, Double perEmployee, Double result, Double potentialUtilization, Double workOrganization) {
        this.id = id;
        this.date = date;
        this.shift = shift;
        this.perSeamstress = perSeamstress;
        this.perSeamstressQc = perSeamstressQc;
        this.perEmployee = perEmployee;
        this.result = result;
        this.potentialUtilization = potentialUtilization;
        this.workOrganization = workOrganization;
    }

    public ShiftProduction() {
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

    public Character getShift() {
        return shift;
    }

    public void setShift(Character shift) {
        this.shift = shift;
    }

    public Double getPerSeamstress() {
        return perSeamstress;
    }

    public void setPerSeamstress(Double perSeamstress) {
        this.perSeamstress = perSeamstress;
    }

    public Double getPerSeamstressQc() {
        return perSeamstressQc;
    }

    public void setPerSeamstressQc(Double perSeamstressQc) {
        this.perSeamstressQc = perSeamstressQc;
    }

    public Double getPerEmployee() {
        return perEmployee;
    }

    public void setPerEmployee(Double perEmployee) {
        this.perEmployee = perEmployee;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getPotentialUtilization() {
        return potentialUtilization;
    }

    public void setPotentialUtilization(Double potentialUtilization) {
        this.potentialUtilization = potentialUtilization;
    }

    public Double getWorkOrganization() {
        return workOrganization;
    }

    public void setWorkOrganization(Double workOrganization) {
        this.workOrganization = workOrganization;
    }

    @Override
    public String toString() {
        return "ShiftProduction{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", shift=" + shift +
                ", perSeamstress=" + perSeamstress +
                ", perSeamstressQc=" + perSeamstressQc +
                ", perEmployee=" + perEmployee +
                ", result=" + result +
                ", potentialUtilization=" + potentialUtilization +
                ", workOrganization=" + workOrganization +
                '}';
    }
}
