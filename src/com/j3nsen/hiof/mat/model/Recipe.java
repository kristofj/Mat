package com.j3nsen.hiof.mat.model;

public class Recipe {
	private int recipeId;
	private String title;
	private String description;
	private float rating;
	private int ratingCount;
	private String creator;
	private int imageId;

	public Recipe(int recipeId, String title, String description, float rating, int ratingCount, String creator, int imageId) {
		this.recipeId = recipeId;
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.creator = creator;
		this.imageId = imageId;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public float getRating() {
		return rating;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public String getCreator() {
		return creator;
	}

	public int getImageId() {
		return imageId;
	}
}
