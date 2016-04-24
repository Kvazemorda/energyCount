package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.CapacitySourceObjectEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOURNALOTHERMETHOD", schema = "PUBLIC", catalog = "UE_DB")
public class JournalOtherMethodEntity {
    private int id;
    private Date date;
    private double timeWork, value;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;

    public JournalOtherMethodEntity() {
    }

    public JournalOtherMethodEntity(Date date, double timeWork, double value, CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.date = date;
        this.timeWork = timeWork;
        this.value = value;
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "TIMEWORK", nullable = false, insertable = true, updatable = true)
    public double getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(double timeWork) {
        this.timeWork = timeWork;
    }

    @Basic
    @Column(name = "value", nullable = false, insertable = true, updatable = true)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "Capacity", referencedColumnName = "ID", nullable = false)
    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalOtherMethodEntity)) return false;

        JournalOtherMethodEntity that = (JournalOtherMethodEntity) o;

        if (capacitySourceObjectEntity != null ? !capacitySourceObjectEntity.equals(that.capacitySourceObjectEntity) : that.capacitySourceObjectEntity != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (capacitySourceObjectEntity != null ? capacitySourceObjectEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JournalOtherMethodEntity{" +
                "id=" + id +
                ", date=" + date +
                ", timeWork=" + timeWork +
                ", value=" + value +
                '}';
    }
}
