package com.github.dodivargas.assemblageservice.dto;

public class RuleResult {

    private Integer rulingId;
    private String name;
    private Boolean openedForVotes;
    private Boolean aproved;

    public Integer getRulingId() {
        return rulingId;
    }

    public Boolean getOpenedForVotes() {
        return openedForVotes;
    }

    public void setOpenedForVotes(Boolean openedForVotes) {
        this.openedForVotes = openedForVotes;
    }

    public void setRulingId(Integer rulingId) {
        this.rulingId = rulingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAproved() {
        return aproved;
    }

    public void setAproved(Boolean aproved) {
        this.aproved = aproved;
    }


    public static final class RuleResultBuilder {
        private Integer rulingId;
        private String name;
        private Boolean openedForVotes;
        private Boolean aproved;

        private RuleResultBuilder() {
        }

        public static RuleResultBuilder of() {
            return new RuleResultBuilder();
        }

        public RuleResultBuilder withRulingId(Integer rulingId) {
            this.rulingId = rulingId;
            return this;
        }

        public RuleResultBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RuleResultBuilder withOpenedForVotes(Boolean openedForVotes) {
            this.openedForVotes = openedForVotes;
            return this;
        }

        public RuleResultBuilder withAproved(Boolean aproved) {
            this.aproved = aproved;
            return this;
        }

        public RuleResult build() {
            RuleResult ruleResult = new RuleResult();
            ruleResult.setRulingId(rulingId);
            ruleResult.setName(name);
            ruleResult.setOpenedForVotes(openedForVotes);
            ruleResult.setAproved(aproved);
            return ruleResult;
        }
    }
}
