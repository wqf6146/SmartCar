package com.yhkj.smartcar.bean.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yhkj.smartcar.bean.LocalConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_CONFIG".
*/
public class LocalConfigDao extends AbstractDao<LocalConfig, Long> {

    public static final String TABLENAME = "LOCAL_CONFIG";

    /**
     * Properties of entity LocalConfig.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BAutoLogin = new Property(1, boolean.class, "bAutoLogin", false, "B_AUTO_LOGIN");
        public final static Property Phone = new Property(2, String.class, "phone", false, "PHONE");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property Token = new Property(4, String.class, "token", false, "TOKEN");
        public final static Property Sysmes = new Property(5, boolean.class, "sysmes", false, "SYSMES");
    }


    public LocalConfigDao(DaoConfig config) {
        super(config);
    }
    
    public LocalConfigDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_CONFIG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"B_AUTO_LOGIN\" INTEGER NOT NULL ," + // 1: bAutoLogin
                "\"PHONE\" TEXT," + // 2: phone
                "\"PASSWORD\" TEXT," + // 3: password
                "\"TOKEN\" TEXT," + // 4: token
                "\"SYSMES\" INTEGER NOT NULL );"); // 5: sysmes
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_CONFIG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalConfig entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBAutoLogin() ? 1L: 0L);
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(3, phone);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(5, token);
        }
        stmt.bindLong(6, entity.getSysmes() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalConfig entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBAutoLogin() ? 1L: 0L);
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(3, phone);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(5, token);
        }
        stmt.bindLong(6, entity.getSysmes() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalConfig readEntity(Cursor cursor, int offset) {
        LocalConfig entity = new LocalConfig( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getShort(offset + 1) != 0, // bAutoLogin
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // phone
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // token
            cursor.getShort(offset + 5) != 0 // sysmes
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalConfig entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBAutoLogin(cursor.getShort(offset + 1) != 0);
        entity.setPhone(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setToken(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSysmes(cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalConfig entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalConfig entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalConfig entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
