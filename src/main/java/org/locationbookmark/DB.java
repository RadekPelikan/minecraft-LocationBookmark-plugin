package org.locationbookmark;

import java.sql.*;
import java.util.ArrayList;

public class DB {

    // connect to database
    public static void connect() {
        String url = "jdbc:sqlite:locationbookmark.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());

                ResultSet rs = meta.getTables(null, null, "locationbookmark", null);
                if (!rs.next()) {
                    String sql = "CREATE TABLE locationbookmark (\n"
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                            + "x integer NOT NULL,\n"
                            + "y integer NOT NULL,\n"
                            + "z integer NOT NULL,\n"
                            + "locationName text NOT NULL,\n"
                            + "player text NOT NULL \n"
                            + ");";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);
                    System.out.println("Created table");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // add location
    public static Boolean addLocation (String playerName, double x, double y, double z, String locationName){
        String url = "jdbc:sqlite:locationbookmark.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String sql = "INSERT INTO locationbookmark (x, y, z, player, locationName) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, x);
                pstmt.setDouble(2, y);
                pstmt.setDouble(3, z);
                pstmt.setString(4, playerName);
                pstmt.setString(5, locationName );
                pstmt.executeUpdate();
                conn.close();
                System.out.println("Added location");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // get location
    public static Location getLocation (String playerName, String locationName){
        String url = "jdbc:sqlite:locationbookmark.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String sql = "SELECT x, y,z, player, locationName FROM locationbookmark WHERE player = ? AND locationName = ?;";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, playerName);
                pstmt.setString(2, locationName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    String player = rs.getString("player");
                    String location = rs.getString("locationName");
                    conn.close();
                    return new Location(x, y, z, player, location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // delete location
    public static boolean deleteLocation (String playerName, String locationName){
        String url = "jdbc:sqlite:locationbookmark.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String sql = "DELETE FROM locationbookmark WHERE player = ? AND locationName = ?;";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, playerName);
                pstmt.setString(2, locationName);
                pstmt.executeUpdate();
                conn.close();
                System.out.println("Deleted location");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // get all locations
    public static ArrayList<Location> getAllLocations(String playerName){
        String url = "jdbc:sqlite:locationbookmark.db";
        ArrayList<Location> locations = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String sql = "SELECT x, y, z, player, locationName FROM locationbookmark WHERE player = ?;";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, playerName);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    String player = rs.getString("player");
                    String locationName = rs.getString("locationName");
                    locations.add(new Location(x, y, z, player, locationName));
                }
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

}
