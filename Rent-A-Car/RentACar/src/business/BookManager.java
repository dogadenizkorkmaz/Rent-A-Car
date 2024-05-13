package business;

import core.Helper;
import dao.BookDao;
import entity.Book;
import entity.Car;
import entity.Model;

import java.util.ArrayList;

public class BookManager {

    private final BookDao bookDao;

    public BookManager(){
        this.bookDao= new BookDao();

    }

    public Book getById(int id){return this.bookDao.getById(id);}

    public ArrayList<Book> findAll(){return this.bookDao.findAll();}

    public ArrayList<Object[]> getForTable(int size ,ArrayList<Book>rentalList){
        ArrayList<Object[]> rentalObjList = new ArrayList<>();
        for(Book obj : rentalList){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getCar().getPlate();
            rowObject[i++] = obj.getCar().getModel().getBrand().getName();
            rowObject[i++] = obj.getCar().getModel().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getIdno();
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            rowObject[i++] = obj.getPrc();
            rentalObjList.add(rowObject);
        }
        return  rentalObjList;
    }

    public ArrayList<Book>  searcForTable(int carId){
        String select ="SELECT * FROM public.book ";
        ArrayList<String> whereList = new ArrayList<>();
        if (carId != 0){
            whereList.add("book_car_id = " +carId );

        }
        String whereStr = String.join(" AND ",whereList);
        String query=select;
        if(whereStr.length() > 0){
            query +=  " WHERE "+whereStr;

        }

        return this.bookDao.selectByQuery(query);

    }

    public boolean delete(int id ){

        if(this.getById(id)==null){
            Helper.showMsg(id+" ID kayıtlı model bulunamadı");
            return false;
        }
        return this.bookDao.delete(id);
    }

 public boolean save(Book book){
        return this.bookDao.save(book);
    }


}
