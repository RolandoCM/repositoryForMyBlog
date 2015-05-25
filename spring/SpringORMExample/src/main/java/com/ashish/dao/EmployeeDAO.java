package com.ashish.dao;

import java.util.List;

import javax.sql.DataSource;

import com.ashish.entity.EmployeeEntity;

public interface EmployeeDAO {
	 /** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	   public void setDataSource(DataSource ds);
	   /** 
	    * This is the method to be used to create
	    * a record in the EMPLOYEE and EMPLOYEE_ALLOCATION tables.
	    */
	   public void insertEmpRecords();
	   /** 
	    * This is the method to be used to list down
	    * all the records from the EMPLOYEE and EMPLOYEE_ALLOCATION tables.
	    */
	   public void listEmployees();
	   
	   /**
	    * This method releases the resources (e.g. close the session factory) 
	    */
	   public void releaseResources();
}
