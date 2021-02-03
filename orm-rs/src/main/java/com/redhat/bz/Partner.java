package com.redhat.bz;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.redhat.bz.Configurations.JSON_DATETIME_FORMAT;
import static javax.persistence.CascadeType.*;


@Entity
@Table(
    name = "PARTNERS"
)
@org.hibernate.annotations.BatchSize(size = 20)
public class Partner {
    @Id
    @GeneratedValue(generator = "Partner_UUID")
    @GenericGenerator(
        name = "Partner_UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "uuid-char")
    @Column(name = "PARTNER_ID", length = 36, nullable = false, updatable = false)
    @JsonbProperty("id")
    private UUID id;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    @JsonbProperty("version")
    private Integer version;

    @Size(min = 6, max = 6, message = "{Partner.pid.size.validation.message}")
    @Column(name = "PID", nullable = false, updatable = false, unique = true)
    @JsonbProperty("pid")
    private String pid;

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DIST_ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_PARTNER_DIST_ACCOUNT_ID",
        foreignKeyDefinition = "FOREIGN KEY(DIST_ACCOUNT_ID) REFERENCES DISTRI_ACCOUNTS(DIST_ACCOUNT_ID) ON DELETE CASCADE"))
    private DistributorAccount distributorAccount;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    @JsonbProperty("created_time")
    @JsonbDateFormat(value = JSON_DATETIME_FORMAT)
    private ZonedDateTime createdTime;

    @Column(name = "UPDATED_TIME", nullable = false)
    @JsonbProperty("updated_time")
    @JsonbDateFormat(value = JSON_DATETIME_FORMAT)
    private ZonedDateTime updatedTime;


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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public DistributorAccount getDistributorAccount() {
        return distributorAccount;
    }

    public void setDistributorAccount(DistributorAccount distributorAccount) {
        this.distributorAccount = distributorAccount;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @PrePersist
    public void persist() {
        createdTime = ZonedDateTime.now(JavaTimeUtil.ZONE_ID);
        updatedTime = ZonedDateTime.now(JavaTimeUtil.ZONE_ID);
    }

    @PreUpdate
    public void update() {
        updatedTime = ZonedDateTime.now(JavaTimeUtil.ZONE_ID);
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + id +
            ", version=" + version +
            ", pid='" + pid + '\'' +
            ", distributorAccount=" + distributorAccount +
            '}';
    }
}
