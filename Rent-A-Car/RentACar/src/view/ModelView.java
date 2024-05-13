package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brand;
import entity.Model;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class ModelView extends Layout {
    private JPanel container;
    private JLabel lbl_heading;
    private JComboBox<ComboItem> cmb_brand;
    private JTextField fld_model_name;
    private JTextField fld_model_year;
    private JComboBox<Model.Type> cmb_model_type;
    private JComboBox<Model.Fuel> cmb_model_fuel;
    private JComboBox<Model.Gear> cmb_model_gear;
    private JButton btn_model_save;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;



    public ModelView(Model model) {
        this.model= model ;
        this.modelManager= new ModelManager();
        this.brandManager= new BrandManager();
        this.add(container);
        guiInitilaze(300,500);
        //this.cmb_brand.setModel(new DefaultComboBoxModel());

        for(Brand brand : this.brandManager.findAll()){
            this.cmb_brand.addItem(new ComboItem(brand.getId(),brand.getName()));

        }

        this.cmb_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));

        if(this.model.getId() != 0){
            this.fld_model_year.setText(this.model.getYear());
            this.fld_model_name.setText(this.model.getName());
            this.cmb_model_fuel.getModel().setSelectedItem(this.model.getFuel());
            this.cmb_model_type.getModel().setSelectedItem(this.model.getType());
            this.cmb_model_gear.getModel().setSelectedItem(this.model.getGear());
            ComboItem defaultBrand = new ComboItem(this.model.getBrand().getId(),this.model.getBrand().getName());
            this.cmb_brand.getModel().setSelectedItem(defaultBrand);
        }

        this.btn_model_save.addActionListener(e -> {
            if(Helper.isFieldListEmpty(new JTextField[]{this.fld_model_name,this.fld_model_year,})){
                Helper.showMsg("fill");
            }else{
                boolean result = false;
                ComboItem selectedBrand = (ComboItem) cmb_brand.getSelectedItem();
                this.model.setBrand_id(selectedBrand.getKey());
                this.model.setName(fld_model_name.getText());
                this.model.setYear(fld_model_year.getText());
                this.model.setType((Model.Type) cmb_model_type.getSelectedItem());
                this.model.setFuel((Model.Fuel) cmb_model_fuel.getSelectedItem());
                this.model.setGear((Model.Gear) cmb_model_gear.getSelectedItem());
                if(this.model.getId()!=0){
                    result = this.modelManager.update(this.model);

                }else{
                    result=this.modelManager.save(this.model);

                }
                if (result){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }



        });
    }
}
