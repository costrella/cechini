package com.costrella.cechini.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Worker} entity.
 */
@ApiModel(description = "Pomysły: notyfikacje")
public class WorkerDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private LocalDate hiredDate;

    private String desc;

    private String login;

    private String password;

    private Long target;


    private Long statusId;

    private String statusName;
    
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

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
            ", hiredDate='" + getHiredDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", target=" + getTarget() +
            ", statusId=" + getStatusId() +
            ", statusName='" + getStatusName() + "'" +
            "}";
    }
}
