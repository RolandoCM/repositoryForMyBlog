package com.ashish.rest.controller;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ashish.rest.bean.Employee;

@Path("/hello")
public class HelloWorldREST {

	@GET
	@Path("/{parameter}")
	public Response responseMsg( @PathParam("parameter") String parameter,
			@DefaultValue("Nothing to say") @QueryParam("value") String value) {

		String output = "Hello from: " + parameter + " : " + value;

		return Response.status(200).entity(output).build();

	}
	
	@GET
	@Path("/json/{empId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee( @PathParam("empId") int empId,
			@DefaultValue("No Employee Id passed") @QueryParam("value") String value) {

		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setName("Ashish Mondal");

		return emp;

	}

}
