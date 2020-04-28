package net.sppan.base.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

import net.sppan.base.entity.support.BaseEntity;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
@Entity
@Table(name = "tb_staff")
public class Staff extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 员工id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 描述
	 */
	private String description;



	/**
	 * 出生日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;

	/**
	 * 入职日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date ruzhi;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 部门ID
	 */
	private  Integer deptid;

	/**
	 * 部门名称
	 */
	private String deptname;

	private String sfsc;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRuzhi() {
		return ruzhi;
	}

	public void setRuzhi(Date ruzhi) {
		this.ruzhi = ruzhi;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSfsc() {
		return sfsc;
	}

	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}
}
