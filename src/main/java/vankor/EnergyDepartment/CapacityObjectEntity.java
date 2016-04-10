package vankor.EnergyDepartment;

import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CAPACITYEQUIPMENTEACHRESOURCE", schema = "PUBLIC", catalog = "UE_DB")
public class CapacityObjectEntity implements Comparable {
    private int id;
    private double capacity;
    private TypeResourceEntity typeResourceEntity;
    private Set<ActInstallCountEntity> actInstallCountEntities;
    private Boolean source;
    private Boolean consumer;
    private ObjectOnPlaceEntity objectOnPlaceEntity;
    private Date dateInstall, dateUnInstall;
    private String description;

    public CapacityObjectEntity() {
    }

    public CapacityObjectEntity(double capacity, TypeResourceEntity typeResourceEntity, Boolean source, Boolean consumer, ObjectOnPlaceEntity objectOnPlaceEntity, Date dateInstall, String description) {
        this.capacity = capacity;
        this.typeResourceEntity = typeResourceEntity;
        this.source = source;
        this.consumer = consumer;
        this.objectOnPlaceEntity = objectOnPlaceEntity;
        this.dateInstall = dateInstall;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapacityObjectEntity)) return false;

        CapacityObjectEntity that = (CapacityObjectEntity) o;

        if (Double.compare(that.capacity, capacity) != 0) return false;
        if (id != that.id) return false;
        if (actInstallCountEntities != null ? !actInstallCountEntities.equals(that.actInstallCountEntities) : that.actInstallCountEntities != null)
            return false;
        if (consumer != null ? !consumer.equals(that.consumer) : that.consumer != null) return false;
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
        result = id;
        temp = Double.doubleToLongBits(capacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (typeResourceEntity != null ? typeResourceEntity.hashCode() : 0);
        result = 31 * result + (actInstallCountEntities != null ? actInstallCountEntities.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (consumer != null ? consumer.hashCode() : 0);
        result = 31 * result + (objectOnPlaceEntity != null ? objectOnPlaceEntity.hashCode() : 0);
        result = 31 * result + (dateInstall != null ? dateInstall.hashCode() : 0);
        result = 31 * result + (dateUnInstall != null ? dateUnInstall.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if(source == true){
            return typeResourceEntity.getName() +
                    " источн., мощн. - " + capacity;
        }else{
            return typeResourceEntity.getName() +
                    " потребит., мощн. - " + capacity;
        }
    }

    @Override
    public int compareTo(Object o) {
        CapacityObjectEntity capacityObjectEntity = (CapacityObjectEntity) o;

        return this.getObjectOnPlaceEntity().getName().compareTo(capacityObjectEntity.getObjectOnPlaceEntity().getName());
    }

    @OneToMany(mappedBy = "capacityObjectEntity", cascade = CascadeType.ALL)
    public Set<ActInstallCountEntity> getActInstallCountEntities() {
        return actInstallCountEntities;
    }

    public void setActInstallCountEntities(Set<ActInstallCountEntity> actInstallCountEntities) {
        this.actInstallCountEntities = actInstallCountEntities;
    }
}
