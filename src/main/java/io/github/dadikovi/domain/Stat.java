package io.github.dadikovi.domain;


import javax.persistence.*;

import java.io.Serializable;

import io.github.dadikovi.domain.enumeration.StatObject;

/**
 * A Stat.
 */
@Entity
@Table(name = "stat")
public class Stat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private StatObject objectType;

    @Column(name = "object_name")
    private String objectName;

    @Column(name = "stat_value")
    private String statValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatObject getObjectType() {
        return objectType;
    }

    public Stat objectType(StatObject objectType) {
        this.objectType = objectType;
        return this;
    }

    public void setObjectType(StatObject objectType) {
        this.objectType = objectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public Stat objectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getStatValue() {
        return statValue;
    }

    public Stat statValue(String statValue) {
        this.statValue = statValue;
        return this;
    }

    public void setStatValue(String statValue) {
        this.statValue = statValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stat)) {
            return false;
        }
        return id != null && id.equals(((Stat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stat{" +
            "id=" + getId() +
            ", objectType='" + getObjectType() + "'" +
            ", objectName='" + getObjectName() + "'" +
            ", statValue='" + getStatValue() + "'" +
            "}";
    }
}
