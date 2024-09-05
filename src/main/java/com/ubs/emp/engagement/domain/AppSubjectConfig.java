package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppSubjectConfig.
 */
@Entity
@Table(name = "app_subject_config")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppSubjectConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "version")
    private Integer version;

    @Column(name = "payload")
    private String payload;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppSubjectConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public AppSubjectConfig subjectCode(String subjectCode) {
        this.setSubjectCode(subjectCode);
        return this;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getVersion() {
        return this.version;
    }

    public AppSubjectConfig version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPayload() {
        return this.payload;
    }

    public AppSubjectConfig payload(String payload) {
        this.setPayload(payload);
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppSubjectConfig)) {
            return false;
        }
        return getId() != null && getId().equals(((AppSubjectConfig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppSubjectConfig{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", version=" + getVersion() +
            ", payload='" + getPayload() + "'" +
            "}";
    }
}
