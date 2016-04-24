package vankor.EnergyDepartment.Owner;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "legalForm")
public class LegalFormEntity {
    int id;
    String name, fullName;
    Date start, end;
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

    @Temporal(TemporalType.DATE)
    @Column(name ="DateStart", nullable = false, insertable = true, updatable = true)
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Temporal(TemporalType.DATE)
    @Column(name ="DateEnd", nullable = false, insertable = true, updatable = true)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @OneToMany(mappedBy = "legalFormEntity", cascade = CascadeType.ALL)
    public Set<OwnerEntity> getOwnerEntitySet() {
        return ownerEntitySet;
    }

    public void setOwnerEntitySet(Set<OwnerEntity> ownerEntitySet) {
        this.ownerEntitySet = ownerEntitySet;
    }
}
