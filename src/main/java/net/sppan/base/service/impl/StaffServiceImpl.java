package net.sppan.base.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.dao.IStaffDao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.Dept;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.Staff;
import net.sppan.base.entity.User;
import net.sppan.base.service.IDeptService;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.IStaffService;
import net.sppan.base.service.IUserService;
import net.sppan.base.service.support.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 员工表  服务实现类
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff, Integer> implements IStaffService {

	@Autowired
	private IStaffDao staffDao;

	@Autowired
	private IDeptService deptService;

	@Override
	public IBaseDao<Staff, Integer> getBaseDao() {
		return this.staffDao;
	}


	@Override
	public void saveOrUpdate(Staff staff) {
		Dept dept;
		dept=deptService.find(staff.getDeptid());
		if(staff.getId() != null){
			Staff dstaff = find(staff.getId());
			dstaff.setName(staff.getName());
			dstaff.setAge(staff.getAge());
			dstaff.setBirthday(staff.getBirthday());
			dstaff.setTelephone(staff.getTelephone());
			dstaff.setAddress(staff.getAddress());;
			dstaff.setDescription(staff.getDescription());
			dstaff.setRuzhi(staff.getRuzhi());
			dstaff.setDeptid(staff.getDeptid());
			dstaff.setDeptname(dept.getDeptname());
			update(dstaff);
		}else{
			staff.setDeptname(dept.getDeptname());
			staff.setSfsc("0");
			save(staff);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		Staff staff = find(id);
		staff.setSfsc("1");
		update(staff);
		/*super.delete(id);*/
	}


	
}
