package com.org.coop.retail.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the gl_header database table.
 * 
 */
@Entity
@Table(name="gl_header")
@NamedQuery(name="GlHeader.findAll", query="SELECT g FROM GlHeader g")
public class GlHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="gl_header_code")
	private int glHeaderCode;

	@Column(name="gl_header_desc")
	private String glHeaderDesc;

	private String status;

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
	
	//bi-directional many-to-one association to GlSubHeader
	@OneToMany(mappedBy="glHeader", fetch = FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private List<GlSubHeader> glSubHeaders;

	public GlHeader() {
	}

	public int getGlHeaderCode() {
		return this.glHeaderCode;
	}

	public void setGlHeaderCode(int glHeaderCode) {
		this.glHeaderCode = glHeaderCode;
	}

	public String getGlHeaderDesc() {
		return this.glHeaderDesc;
	}

	public void setGlHeaderDesc(String glHeaderDesc) {
		this.glHeaderDesc = glHeaderDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GlSubHeader> getGlSubHeaders() {
		return this.glSubHeaders;
	}

	public void setGlSubHeaders(List<GlSubHeader> glSubHeaders) {
		this.glSubHeaders = glSubHeaders;
	}

	public GlSubHeader addGlSubHeader(GlSubHeader glSubHeader) {
		getGlSubHeaders().add(glSubHeader);
		glSubHeader.setGlHeader(this);

		return glSubHeader;
	}

	public GlSubHeader removeGlSubHeader(GlSubHeader glSubHeader) {
		getGlSubHeaders().remove(glSubHeader);
		glSubHeader.setGlHeader(null);

		return glSubHeader;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + glHeaderCode;
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
		GlHeader other = (GlHeader) obj;
		if (glHeaderCode != other.glHeaderCode)
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