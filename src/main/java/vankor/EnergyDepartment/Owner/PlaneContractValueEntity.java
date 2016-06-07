package vankor.EnergyDepartment.Owner;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "planecontractvalue")
public class PlaneContractValueEntity implements Serializable {
    int id;
    double value;
    Date dateStart, dateEnd;
    ContractEntity contractEntity;

    public PlaneContractValueEntity() {
    }

    public PlaneContractValueEntity(double value, Date dateStart, Date dateEnd) {
        this.value = value;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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
    @Column(name = "value", nullable = false, insertable = true, updatable = true)
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "Date", nullable = false, insertable = true, updatable = true)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DateEnd", nullable = false, insertable = true, updatable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @ManyToOne
    @JoinColumn(name = "contract", referencedColumnName = "id")
    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaneContractValueEntity)) return false;

        PlaneContractValueEntity that = (PlaneContractValueEntity) o;

        if (Double.compare(that.value, value) != 0) return false;
        if (contractEntity != null ? !contractEntity.equals(that.contractEntity) : that.contractEntity != null)
            return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (dateStart != null ? !dateStart.equals(that.dateStart) : that.dateStart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (contractEntity != null ? contractEntity.hashCode() : 0);
        return result;
    }
}
