package com.j3nsen.hiof.mat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j3nsen.hiof.mat.model.*;

public class SqlHelper extends SQLiteOpenHelper {

	private final static String IMAGE_TABLE = "Image";
	private final static String RECIPE_TABLE = "Recipe";
	private final static String PRODUCT_TABLE = "Product";
	private final static String RECIPEPRODUCT_TABLE = "RecipeProduct";
	private final static String FAVOURITE_TABLE = "Favorite";
	private final static String UPDATE_TABLE = "Update";

	private final static String DB_NAME = "mat.db";
	private final static int DB_VERSION = 1;

	private final static String RECIPE_TABLE_CR =
		"CREATE TABLE Recipe(" +
			"RecipeId INT NOT NULL," +
			"Title TEXT NOT NULL," +
			"Description TEXT NOT NULL," +
			"Rating REAL NOT NULL," +
			"RatingCount INT NOT NULL," +
			"Creator TEXT NOT NULL," +
			"PRIMARY KEY(RecipeId)" +
			");";

	private final static String IMAGE_TABLE_CR =
		"CREATE TABLE Image(" +
			"ImageId INT NOT NULL," +
			"FilePath TEXT NOT NULL," +
			"RecipeId INT NOT NULL," +
			"FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId)," +
			"PRIMARY KEY(ImageId)" +
			");";

	private final static String PRODUCT_TABLE_CR =
		"CREATE TABLE Product(" +
			"ProductCode TEXT NOT NULL," +
			"BarcodeFormat TEXT NOT NULL," +
			"Producer TEXT NOT NULL," +
			"Name TEXT NOT NULL," +
			"PRIMARY KEY(ProductCode)" +
			");";

	private final static String RECIPEPRODUCT_TABLE_CR =
		"CREATE TABLE RecipeProduct(" +
			"RecipeId INT NOT NULL," +
			"ProductCode INT NOT NULL," +
			"FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId)," +
			"FOREIGN KEY(ProductCode) REFERENCES Product(ProductId)," +
			"PRIMARY KEY(RecipeId, ProductCode)" +
			");";

	private final static String FAVORITE_TABLE_CR =
		"CREATE TABLE Favorite(" +
			"RecipeId INT NOT NULL," +
			"FOREIGN KEY(RecipeId) REFERENCES Recipe(RecipeId)," +
			"PRIMARY KEY(UserId, RecipeId)" +
			");";

	private final static String UPDATE_TABLE_CR =
		"CREATE TABLE Update(" +
			"UpdateTime INT NOT NULL" +
			"PRIMARY KEY(UpdateTime)" +
			");";

	public SqlHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void insertImage(Image image) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (image != null) {
			cv.put("ImageId", image.getImageId());
			cv.put("FilePath", image.getFilePath());

			db.insert(IMAGE_TABLE, null, cv);
		}
	}

	public void insertFavorite(Favorite favorite) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (favorite != null) {
			cv.put("RecipeId", favorite.getRecipeId());

			db.insert(FAVOURITE_TABLE, null, cv);
		}
	}

	public void insertProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (product != null) {
			cv.put("BarcodeFormat", product.getBarcodeFormat());
			cv.put("Name", product.getName());
			cv.put("Producer", product.getProducer());
			cv.put("ProductCode", product.getProductCode());

			db.insert(PRODUCT_TABLE, null, cv);
		}
	}

	public void insertRecipe(Recipe recipe) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (recipe != null) {
			cv.put("RecipeId", recipe.getRecipeId());
			cv.put("ImageId", recipe.getImageId());
			cv.put("Creator", recipe.getCreator());
			cv.put("Description", recipe.getDescription());
			cv.put("Rating", recipe.getRating());
			cv.put("RatingCount", recipe.getRatingCount());
			cv.put("Title", recipe.getTitle());

			db.insert(RECIPE_TABLE, null, cv);
		}
	}

	public void insertRecipeProdcut(RecipeProduct recipeProduct) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (recipeProduct != null) {
			cv.put("RecipeId", recipeProduct.getRecipeId());
			cv.put("ProductCode", recipeProduct.getProductCode());

			if (db.insert(RECIPEPRODUCT_TABLE, null, cv) == -1)
				;
		}
	}

	//Returnerer når siste oppdatering ble gjort.
	public int getLastUpdate() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(UPDATE_TABLE, null, null, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		int latestUpdate = cursor.getInt(0);
		cursor.close();

		return latestUpdate;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tables[] = new String[]{IMAGE_TABLE_CR, RECIPE_TABLE_CR, PRODUCT_TABLE_CR, RECIPEPRODUCT_TABLE_CR, FAVORITE_TABLE_CR};

		for (String table : tables) {
			db.execSQL(table);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO: droppe alle tabeller og lage de på nytt.
	}
}
