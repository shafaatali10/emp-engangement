package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppTopicLookup.
 */
@Entity
@Table(name = "app_topic_lookup")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppTopicLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "topic_code")
    private String topicCode;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "target_group")
    private String targetGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppTopicLookup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicCode() {
        return this.topicCode;
    }

    public AppTopicLookup topicCode(String topicCode) {
        this.setTopicCode(topicCode);
        return this;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public AppTopicLookup topicName(String topicName) {
        this.setTopicName(topicName);
        return this;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTargetGroup() {
        return this.targetGroup;
    }

    public AppTopicLookup targetGroup(String targetGroup) {
        this.setTargetGroup(targetGroup);
        return this;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppTopicLookup)) {
            return false;
        }
        return getId() != null && getId().equals(((AppTopicLookup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppTopicLookup{" +
            "id=" + getId() +
            ", topicCode='" + getTopicCode() + "'" +
            ", topicName='" + getTopicName() + "'" +
            ", targetGroup='" + getTargetGroup() + "'" +
            "}";
    }
}
