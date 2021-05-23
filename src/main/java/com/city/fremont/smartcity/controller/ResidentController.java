package com.city.fremont.smartcity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.city.fremont.smartcity.document.Resident;
import com.city.fremont.smartcity.repository.ResidentDataImpl;
import com.city.fremont.smartcity.repository.ResidentRepository;

@RestController
@RequestMapping(value="/resident/")
public class ResidentController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final ResidentRepository residentRepository;
	private final ResidentDataImpl residentDataImpl;
	
	public ResidentController(ResidentRepository residentRepository, ResidentDataImpl residentDataImpl) {
		this.residentRepository = residentRepository;
		this.residentDataImpl = residentDataImpl;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Resident> getAllResidents() {
		LOG.info("Getting all users.");
		return residentRepository.findAll();
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Resident getResident(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		return residentRepository.findById(userId).orElse(new Resident());
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Resident addNewResident(@RequestBody Resident resident) {
		LOG.info("Saving Resident.");
		return residentRepository.save(resident);
	}
	
	@RequestMapping(value = "/{userId}/settings", method = RequestMethod.GET)
	public Object getAllResidentSettings(@PathVariable String userId) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			return resident.getSettings();
		} else {
			return "Resident not found.";
		}
	}
	
	@RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
	public String getResidentSetting(@PathVariable String userId, @PathVariable String key) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			return resident.getSettings().get(key);
		} else {
			return "Resident not found.";
		}
	}
	
	@RequestMapping(value = "/{userId}/settings/{key}/{value}", method = RequestMethod.GET)
	public String addResidentSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			resident.getSettings().put(key, value);
			residentRepository.save(resident);
			return "Setting added";
		} else {
			return "Resident not found.";
		}
	}
	
	/* Shows how to use the Mongo template DataImpl class */
	@RequestMapping(value = "/{userId}/settings1/{key}/{value}", method = RequestMethod.GET)
	public String addResident1Setting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		return residentDataImpl.addResidentSetting(userId, key, value);
	}
}
