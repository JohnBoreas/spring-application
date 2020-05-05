package com.springweb.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author boreas
 * @create 2020-02-03 下午 3:00
 */
@Entity// @Entity(name="EntityName") 必须，用来标注一个数据库对应的实体，数据库中创建的表名默认和类名一致。其中，name 为可选，对应数据库中一个表，使用此注解标记 Pojo 是一个 JPA 实体
@Table(name = "tb_user")// 用来标注一个数据库对应的实体，数据库中创建的表名默认和类名一致。
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id// 必须，@Id 定义了映射到数据库表的主键的属性，一个实体只能有一个属性被映射为主键。
    @GeneratedValue(strategy = GenerationType.IDENTITY)// @GeneratedValue可选,Hibernate提供的主键生成策略注解，strategy: 表示主键生成策略，有 AUTO、INDENTITY、SEQUENCE 和 TABLE 4 种，分别表示让 ORM 框架自动选择，generator: 表示主键生成器的名称
    private Long id;
    @Column(name = "user_id", nullable = false, unique = true)// @Column 描述了数据库表中该字段的详细定义
    private Long userId;
    @Column(name = "user_name", nullable = false, unique = true, length = 255)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column()
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column
    private Integer age;
    @Column
    private Date birthday;

    public User() {
    }
    public User(Long userId, String userName, String password, String email, String phone) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
