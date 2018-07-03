package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PhysicalDatacenter.
 */
@Entity
@Table(name = "physical_datacenter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PhysicalDatacenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JsonIgnoreProperties("")
    private VMHost vmhost;

    @ManyToOne
    @JsonIgnoreProperties("")
    private StorageArray storagearray;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PhysicalDatacenter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public PhysicalDatacenter address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public VMHost getVmhost() {
        return vmhost;
    }

    public PhysicalDatacenter vmhost(VMHost vMHost) {
        this.vmhost = vMHost;
        return this;
    }

    public void setVmhost(VMHost vMHost) {
        this.vmhost = vMHost;
    }

    public StorageArray getStoragearray() {
        return storagearray;
    }

    public PhysicalDatacenter storagearray(StorageArray storageArray) {
        this.storagearray = storageArray;
        return this;
    }

    public void setStoragearray(StorageArray storageArray) {
        this.storagearray = storageArray;
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
        PhysicalDatacenter physicalDatacenter = (PhysicalDatacenter) o;
        if (physicalDatacenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), physicalDatacenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhysicalDatacenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
