package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.CapacitySourceObjectEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOURNALFILL", schema = "PUBLIC", catalog = "UE_DB")
public class JournalFillEntity {
    int id;
    Date date;
    double value;
    CapacitySourceObjectEntity capacitySourceObjectEntity;

    public JournalFillEntity() {
    }

    public JournalFillEntity(Date date, double value, CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.date = date;
        this.value = value;
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    @JoinColumn(name = "capacity", referencedColumnName = "id")
    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }
}
