package net.sppan.base.service;

import net.sppan.base.entity.Staff;
import net.sppan.base.service.support.IBaseService;

/**
 * <p>
 * 员工服务类
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
public interface IStaffService extends IBaseService<Staff, Integer> {


	/**
	 * 增加或者修改员工
	 * @param satff
	 */
	void saveOrUpdate(Staff satff);



}
