package vankor.EnergyDepartment.Owner;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Owner")
public class OwnerEntity implements Serializable{
    int id;
    String name, inn, kpp, fullName;
    Set<ContractEntity> contractEntitySet;
    LegalFormEntity legalFormEntity;


    public OwnerEntity() {}
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
    @Column(name ="inn", nullable = false, insertable = true, updatable = true)
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }
    @Basic
    @Column(name ="kpp", nullable = false, insertable = true, updatable = true)
    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @Basic
    @Column(name ="fullname", nullable = false, insertable = true, updatable = true)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @OneToMany(mappedBy = "ownerEntity", cascade = CascadeType.ALL)
    public Set<ContractEntity> getContractEntitySet() {
        return contractEntitySet;
    }
    public void setContractEntitySet(Set<ContractEntity> contractEntitySet) {
        this.contractEntitySet = contractEntitySet;
    }

    @ManyToOne
    @JoinColumn(name = "legalform", referencedColumnName = "id")
    public LegalFormEntity getLegalFormEntity() {
        return legalFormEntity;
    }

    public void setLegalFormEntity(LegalFormEntity legalFormEntity) {
        this.legalFormEntity = legalFormEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerEntity)) return false;

        OwnerEntity that = (OwnerEntity) o;

        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
        if (kpp != null ? !kpp.equals(that.kpp) : that.kpp != null) return false;
        if (legalFormEntity != null ? !legalFormEntity.equals(that.legalFormEntity) : that.legalFormEntity != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        result = 31 * result + (kpp != null ? kpp.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (legalFormEntity != null ? legalFormEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
