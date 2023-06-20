package service;

import entite.Materiel;

import entite.UserMateriel;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MaterielService implements Iservice<Materiel>{
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public MaterielService() {
        connexion = DataSource.getInstance().getCnx();

    }

    @Override
    public void insert(Materiel materiel) {
        String query = "INSERT INTO materiel ( statut, name) VALUES (?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {

            pst.setBoolean(1, materiel.isStatut());
            pst.setString(2, materiel.getName());


            pst.executeUpdate();
            System.out.println("Materiel inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Materiel> readAll() {
        String requete="select * from materiel";
        List<Materiel> list=new ArrayList<>();
        try {
            ste=connexion.createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                Materiel materiel=new Materiel(
                        rs.getInt("idMateriel"),
                        rs.getString("image"),
                        rs.getString("name")
                );
                list.add(materiel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Materiel readById(int id) {
        String query = "SELECT * FROM materiel WHERE idMateriel = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                int idMateriel = rs.getInt("idMateriel");
                String name = rs.getString("name");
                String image = rs.getString("image");

                return new Materiel(idMateriel,name,image);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM materiel WHERE idMateriel = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("Materiel deleted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Materiel materiel) {
        String query = "UPDATE materiel SET name = ? WHERE id = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, materiel.getName());


            pst.executeUpdate();
            System.out.println("Material updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void louerMateriel(Materiel materiel, UserMateriel userMateriel){
        String query = "INSERT INTO userMateriel ( idMateriel, dateDebut,dateFin) VALUES (?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {

            pst.setInt(1, materiel.getIdMateriel());
            pst.setDate(2, userMateriel.getDateDebut());
            pst.setDate(3, userMateriel.getDateFin());


            pst.executeUpdate();
            System.out.println("demande inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    @Override
public void update(Reclamation rec) {
    String query = "UPDATE reclamation SET status = ? WHERE id = ?";

    try (
        Connection connection = DriverManager.getConnection("your_connection_string")) {
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, materiel.getName());
        pst.setInt(2, materiel.getId());

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Material updated successfully.");
        } else {
            System.out.println("No material found with the given ID.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

     */
}
