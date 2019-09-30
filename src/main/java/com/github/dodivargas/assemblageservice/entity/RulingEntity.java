package com.github.dodivargas.assemblageservice.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "ruling", schema = "assemblage")
public class RulingEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;
    @Column(name = "name")
    private String name;

    public RulingEntity() {
    }

    public RulingEntity(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class RulingEntityBuilder {
        private Integer id;
        private String name;

        private RulingEntityBuilder() {
        }

        public static RulingEntityBuilder of() {
            return new RulingEntityBuilder();
        }

        public RulingEntityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public RulingEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RulingEntity build() {
            RulingEntity rulingEntity = new RulingEntity();
            rulingEntity.setId(id);
            rulingEntity.setName(name);
            return rulingEntity;
        }
    }
}
