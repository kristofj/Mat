package com.j3nsen.hiof.mat.model;

public class RecipeProduct {
	private int recipeId;
	private int productCode;

	public RecipeProduct(int recipeId, int productCode) {
		this.recipeId = recipeId;
		this.productCode = productCode;
	}

	public int getRecipeId() {
		return this.recipeId;
	}

	public int getProductCode() {
		return this.productCode;
	}
}
