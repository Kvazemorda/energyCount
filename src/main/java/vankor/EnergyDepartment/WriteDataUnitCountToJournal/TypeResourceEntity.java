package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.CapacitySourceObjectEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TYPERESOURCE", schema = "PUBLIC", catalog = "UE_DB")
public class TypeResourceEntity implements Comparable{
    private int id;
    private String name;
    private Set<CapacitySourceObjectEntity> CEEREntities;

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
        if (!(o instanceof TypeResourceEntity)) return false;

        TypeResourceEntity that = (TypeResourceEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    @OneToMany(mappedBy = "typeResourceEntity", cascade = CascadeType.ALL)
    public Set<CapacitySourceObjectEntity> getCEEREntities() {
        return CEEREntities;
    }

    public void setCEEREntities(Set<CapacitySourceObjectEntity> CEEREntities) {
        this.CEEREntities = CEEREntities;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        TypeResourceEntity typeResourceEntity = (TypeResourceEntity) o;
        if(this.getId() > typeResourceEntity.getId()){
            return 1;
        }else if(this.getId() < typeResourceEntity.getId()){
            return -1;
        }else {
            return 0;
        }
    }
}
