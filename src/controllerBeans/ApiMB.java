package controllerBeans;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/register")
public class ApiMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@GET
	public Response getResponse() {

		String result = "<h1>RESTful Demo Application</h1>In real world application, a collection of users will be returned !!";
		return Response.status(200).entity(result).build();
	}

}
