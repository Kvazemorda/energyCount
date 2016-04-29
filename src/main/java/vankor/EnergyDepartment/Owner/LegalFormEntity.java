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
    public String toString() {
        return name;
    }
}
