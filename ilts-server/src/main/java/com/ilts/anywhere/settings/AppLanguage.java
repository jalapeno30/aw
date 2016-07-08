package com.ilts.anywhere.settings;

import java.util.List;
import java.util.Map;

public class AppLanguage {
	
	private Integer id;
	private String alias;
	private String name;
	private List<Map<String, Object>> entries;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Map<String, Object>> getEntries() {
		return entries;
	}
	public void setEntries(List<Map<String, Object>> entries) {
		this.entries = entries;
	}
}
