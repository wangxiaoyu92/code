package net.sppan.base.service.impl;

import java.util.*;

import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.dao.IDeptDao;
import net.sppan.base.dao.IStaffDao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.*;
import net.sppan.base.service.IDeptService;
import net.sppan.base.service.IRoleService;
import net.sppan.base.service.IStaffService;
import net.sppan.base.service.IUserService;
import net.sppan.base.service.specification.SimpleSpecificationBuilder;
import net.sppan.base.service.specification.SpecificationOperator;
import net.sppan.base.service.support.impl.BaseServiceImpl;

import net.sppan.base.vo.ZtreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 部门表  服务实现类
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept, Integer> implements IDeptService {

	@Autowired
	private IDeptDao deptDao;

	@Override
	public IBaseDao<Dept, Integer> getBaseDao() {
		return this.deptDao;
	}


	@Override
	public void saveOrUpdate(Dept dept) {
		if(dept.getId() != null){
			Dept ddept = find(dept.getId());
			ddept.setDeptname(dept.getDeptname());
			ddept.setNumber(dept.getNumber());
			ddept.setDescription(dept.getDescription());
			ddept.setAddress(dept.getAddress());
			ddept.setTelephone(dept.getTelephone());
			ddept.setCreatetime(dept.getCreatetime());
			if (dept.getParent().getId() == null) {
				dept.setParent(null);
				ddept.setCj(1);
			}else{
				ddept.setCj(dept.getParent().getCj()+1);
			}
			ddept.setParent(dept.getParent());
			update(ddept);
		}else{
			if (dept.getParent().getId() == null) {
			   dept.setParent(null);
			   dept.setCj(1);
			}else{
				dept.setCj(dept.getParent().getCj()+1);
			}
			dept.setSfsc("0");
			save(dept);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		Dept dept = find(id);
		dept.setSfsc("1");
		update(dept);
		SimpleSpecificationBuilder<Dept> builder = new SimpleSpecificationBuilder<Dept>();
		builder.add("parent", SpecificationOperator.Operator.eq.name(), dept);
		List<Dept> list=deptDao.findAll(builder.generateSpecification());
		for(Dept ddept:list){
			ddept.setSfsc("1");
			update(ddept);
		}
		/*super.delete(id);*/
	}

	@Override
	public List<ZtreeView> tree(int deptId) {
		List<ZtreeView> resulTreeNodes = new ArrayList<ZtreeView>();

		ZtreeView node;
		SimpleSpecificationBuilder<Dept> builder = new SimpleSpecificationBuilder<Dept>();
		builder.add("sfsc", SpecificationOperator.Operator.likeAll.name(), "0");
		List<Dept> all = deptDao.findAll(builder.generateSpecification());
		for (Dept dept : all) {
			node = new ZtreeView();
			node.setId(Long.valueOf(dept.getId()));
			if (dept.getParent() == null) {
				node.setpId(0L);
			} else {
				node.setpId(Long.valueOf(dept.getParent().getId()));
			}
			node.setName(dept.getDeptname());
			resulTreeNodes.add(node);
		}
		return resulTreeNodes;
	}


	
}
