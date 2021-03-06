package com.org.coop.society.data.customer.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;


/**
 * The persistent class for the branch_master database table.
 * 
 */
@Entity
@Table(name="branch_master")
@NamedQuery(name="BranchMaster.findAll", query="SELECT b FROM BranchMaster b")
public class BranchMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="branch_id")
	private int branchId;

	@Column(name="bank_name")
	private String bankName;

	@Column(name="branch_name")
	private String branchName;

	@Column(name="create_date", updatable=false)
	private Timestamp createDate;

	@Column(name="create_user")
	private String createUser;

	private String email1;

	private String email2;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	@Column(name="ifsc_code")
	private String ifscCode;

	@Column(name="micr_code")
	private String micrCode;

	@Column(name="parent_id")
	private int parentId;

	private String phone1;

	private String phone2;

	private String remarks;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	@Column(name="update_date", insertable=false, updatable=false)
	private Timestamp updateDate;

	@Column(name="update_user")
	private String updateUser;

	//bi-directional many-to-one association to BranchAddress
	@OneToMany(mappedBy="branchMaster", cascade=CascadeType.ALL)
	@Where(clause = "end_date is null")
	private List<BranchAddress> branchAddresses;

	//bi-directional many-to-one association to BranchRule
	@OneToMany(mappedBy="branchMaster", cascade=CascadeType.ALL)
	private List<BranchRule> branchRules;

	//bi-directional many-to-one association to RoleMaster
	@OneToMany(mappedBy="branchMaster", cascade=CascadeType.ALL)
	private List<RoleMaster> roleMasters;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="branchMaster", cascade=CascadeType.ALL)
	private List<User> users;

	public BranchMaster() {
	}

	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEmail1() {
		return this.email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return this.email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIfscCode() {
		return this.ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getMicrCode() {
		return this.micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public List<BranchAddress> getBranchAddresses() {
		if(this.branchAddresses == null) {
			branchAddresses = new ArrayList<BranchAddress>();
		}
		return this.branchAddresses;
	}

	public void setBranchAddresses(List<BranchAddress> branchAddresses) {
		this.branchAddresses = branchAddresses;
	}

	public BranchAddress addBranchAddress(BranchAddress branchAddress) {
		getBranchAddresses().add(branchAddress);
		branchAddress.setBranchMaster(this);

		return branchAddress;
	}

	public BranchAddress removeBranchAddress(BranchAddress branchAddress) {
		getBranchAddresses().remove(branchAddress);
		branchAddress.setBranchMaster(null);

		return branchAddress;
	}

	public List<BranchRule> getBranchRules() {
		return this.branchRules;
	}

	public void setBranchRules(List<BranchRule> branchRules) {
		this.branchRules = branchRules;
	}

	public BranchRule addBranchRule(BranchRule branchRule) {
		getBranchRules().add(branchRule);
		branchRule.setBranchMaster(this);

		return branchRule;
	}

	public BranchRule removeBranchRule(BranchRule branchRule) {
		getBranchRules().remove(branchRule);
		branchRule.setBranchMaster(null);

		return branchRule;
	}

	public List<RoleMaster> getRoleMasters() {
		return this.roleMasters;
	}

	public void setRoleMasters(List<RoleMaster> roleMasters) {
		this.roleMasters = roleMasters;
	}

	public RoleMaster addRoleMaster(RoleMaster roleMaster) {
		getRoleMasters().add(roleMaster);
		roleMaster.setBranchMaster(this);

		return roleMaster;
	}

	public RoleMaster removeRoleMaster(RoleMaster roleMaster) {
		getRoleMasters().remove(roleMaster);
		roleMaster.setBranchMaster(null);

		return roleMaster;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setBranchMaster(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setBranchMaster(null);

		return user;
	}
}