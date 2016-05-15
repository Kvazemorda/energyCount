package vankor.EnergyDepartment.Owner;

import vankor.EnergyDepartment.CapacitySourceObjectEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CONTRACT1", schema = "PUBLIC", catalog = "UE_DB")
public class ContractEntity {
    private int id;
    private String contract;
    private OwnerEntity ownerEntity;
    private Set<CapacitySourceObjectEntity> capacitySourceObjectEntities;
    private Set<PlaneContractValueEntity> planeContractValueEntitySet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contractNumber", nullable = false, insertable = true, updatable = true)
    public String getContract() {
        return contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }

    @ManyToOne
    @JoinColumn(name= "Owner", referencedColumnName = "id", nullable = false)
    public OwnerEntity getOwnerEntity() {
        return ownerEntity;
    }
    public void setOwnerEntity(OwnerEntity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    @OneToMany(mappedBy = "contractEntity", cascade = CascadeType.ALL)
    public Set<CapacitySourceObjectEntity> getCapacitySourceObjectEntities() {
        return capacitySourceObjectEntities;
    }
    public void setCapacitySourceObjectEntities(Set<CapacitySourceObjectEntity> capacitySourceObjectEntities) {
        this.capacitySourceObjectEntities = capacitySourceObjectEntities;
    }
    @OneToMany(mappedBy = "contractEntity", cascade = CascadeType.ALL)
    public Set<PlaneContractValueEntity> getPlaneContractValueEntitySet() {
        return planeContractValueEntitySet;
    }

    public void setPlaneContractValueEntitySet(Set<PlaneContractValueEntity> planeContractValueEntitySet) {
        this.planeContractValueEntitySet = planeContractValueEntitySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContractEntity)) return false;
        ContractEntity that = (ContractEntity) o;
        if (contract != null ? !contract.equals(that.contract) : that.contract != null) return false;
        if (ownerEntity != null ? !ownerEntity.equals(that.ownerEntity) : that.ownerEntity != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = contract != null ? contract.hashCode() : 0;
        result = 31 * result + (ownerEntity != null ? ownerEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ownerEntity  + " " + contract;
    }
}
