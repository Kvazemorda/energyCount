package vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity;

import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "UNITCOUNT", schema = "PUBLIC", catalog = "UE_DB")
public class UnitCountEntity implements Comparable, Serializable{
    private int id;
    private String number;
    private String model;
    private Set<ActInstallCountEntity> actInstallCountsById;
    private Boolean deletedUnitCount;

    public UnitCountEntity() {
    }

    public UnitCountEntity(String number, String model) {
        this.number = number;
        this.model = model;
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
    @Column(name = "NUMBER", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "MODEL", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "DeletedUnitCount", nullable = true, insertable = true, updatable = true)
    public Boolean getDeletedUnitCount() {
        return deletedUnitCount;
    }
    public void setDeletedUnitCount(Boolean deletedUnitCount) {
        this.deletedUnitCount = deletedUnitCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitCountEntity)) return false;

        UnitCountEntity that = (UnitCountEntity) o;

        if (actInstallCountsById != null ? !actInstallCountsById.equals(that.actInstallCountsById) : that.actInstallCountsById != null)
            return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "unitCountByUnitCount", cascade = CascadeType.ALL)
    public Set<ActInstallCountEntity> getActInstallCountsById() {
        return this.actInstallCountsById;
    }

    public void setActInstallCountsById(Set<ActInstallCountEntity> actInstallCountsById) {
        this.actInstallCountsById = actInstallCountsById;
    }

    @Override
    public int compareTo(Object o) {
        UnitCountEntity unitCountEntity = (UnitCountEntity) o;
        return this.getNumber().compareTo(unitCountEntity.getNumber());
    }

    @Override
    public String toString() {
        return "N " + number +" модель " + model;
    }
}