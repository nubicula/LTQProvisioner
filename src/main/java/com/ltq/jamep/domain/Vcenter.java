package com.ltq.jamep.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vcenter.
 */
@Entity
@Table(name = "vcenter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vcenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mo_ref")
    private String moRef;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "jhi_password")
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoRef() {
        return moRef;
    }

    public Vcenter moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public Vcenter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Vcenter ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public Vcenter userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Vcenter password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vcenter vcenter = (Vcenter) o;
        if (vcenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vcenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vcenter{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
