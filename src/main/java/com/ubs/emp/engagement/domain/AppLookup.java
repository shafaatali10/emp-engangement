package com.ubs.emp.engagement.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AppLookup.
 */
@Entity
@Table(name = "app_lookup")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "lookup_code")
    private String lookupCode;

    @Column(name = "display_value")
    private String displayValue;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "category")
    private String category;

    @Column(name = "dependent_code")
    private String dependentCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppLookup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLookupCode() {
        return this.lookupCode;
    }

    public AppLookup lookupCode(String lookupCode) {
        this.setLookupCode(lookupCode);
        return this;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }

    public AppLookup displayValue(String displayValue) {
        this.setDisplayValue(displayValue);
        return this;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public AppLookup sequence(Integer sequence) {
        this.setSequence(sequence);
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCategory() {
        return this.category;
    }

    public AppLookup category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDependentCode() {
        return this.dependentCode;
    }

    public AppLookup dependentCode(String dependentCode) {
        this.setDependentCode(dependentCode);
        return this;
    }

    public void setDependentCode(String dependentCode) {
        this.dependentCode = dependentCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppLookup)) {
            return false;
        }
        return getId() != null && getId().equals(((AppLookup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLookup{" +
            "id=" + getId() +
            ", lookupCode='" + getLookupCode() + "'" +
            ", displayValue='" + getDisplayValue() + "'" +
            ", sequence=" + getSequence() +
            ", category='" + getCategory() + "'" +
            ", dependentCode='" + getDependentCode() + "'" +
            "}";
    }
}
