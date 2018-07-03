package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Datastore.
 */
@Entity
@Table(name = "datastore")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Datastore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mo_ref")
    private String moRef;

    @Column(name = "name")
    private String name;

    @Column(name = "wwn")
    private String wwn;

    @ManyToOne
    @JsonIgnoreProperties("")
    private StorageArray storagearray;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DatastoreCluster datastorecluster;

    @ManyToMany(mappedBy = "datastores")
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

    public Datastore moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public Datastore name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWwn() {
        return wwn;
    }

    public Datastore wwn(String wwn) {
        this.wwn = wwn;
        return this;
    }

    public void setWwn(String wwn) {
        this.wwn = wwn;
    }

    public StorageArray getStoragearray() {
        return storagearray;
    }

    public Datastore storagearray(StorageArray storageArray) {
        this.storagearray = storageArray;
        return this;
    }

    public void setStoragearray(StorageArray storageArray) {
        this.storagearray = storageArray;
    }

    public DatastoreCluster getDatastorecluster() {
        return datastorecluster;
    }

    public Datastore datastorecluster(DatastoreCluster datastoreCluster) {
        this.datastorecluster = datastoreCluster;
        return this;
    }

    public void setDatastorecluster(DatastoreCluster datastoreCluster) {
        this.datastorecluster = datastoreCluster;
    }

    public Set<VMHost> getVmhosts() {
        return vmhosts;
    }

    public Datastore vmhosts(Set<VMHost> vMHosts) {
        this.vmhosts = vMHosts;
        return this;
    }

    public Datastore addVmhost(VMHost vMHost) {
        this.vmhosts.add(vMHost);
        vMHost.getDatastores().add(this);
        return this;
    }

    public Datastore removeVmhost(VMHost vMHost) {
        this.vmhosts.remove(vMHost);
        vMHost.getDatastores().remove(this);
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
        Datastore datastore = (Datastore) o;
        if (datastore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datastore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Datastore{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            ", wwn='" + getWwn() + "'" +
            "}";
    }
}
