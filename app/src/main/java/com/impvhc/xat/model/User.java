
package com.impvhc.xat.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String id;
    private String displayName;
    private String email;
    private boolean online;
    private String organization_id;

    public User() {
    }

    public User(String id, String displayName, String email, boolean online, String organization_id) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.online = online;
        this.organization_id = organization_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }
}
