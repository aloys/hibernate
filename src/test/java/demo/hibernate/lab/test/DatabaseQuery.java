package demo.hibernate.lab.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Created by amazimpaka on 2018-05-16
 */
public class DatabaseQuery {


    private static final Logger logger = LoggerFactory.getLogger(DatabaseQuery.class);

    public static void query(String tableName){

        HibernateSession.getCurrentSession().doWork( (connection) ->{

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
}
