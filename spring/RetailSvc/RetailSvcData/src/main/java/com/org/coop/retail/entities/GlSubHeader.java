package com.org.coop.retail.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the gl_sub_header database table.
 * 
 */
@Entity
@Table(name="gl_sub_header")
@NamedQuery(name="GlSubHeader.findAll", query="SELECT g FROM GlSubHeader g")
public class GlSubHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="gl_sub_header_code")
	private int glSubHeaderCode;

	@Column(name="gl_sub_header_desc")
	private String glSubHeaderDesc;

	@Column(name="delete_ind")
	private String deleteInd;

	@Column(name="delete_reason")
	private String deleteReason;

	@Column(name="passing_auth_ind")
	private String passingAuthInd;

	@Column(name="passing_auth_remark")
	private String passingAuthRemark;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="create_user")
	private String createUser;
	
	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="update_user")
	private String updateUser;
	
	//bi-directional many-to-one association to GlMaster
	@OneToMany(mappedBy="glSubHeader", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private List<GlMaster> glMasters;

	//bi-directional many-to-one association to GlHeader
	@ManyToOne
	@JoinColumn(name="gl_header_code")
	private GlHeader glHeader;

	public GlSubHeader() {
	}

	public int getGlSubHeaderCode() {
		return this.glSubHeaderCode;
	}

	public void setGlSubHeaderCode(int glSubHeaderCode) {
		this.glSubHeaderCode = glSubHeaderCode;
	}

	public String getGlSubHeaderDesc() {
		return this.glSubHeaderDesc;
	}

	public void setGlSubHeaderDesc(String glSubHeaderDesc) {
		this.glSubHeaderDesc = glSubHeaderDesc;
	}

	public List<GlMaster> getGlMasters() {
		return this.glMasters;
	}

	public void setGlMasters(List<GlMaster> glMasters) {
		this.glMasters = glMasters;
	}

	public GlMaster addGlMaster(GlMaster glMaster) {
		getGlMasters().add(glMaster);
		glMaster.setGlSubHeader(this);

		return glMaster;
	}

	public GlMaster removeGlMaster(GlMaster glMaster) {
		getGlMasters().remove(glMaster);
		glMaster.setGlSubHeader(null);

		return glMaster;
	}

	public GlHeader getGlHeader() {
		return this.glHeader;
	}

	public void setGlHeader(GlHeader glHeader) {
		this.glHeader = glHeader;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + glSubHeaderCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlSubHeader other = (GlSubHeader) obj;
		if (glSubHeaderCode != other.glSubHeaderCode)
			return false;
		return true;
	}

	public String getDeleteInd() {
		return deleteInd;
	}

	public void setDeleteInd(String deleteInd) {
		this.deleteInd = deleteInd;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getPassingAuthInd() {
		return passingAuthInd;
	}

	public void setPassingAuthInd(String passingAuthInd) {
		this.passingAuthInd = passingAuthInd;
	}

	public String getPassingAuthRemark() {
		return passingAuthRemark;
	}

	public void setPassingAuthRemark(String passingAuthRemark) {
		this.passingAuthRemark = passingAuthRemark;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@PreUpdate
	public void updateTimeStamps() {
		long currentTime = System.currentTimeMillis();
	    updateDate = new Timestamp(currentTime);
	    if (createDate == null) {
	    	createDate = new Timestamp(currentTime);
	    }
	}
}