package vankor.EnergyDepartment.Owner;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "legalForm")
public class LegalFormEntity {
    int id;
    String name, fullName;
    Set<OwnerEntity> ownerEntitySet;

    public LegalFormEntity() {
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
    @Column(name ="name", nullable = false, insertable = true, updatable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name ="fullName", nullable = false, insertable = true, updatable = true)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @OneToMany(mappedBy = "legalFormEntity", cascade = CascadeType.ALL)
    public Set<OwnerEntity> getOwnerEntitySet() {
        return ownerEntitySet;
    }

    public void setOwnerEntitySet(Set<OwnerEntity> ownerEntitySet) {
        this.ownerEntitySet = ownerEntitySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LegalFormEntity)) return false;

        LegalFormEntity that = (LegalFormEntity) o;

        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
