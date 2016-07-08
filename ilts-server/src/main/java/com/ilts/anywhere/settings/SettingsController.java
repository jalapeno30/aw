package com.ilts.anywhere.settings;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilts.anywhere.authentication.AuthenticationService;

@Controller
public class SettingsController {

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private AuthenticationService authService;
	
	@RequestMapping(value = "/settings/languagesSerialized/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<AppLanguageSerialized> removeOrder(
			@PathVariable(value = "id") String id,
			HttpServletResponse response) {
		
		return new ResponseEntity<AppLanguageSerialized>(this.settingsService.getSerializedLanguages(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/settings/languagesSerialized")
	public @ResponseBody ResponseEntity<List<AppLanguageSerialized>> getSerializedLanguages(
			HttpServletResponse response) {
		
		return new ResponseEntity<List<AppLanguageSerialized>>(this.settingsService.getSerializedLanguages(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/settings/saveLanguagesSerialized", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> saveSerializedLanguages(
			@RequestParam(value="token", required = true) String token,
			@RequestBody String jsonRequest,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		} else if (!this.authService.isAdmin(token)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<Object>(this.settingsService.saveSerializedLanguage(jsonRequest), HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/settings/newLanguagesSerialized", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> saveNewSerializedLanguages(
			@RequestParam(value="token", required = true) String token,
			@RequestBody String jsonRequest,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		} else if (!this.authService.isAdmin(token)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<Object>(this.settingsService.saveNewSerializedLanguage(jsonRequest), HttpStatus.OK);
		}
		
		
	}
	
}
