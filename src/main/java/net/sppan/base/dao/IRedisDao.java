package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.Dept;

import net.sppan.base.entity.Redis;
import org.springframework.stereotype.Repository;

@Repository
public interface IRedisDao extends IBaseDao<Redis, Integer> {



}
