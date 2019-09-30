package com.github.dodivargas.assemblageservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ruling_status", schema = "assemblage")
public class RulingStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ruling_id")
    private RulingEntity rulingId;
    @Column(name = "open_for_vote")
    private Boolean openForVote;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "result")
    private Boolean result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RulingEntity getRulingId() {
        return rulingId;
    }

    public void setRulingId(RulingEntity rulingId) {
        this.rulingId = rulingId;
    }

    public Boolean getOpenForVote() {
        return openForVote;
    }

    public void setOpenForVote(Boolean openForVote) {
        this.openForVote = openForVote;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }


    public static final class RulingStatusEntityBuilder {
        private Integer id;
        private RulingEntity rulingId;
        private Boolean openForVote;
        private Date expirationDate;
        private Boolean result;

        private RulingStatusEntityBuilder() {
        }

        public static RulingStatusEntityBuilder of() {
            return new RulingStatusEntityBuilder();
        }

        public RulingStatusEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public RulingStatusEntityBuilder withRulingId(RulingEntity rulingId) {
            this.rulingId = rulingId;
            return this;
        }

        public RulingStatusEntityBuilder withOpenForVote(Boolean openForVote) {
            this.openForVote = openForVote;
            return this;
        }

        public RulingStatusEntityBuilder withExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public RulingStatusEntityBuilder withResult(Boolean result) {
            this.result = result;
            return this;
        }

        public RulingStatusEntity build() {
            RulingStatusEntity rulingStatusEntity = new RulingStatusEntity();
            rulingStatusEntity.setId(id);
            rulingStatusEntity.setRulingId(rulingId);
            rulingStatusEntity.setOpenForVote(openForVote);
            rulingStatusEntity.setExpirationDate(expirationDate);
            rulingStatusEntity.setResult(result);
            return rulingStatusEntity;
        }
    }
}
