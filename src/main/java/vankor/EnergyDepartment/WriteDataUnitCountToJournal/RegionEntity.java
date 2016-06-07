package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "REGION", schema = "PUBLIC", catalog = "UE_DB")
public class RegionEntity implements Serializable{
    private int id;
    private String name;
    private Set<PlaceEntity> placesById;

    public RegionEntity() {
    }

    public RegionEntity(String name) {
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
        if (!(o instanceof RegionEntity)) return false;

        RegionEntity that = (RegionEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "regionByRegion", cascade = CascadeType.ALL)
    public Set<PlaceEntity> getPlacesById() {
        return placesById;
    }

    public void setPlacesById(Set<PlaceEntity> placesById) {
        this.placesById = placesById;
    }

    @Override
    public String toString() {
        return name;
    }
}
