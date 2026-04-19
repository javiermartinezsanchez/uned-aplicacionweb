package es.alumno.uned.model.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Para guardar los usuarios en sesión (activos), generamos esta Lista para mantenerlos.
 * 
 * 
 */
public class UserActiveStore {

    public List<String> users;

    public UserActiveStore() {
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
