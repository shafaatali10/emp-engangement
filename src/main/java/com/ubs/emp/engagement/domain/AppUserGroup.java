package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppUserGroup.
 */
@Entity
@Table(name = "app_user_group")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "email")
    private String email;

    @Column(name = "admin_user")
    private String adminUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUserGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public AppUserGroup groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUserGroup email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminUser() {
        return this.adminUser;
    }

    public AppUserGroup adminUser(String adminUser) {
        this.setAdminUser(adminUser);
        return this;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUserGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", email='" + getEmail() + "'" +
            ", adminUser='" + getAdminUser() + "'" +
            "}";
    }
}
