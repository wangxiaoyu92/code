package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.Staff;
import net.sppan.base.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface IStaffDao extends IBaseDao<Staff, Integer> {



}
