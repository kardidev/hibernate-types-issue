package com.kardi.test.multidata.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "problem_type_entity")
public class ProblemTypeEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column
    // Lines below cannot solve the issue, when we want to create a new
    // database from entities and use it with different database types
    //@Type(type="org.hibernate.type.BinaryType")
    //@Type(type="org.hibernate.type.MaterializedBlobType")
    //@Lob
    private byte[] binary_field;
    @Column(length = 10)
    private byte[] limited_binary_1;
    @Column(length = 270)
    private byte[] limited_binary_2;
    @Column(length = 100001)
    private byte[] limited_binary_3;

    @Column
    //@Lob // if we add @Lob, postgresql creates text field, but cannot work with it without LOB streams options
    private String text_field;
    @Column(length = 10)
    private String limited_string_1;
    @Column(length = 270)
    private String limited_string_2;
    @Column(length = 100001)
    private String limited_string_3;

    // date operations
    @Column(name = "control_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date controlDate;
    @Column
    private int months;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBinary_field() {
        return binary_field;
    }

    public void setBinary_field(byte[] binary_field) {
        this.binary_field = binary_field;
    }

    public String getText_field() {
        return text_field;
    }

    public void setText_field(String text_field) {
        this.text_field = text_field;
    }

    public byte[] getLimited_binary_1() {
        return limited_binary_1;
    }

    public void setLimited_binary_1(byte[] limited_binary_1) {
        this.limited_binary_1 = limited_binary_1;
    }

    public byte[] getLimited_binary_2() {
        return limited_binary_2;
    }

    public void setLimited_binary_2(byte[] limited_binary_2) {
        this.limited_binary_2 = limited_binary_2;
    }

    public byte[] getLimited_binary_3() {
        return limited_binary_3;
    }

    public void setLimited_binary_3(byte[] limited_binary_3) {
        this.limited_binary_3 = limited_binary_3;
    }

    public String getLimited_string_1() {
        return limited_string_1;
    }

    public void setLimited_string_1(String limited_string_1) {
        this.limited_string_1 = limited_string_1;
    }

    public String getLimited_string_2() {
        return limited_string_2;
    }

    public void setLimited_string_2(String limited_string_2) {
        this.limited_string_2 = limited_string_2;
    }

    public String getLimited_string_3() {
        return limited_string_3;
    }

    public void setLimited_string_3(String limited_string_3) {
        this.limited_string_3 = limited_string_3;
    }

    public Date getControlDate() {
        return controlDate;
    }

    public void setControlDate(Date controlDate) {
        this.controlDate = controlDate;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
