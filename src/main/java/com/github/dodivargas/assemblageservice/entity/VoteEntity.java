package com.github.dodivargas.assemblageservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ruling_votes", schema = "assemblage")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ruling_status_id")
    private RulingStatusEntity rulingStatusId;
    @Column(name = "tax_id")
    private String taxId;
    @Column(name = "in_favor")
    private Boolean inFavor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RulingStatusEntity getRulingStatusId() {
        return rulingStatusId;
    }

    public void setRulingStatusId(RulingStatusEntity rulingStatusId) {
        this.rulingStatusId = rulingStatusId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Boolean getInFavor() {
        return inFavor;
    }

    public void setInFavor(Boolean inFavor) {
        this.inFavor = inFavor;
    }

    public static final class VoteEntityBuilder {
        private Integer id;
        private RulingStatusEntity rulingId;
        private String taxId;
        private Boolean inFavor;

        private VoteEntityBuilder() {
        }

        public static VoteEntityBuilder of() {
            return new VoteEntityBuilder();
        }

        public VoteEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public VoteEntityBuilder withRulingId(RulingStatusEntity rulingId) {
            this.rulingId = rulingId;
            return this;
        }

        public VoteEntityBuilder withTaxId(String taxId) {
            this.taxId = taxId;
            return this;
        }

        public VoteEntityBuilder withInFavor(Boolean inFavor) {
            this.inFavor = inFavor;
            return this;
        }

        public VoteEntity build() {
            VoteEntity voteEntity = new VoteEntity();
            voteEntity.setId(id);
            voteEntity.setRulingStatusId(rulingId);
            voteEntity.setTaxId(taxId);
            voteEntity.setInFavor(inFavor);
            return voteEntity;
        }
    }
}
