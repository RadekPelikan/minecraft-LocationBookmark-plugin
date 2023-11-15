package org.locationbookmark;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DB {

    private static final String url = "jdbc:sqlite:locationbookmark.db";

    // connect to database
    public static void initialize() {
        try (Connection connection = DriverManager.getConnection(url)) {
            throwIfNull(connection);
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());

            updateOldTable(meta, connection);
            ResultSet rs = meta.getTables(null, null, "LocationBookmark", null);
            if (!rs.next()) {
                String sql = """
                        CREATE TABLE LocationBookmark (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            playerName          VARCHAR(32)         NOT NULL,
                            name                VARCHAR(32)         NOT NULL,
                            description         VARCHAR(128)        NULL,
                            x                   INTEGER             NULL,
                            y                   INTEGER             NULL,
                            z                   INTEGER             NULL,
                            parentLocationId    INTEGER             NULL,
                            FOREIGN KEY(parentBookmarkId) REFERENCES LocationBookmark(id)
                            );""";
                Statement stmt = connection.createStatement();
                stmt.execute(sql);
                System.out.println("Created table");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean addLocation(Location location) {
        try (Connection connection = DriverManager.getConnection(url)) {
            throwIfNull(connection);

            String sql = "INSERT INTO LocationBookmark (name, playerName, x, y, z) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getPlayer());
            pstmt.setDouble(3, location.getX());
            pstmt.setDouble(4, location.getY());
            pstmt.setDouble(5, location.getZ());
            pstmt.executeUpdate();
            System.out.println("Added location");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean addLocation(Location location, int parentLocationId) {
        if (parentLocationId < 1)
            throw new InvalidParameterException("Invalid value for parentLocationId");
        try (Connection connection = DriverManager.getConnection(url)) {
            throwIfNull(connection);

            String sql = "INSERT INTO LocationBookmark (name, playerName, x, y, z, parentBookmarkId) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getPlayer());
            pstmt.setDouble(3, location.getX());
            pstmt.setDouble(4, location.getY());
            pstmt.setDouble(5, location.getZ());
            pstmt.setInt(6, parentLocationId);
            pstmt.executeUpdate();
            System.out.println("Added location");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Location> getLocations(String playerName, String locationName) {
        ArrayList<Location> locations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url)) {
            throwIfNull(connection);

            String sql = "SELECT id, playerName, name, x, y, z FROM LocationBookmark WHERE player = ? AND locationName = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, playerName);
            pstmt.setString(2, locationName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("id");
                var _playerName = rs.getString("player");
                var _locationName = rs.getString("name");
                var x = rs.getDouble("x");
                var y = rs.getDouble("y");
                var z = rs.getDouble("z");
                var parentLocationId = rs.getInt("parentLocationId");
                locations.add(new Location(id, _locationName, _playerName, x, y, z, parentLocationId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static ArrayList<Location> getLocations(String playerName, String locationName, ArrayList<String> parentGroups) {
        ArrayList<Location> locations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url)) {
            throwIfNull(connection);

            StringBuilder sql_tables = new StringBuilder();
            for (int i = 0; i < parentGroups.size(); ++i) {
                var parentGroup = parentGroups.get(i);
                char characterIdentifier = (char) ('B' + i);
                sql_tables.append(String.format(", %s %c", parentGroup, characterIdentifier));
            }
            StringBuilder sql = new StringBuilder(String.format("""
                    SELECT A.id, A.playerName, A.name, A.x, A.y, A.z
                    FROM LocationBookmark A%s
                    WHERE A.CustomerID <> B.CustomerID
                    AND A.player = ? AND A.locationName = ?
                    ;""", sql_tables));
            for (var parentGroup : parentGroups) {
                sql.append(String.format("""
                        AND B.name = %s
                        AND A.parentLocationId = B.id""", parentGroup));
            }

            PreparedStatement pstmt = connection.prepareStatement(sql == null ? null : sql.toString());
            pstmt.setString(1, playerName);
            pstmt.setString(2, locationName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("id");
                var _playerName = rs.getString("player");
                var _locationName = rs.getString("name");
                var x = rs.getDouble("x");
                var y = rs.getDouble("y");
                var z = rs.getDouble("z");
                var parentLocationId = rs.getInt("parentLocationId");
                locations.add(new Location(id, _locationName, _playerName, x, y, z, parentLocationId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

//
//    // delete location
//    public static boolean deleteLocation(String playerName, String locationName) {
//        try (Connection conn = DriverManager.getConnection(url)) {
//            if (conn != null) {
//                String sql = "DELETE FROM LocationBookmark WHERE player = ? AND locationName = ?;";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, playerName);
//                pstmt.setString(2, locationName);
//                pstmt.executeUpdate();
//                conn.close();
//                System.out.println("Deleted location");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    // get all locations
//    public static ArrayList<Location> getAllLocations(String playerName) {
//        ArrayList<Location> locations = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(url)) {
//            if (conn != null) {
//                String sql = "SELECT x, y, z, player, locationName FROM LocationBookmark WHERE player = ?;";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, playerName);
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    double x = rs.getDouble("x");
//                    double y = rs.getDouble("y");
//                    double z = rs.getDouble("z");
//                    String player = rs.getString("player");
//                    String locationName = rs.getString("locationName");
//                    locations.add(new Location(x, y, z, player, locationName));
//                }
//                conn.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return locations;
//    }


    private static void throwIfNull(Connection connection) throws SQLException {
        if (connection == null)
            throw new SQLException("Failed to establish SQL connection");
    }


    /**
     * @param meta
     * @param connection
     * @return True if table name has been changed
     * @throws SQLException
     */
    private static boolean updateOldTable(DatabaseMetaData meta, Connection connection) throws SQLException {
        ResultSet oldRs = meta.getTables(null, null, "locationbookmark", null);
        if (!oldRs.next()) {
            return false;
        }
        String sql = """
                ALTER TABLE locationbookmark
                ADD parentLocationId INTEGER NULL,
                ADD CONSTRAINT fk_parentLocationId
                FOREIGN KEY(parentLocationId) REFERENCES LocationBookmark(id);

                ALTER TABLE locationbookmark
                RENAME TO LocationBookmark;
                """;
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        return true;
    }
}
