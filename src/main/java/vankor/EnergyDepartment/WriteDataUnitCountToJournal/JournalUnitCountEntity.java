package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOURNALUNITCOUNT", schema = "PUBLIC", catalog = "UE_DB")
public class JournalUnitCountEntity implements Comparable {
    private int id;
    private double countUnit, value;
    private Date dateCount;
    private ActInstallCountEntity actInstallCountEntity;

    public JournalUnitCountEntity() {
    }

    public JournalUnitCountEntity(double count, Date dateCount, ActInstallCountEntity actInstallCountEntity, double value) {
        this.countUnit = count;
        this.dateCount = dateCount;
        this.actInstallCountEntity = actInstallCountEntity;
        this.value = value;
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
    @Column(name = "COUNT", nullable = false, insertable = true, updatable = true)
    public double getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(double countUnit) {
        this.countUnit = countUnit;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATECOUNT", nullable = false, insertable = true, updatable = true)
    public Date getDateCount() {
        return dateCount;
    }

    public void setDateCount(Date dateCount) {
        this.dateCount = dateCount;
    }

    @Basic
    @Column(name = "value", nullable = false, insertable = true, updatable = true)
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalUnitCountEntity)) return false;
        JournalUnitCountEntity that = (JournalUnitCountEntity) o;
        if (actInstallCountEntity != null ? !actInstallCountEntity.equals(that.actInstallCountEntity) : that.actInstallCountEntity != null)
            return false;
        if (dateCount != null ? !dateCount.equals(that.dateCount) : that.dateCount != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dateCount != null ? dateCount.hashCode() : 0;
        result = 31 * result + (actInstallCountEntity != null ? actInstallCountEntity.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        JournalUnitCountEntity journalUnitCountEntity = (JournalUnitCountEntity) o;
        return this.dateCount.compareTo(journalUnitCountEntity.dateCount);
    }

    @ManyToOne
    @JoinColumn(name = "ActInstallCount", referencedColumnName = "id", nullable = false)
    public ActInstallCountEntity getActInstallCountEntity() {
        return actInstallCountEntity;
    }

    public void setActInstallCountEntity(ActInstallCountEntity actInstallCountEntity) {
        this.actInstallCountEntity = actInstallCountEntity;
    }
}
