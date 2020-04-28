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
@Table(name = "tb_redis")
public class Redis extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 失效次数
	 */
	private Integer sxcs;

	/**
	 * 失效时间
	 */
	private Integer sxsj;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSxcs() {
		return sxcs;
	}

	public void setSxcs(Integer sxcs) {
		this.sxcs = sxcs;
	}

	public Integer getSxsj() {
		return sxsj;
	}

	public void setSxsj(Integer sxsj) {
		this.sxsj = sxsj;
	}
}
