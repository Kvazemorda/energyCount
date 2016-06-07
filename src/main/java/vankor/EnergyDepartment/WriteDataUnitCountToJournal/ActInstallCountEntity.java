package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "ACTINSTALLCOUNT", schema = "PUBLIC", catalog = "UE_DB")
public class ActInstallCountEntity implements Comparable, Serializable{
    private int id;
    private Date dateInstall;
    private Date dateNextCalibration;
    private Double firstCountValue;
    private Date dateCreate;
    private UnitCountEntity unitCountByUnitCount;
    private Date dateUnInstall;
    private Double lastCountValue;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private Set<JournalUnitCountEntity> journalUnitCountEntitySet;
    private String Description;

    public ActInstallCountEntity() {
    }

    public ActInstallCountEntity(Date dateInstall, Date dateNextCalibration, Double firstCountValue, Date dateCreate, UnitCountEntity unitCountByUnitCount, CapacitySourceObjectEntity capacitySourceObjectEntity, String description) {
        this.dateInstall = dateInstall;
        this.dateNextCalibration = dateNextCalibration;
        this.firstCountValue = firstCountValue;
        this.dateCreate = dateCreate;
        this.unitCountByUnitCount = unitCountByUnitCount;
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
        Description = description;
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
    @Column(name = "Description", nullable = true, insertable = true, updatable = true)
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEINSTALL", nullable = false, insertable = true, updatable = true)
    public Date getDateInstall() {
        return dateInstall;
    }

    public void setDateInstall(Date dateInstall) {
        this.dateInstall = dateInstall;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATENEXTCALIBRATION", nullable = false, insertable = true, updatable = true)
    public Date getDateNextCalibration() {
        return dateNextCalibration;
    }

    public void setDateNextCalibration(Date dateNextCalibration) {
        this.dateNextCalibration = dateNextCalibration;
    }

    @Basic
    @Column(name = "FIRSTCOUNTVALUE", nullable = false, insertable = true, updatable = true)
    public Double getFirstCountValue() {
        return firstCountValue;
    }

    public void setFirstCountValue(Double firstCountValue) {
        this.firstCountValue = firstCountValue;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATECREATE", nullable = false, insertable = true, updatable = true)
    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActInstallCountEntity)) return false;

        ActInstallCountEntity that = (ActInstallCountEntity) o;

        if (Description != null ? !Description.equals(that.Description) : that.Description != null) return false;
        if (dateCreate != null ? !dateCreate.equals(that.dateCreate) : that.dateCreate != null) return false;
        if (dateInstall != null ? !dateInstall.equals(that.dateInstall) : that.dateInstall != null) return false;
        if (dateNextCalibration != null ? !dateNextCalibration.equals(that.dateNextCalibration) : that.dateNextCalibration != null)
            return false;
        if (dateUnInstall != null ? !dateUnInstall.equals(that.dateUnInstall) : that.dateUnInstall != null)
            return false;
        if (firstCountValue != null ? !firstCountValue.equals(that.firstCountValue) : that.firstCountValue != null)
            return false;
        if (lastCountValue != null ? !lastCountValue.equals(that.lastCountValue) : that.lastCountValue != null)
            return false;
        if (unitCountByUnitCount != null ? !unitCountByUnitCount.equals(that.unitCountByUnitCount) : that.unitCountByUnitCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateInstall != null ? dateInstall.hashCode() : 0;
        result = 31 * result + (dateNextCalibration != null ? dateNextCalibration.hashCode() : 0);
        result = 31 * result + (firstCountValue != null ? firstCountValue.hashCode() : 0);
        result = 31 * result + (dateCreate != null ? dateCreate.hashCode() : 0);
        result = 31 * result + (unitCountByUnitCount != null ? unitCountByUnitCount.hashCode() : 0);
        result = 31 * result + (dateUnInstall != null ? dateUnInstall.hashCode() : 0);
        result = 31 * result + (lastCountValue != null ? lastCountValue.hashCode() : 0);
        result = 31 * result + (Description != null ? Description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "UnitCount", referencedColumnName = "id", nullable = false)
    public UnitCountEntity getUnitCountByUnitCount() {
        return unitCountByUnitCount;
    }

    public void setUnitCountByUnitCount(UnitCountEntity unitCountByUnitCount) {
        this.unitCountByUnitCount = unitCountByUnitCount;
    }

    @Override
    public int compareTo(Object o) {
        ActInstallCountEntity actInstallCountEntity = (ActInstallCountEntity) o;

        return this.dateInstall.compareTo(actInstallCountEntity.dateInstall);
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEUNINSTALL", nullable = true, insertable = true, updatable = true)
    public Date getDateUnInstall() {
        return dateUnInstall;
    }

    public void setDateUnInstall(Date dateUnInstall) {
        this.dateUnInstall = dateUnInstall;
    }
    @Basic
    @Column(name = "LASTCOUNT", nullable = true, insertable = true, updatable = true)
    public Double getLastCountValue() {
        return lastCountValue;
    }

    public void setLastCountValue(Double lastCountValue) {
        this.lastCountValue = lastCountValue;
    }

    @ManyToOne
    @JoinColumn(name = "Capacity", referencedColumnName = "ID", nullable = false)
    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actInstallCountEntity", cascade = CascadeType.ALL)
    public Set<JournalUnitCountEntity> getJournalUnitCountEntitySet() {
        return journalUnitCountEntitySet;
    }

    public void setJournalUnitCountEntitySet(Set<JournalUnitCountEntity> journalUnitCountEntitySet) {
        this.journalUnitCountEntitySet = journalUnitCountEntitySet;
    }
}
