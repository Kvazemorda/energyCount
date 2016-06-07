package vankor.EnergyDepartment.WriteDataUnitCountToJournal;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS", schema = "PUBLIC", catalog = "UE_DB")
public class UsersEntity implements Serializable{
    private int id;
    private String name;
    private String mail;
    private PlaceEntity placeEntity;
    private String userName;

    public UsersEntity() {
    }

    public UsersEntity(String name, String mail, PlaceEntity placeEntity, String userName) {
        this.name = name;
        this.mail = mail;
        this.placeEntity = placeEntity;
        this.userName = userName;
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
    @Column(name = "NAME", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "MAIL", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "usernamepc", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersEntity)) return false;

        UsersEntity that = (UsersEntity) o;

        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (placeEntity != null ? !placeEntity.equals(that.placeEntity) : that.placeEntity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (placeEntity != null ? placeEntity.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "PLACE", referencedColumnName = "ID", nullable = false)
    public PlaceEntity getPlaceEntity() {
        return placeEntity;
    }

    public void setPlaceEntity(PlaceEntity placeEntity) {
        this.placeEntity = placeEntity;
    }
}