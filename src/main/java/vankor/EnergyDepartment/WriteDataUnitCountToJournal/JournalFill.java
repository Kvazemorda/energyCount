package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import Forms.Object.CapacityConnectToObject;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journalFill")
public class JournalFill {
    int id;
    Date date;
    double value;
    CapacityConnectToObject capacityConnectToObject;

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
    public CapacityConnectToObject getCapacityConnectToObject() {
        return capacityConnectToObject;
    }

    public void setCapacityConnectToObject(CapacityConnectToObject capacityConnectToObject) {
        this.capacityConnectToObject = capacityConnectToObject;
    }
}
