package com.kostrzewa.cechini.model;

import java.io.Serializable;
import java.time.Instant;

public class WorkerDTO implements Serializable {

    private Long id;

    private String name;

    private String surname;

    private String desc;

    private String phone;

    private String login;

    private String password;

    private Long target;

    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkerDTO)) {
            return false;
        }

        return id != null && id.equals(((WorkerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkerDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", surname='" + getSurname() + "'" +
                ", desc='" + getDesc() + "'" +
                ", phone='" + getPhone() + "'" +
                ", loginAsync='" + getLogin() + "'" +
                ", password='" + getPassword() + "'" +
                ", target=" + getTarget() +
                ", active='" + isActive() + "'" +
                "}";
    }
}
