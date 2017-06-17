package com.yhkj.smartcar.bean.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yhkj.smartcar.bean.LocalConfig;

import com.yhkj.smartcar.bean.db.LocalConfigDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig localConfigDaoConfig;

    private final LocalConfigDao localConfigDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        localConfigDaoConfig = daoConfigMap.get(LocalConfigDao.class).clone();
        localConfigDaoConfig.initIdentityScope(type);

        localConfigDao = new LocalConfigDao(localConfigDaoConfig, this);

        registerDao(LocalConfig.class, localConfigDao);
    }
    
    public void clear() {
        localConfigDaoConfig.clearIdentityScope();
    }

    public LocalConfigDao getLocalConfigDao() {
        return localConfigDao;
    }

}