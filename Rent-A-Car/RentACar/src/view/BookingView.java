package view;

import business.BookManager;
import core.Helper;
import entity.Book;
import entity.Car;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingView extends Layout {
    private JPanel container;
    private JLabel lbl_car_info;
    private JTextField fld_book_name;
    private JTextField fld_book_idno;
    private JTextField fld_book_mail;
    private JTextField fld_book_strt_date;
    private JTextField fld_book_fnsh_date;
    private JTextField fld_book_prc;
    private JTextArea txta_book_note;
    private JButton btn_book_save;
    private JTextField fld_book_mpno;


    private Car car ;
    private BookManager bookManager;


    public BookingView(Car selectedCar,String strt_date , String fnsh_date){
        this.bookManager =new BookManager();
        this.car=selectedCar;

        this.add(container);
        guiInitilaze(300,600);

        lbl_car_info.setText("Araç : "+
                this.car.getPlate()+" / "+
                this.car.getModel().getBrand().getName()+" / "+
                this.car.getModel().getName());


        this.fld_book_strt_date.setText(strt_date);
        this.fld_book_fnsh_date.setText(fnsh_date);

        this.fld_book_name.setText("Derya Deniz");
        this.fld_book_idno.setText("1234123411");
        this.fld_book_mail.setText("test@patika.dev");
        this.fld_book_mpno.setText("05501234567");
        this.fld_book_prc.setText("2350");
        this.txta_book_note.setText("Not bırakıldı");


        btn_book_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_book_name,
                    this.fld_book_mail,
                    this.fld_book_prc,
                    this.fld_book_mpno,
                    this.fld_book_idno,
                    this.fld_book_strt_date,
                    this.fld_book_fnsh_date
            };

            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                Book book = new Book();
                book.setbCase("done");
                book.setCar_id(this.car.getId());
                book.setName(this.fld_book_name.getText());
                book.setStrt_date(LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFnsh_date(LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdno(this.fld_book_idno.getText());
                book.setMpno(this.fld_book_mpno.getText());
                book.setMail(this.fld_book_mail.getText());
                book.setNote(this.txta_book_note.getText());
                book.setPrc(Integer.parseInt(this.fld_book_prc.getText()));

                if (this.bookManager.save(book)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }

            }
        });


    }


}
