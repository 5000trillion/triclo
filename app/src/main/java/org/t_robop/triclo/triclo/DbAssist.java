package org.t_robop.triclo.triclo;


import io.realm.RealmObject;
import io.realm.annotations.Ignore;


/**
 * Created by iris on 2017/06/22.
 */

public class DbAssist extends RealmObject {
    private String name;
    private int id;

    @Ignore
    private int sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
