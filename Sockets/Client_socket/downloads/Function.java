package database;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import objet.*;

public class Function {
    public ArrayList<Feu> getAllFeu(){
        return null;
    }
    public static ArrayList<Placement> getPlacementMemeSecteur(String secteur)throws SQLException{
        ArrayList<Placement> value = new ArrayList<>();
        Connection connection =null;
        PreparedStatement updateStmt=null ;
        Secteur s = Secteur.getSecteurByName(secteur);
        try {
            connection = Connexion.getConnection();
            String requette = "SELECT * FROM PLACEMENT WHERE secteur = ?" ;
            updateStmt = connection.prepareStatement(requette);
            updateStmt.setInt(1, s.getIdSecteur());
            ResultSet res = updateStmt.executeQuery();
            while (res.next()) {
                int idPlacement = res.getInt("idPlacement");
                int feu = res.getInt("feu");
                int idSecteur = res.getInt("secteur");
                String direction = res.getString("direction");

            
                Placement placement = new Placement(idPlacement, feu, idSecteur, direction);
                value.add(placement); 
            }


        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de JDBC : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
        finally{
            if (connection!=null) {
                connection.close();
            }
            if (updateStmt!=null) {
                updateStmt.close();
            }
        }
        return value;
    }
    public static ArrayList<Placement> getPlacementMSMDir(String secteur,String direction)throws SQLException{
        ArrayList<Placement> value = new ArrayList<>();
        Connection connection =null;
        PreparedStatement updateStmt=null ;
        Secteur s = Secteur.getSecteurByName(secteur);
        try {
            connection = Connexion.getConnection();
            String requette = "SELECT * FROM PLACEMENT WHERE secteur = ? AND direction = ?" ;
            updateStmt = connection.prepareStatement(requette);
            updateStmt.setInt(1, s.getIdSecteur());
            updateStmt.setString(2, direction);
            ResultSet res = updateStmt.executeQuery();
            while (res.next()) {
                int idPlacement = res.getInt("idPlacement");
                int feu = res.getInt("feu");
                int idSecteur = res.getInt("secteur");
            
                Placement placement = new Placement(idPlacement, feu, idSecteur, direction);
                value.add(placement); 
            }


        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de JDBC : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
        finally{
            if (connection!=null) {
                connection.close();
            }
            if (updateStmt!=null) {
                updateStmt.close();
            }
        }
        return value;
    }
    public static ArrayList<Placement> getPlacementMSDiffDir(String secteur,String direction)throws SQLException{
        ArrayList<Placement> value = new ArrayList<>();
        Connection connection =null;
        PreparedStatement updateStmt=null ;
        Secteur s = Secteur.getSecteurByName(secteur);
        try {
            connection = Connexion.getConnection();
            String requette = "SELECT * FROM PLACEMENT WHERE secteur = ? AND direction != ?" ;
            updateStmt = connection.prepareStatement(requette);
            updateStmt.setInt(1, s.getIdSecteur());
            updateStmt.setString(2, direction);
            ResultSet res = updateStmt.executeQuery();
            while (res.next()) {
                int idPlacement = res.getInt("idPlacement");
                int feu = res.getInt("feu");
                int idSecteur = res.getInt("secteur");
                String diffDirection = res.getString("direction");
            
                Placement placement = new Placement(idPlacement, feu, idSecteur, diffDirection);
                value.add(placement); 
            }


        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de JDBC : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
        finally{
            if (connection!=null) {
                connection.close();
            }
            if (updateStmt!=null) {
                updateStmt.close();
            }
        }
        return value;
    }
    public static void updateStatusFeu(int idFeu,int status)throws SQLException{
        Connection connection =null;
        PreparedStatement updateStmt=null ;
        try {
            connection = Connexion.getConnection();
            String requette = "UPDATE FEU SET status = ? WHERE idFeu = ?" ;
            updateStmt = connection.prepareStatement(requette);
            updateStmt.setInt(1, status);
            updateStmt.setInt(2, idFeu);

           updateStmt.executeUpdate();
            
            
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de JDBC : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
        finally{
            if (connection!=null) {
                connection.close();
            }
            if (updateStmt!=null) {
                updateStmt.close();
            }
        }
    }

    public static void changeStatus(String secteur,String direction) throws SQLException{
        ArrayList<Placement> list = Function.getPlacementMSMDir(secteur, direction);
        for (Placement placement : list) {
            updateStatusFeu(placement.getFeu(), 1);
        }
        ArrayList<Placement> list2 = Function.getPlacementMSDiffDir(secteur, direction);
        for (Placement placement : list2) {
            updateStatusFeu(placement.getFeu(), 0);
        }
    }
    public static ArrayList<Placement> getPlacementMSMStat(String secteur,int status)throws SQLException{
        ArrayList<Placement> value = new ArrayList<>();
        Connection connection =null;
        PreparedStatement updateStmt=null ;
        Secteur s = Secteur.getSecteurByName(secteur);
        ArrayList<Feu> listFeu= Feu.getFeuByStatus(status) ;

        try {
            connection = Connexion.getConnection();

            String requette = "SELECT * FROM PLACEMENT WHERE secteur = ?" ;
            updateStmt = connection.prepareStatement(requette);
            updateStmt.setInt(1, s.getIdSecteur());
            ResultSet res = updateStmt.executeQuery();
            while (res.next()) {
                
                int idPlacement = res.getInt("idPlacement");
                int feu = res.getInt("feu");
                int idSecteur = res.getInt("secteur");
                String direction = res.getString("direction");
                for (Feu feu2 : listFeu) {
                    if (feu2.getIdFeu() == feu) {
                        Placement placement = new Placement(idPlacement, feu, idSecteur, direction);
                        value.add(placement); 
                    }
                }
            }


        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de JDBC : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base : " + e.getMessage());
        }
        finally{
            if (connection!=null) {
                connection.close();
            }
            if (updateStmt!=null) {
                updateStmt.close();
            }
        }
        return value;
    }
    public static void switchStatus(String secteur){
        try {
            ArrayList<Placement> placements = getPlacementMSMStat(secteur, 0);
            changeStatus(secteur, placements.get(0).getDirection());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    // public static ArrayList<Feu> getMemeDirection(int direction,int secteur)throws SQLException{
    //     ArrayList<Feu> value = new ArrayList<>();
    //     Connection connection =null;
    //     PreparedStatement updateStmt=null ;
    //     try {
    //         connection = Connexion.getConnection();
    //         String requette = "SELECT * FROM FEU WHERE direction = ? AND secteur = ?" ;
    //         updateStmt = connection.prepareStatement(requette);
    //         updateStmt.setInt(1, direction);
    //         updateStmt.setInt(2, secteur);
    //         ResultSet res = updateStmt.executeQuery();
    //         while (res.next()) {
    //             int idFeu = res.getInt("idFeu");
    //             int status = res.getInt("status");
                

    //             Feu feu = new Feu(idFeu, secteur, direction, status);
    //             value.add(feu); 
    //         }
            
            
    //     } catch (ClassNotFoundException e) {
    //         System.out.println("Erreur de JDBC : " + e.getMessage());
    //         e.printStackTrace();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de connexion à la base : " + e.getMessage());
    //     }
    //     finally{
    //         if (connection!=null) {
    //             connection.close();
    //         }
    //         if (updateStmt!=null) {
    //             updateStmt.close();
    //         }
    //     }
    //     return value;
    // }
    // public static ArrayList<Feu> getDifDirection(int direction,int secteur)throws SQLException{
    //     ArrayList<Feu> value = new ArrayList<>();
    //     Connection connection =null;
    //     PreparedStatement updateStmt=null ;
    //     try {
    //         connection = Connexion.getConnection();
    //         String requette = "SELECT * FROM FEU WHERE direction != ? AND secteur = ?" ;
    //         updateStmt = connection.prepareStatement(requette);
    //         updateStmt.setInt(1, direction);
    //         updateStmt.setInt(2, secteur);

    //         ResultSet res = updateStmt.executeQuery();
    //         while (res.next()) {
    //             int idFeu = res.getInt("idFeu");
    //             int status = res.getInt("status");
    //             int directionDiff = res.getInt("direction");

    //             Feu feu = new Feu(idFeu, secteur, directionDiff, status);
    //             value.add(feu); 
    //         }
            
            
    //     } catch (ClassNotFoundException e) {
    //         System.out.println("Erreur de JDBC : " + e.getMessage());
    //         e.printStackTrace();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de connexion à la base : " + e.getMessage());
    //     }
    //     finally{
    //         if (connection!=null) {
    //             connection.close();
    //         }
    //         if (updateStmt!=null) {
    //             updateStmt.close();
    //         }
    //     }
    //     return value;
    // }
    // public static void updateStatusFeu(int idFeu,int status)throws SQLException{
    //     Connection connection =null;
    //     PreparedStatement updateStmt=null ;
    //     try {
    //         connection = Connexion.getConnection();
    //         String requette = "UPDATE FEU SET status = ? WHERE idFeu = ?" ;
    //         updateStmt = connection.prepareStatement(requette);
    //         updateStmt.setInt(1, status);
    //         updateStmt.setInt(2, idFeu);

    //        updateStmt.executeUpdate();
            
            
    //     } catch (ClassNotFoundException e) {
    //         System.out.println("Erreur de JDBC : " + e.getMessage());
    //         e.printStackTrace();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de connexion à la base : " + e.getMessage());
    //     }
    //     finally{
    //         if (connection!=null) {
    //             connection.close();
    //         }
    //         if (updateStmt!=null) {
    //             updateStmt.close();
    //         }
    //     }
    // }
    // public static void changeStatus(int direction,int secteur) throws SQLException{
    //     ArrayList<Feu> list = Function.getMemeDirection(direction,secteur);
    //     int newStatus = 1 - list.get(0).getStatus();
    //     for(Feu feu : list){
    //         Function.updateStatusFeu(feu.getIdFeu(), Math.abs(newStatus));

    //     }
    //     ArrayList<Feu> list2 = Function.getDifDirection(direction,secteur);
    //     int newStatus2 = 1 - list2.get(0).getStatus();
    //     for(Feu feu : list2){
    //         Function.updateStatusFeu(feu.getIdFeu(),Math.abs(newStatus2));

    //     }
    // }

    public static void main(String[] args)throws SQLException { 
        String sec1 = "S2"; 
        String dir1 = "D1";
        

        ArrayList<Placement> placements = getPlacementMSDiffDir(sec1,dir1);
    
        
        if (placements.isEmpty()) {
            System.out.println("Aucun placement trouve pour  le secteur : " +sec1);
        } else {
            System.out.println("Placements trouves pour la reference : " + sec1);
            for (Placement placement : placements) {
                System.out.println(placement); 
            }
        }
    }


}
