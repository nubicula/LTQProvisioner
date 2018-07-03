package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DatastoreCluster.
 */
@Entity
@Table(name = "datastore_cluster")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatastoreCluster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mo_ref")
    private String moRef;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "datastoreclusters")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VMHost> vmhosts = new HashSet<>();

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

    public DatastoreCluster moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public DatastoreCluster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VMHost> getVmhosts() {
        return vmhosts;
    }

    public DatastoreCluster vmhosts(Set<VMHost> vMHosts) {
        this.vmhosts = vMHosts;
        return this;
    }

    public DatastoreCluster addVmhost(VMHost vMHost) {
        this.vmhosts.add(vMHost);
        vMHost.getDatastoreclusters().add(this);
        return this;
    }

    public DatastoreCluster removeVmhost(VMHost vMHost) {
        this.vmhosts.remove(vMHost);
        vMHost.getDatastoreclusters().remove(this);
        return this;
    }

    public void setVmhosts(Set<VMHost> vMHosts) {
        this.vmhosts = vMHosts;
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
        DatastoreCluster datastoreCluster = (DatastoreCluster) o;
        if (datastoreCluster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datastoreCluster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DatastoreCluster{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
