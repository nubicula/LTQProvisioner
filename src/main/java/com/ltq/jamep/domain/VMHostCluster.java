package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VMHostCluster.
 */
@Entity
@Table(name = "vm_host_cluster")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VMHostCluster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mo_ref")
    private String moRef;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private VMHost vmhost;

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

    public VMHostCluster moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public VMHostCluster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VMHost getVmhost() {
        return vmhost;
    }

    public VMHostCluster vmhost(VMHost vMHost) {
        this.vmhost = vMHost;
        return this;
    }

    public void setVmhost(VMHost vMHost) {
        this.vmhost = vMHost;
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
        VMHostCluster vMHostCluster = (VMHostCluster) o;
        if (vMHostCluster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vMHostCluster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VMHostCluster{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
