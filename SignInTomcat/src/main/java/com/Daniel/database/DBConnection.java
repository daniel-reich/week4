package com.Daniel.database;
import com.Daniel.model.Guest;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    Connection conn;                                                //our connnection to the db - presist for life of program

    // we dont want this garbage collected until we are done
    public DBConnection(String dbName) throws Exception {    // note more general exception
        Class.forName("org.hsqldb.jdbcDriver");
        //conn = DriverManager.getConnection("jdbc:hsqldb:hsql:" + dbName, "SA", "");
        // conn = DriverManager.getConnection("jdbc:hsqldb:file:/Applications/hsqldb-2.3.4/hsqldb/lib/testDB", "SA", "");
        conn = DriverManager.getConnection("jdbc:hsqldb:file:/Applications/hsqldb-2.3.4/hsqldb/lib/guest;","SA","");

    }

    public Connection getConn() {
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();
    }

    //use for SQL command SELECT
    public synchronized void query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement();
        rs = st.executeQuery(expression);

        dump(rs);
        st.close();
    }

    public synchronized void update(String expression) throws SQLException {

        Statement st = null;

        st = conn.createStatement();    // statements

        int i = st.executeUpdate(expression);    // run the query

        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }

    public static void dump(ResultSet rs) throws SQLException {

        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta = rs.getMetaData();
        int colmax = meta.getColumnCount();
        int i;
        Object o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }

    }



}
