package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppSubject.
 */
@Entity
@Table(name = "app_subject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "topic_code")
    private String topicCode;

    @Column(name = "status")
    private String status;

    @Column(name = "is_approval_required")
    private Boolean isApprovalRequired;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "details_json")
    private String detailsJson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppSubject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public AppSubject subjectCode(String subjectCode) {
        this.setSubjectCode(subjectCode);
        return this;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTopicCode() {
        return this.topicCode;
    }

    public AppSubject topicCode(String topicCode) {
        this.setTopicCode(topicCode);
        return this;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getStatus() {
        return this.status;
    }

    public AppSubject status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsApprovalRequired() {
        return this.isApprovalRequired;
    }

    public AppSubject isApprovalRequired(Boolean isApprovalRequired) {
        this.setIsApprovalRequired(isApprovalRequired);
        return this;
    }

    public void setIsApprovalRequired(Boolean isApprovalRequired) {
        this.isApprovalRequired = isApprovalRequired;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public AppSubject isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getDetailsJson() {
        return this.detailsJson;
    }

    public AppSubject detailsJson(String detailsJson) {
        this.setDetailsJson(detailsJson);
        return this;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppSubject)) {
            return false;
        }
        return getId() != null && getId().equals(((AppSubject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppSubject{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", topicCode='" + getTopicCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", isApprovalRequired='" + getIsApprovalRequired() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", detailsJson='" + getDetailsJson() + "'" +
            "}";
    }
}
