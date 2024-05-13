package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminView extends  Layout{
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scl_model;
    private JTable tbl_model;
    private JTable tbl_car;

    private JComboBox<ComboItem> cmb_s_model_brand;

    private JComboBox <ComboItem> cmb_plate_filter;

    private JComboBox<Model.Type> cmb_s_model_type;
    private JComboBox <Model.Fuel>cmb_s_model_fuel;
    private JComboBox <Model.Gear>cmb_s_model_gear;


    private JButton btn_search_model;
    private JButton btn_cncl_model;
    private JPanel pnl_car;
    private JPanel pnl_booking_search;
    private JTable tbl_booking;
    private JComboBox<Model.Gear> cmb_booking_gear;
    private JComboBox<Model.Fuel> cmb_booking_fuel;
    private JComboBox<Model.Type> cmb_booking_type;
    private JFormattedTextField fld_start_date;
    private JFormattedTextField fld_fnsh_date;
    private JButton btn_booking_search;
    private JButton btn_cncl_booking;
    private JPanel pnl_booking_list;
    private JTable tbl_rentals;
    private JButton btn_rental_search;
    private JButton btn_cncl_rental;

    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private DefaultTableModel tmdl_rentals = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private BookManager bookManager;

    private JPopupMenu brand_Menu;
    private JPopupMenu model_Menu;
    private JPopupMenu car_Menu;
    private JPopupMenu booking_Menu;
    private JPopupMenu rental_Menu;

    private Object[] col_model;
    private Object[] col_car;

    private Object[] col_rental;


    public AdminView(User user) {
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.carManager= new CarManager();
        this.bookManager= new BookManager();

        add(container);
        this.guiInitilaze(1000,500);
        this.user = user;
        if(this.user==null){
            dispose();

        }
        this.lbl_welcome.setText("Hoşgeldin : "+ this.user.getRole());

        loadComponent();

        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        loadCarTable();
        loadCarComponent();

        loadBookingTable(null);
        loadBookingCompanent();
        loadBookingFilter();


        loadRentalsTable(null);
        loadRentalFilterPlate();
        loadRentalComponent();

    }

    private void loadComponent(){
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView=new LoginView();
            }
        });
    }
    public void loadBookingCompanent() {
        tableRowSelect(this.tbl_booking);
        this.booking_Menu = new JPopupMenu();
        this.booking_Menu.add("Rezervasyon Yap").addActionListener(e ->{
            int selectCarId = this.getTableSelectedRow(this.tbl_booking,0);

            BookingView bookingView = new BookingView(
              this.carManager.getById(selectCarId),
                    this.fld_start_date.getText(),
                    this.fld_fnsh_date.getText()
            );
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                    loadRentalsTable(null);

                }
            });
        });


        this.tbl_booking.setComponentPopupMenu(booking_Menu);
        btn_booking_search.addActionListener(e -> {
            ArrayList<Car> carList = this.carManager.searchForBooking(
                    fld_start_date.getText(),
                    fld_fnsh_date.getText(),
                    (Model.Type) cmb_booking_type.getSelectedItem(),
                    (Model.Gear) cmb_booking_gear.getSelectedItem(),
                    (Model.Fuel) cmb_booking_fuel.getSelectedItem()


            );
            ArrayList<Object[]> carBookingRow = this.carManager.getForTable(this.col_car.length,carList);
            loadBookingTable(carBookingRow);

        });
        btn_cncl_booking.addActionListener(e -> {
            loadBookingFilter();


        });

    }

//    public void loadRentalsTable() {
//        this.col_car = new Object[]{"ID", "Marka", "Model", "Plaka", "Renk", "Km", "Yıl", "Tip", "Yakit Türü", "Vites"};
//        ArrayList<Object[]> carList = this.carManager.getForTable(this.col_car.length,this.carManager.rentalList());
//        createTable(this.tmdl_rentals, this.tbl_rentals, col_car, carList);
//    }
private void loadRentalComponent() {
    this.tableRowSelect(this.tbl_rentals);

    this.rental_Menu = new JPopupMenu();
    this.rental_Menu.add("İptal").addActionListener(e -> {
        if(Helper.confirm("sure")){
            int selectModelId   = this.getTableSelectedRow(tbl_rentals,0);
            if(this.bookManager.delete(selectModelId)){
                Helper.showMsg("done");
                loadRentalsTable(null);
                loadModelTable(null);
                loadCarTable();

            }else {
                Helper.showMsg("error");
            }
        }
    });
    this.tbl_rentals.setComponentPopupMenu(this.rental_Menu);

    this.btn_rental_search.addActionListener(e -> {
        if(this.cmb_plate_filter.getSelectedItem() != null){
        ComboItem selectedBrand = (ComboItem) this.cmb_plate_filter.getSelectedItem();
        int plateId = 0;
        if (selectedBrand != null) {
            plateId = selectedBrand.getKey();
        }
        ArrayList<Book> rentalListBySearch = this.bookManager.searcForTable(plateId);
        ArrayList<Object[]> modelRowListBySearch = this.bookManager.getForTable(this.col_rental.length, rentalListBySearch);
        loadRentalsTable(modelRowListBySearch);
    }});
    this.btn_cncl_rental.addActionListener(e -> {
        this.cmb_plate_filter.setSelectedItem(null);
        loadRentalsTable(null);
    });
}
    public void loadRentalsTable(ArrayList<Object[]> rentalList) {
        this.col_rental = new Object[]{"ID", "Plaka", "Marka", "Model", "Müşteri Ad Soyad",  "Telefon","Mail","T.C Kimlik No", "Başlama Tarihi", "Bitiş Tarihi", "Kiralama Bedeli"};
        if(rentalList ==null) {
            rentalList = this.bookManager.getForTable(this.col_rental.length, this.bookManager.findAll());
        }
        createTable(this.tmdl_rentals, this.tbl_rentals, col_rental, rentalList);
    }
    public void loadRentalFilterPlate() {
        this.cmb_plate_filter.removeAllItems();
        for (Book obj : bookManager.findAll()) {
            this.cmb_plate_filter.addItem(new ComboItem(obj.getCar_id(),obj.getCar().getPlate()));
        }
        this.cmb_plate_filter.setSelectedItem(null);
    }

    public void loadBookingTable(ArrayList<Object[]> carlist) {
        Object[] col_booking_list = {"ID","Marka", "Model","Plaka","Renk","Km","Yıl","Tip","Yakit Türü","Vites"};
        createTable(this.tmdl_booking,this.tbl_booking,col_booking_list,carlist);

    }

    public void loadBookingFilter(){
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);

    }

    private void loadCarComponent() {
        this.tableRowSelect(this.tbl_car);

        this.car_Menu = new JPopupMenu();
        this.car_Menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_Menu.add("Güncelle").addActionListener(e -> {
            int selectCarlId   = this.getTableSelectedRow(tbl_car,0);
            CarView carView = new CarView(this.carManager.getById(selectCarlId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                    loadRentalsTable(null);
                }
            });
        });
        this.car_Menu.add("Sil").addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selectCarId   = this.getTableSelectedRow(tbl_car,0);
                if(this.carManager.delete(selectCarId)){
                    Helper.showMsg("done");

                    loadCarTable();
                }else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_car.setComponentPopupMenu(this.car_Menu);

//        this.btn_search_model.addActionListener(e -> {
//            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
//            int brandId = 0;
//            if (selectedBrand != null) {
//                brandId = selectedBrand.getKey();
//            }
//            ArrayList<Model> modelListBySearch = this.modelManager.searcForTable(
//                    brandId,
//                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
//                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
//                    (Model.Type) cmb_s_model_type.getSelectedItem()
//            );
//
//            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
//            loadModelTable(modelRowListBySearch);
//        });

    }
    private void loadModelComponent() {
        this.tableRowSelect(this.tbl_model);

        this.model_Menu = new JPopupMenu();
        this.model_Menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRentalsTable(null);
                    loadModelTable(null);
                    loadCarTable();
                }
            });
        });
        this.model_Menu.add("Güncelle").addActionListener(e -> {
            int selectModelId   = this.getTableSelectedRow(tbl_model,0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                    loadCarTable();
                    loadRentalsTable(null);
                }
            });
        });
        this.model_Menu.add("Sil").addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selectModelId   = this.getTableSelectedRow(tbl_model,0);
                if(this.modelManager.delete(selectModelId)){
                    Helper.showMsg("done");

                    loadModelTable(null);
                    loadCarTable();
                }else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(this.model_Menu);

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searcForTable(
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem()
            );

            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });
        this.btn_cncl_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    public void loadCarTable() {
        this.col_car = new Object[]{"ID","Marka", "Model","Plaka","Renk","Km","Yıl","Tip","Yakit Türü","Vites"};
        ArrayList<Object[]> carList =this.carManager.getForTable(col_car.length,this.carManager.findAll());
        createTable(this.tmdl_car,this.tbl_car,col_car,carList);

    }
    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID","Marka", "Model Adı","Tip","Yıl","Yakıt Türü ","Vites"};
        if(modelList ==null){
            modelList = this.modelManager.getForTable(this.col_model.length,this.modelManager.findAll());
        }

        this.createTable(this.tmdl_model, this.tbl_model, this.col_model, modelList);
    }
    public void loadModelFilter() {
        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }
    public void loadBookFilterPlate() {
        this.cmb_plate_filter.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_plate_filter.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_plate_filter.setSelectedItem(null);
    }
    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }
    public void loadBrandComponent(){

        this.tableRowSelect(this.tbl_brand);
        this.brand_Menu = new JPopupMenu();
        this.brand_Menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                }
            });
        });
        this.brand_Menu.add("Güncelle").addActionListener(e -> {
            int selectBrandId   = this.getTableSelectedRow(tbl_brand,0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                }
            });
        });
        this.brand_Menu.add("Sil").addActionListener(e -> {
            if(Helper.confirm("sure")){
            int selectBrandId   = this.getTableSelectedRow(tbl_brand,0);
                if(this.brandManager.delete(selectBrandId)){
                    Helper.showMsg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                }else {
                    Helper.showMsg("error");
                }
            }

        });

        this.tbl_brand.setComponentPopupMenu(this.brand_Menu);
    }

    private void createUIComponents() throws ParseException {
       this.fld_start_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_start_date.setText("10/10/2023");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("16/10/2023");


    }
}
