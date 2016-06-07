package vankor.EnergyDepartment;

import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.Owner.ContractEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalOtherMethodEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CAPACITYSOURCEOBJECT", schema = "PUBLIC", catalog = "UE_DB")
public class CapacitySourceObjectEntity implements Comparable, Serializable {
    private int id;
    private double capacity;
    private TypeResourceEntity typeResourceEntity;
    private Set<ActInstallCountEntity> actInstallCountEntities;
    private Set<JournalOtherMethodEntity> journalOtherMethodEntities;
    private Boolean source;
    private Boolean consumer;
    private ObjectOnPlaceEntity objectOnPlaceEntity;
    private Date dateInstall, dateUnInstall;
    private String description;
    private ContractEntity contractEntity;
    private Set<BalanceResourceEntity> balanceResourceEntity;
    private Set<CapacitySourceObjectEntity> rootCapacity;

    public CapacitySourceObjectEntity() {
    }

    public CapacitySourceObjectEntity(double capacity, TypeResourceEntity typeResourceEntity, Boolean source,
                                      Boolean consumer, ObjectOnPlaceEntity objectOnPlaceEntity, Date dateInstall,
                                      String description, ContractEntity contractEntity) {
        this.capacity = capacity;
        this.typeResourceEntity = typeResourceEntity;
        this.source = source;
        this.consumer = consumer;
        this.objectOnPlaceEntity = objectOnPlaceEntity;
        this.dateInstall = dateInstall;
        this.description = description;
        this.contractEntity = contractEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CAPACITY")
    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "Source")
    public Boolean getSource() {
        return source;
    }

    public void setSource(Boolean source) {
        this.source = source;
    }

    @Basic
    @Column(name = "Consumer")
    public Boolean getConsumer() {
        return consumer;
    }

    public void setConsumer(Boolean consumer) {
        this.consumer = consumer;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATEINSTALL", nullable = false, insertable = true, updatable = true)
    public Date getDateInstall() {
        return dateInstall;
    }

    public void setDateInstall(Date dateInstall) {
        this.dateInstall = dateInstall;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATEUNINSTALL", nullable = true, insertable = true, updatable = true)
    public Date getDateUnInstall() {
        return dateUnInstall;
    }

    public void setDateUnInstall(Date dateUnInstall) {
        this.dateUnInstall = dateUnInstall;
    }

    @ManyToOne
    @JoinColumn(name = "ObjectOnPlace", referencedColumnName = "ID", nullable = false)
    public ObjectOnPlaceEntity getObjectOnPlaceEntity() {
        return objectOnPlaceEntity;
    }

    public void setObjectOnPlaceEntity(ObjectOnPlaceEntity objectOnPlaceEntity) {
        this.objectOnPlaceEntity = objectOnPlaceEntity;
    }

    @ManyToOne
    @JoinColumn(name = "TypeResource", referencedColumnName = "ID", nullable = false)
    public TypeResourceEntity getTypeResourceEntity() {
        return typeResourceEntity;
    }

    public void setTypeResourceEntity(TypeResourceEntity typeResourceEntity) {
        this.typeResourceEntity = typeResourceEntity;
    }

    @ManyToOne
    @JoinColumn(name = "Contract1", referencedColumnName = "ID", nullable = false)
    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
    }

    @ManyToMany
    @JoinColumn(name = "CapacityRoot", referencedColumnName = "id", nullable = true)
    public Set<CapacitySourceObjectEntity> getRootCapacity() {
        return rootCapacity;
    }

    public void setRootCapacity(Set<CapacitySourceObjectEntity> rootCapacity) {
        this.rootCapacity = rootCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapacitySourceObjectEntity)) return false;

        CapacitySourceObjectEntity that = (CapacitySourceObjectEntity) o;

        if (Double.compare(that.capacity, capacity) != 0) return false;
        if (actInstallCountEntities != null ? !actInstallCountEntities.equals(that.actInstallCountEntities) : that.actInstallCountEntities != null)
            return false;
        if (consumer != null ? !consumer.equals(that.consumer) : that.consumer != null) return false;
        if (contractEntity != null ? !contractEntity.equals(that.contractEntity) : that.contractEntity != null)
            return false;
        if (dateInstall != null ? !dateInstall.equals(that.dateInstall) : that.dateInstall != null) return false;
        if (dateUnInstall != null ? !dateUnInstall.equals(that.dateUnInstall) : that.dateUnInstall != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (objectOnPlaceEntity != null ? !objectOnPlaceEntity.equals(that.objectOnPlaceEntity) : that.objectOnPlaceEntity != null)
            return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (typeResourceEntity != null ? !typeResourceEntity.equals(that.typeResourceEntity) : that.typeResourceEntity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(capacity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (typeResourceEntity != null ? typeResourceEntity.hashCode() : 0);
        result = 31 * result + (actInstallCountEntities != null ? actInstallCountEntities.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (consumer != null ? consumer.hashCode() : 0);
        result = 31 * result + (objectOnPlaceEntity != null ? objectOnPlaceEntity.hashCode() : 0);
        result = 31 * result + (dateInstall != null ? dateInstall.hashCode() : 0);
        result = 31 * result + (dateUnInstall != null ? dateUnInstall.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (contractEntity != null ? contractEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (source == true) {
            return typeResourceEntity.getName() +
                    " истч., мощн. - " + capacity + " " + description + " " + contractEntity;
        } else {
            return typeResourceEntity.getName() +
                    " потрб., мощн. - " + capacity + " " + description + " " + contractEntity;
        }
    }

    @Override
    public int compareTo(Object o) {
        CapacitySourceObjectEntity capacitySourceObjectEntity = (CapacitySourceObjectEntity) o;

        return this.getObjectOnPlaceEntity().getName().compareTo(capacitySourceObjectEntity.getObjectOnPlaceEntity().getName());
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "capacitySourceObjectEntity", cascade = CascadeType.ALL)
    public Set<ActInstallCountEntity> getActInstallCountEntities() {
        return actInstallCountEntities;
    }

    public void setActInstallCountEntities(Set<ActInstallCountEntity> actInstallCountEntities) {
        this.actInstallCountEntities = actInstallCountEntities;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "capacitySourceObjectEntity", cascade = CascadeType.ALL)
    public Set<JournalOtherMethodEntity> getJournalOtherMethodEntities() {
        return journalOtherMethodEntities;
    }

    public void setJournalOtherMethodEntities(Set<JournalOtherMethodEntity> journalOtherMethodEntities) {
        this.journalOtherMethodEntities = journalOtherMethodEntities;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "capacitySourceObjectEntity", cascade = CascadeType.ALL)
    public Set<BalanceResourceEntity> getBalanceResourceEntity() {
        return balanceResourceEntity;
    }

    public void setBalanceResourceEntity(Set<BalanceResourceEntity> balanceResourceEntity) {
        this.balanceResourceEntity = balanceResourceEntity;
    }
}
