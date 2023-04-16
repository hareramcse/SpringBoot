package com.hs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hs.collection.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
}
