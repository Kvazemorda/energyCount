package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.ObjectOnPlaceEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "PLACE", schema = "PUBLIC", catalog = "UE_DB")
public class PlaceEntity implements Serializable {
    private int id;
    private String name;
    private Set<ObjectOnPlaceEntity> objectOnPlacesById = new TreeSet<ObjectOnPlaceEntity>();
    private RegionEntity regionByRegion;
    private Set<UsersEntity> usersEntity;

    public PlaceEntity() {
    }

    public PlaceEntity(RegionEntity regionByRegion, String name) {
        this.regionByRegion = regionByRegion;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceEntity)) return false;

        PlaceEntity that = (PlaceEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (objectOnPlacesById != null ? !objectOnPlacesById.equals(that.objectOnPlacesById) : that.objectOnPlacesById != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (regionByRegion != null ? regionByRegion.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "placeByPlace", cascade = CascadeType.ALL)
    public Set<ObjectOnPlaceEntity> getObjectOnPlacesById() {
        return objectOnPlacesById;
    }

    public void setObjectOnPlacesById(Set<ObjectOnPlaceEntity> objectOnPlacesById) {
        this.objectOnPlacesById = objectOnPlacesById;
    }

    @ManyToOne
    @JoinColumn(name = "REGION", referencedColumnName = "ID", nullable = false)
    public RegionEntity getRegionByRegion() {
        return regionByRegion;
    }

    public void setRegionByRegion(RegionEntity regionByRegion) {
        this.regionByRegion = regionByRegion;
    }

    @Override
    public String toString() {
        return name;
    }

    @OneToMany(mappedBy = "placeEntity", cascade = CascadeType.ALL)
    public Set<UsersEntity> getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(Set<UsersEntity> usersEntity) {
        this.usersEntity = usersEntity;
    }
}