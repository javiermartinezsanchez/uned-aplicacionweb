package es.alumno.uned.config;

import java.io.Serializable;
import java.util.List;

import es.alumno.uned.model.repository.UserActiveStore;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class ActiveUserListener implements HttpSessionBindingListener, Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private UserActiveStore activeUserStore;

    public ActiveUserListener(String username, UserActiveStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    public ActiveUserListener() {
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        ActiveUserListener user = (ActiveUserListener) event.getValue();
        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        ActiveUserListener user = (ActiveUserListener) event.getValue();
        users.remove(user.getUsername());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
