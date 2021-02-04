package com.redhat.bz;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Entity
@Table(
    name = "DISTRIBUTION_ITERATIONS"
)
@org.hibernate.annotations.BatchSize(size = 20)
public class DistributionIteration implements Serializable {

    @Id
    @GeneratedValue(generator = "DistributionIteration_UUID")
    @GenericGenerator(
        name = "DistributionIteration_UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "uuid-char")
    @Column(name = "DIST_ITERATION_ID", length = 36, nullable = false, updatable = false)
    @JsonbProperty("id")
    private UUID id;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    @JsonbProperty("version")
    private Integer version;

    @Column(name = "CURRENT", nullable = false)
    @JsonbProperty("current")
    private Boolean current;

    @JsonbProperty("distributorTransactions")
    @OneToMany(mappedBy = "distributionIteration", fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REMOVE, REFRESH, DETACH}, orphanRemoval = true)
    private List<DistributorTransaction> transactions = new ArrayList<DistributorTransaction>();

    public void addDistributorTransaction(DistributorTransaction distributorTransaction) {
        transactions.add(distributorTransaction);
        distributorTransaction.setDistributionIteration(this);
    }

    public void removeDistributorTransaction(DistributorTransaction distributorTransaction) {
        transactions.remove(distributorTransaction);
        distributorTransaction.setDistributionIteration(null);
    }

    @JsonbTransient
    @ManyToOne (targetEntity = DistributorAccount.class)
    @JoinColumn(name = "DIST_ACCOUNT_ID", nullable = false, updatable = false, insertable = true, foreignKey = @ForeignKey(name = "FK_DIST_ITERATION_ACCOUNT_ID"))
    private DistributorAccount distributorAccount;

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



    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public List<DistributorTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DistributorTransaction> transactions) {
        this.transactions = transactions;
    }

    public DistributorAccount getDistributorAccount() {
        return distributorAccount;
    }

    public void setDistributorAccount(DistributorAccount distributorAccount) {
        this.distributorAccount = distributorAccount;
    }

    @Override
    public String toString() {
        return "DistributionIteration{" +
            "id=" + id +
            ", version=" + version +
            ", current=" + current +
            ", transactions=" + transactions +
            ", distributorAccount=" + distributorAccount +
            '}';
    }
}
