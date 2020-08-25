package com.zistrong.literacy.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.zistrong.literacy.entity.Charactor;

public interface CharactorService extends CrudRepository<Charactor, Long> {

    List<Charactor> findByCharString(String charString);
    
    @Query("select c from Charactor c order by id")
    List<Charactor> find();
    @Query("select c from Charactor c where c. favorite = true order by id")
    List<Charactor> findFavorite();
    
    @Modifying
    @Transactional
    @Query(value = "update currentChar set count=   :count  where id= 1", nativeQuery = true)
    public int updateIndex(@Param("count") int count);
    
    @Modifying
    @Transactional
    @Query(value = "update charatctor set favorite =  not(favorite)  where id= :id", nativeQuery = true)
    public int favorite(@Param("id") Long id);
}
