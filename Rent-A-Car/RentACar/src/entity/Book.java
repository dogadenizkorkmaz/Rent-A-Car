package entity;

import java.time.LocalDate;

public class Book {
    private int id;
    private int car_id;
    private Car car;
    private String name;
    private String idno;
    private String mpno;
    private String mail;
    private LocalDate strt_date;
    private LocalDate fnsh_date;
    private String bCase;
    private String note;
    private int prc;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMpno() {
        return mpno;
    }

    public void setMpno(String mpno) {
        this.mpno = mpno;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getStrt_date() {
        return strt_date;
    }

    public void setStrt_date(LocalDate strt_date) {
        this.strt_date = strt_date;
    }

    public LocalDate getFnsh_date() {
        return fnsh_date;
    }

    public void setFnsh_date(LocalDate fnsh_date) {
        this.fnsh_date = fnsh_date;
    }

    public String getbCase() {
        return bCase;
    }

    public void setbCase(String bCase) {
        this.bCase = bCase;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrc() {
        return prc;
    }

    public void setPrc(int prc) {
        this.prc = prc;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", car_id=" + car_id +
                ", car=" + car +
                ", name='" + name + '\'' +
                ", idno='" + idno + '\'' +
                ", mpno='" + mpno + '\'' +
                ", mail='" + mail + '\'' +
                ", strt_date=" + strt_date +
                ", fnsh_date=" + fnsh_date +
                ", bCase='" + bCase + '\'' +
                ", note='" + note + '\'' +
                ", prc=" + prc +
                '}';
    }
}
