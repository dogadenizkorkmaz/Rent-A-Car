package dao;

import core.Db;
import entity.Brand;

import java.sql.*;
import java.util.ArrayList;

public class BrandDao {
private final Connection con ;

public BrandDao(){
    this.con = Db.getInstance();
}
    public Brand getById(int id){
        Brand obj=null;
        String query ="SELECT * FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs =  pr.executeQuery();
            if(rs.next()){
                obj = this.match(rs);


            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;

    }
    public ArrayList<Brand> findAll(){
        ArrayList<Brand> brandsList = new ArrayList<>();
        String sql ="SELECT * FROM public.brand ORDER BY brand_id ASC";
        try {
            //ResultSet rs =this.con.createStatement().executeQuery(sql);
            Statement st =this.con.createStatement();
            ResultSet rs =st.executeQuery(sql);

            while(rs.next()){
                brandsList.add(this.match(rs));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return brandsList;
    }

    public Brand  match(ResultSet rs) throws SQLException {
     Brand obj   = new Brand();
        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));
        return obj;
    }

    public boolean save(Brand brand){
    String query="INSERT INTO public.brand(brand_name) VALUES (?)";
    try{
        PreparedStatement pr = this.con.prepareStatement(query); {
            pr.setString(1,brand.getName());
            return pr.executeUpdate() != -1;
        }
    }catch (SQLException e){
        e.printStackTrace();
        return true;
    }

    }
    public boolean update(Brand brand){
        String query="UPDATE public.brand SET brand_name = ? WHERE brand_id = ?";
        try{
            PreparedStatement pr = this.con.prepareStatement(query); {
                pr.setString(1,brand.getName());
                pr.setInt(2,brand.getId());
                return pr.executeUpdate() != -1;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }

    }
public boolean delete(int id){
    String query="DELETE FROM public.brand WHERE brand_id =?";
    try {
        PreparedStatement pr = con.prepareStatement(query);
        pr.setInt(1,id);
        return pr.executeUpdate() != -1 ;

    }catch (SQLException e){
        e.printStackTrace();
    }
    return true;
}


}
