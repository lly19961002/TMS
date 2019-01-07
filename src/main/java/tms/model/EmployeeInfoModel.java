package tms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee_info", schema = "tms")
public class EmployeeInfoModel implements Serializable {
    private static final long serialVersionUID = -5623157455780360051L;
    private String jobNo;
    private String employeeName;
    private String sex;
    private Integer age;
    private String idCard;
    private String phone;
    private String hireDate;
    private String post;

    @Id
    @Column(name = "job_no" ,  nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  String  getJobNo() { return jobNo; }
    public void setJobNo(String jobNo) { this.jobNo =jobNo; }

    @Basic
    @Column(name = "employee_name")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "ID_card")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "hire_date")
    public String getHireDate() { return hireDate; }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate.substring(0,10);
    }

    @Basic
    @Column(name = "post")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        EmployeeInfoModel that = (EmployeeInfoModel) o;
//
//        if (jobNo != that.jobNo) return false;
//        if (employeeName != null ? !employeeName.equals(that.employeeName) : that.employeeName != null) return false;
//        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
//        if (age != null ? !age.equals(that.age) : that.age != null) return false;
//        if (idCard != null ? !idCard.equals(that.idCard) : that.idCard != null) return false;
//        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
//        if (hireDate != null ? !hireDate.equals(that.hireDate) : that.hireDate != null) return false;
//        if (post != null ? !post.equals(that.post) : that.post != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = jobNo;
//        result = 31 * result + (employeeName != null ? employeeName.hashCode() : 0);
//        result = 31 * result + (sex != null ? sex.hashCode() : 0);
//        result = 31 * result + (age != null ? age.hashCode() : 0);
//        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
//        result = 31 * result + (phone != null ? phone.hashCode() : 0);
//        result = 31 * result + (hireDate != null ? hireDate.hashCode() : 0);
//        result = 31 * result + (post != null ? post.hashCode() : 0);
//        return result;
//    }
}
