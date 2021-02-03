package com.redhat.bz;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(
    name = "DISTRI_TRANSACTIONS"
)
@org.hibernate.annotations.BatchSize(size = 20)
public class DistributorTransaction implements Serializable {

    @Id
    @GeneratedValue(generator = "DistributorTransaction_UUID")
    @GenericGenerator(
        name = "DistributorTransaction_UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "uuid-char")
    @Column(name = "DISTRI_TX_ID", length = 36, nullable = false, updatable = false)
    private UUID id;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;

    @JsonbTransient
    @ManyToOne(targetEntity = DistributionIteration.class)
    @JoinColumn(name = "DIST_ITERATION_ID", updatable = false, insertable = false, foreignKey = @ForeignKey(name = "FK_DIST_TRANS_ITERATION_ID"))
    private DistributionIteration distributionIteration;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public DistributionIteration getDistributionIteration() {
        return distributionIteration;
    }

    public void setDistributionIteration(DistributionIteration distributionIteration) {
        this.distributionIteration = distributionIteration;
    }

    @Override
    public String toString() {
        return "DistributorTransaction{" +
            "id=" + id +
            ", version=" + version +
            ", distributionIteration=" + distributionIteration +
            '}';
    }
}
