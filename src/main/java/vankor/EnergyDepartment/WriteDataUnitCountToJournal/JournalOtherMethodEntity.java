package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.ObjectOnPlaceEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOURNALOTHERMETHOD", schema = "PUBLIC", catalog = "UE_DB")
public class JournalOtherMethodEntity {
    private int id;
    private Date date;
    private double timeWork, value;
    private ObjectOnPlaceEntity objectOnPlaceEntity;



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

    @Basic
    @Column(name = "value", nullable = false, insertable = true, updatable = true)
    public void setTimeWork(double timeWork) {
        this.timeWork = timeWork;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JournalOtherMethodEntity that = (JournalOtherMethodEntity) o;

        if (Double.compare(that.timeWork, timeWork) != 0) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        long temp;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        temp = Double.doubleToLongBits(timeWork);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ObjectOnPlace", referencedColumnName = "ID", nullable = false)

    public ObjectOnPlaceEntity getObjectOnPlaceEntity() {
        return objectOnPlaceEntity;
    }

    public void setObjectOnPlaceEntity(ObjectOnPlaceEntity objectOnPlaceEntity) {
        this.objectOnPlaceEntity = objectOnPlaceEntity;
    }
}
