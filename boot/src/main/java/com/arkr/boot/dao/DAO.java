package com.arkr.boot.dao;

import java.io.Serializable;

/**
 * @author hztanhuayou
 * @date 2017/12/8
 */
public interface DAO extends Serializable {
    String INSERT = " INSERT ";
    String INTO = " INTO ";
    String VALUES = " VALUES ";
    String SELECT = " SELECT ";
    String FROM = " FROM ";
    String COUNT = " COUNT(*) ";
    String WHERE = " WHERE ";
    String AND = " AND ";
    String LIKE = " LIKE ";
    String GROUP_BY = " GROUP BY ";
    String OFFSET = " OFFSET ";
    String LIMIT = " LIMIT ";
    String LIMIT_ONE = " LIMIT 1 ";
    String UPDATE = " UPDATE ";
    String SET = " SET ";
    String ID = " id ";
}
