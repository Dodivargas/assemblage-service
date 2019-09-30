package com.github.dodivargas.assemblageservice.repository;

import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulingStatusRepository extends CrudRepository<RulingStatusEntity, Integer> {
}
