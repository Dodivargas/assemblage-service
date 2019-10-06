package com.github.dodivargas.assemblageservice.repository;

import com.github.dodivargas.assemblageservice.entity.VoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {

    @Query("select r from VoteEntity as r where r.rulingStatusId.rulingId.id = :rulingId ")
    List<VoteEntity> findAllByRulingStatusId(@Param("rulingId") Integer rulingId);

}
