package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VCenter.
 */
@Entity
@Table(name = "v_center")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VCenter implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private VMHostCluster vmhostcluster;

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

    public VCenter moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public VCenter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public VCenter ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public VCenter userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public VCenter password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VMHostCluster getVmhostcluster() {
        return vmhostcluster;
    }

    public VCenter vmhostcluster(VMHostCluster vMHostCluster) {
        this.vmhostcluster = vMHostCluster;
        return this;
    }

    public void setVmhostcluster(VMHostCluster vMHostCluster) {
        this.vmhostcluster = vMHostCluster;
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
        VCenter vCenter = (VCenter) o;
        if (vCenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vCenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VCenter{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
