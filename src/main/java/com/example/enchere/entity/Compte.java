package com.example.enchere.entity;

import com.example.enchere.service.Connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Compte {
    private int id_compte = 0;
    private Membre membre = null;
    private double solde = 0.0;
    private LocalDate dates = null;
    private int etat = 0;

    public Compte(int id_compte, Membre membre, double solde, LocalDate dates, int etat) {
        this.id_compte = id_compte;
        this.membre = membre;
        this.solde = solde;
        this.dates = dates;
        this.etat = etat;
    }

    public void identificationSoldeActuel(String id_membre) throws Exception {
        Statement st = null;
        ResultSet res = null;
        Connection con = null;
        Compte compte = null;
        Membre membre = null;
        try {
            String requete = "select * from v_compte_final where id_auth=" + id_membre;
            con = Connexion.getConnection();
            st = con.createStatement();
            res = st.executeQuery(requete);
            while (res.next()) {
                this.solde = res.getDouble("solde_actu");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void veficatedContraint(double prix) throws Exception {
        this.identificationSoldeActuel(String.valueOf(Math.toIntExact(this.membre.getId())));
        if (this.solde < prix) {
            throw new Exception("solde Insuffisant");
        }
    }

    public void rechargerCompte() throws Exception {
        Statement st = null;
        ResultSet res = null;
        Connection con = null;
        Compte compte = null;
        Membre membre = null;
        try {
            String requete = "INSERT INTO Compte (id_membre,solde,dates,etat) VALUES (" +
                    this.getMembre().getId() + "," + this.solde + ",'"
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    + "',0)";
            System.out.print(requete);
            con = Connexion.getConnection();
            st = con.createStatement();
            st.executeUpdate(requete);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public int getId_compte() {
        return id_compte;
    }

    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public LocalDate getDates() {
        return dates;
    }

    public void setDates(LocalDate dates) {
        this.dates = dates;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Compte() {
    }

}
