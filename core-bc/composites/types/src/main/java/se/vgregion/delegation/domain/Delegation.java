package se.vgregion.delegation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

/**
 * This class represents a delegation.
 * 
 * @author Simon Göransson
 * @author Claes Lundahl
 */
@Entity
@Table(name = "vgr_delegation")
public class Delegation extends AbstractEntity<Long> implements
        se.vgregion.dao.domain.patterns.entity.Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long delegationKey;

    @ManyToOne
    private DelegationBlock delegationBlock;

    @Column(nullable = false)
    private String delegatedFor;
    @Column(nullable = false)
    private String delegateTo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    private String role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DelegationStatus status;

    private String information;

    private String delegatedForEmail;

    private Integer expiryAlertSentCount = 1;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Clear id.
     */
    public void clearId() {
        this.id = null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDelegatedFor() {
        return delegatedFor;
    }

    public void setDelegatedFor(String delegatedFor) {

        this.delegatedFor = delegatedFor;
    }

    public String getDelegateTo() {
        return delegateTo;
    }

    public void setDelegateTo(String delegateTo) {

        this.delegateTo = delegateTo;
    }

    public Date getValidFrom() {
        if (validFrom != null) {
            return (Date) validFrom.clone();
        } else {
            return null;
        }
    }

    public void setValidFrom(Date validFrom) {

        this.validFrom = (Date) validFrom.clone();
    }

    public Date getValidTo() {
        if (validTo != null) {
            return (Date) validTo.clone();
        } else {
            return null;
        }
    }

    public void setValidTo(Date validTo) {

        this.validTo = (Date) validTo.clone();
    }

    public Long getDelegationKey() {
        return delegationKey;
    }

    public void setDelegationKey(Long delegationKey) {
        this.delegationKey = delegationKey;
    }

    public void setDelegationBlock(DelegationBlock delegationBlock) {
        this.delegationBlock = delegationBlock;
    }

    public DelegationBlock getDelegationBlock() {
        return delegationBlock;
    }

    public DelegationStatus getStatus() {
        return status;
    }

    public void setStatus(DelegationStatus status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDelegatedForEmail() {
        return delegatedForEmail;
    }

    public void setDelegatedForEmail(String delegatedForEmail) {
        this.delegatedForEmail = delegatedForEmail;
    }

    public void setExpiryAlertSentCount(Integer expiryAlertSentCount) {
        this.expiryAlertSentCount = expiryAlertSentCount;
    }

    public Integer getExpiryAlertSentCount() {
        return expiryAlertSentCount;
    }

}
