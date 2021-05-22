package com.city.fremont.smartcity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.city.fremont.smartcity.document.Resident;

@Repository
public interface ResidentRepository extends MongoRepository<Resident, String> {

}
