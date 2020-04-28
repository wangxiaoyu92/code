package net.sppan.base.controller.admin.system;

import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.Dept;
import net.sppan.base.entity.Staff;
import net.sppan.base.service.IDeptService;
import net.sppan.base.service.IStaffService;
import net.sppan.base.service.specification.SimpleSpecificationBuilder;
import net.sppan.base.service.specification.SpecificationOperator;
import net.sppan.base.service.specification.SpecificationOperator.Operator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/admin/staff")
public class StaffController extends BaseController {

	@Autowired
	private IStaffService staffService;

	@Autowired
	private IDeptService deptService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/staff/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Staff> list() {
		SimpleSpecificationBuilder<Staff> builder = new SimpleSpecificationBuilder<Staff>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("name", Operator.likeAll.name(), searchText);

		}
		builder.add("sfsc", Operator.likeAll.name(), "0");
		Page<Staff> page = staffService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		List<Dept> list = deptService.findAll();
		map.put("list", list);
		return "admin/staff/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Staff staff = staffService.find(id);
		map.put("staff", staff);
		List<Dept> list = deptService.findAll();
		map.put("list", list);
		return "admin/staff/form";
	}
	
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Staff staff,ModelMap map){
		try {
			staffService.saveOrUpdate(staff);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			staffService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	

}
