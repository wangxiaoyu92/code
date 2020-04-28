package net.sppan.base.controller.admin.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.Dept;
import net.sppan.base.entity.DeptTree;
import net.sppan.base.entity.Staff;
import net.sppan.base.service.IDeptService;
import net.sppan.base.service.IDeptTreeService;
import net.sppan.base.service.IStaffService;
import net.sppan.base.service.specification.SimpleSpecificationBuilder;
import net.sppan.base.service.specification.SpecificationOperator;
import net.sppan.base.service.specification.SpecificationOperator.Operator;

import net.sppan.base.vo.ZtreeView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/dept")
public class DeptController extends BaseController {

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IDeptTreeService deptTreeService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(ModelMap map) {
		List<DeptTree> list=deptTreeService.findAll();
		for(DeptTree d:list){
			if(d.getParent_id()==null){
				d.setParent_id(0);
			}
		}
		System.out.println(JSON.toJSONString(list));
		String json = JSON.toJSONString(list);
		map.put("list", json);
		return "admin/dept/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Dept> list() {
		SimpleSpecificationBuilder<Dept> builder = new SimpleSpecificationBuilder<Dept>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("deptname", Operator.likeAll.name(), searchText);
		}

		builder.add("sfsc", SpecificationOperator.Operator.likeAll.name(), "0");
		Page<Dept> page = deptService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}

	@RequestMapping(value = { "/listnew" })
	@ResponseBody
	public Object listnew() {
		List<DeptTree> list=deptTreeService.findAll();
		for(DeptTree d:list){
			if(d.getParent_id()==null){
				d.setParent_id(0);
			}
		}
		return JSONArray.toJSON(list);
	}

	@RequestMapping(value = { "/listto" })
	@ResponseBody
	public Object listto() {
		List<DeptTree> list=deptTreeService.findAll();
		for(DeptTree d:list){
			if(d.getParent_id()==null){
				d.setParent_id(0);
			}
		}
		return list;
	}

	@RequestMapping(value = { "/listtree/{deptId}" })
	@ResponseBody
	public Page<Dept> listtree(@PathVariable Integer deptId) {
		SimpleSpecificationBuilder<Dept> builder = new SimpleSpecificationBuilder<Dept>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("deptname", Operator.likeAll.name(), searchText);
		}
		if(deptId!=null){
			builder.add("id", SpecificationOperator.Operator.eq.name(), deptId);
		}

		builder.add("sfsc", SpecificationOperator.Operator.likeAll.name(), "0");
		Page<Dept> page = deptService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		List<Dept> list = deptService.findAll();
		map.put("list", list);
		return "admin/dept/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Dept dept = deptService.find(id);
		map.put("dept", dept);
		List<Dept> list = deptService.findAll();
		map.put("list", list);
		return "admin/dept/form";
	}
	
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Dept dept,ModelMap map){
		try {
			deptService.saveOrUpdate(dept);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			deptService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	@RequestMapping("/tree/{deptId}")
	@ResponseBody
	public List<ZtreeView> tree(@PathVariable Integer deptId){
		List<ZtreeView> list = deptService.tree(deptId);
		return list;
	}
	

}
