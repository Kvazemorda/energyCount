package vankor.EnergyDepartment;

import vankor.EnergyDepartment.WriteDataUnitCountToJournal.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "OBJECTONPLACE", schema = "PUBLIC", catalog = "UE_DB")
public class ObjectOnPlaceEntity {
    private int id;
    private String name;
    private String numbOnMap;
    private Date dateCreate;
    private PlaceEntity placeByPlace;
    private boolean deleteObject;
    private Set<CapacitySourceObjectEntity> capacityObjectEntities;

    public ObjectOnPlaceEntity() {
    }

    public ObjectOnPlaceEntity(String name, String numbOnMap, PlaceEntity placeByPlace, Date dateCreate) {
        this.name = name;
        this.numbOnMap = numbOnMap;
        this.placeByPlace = placeByPlace;
        this.dateCreate = dateCreate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NUMBONMAP", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNumbOnMap() {
        return numbOnMap;
    }

    public void setNumbOnMap(String numbOnMap) {
        this.numbOnMap = numbOnMap;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATECREATE", nullable = false, insertable = true, updatable = true)
    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Basic
    @Column(name = "DELETEOBJECT", nullable = true, insertable = true, updatable = true)
    public boolean isDeleteObject() {
        return deleteObject;
    }
    public void setDeleteObject(boolean deleteObject) {
        this.deleteObject = deleteObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectOnPlaceEntity)) return false;

        ObjectOnPlaceEntity that = (ObjectOnPlaceEntity) o;

        if (id != that.id) return false;
        if (dateCreate != null ? !dateCreate.equals(that.dateCreate) : that.dateCreate != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (numbOnMap != null ? !numbOnMap.equals(that.numbOnMap) : that.numbOnMap != null) return false;
        if (placeByPlace != null ? !placeByPlace.equals(that.placeByPlace) : that.placeByPlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (numbOnMap != null ? numbOnMap.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        result = 31 * result + (placeByPlace != null ? placeByPlace.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "PLACE", referencedColumnName = "ID", nullable = false)
    public PlaceEntity getPlaceByPlace() {
        return placeByPlace;
    }

    public void setPlaceByPlace(PlaceEntity placeByPlace) {
        this.placeByPlace = placeByPlace;
    }

    @Override
    public String toString() {
        return name;
    }

    @OneToMany(mappedBy = "objectOnPlaceEntity", cascade = CascadeType.ALL)
    public Set<CapacitySourceObjectEntity> getCapacityObjectEntities() {
        return capacityObjectEntities;
    }

    public void setCapacityObjectEntities(Set<CapacitySourceObjectEntity> capacityObjectEntities) {
        this.capacityObjectEntities = capacityObjectEntities;
    }
}