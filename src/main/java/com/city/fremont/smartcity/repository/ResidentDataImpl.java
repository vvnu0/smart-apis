package com.city.fremont.smartcity.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.city.fremont.smartcity.document.Resident;

@Repository
public class ResidentDataImpl implements ResidentData {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Resident> getAllUsers() {
		return mongoTemplate.findAll(Resident.class);
	}

	@Override
	public Resident getResidentById(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		return mongoTemplate.findById(query, Resident.class);
	}

	@Override
	public Resident addNewResident(Resident resident) {
		mongoTemplate.save(resident);
		// Will now contain id too
		return resident;
	}

	@Override
	public Object getAllResidentSettings(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		Resident resident = mongoTemplate.findById(query, Resident.class);
		return resident != null ? resident.getSettings() : "Resident not found.";
	}

	@Override
	public String getResidentSetting(String userId, String key) {
		Query query = new Query();
		query.fields().include("settings");
		query.addCriteria(Criteria.where("userId").is(userId).andOperator(Criteria.where("settings." + key).exists(true)));
		Resident resident = mongoTemplate.findById(query, Resident.class);
		return resident != null ? resident.getSettings().get(key) : "Resident setting not found.";
	}

	@Override
	public String addResidentSetting(String userId, String key, String value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		Resident resident = mongoTemplate.findById(query, Resident.class);
		if (resident != null) {
			resident.getSettings().put(key, value);
			mongoTemplate.save(resident);
			return "Key added.";
		} else {
			return "Resident not found.";
		}
	}

}
