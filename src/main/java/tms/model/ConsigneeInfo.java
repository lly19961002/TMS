package tms.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author: lly
 * @Date: 2018-12-19 17:42
 */
@Entity
@Table(name = "consignee_info", schema = "tms", catalog = "")
public class ConsigneeInfo {
    private int id;
    private String consignee;
    private String consigneeContact;
    private String consigneePhone;
    private String consigneeAddress;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "consignee")
    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    @Basic
    @Column(name = "consignee_contact")
    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    @Basic
    @Column(name = "consignee_phone")
    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    @Basic
    @Column(name = "consignee_address")
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsigneeInfo that = (ConsigneeInfo) o;
        return id == that.id &&
                Objects.equals(consignee, that.consignee) &&
                Objects.equals(consigneeContact, that.consigneeContact) &&
                Objects.equals(consigneePhone, that.consigneePhone) &&
                Objects.equals(consigneeAddress, that.consigneeAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, consignee, consigneeContact, consigneePhone, consigneeAddress);
    }
}
