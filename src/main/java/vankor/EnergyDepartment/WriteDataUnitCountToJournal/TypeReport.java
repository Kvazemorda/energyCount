package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import java.util.Set;

public class TypeReport {
    private int id;
    private String nameReport;
    private Set<TypeResourceEntity> typeResourceEntitySet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameReport() {
        return nameReport;
    }

    public void setNameReport(String nameReport) {
        this.nameReport = nameReport;
    }

    public Set<TypeResourceEntity> getTypeResourceEntitySet() {
        return typeResourceEntitySet;
    }

    public void setTypeResourceEntitySet(Set<TypeResourceEntity> typeResourceEntitySet) {
        this.typeResourceEntitySet = typeResourceEntitySet;
    }
}
