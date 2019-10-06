package com.github.dodivargas.assemblageservice.repository;

import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RulingStatusRepository extends CrudRepository<RulingStatusEntity, Integer> {

    Optional<RulingStatusEntity> findByRulingId(RulingEntity integer);
}
