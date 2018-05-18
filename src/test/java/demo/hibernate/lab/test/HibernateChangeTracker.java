package demo.hibernate.lab.test;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by amazimpaka on 2018-05-17
 */
public class HibernateChangeTracker extends EmptyInterceptor {

    private static  final Set<String> TOUCHED_TABLES = new LinkedHashSet<>();

    public static final HibernateChangeTracker INSTANCE = new HibernateChangeTracker();

    private HibernateChangeTracker(){
        super();
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean result = super.onSave(entity, id, state, propertyNames, types);

        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            final Table table = entity.getClass().getAnnotation(Table.class);
            final String annotationName = table != null ? table.name() : "" ;
            final String className = entity.getClass().getSimpleName();

            if(!annotationName.isEmpty() && !TOUCHED_TABLES.contains(annotationName)){
                TOUCHED_TABLES.add(annotationName);
            }else if(!TOUCHED_TABLES.contains(className)){
                TOUCHED_TABLES.add(className);
            }
        }

        return result;
    }


    @Override
    public void afterTransactionCompletion(Transaction tx) {
        super.afterTransactionCompletion(tx);


        synchronized (this){
            for(String tableName : TOUCHED_TABLES){
                DatabaseQuery.query(tableName);
            }
            TOUCHED_TABLES.clear();
        }
    }

    public void cleanUp() {

        synchronized (TOUCHED_TABLES){

            final List<String> tables = new ArrayList<>();
            TOUCHED_TABLES.forEach(table -> tables.add(table));

            for(int k = tables.size() - 1; k >= 0; k--){
                DatabaseQuery.deleteAll(tables.get(k));
            }
            TOUCHED_TABLES.clear();
        }

    }


}
