package com.xiv.gearplanner.models.importers;

import java.util.ArrayList;
import java.util.List;

public class LSItem {
	
	private static final String CATEGORY_SOUL_CRYSTAL = "Soul Crystal";

	String id;
	int level;
	String name;
	String category;
	
	private List<String> classes = new ArrayList<>();

	private List<String> materia = new ArrayList<>();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<String> getClasses() {
		return classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}


	public List<String> getMateria() {
		return materia;
	}

	public void setMateria(List<String> materia) {
		this.materia = materia;
	}

	public void addMateria(String materia) {
		this.materia.add(materia);
	}

	@Override
	public String toString() {
		return "LSItem [id=" + id + ", level=" + level + ", name=" + name + ", category="
				+ category + ", classes=" + classes + "]";
	}
	public boolean isJobStone() {
		return CATEGORY_SOUL_CRYSTAL.equals(category);
	}
}
