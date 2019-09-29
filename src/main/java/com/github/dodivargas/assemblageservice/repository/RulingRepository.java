package com.github.dodivargas.assemblageservice.repository;

import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulingRepository extends CrudRepository<RulingEntity, Integer> {
}
