package mx.edu.utez.rest;

import com.google.gson.Gson;
import mx.edu.utez.dao.UserDAO;
import mx.edu.utez.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UsersService {
    private Gson gson = new Gson();
    private UserDAO userDAO = new UserDAO();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String registerUser(@FormParam("name") String name, @FormParam("lastname") String lastname) {
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);

        boolean result = userDAO.registerUser(user);

        return gson.toJson(result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {
        List<User> users = userDAO.getAllUsers();

        return gson.toJson(users);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserByID(@PathParam("id") int id) {
        User user = userDAO.getUserByID(id);

        return gson.toJson(user);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") int id) {
        boolean result = userDAO.deleteUser(id);

        return gson.toJson(result);
    }
}
