package com.github.dodivargas.assemblageservice.repository;

import com.github.dodivargas.assemblageservice.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {
}
