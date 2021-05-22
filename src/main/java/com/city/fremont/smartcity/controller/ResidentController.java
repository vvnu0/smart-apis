package com.city.fremont.smartcity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.city.fremont.smartcity.document.Resident;
import com.city.fremont.smartcity.repository.ResidentRepository;

@RestController
@RequestMapping(value="/resident/")
@CrossOrigin(origins = "*")
public class ResidentController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final ResidentRepository residentRepository;
	
	public ResidentController(ResidentRepository residentRepository) {
		this.residentRepository = residentRepository;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Resident> getAllUsers() {
		LOG.info("Getting all users.");
		return residentRepository.findAll();
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Resident getResident(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		return residentRepository.findById(userId).orElse(new Resident());
	}
	

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Resident addNewUsers(@RequestBody Resident resident) {
		LOG.info("Saving user.");
		return residentRepository.save(resident);
	}
	
	@RequestMapping(value = "/{userId}/settings", method = RequestMethod.GET)
	public Object getAllUserSettings(@PathVariable String userId) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			return resident.getSettings();
		} else {
			return "Resident not found.";
		}
	}
	
	@RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
	public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			return resident.getSettings().get(key);
		} else {
			return "User not found.";
		}
	}
	
	@RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
	public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		Resident resident = residentRepository.findById(userId).orElse(null);
		if (resident != null) {
			resident.getSettings().put(key, value);
			residentRepository.save(resident);
			return "Setting added";
		} else {
			return "User not found.";
		}
	}
	
}
