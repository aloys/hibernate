package demo.hibernate.lab.test;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by amazimpaka on 2018-05-16
 */
public class DatabaseQuery {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseQuery.class);

    public static void describe(){

        final Session currentSession = HibernateSession.getCurrentSession();

        currentSession.doWork( (connection) ->{

            final DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = metaData.getTables(null, null, "%", types);
            while (rs.next()) {
                logger.debug("---------------------------------------------------------------------");
                final  String tableName = rs.getString("TABLE_NAME");
                logger.debug("Describe table : "+tableName);
                describe(connection, tableName);
                logger.debug("---------------------------------------------------------------------");
            }
        });
    }

    public static void describe(String tableName){


        final Session currentSession = HibernateSession.getCurrentSession();

        currentSession.doWork( (connection) ->{

            describe(connection, tableName);
        });

    }

    private static void describe(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            String name = resultSet.getString("COLUMN_NAME");
            String type = resultSet.getString("TYPE_NAME");
            int size = resultSet.getInt("COLUMN_SIZE");

            logger.info("\t\t Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
        }
    }

    public static void query(String tableName){

        final Session currentSession = HibernateSession.getCurrentSession();

        currentSession.doWork( (connection) ->{

            final String sql = "SELECT * FROM " + tableName;

            try(final PreparedStatement ps = connection.prepareStatement(sql)){
                try(final ResultSet rs = ps.executeQuery()){
                    final ResultSetMetaData metaData = rs.getMetaData();

                    logger.debug("Running query: "+sql);
                    logger.debug("---------------------------------------------------------------------");
                    final StringBuilder columns = new StringBuilder();
                    for(int i = 1; i <= metaData.getColumnCount(); i++){
                        columns.append("| \t" + metaData.getColumnName(i));
                    }
                    logger.debug(columns.toString());
                    logger.debug("---------------------------------------------------------------------");

                    while(rs.next()){
                        final StringBuilder values = new StringBuilder();
                        for(int i = 1; i <= metaData.getColumnCount(); i++){
                            values.append("| \t" + rs.getString(i));
                        }
                        logger.debug(values.toString());
                    }

                    logger.debug("---------------------------------------------------------------------");
                }

            }
        });
    }

    public static void deleteAll(String tableName){

        final Session currentSession = HibernateSession.getCurrentSession();

        currentSession.doWork( (connection) -> {

            final String sql = "DELETE FROM " + tableName;

            try (final PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.execute();
            }
        });
    }
}
