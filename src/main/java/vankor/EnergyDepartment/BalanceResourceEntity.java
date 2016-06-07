package vankor.EnergyDepartment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BALANCERESOURCE", schema = "PUBLIC", catalog = "UE_DB")
public class BalanceResourceEntity implements Serializable{
    private int id;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private Date date;
    private Double balance;

    public BalanceResourceEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "Capacity", referencedColumnName = "ID", nullable = false)
    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
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
    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceResourceEntity)) return false;

        BalanceResourceEntity that = (BalanceResourceEntity) o;

        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (capacitySourceObjectEntity != null ? !capacitySourceObjectEntity.equals(that.capacitySourceObjectEntity) : that.capacitySourceObjectEntity != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = capacitySourceObjectEntity != null ? capacitySourceObjectEntity.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
