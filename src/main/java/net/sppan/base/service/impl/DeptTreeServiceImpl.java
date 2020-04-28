package net.sppan.base.service.impl;

import java.util.*;

import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.dao.IDeptDao;
import net.sppan.base.dao.IDeptTreeDao;
import net.sppan.base.dao.IStaffDao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.*;
import net.sppan.base.service.*;
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
public class DeptTreeServiceImpl extends BaseServiceImpl<DeptTree, Integer> implements IDeptTreeService {

	@Autowired
	private IDeptTreeDao deptTreeDao;

	@Override
	public IBaseDao<DeptTree, Integer> getBaseDao() {
		return this.deptTreeDao;
	}





	
}
