package com.ezorm.dao;

import com.ezorm.model.Package;
import com.ezorm.orm.EzOrm;
import com.ezorm.orm.impl.EzOrmBaseDao;
import org.springframework.stereotype.Component;

/**
 * Created by Li Yu on 2017/6/13.
 */
@Component
public class PackageDao extends EzOrmBaseDao<Package> implements EzOrm<Package> {

}
