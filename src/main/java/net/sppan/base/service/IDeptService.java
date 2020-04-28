package net.sppan.base.service;

import net.sppan.base.entity.Dept;
import net.sppan.base.service.support.IBaseService;
import net.sppan.base.vo.ZtreeView;

import java.util.List;

/**
 * <p>
 * 部门服务类
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
public interface IDeptService extends IBaseService<Dept, Integer> {

	/**
	 * 增加或者修改部门
	 * @param dept
	 */
	void saveOrUpdate(Dept dept);

	/**
	 * 获取部门树
	 * @param deptId
	 * @return
	 */
	List<ZtreeView> tree(int deptId);

}
