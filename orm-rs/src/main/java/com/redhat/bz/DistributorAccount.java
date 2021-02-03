package com.redhat.bz;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(
    name = "DISTRI_ACCOUNTS"
)
@org.hibernate.annotations.BatchSize(size = 20)
public class DistributorAccount implements Serializable {

    @Id
    @GeneratedValue(generator = "DistributorAccount_UUID")
    @GenericGenerator(
        name = "DistributorAccount_UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "uuid-char")
    @Column(name = "DIST_ACCOUNT_ID", length = 36, nullable = false, updatable = false)
    @JsonbProperty("id")
    private UUID id;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    @JsonbProperty("version")
    private Integer version;


    @OneToOne(mappedBy = "distributorAccount", cascade = CascadeType.ALL)
    private Partner partner;

    public void setPartner(Partner partner) {
        this.partner = partner;
        partner.setDistributorAccount(this);
    }

    @JsonbProperty("distributionIterations")
    @OneToMany(mappedBy = "distributorAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistributionIteration> distributionIterations = new ArrayList<>();

    public void addDistributionIteration(DistributionIteration distributionIteration) {
        distributionIterations.add(distributionIteration);
        distributionIteration.setDistributorAccount(this);
    }

    public void removeDistributionIteration(DistributionIteration distributionIteration) {
        distributionIterations.remove(distributionIteration);
        distributionIteration.setDistributorAccount(null);
    }

    @JsonbTransient
    public Optional<DistributionIteration> getCurrentDistributionIteration() {
        return this.distributionIterations.stream()
            .filter(DistributionIteration::getCurrent)
            .findFirst();
    }


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

    public Partner getPartner() {
        return partner;
    }

    public List<DistributionIteration> getDistributionIterations() {
        return distributionIterations;
    }

    public void setDistributionIterations(List<DistributionIteration> distributionIterations) {
        this.distributionIterations = distributionIterations;
    }

    @Override
    public String toString() {
        return "DistributorAccount{" +
            "id=" + id +
            ", version=" + version +
            ", partner=" + partner +
            ", distributionIterations=" + distributionIterations +
            '}';
    }
}
