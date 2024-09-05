package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppSubjectType.
 */
@Entity
@Table(name = "app_subject_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppSubjectType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppSubjectType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public AppSubjectType subjectCode(String subjectCode) {
        this.setSubjectCode(subjectCode);
        return this;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getDescription() {
        return this.description;
    }

    public AppSubjectType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppSubjectType)) {
            return false;
        }
        return getId() != null && getId().equals(((AppSubjectType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppSubjectType{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
