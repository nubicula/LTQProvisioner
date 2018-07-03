package com.ltq.jamep.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VMHost.
 */
@Entity
@Table(name = "vm_host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VMHost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mo_ref")
    private String moRef;

    @Column(name = "name")
    private String name;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PhysicalDatacenter physicaldatacenter;

    @ManyToOne
    @JsonIgnoreProperties("")
    private VMHostCluster vmhostcluster;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "vmhost_datastore",
               joinColumns = @JoinColumn(name = "vmhosts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "datastores_id", referencedColumnName = "id"))
    private Set<Datastore> datastores = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "vmhost_datastorecluster",
               joinColumns = @JoinColumn(name = "vmhosts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "datastoreclusters_id", referencedColumnName = "id"))
    private Set<DatastoreCluster> datastoreclusters = new HashSet<>();

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

    public VMHost moRef(String moRef) {
        this.moRef = moRef;
        return this;
    }

    public void setMoRef(String moRef) {
        this.moRef = moRef;
    }

    public String getName() {
        return name;
    }

    public VMHost name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public VMHost ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public PhysicalDatacenter getPhysicaldatacenter() {
        return physicaldatacenter;
    }

    public VMHost physicaldatacenter(PhysicalDatacenter physicalDatacenter) {
        this.physicaldatacenter = physicalDatacenter;
        return this;
    }

    public void setPhysicaldatacenter(PhysicalDatacenter physicalDatacenter) {
        this.physicaldatacenter = physicalDatacenter;
    }

    public VMHostCluster getVmhostcluster() {
        return vmhostcluster;
    }

    public VMHost vmhostcluster(VMHostCluster vMHostCluster) {
        this.vmhostcluster = vMHostCluster;
        return this;
    }

    public void setVmhostcluster(VMHostCluster vMHostCluster) {
        this.vmhostcluster = vMHostCluster;
    }

    public Set<Datastore> getDatastores() {
        return datastores;
    }

    public VMHost datastores(Set<Datastore> datastores) {
        this.datastores = datastores;
        return this;
    }

    public VMHost addDatastore(Datastore datastore) {
        this.datastores.add(datastore);
        datastore.getVmhosts().add(this);
        return this;
    }

    public VMHost removeDatastore(Datastore datastore) {
        this.datastores.remove(datastore);
        datastore.getVmhosts().remove(this);
        return this;
    }

    public void setDatastores(Set<Datastore> datastores) {
        this.datastores = datastores;
    }

    public Set<DatastoreCluster> getDatastoreclusters() {
        return datastoreclusters;
    }

    public VMHost datastoreclusters(Set<DatastoreCluster> datastoreClusters) {
        this.datastoreclusters = datastoreClusters;
        return this;
    }

    public VMHost addDatastorecluster(DatastoreCluster datastoreCluster) {
        this.datastoreclusters.add(datastoreCluster);
        datastoreCluster.getVmhosts().add(this);
        return this;
    }

    public VMHost removeDatastorecluster(DatastoreCluster datastoreCluster) {
        this.datastoreclusters.remove(datastoreCluster);
        datastoreCluster.getVmhosts().remove(this);
        return this;
    }

    public void setDatastoreclusters(Set<DatastoreCluster> datastoreClusters) {
        this.datastoreclusters = datastoreClusters;
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
        VMHost vMHost = (VMHost) o;
        if (vMHost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vMHost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VMHost{" +
            "id=" + getId() +
            ", moRef='" + getMoRef() + "'" +
            ", name='" + getName() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            "}";
    }
}
