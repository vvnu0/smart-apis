package com.city.fremont.smartcity.repository;

import java.util.List;

import com.city.fremont.smartcity.document.Resident;

public interface ResidentData {
	List<Resident> getAllUsers();

	Resident getResidentById(String userId);

	Resident addNewResident(Resident resident);

	Object getAllResidentSettings(String userId);

	String getResidentSetting(String userId, String key);

	String addResidentSetting(String userId, String key, String value);

}
