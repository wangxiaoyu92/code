package net.sppan.base.entity;

import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

import net.sppan.base.entity.support.BaseEntity;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zhangkun
 * @since 2019-4-23
 */
@Entity
@Table(name = "tb_dept")
public class Dept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 部门名称
	 */
	private String deptname;

	/**
	 * 部门人数
	 */
	private String number;



	/**
	 * 部门描述
	 */
	private String description;



	/**
	 * 成立日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date createtime;


	/**
	 * 部门电话
	 */
	private String telephone;

	/**
	 * 部门地址
	 */
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Dept parent;

	private String sfsc;

	private Integer cj;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public String getSfsc() {
		return sfsc;
	}

	public void setSfsc(String sfsc) {
		this.sfsc = sfsc;
	}

	public Integer getCj() {
		return cj;
	}

	public void setCj(Integer cj) {
		this.cj = cj;
	}
}
