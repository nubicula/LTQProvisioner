package com.ltq.jamep.domain;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "datastore_virtualvolume",
               joinColumns = @JoinColumn(name = "datastores_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "virtualvolumes_id", referencedColumnName = "id"))
    private Set<VirtualVolume> virtualvolumes = new HashSet<>();

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

    public Set<VirtualVolume> getVirtualvolumes() {
        return virtualvolumes;
    }

    public Datastore virtualvolumes(Set<VirtualVolume> virtualVolumes) {
        this.virtualvolumes = virtualVolumes;
        return this;
    }

    public Datastore addVirtualvolume(VirtualVolume virtualVolume) {
        this.virtualvolumes.add(virtualVolume);
        return this;
    }

    public Datastore removeVirtualvolume(VirtualVolume virtualVolume) {
        this.virtualvolumes.remove(virtualVolume);
        return this;
    }

    public void setVirtualvolumes(Set<VirtualVolume> virtualVolumes) {
        this.virtualvolumes = virtualVolumes;
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
