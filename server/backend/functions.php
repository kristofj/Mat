<?php
function dbConnect() {
	$username = "android_mat";
	$password = "matergodt12";
	$database = "mat";

	$mysqli = new mysqli('localhost', $username, $password, $database);
	if($mysqli->connect_error) {
		$response = ["status"=>0, "message"=>"Could not connect to database."];
		exit(json_encode($response));
	}
	return $mysqli;
}

function login($data) {
	$username = $data->username;
	$password = $data->password;

	$token = "";
	$id = 0;

	$mysqli = dbConnect();

	$sql = "SELECT UserId,Token FROM User WHERE Username='%s' AND Password='%s'";
	$query = sprintf($sql, $username, $password);

	if($result = $mysqli->query($query)) {
		$row = $result->fetch_row();
		if($row != NULL) {
			$id = (int)$row[0];
			$token = $row[1];
		}
	}
	dbClose();

	if(!empty($token)) {
		$response = ["status"=>1,"id"=>$id,"token"=>$token];
	} else {
		$response = ["status"=>0, "message"=>"Invalid login"];
	}
	echo json_encode($response);
}

function dbClose($mysqli) {
	$mysqli->close();
}

function addUser($json) {
	$mysqli = dbConnect();

	$sql_check = "SELECT * FROM User WHERE Username='%s'";
	$sql = "INSERT INTO User (Username, Password, Token) VALUES ('%s','%s','%s');";
	
	$username = $user->username;
	$password = $user->password;
	$token = getToken($username, $password);
	
	$query_check = sprintf($sql_check, $username);
	$query = sprintf($sql, $username, $password, $token);
	
	if($mysqli->query($query_check)) {
		$row = $mysqli->fetch_row();
		if($row != NULL) {
			dbClose($mysqli);
			$response = ["status"=>0, "message":"User already exists"];
			exit(json_encode($response));
		}
	}

	if($mysqli->query($mysqli, $query)) {
		$id = $mysqli->insert_id;
		$response = ["status"=>1,"token"=>$token, "id"=>$id];
		echo json_encode($response);
	} else {
		$response = ["status"=>0,"message"=>"Username already exists."];
		echo json_encode($response);
	}
	
	dbClose($mysqli);
}

function getToken($s1, $s2) {
	return hash("sha256", $s1.$s2);
}

function addProducts($json) {
	if(authenticate($json))

	$mysqli = dbConnect();
	
	$sql = "INSERT INTO Product (ProductCode,BarcodeFormat,Name) VALUES ('%s','%s','%s');";

	foreach($json->data as $product) {
		$productCode = $product->product_code;
		$barcode = $product->barcode_format;
		$name = $product->name;

		$query = sprintf($sql, $productCode, $barcode, $name);

		$mysqli->query($mysqli, $query);
	}
	dbClose($mysqli);
}

function addRecipes($json) {
	$mysqli = dbConnect();
	
	$sql = "INSERT INTO Recipe (Title, Description, ImagePath, UserId) VALUES ('%s','%s','%s',%d);";
	$sql_recProd = "INSERT INTO RecipeProduct VALUES (%d,%d,'%s',%f);";

	foreach($json->data as $recipe) {
		$title = $recipe->title;
		$description = $recipe->description;
		$imagePath = $recipe->image;
		$userId = $recipe->user_id;

		$query = sprintf($sql, $title, $description, $imagePath, $userId);

		$mysqli->query($mysqli, $query);

		$recipeId = $mysqli->insert_id;

		foreach($recipe->products as $product) {
			$query_recProd = sprintf($sql_recProd, $recipeId, $product->id, $product->unit, $product->value);
			$mysqli->query($query_recProd);
		}
	}
	dbClose($mysqli);
}

function update($json) {
	$mysqli = dbConnect();
	
	$recipe_version = $json->recipe_version;
	$product_version = $json->product_version;

	$recipes = [];
	$products = [];

	$response = [];

	$sql_recipe = sprintf("SELECT * FROM Recipe WHERE RecipeId > %d", $recipe_version);
	$sql_product = sprintf("SELECT * FROM Product WHERE ProductId > %d", $product_version);

	if($recipe_result = $mysqli->query($sql_recipe)) {
		while($row = $recipe_result->fetch_row()) {
			$recipes[] = ["id"=>$row[0], "title"=>$row[1], "description"=>$row[2], "creator"];
		}
	}

	if($change_result = $mysqli->query($sql)) {
		while($row = $change_result->fetch_row()) {
			$change = ["version_id"=>$row[0], "operation"=>$row[1], "table"=>$row[2], "id"=>$row[3]];
			$changes[] = $change;
		}
	}

	dbClose();

	if(count($changes) == 0) {
		$response = ["status"=>1];
	} else {
		$response = ["changes":$changes];
		echo json_encode($changes);
	}
	echo json_encode($response);
}

function getAll($with_ids, $recipeVersion, $productVersion) {
	$mysqli = dbConnect();

	$recipes = [];
	$products = [];

	$latest_version = 0;
	
	if($with_ids){
		$sql_recipe = sprintf("SELECT Recipe.RecipeId,Title,Description,ImagePath,Username
					FROM Recipe,User
					WHERE Recipe.UserId=User.UserId
						AND RecipeId > %d;", $recipeVersion);
		$sql_product = sprintf("SELECT * FROM Product WHERE ProductId > %d;", $productVersion);


	} else {
		$sql_recipe = "SELECT Recipe.RecipeId,Title,Description,ImagePath,Username
							FROM Recipe,User
							WHERE Recipe.UserId=User.UserId;";
		$sql_product = "SELECT * FROM Product;";
		$sql_recProd = "SELECT ProductCode FROM RecipeProduct WHERE RecipeId=%d;";
	}

	if($recipe_result = $mysqli->query($sql_recipe)) {
		while($row = $recipe_result->fetch_row()) {
			$recProds = [];

			$recProds_query = sprintf($sql_recProd, $row[0]);
			if($recProds_result = $mysqli->query($recProds_query)) {
				while($recProd = $recProds_result->fetch_row()) {
					$recProds[] = $recProd[0];
				}
			}

			$recipe = ["id"=>$row[0], "title"=>$row[1], "description"=>$row[2], "image"=>$row[3], "creator"=>$row[4], "products"=>$recProds];				

			$recipes[] = $recipe;
		}
	}
	
	if($product_result = $mysqli->query($sql_product)) {
		while($row = $product_result->fetch_row()) {
			$product = ["product_code"=>$row[0], "barcode_format"=>$row[1], "name"=>$row[2]];
			$products[] = $product;
		}
	}
	$result = ["recipes"=>$recipes, "products"=>$products];
	echo json_encode($result);
}

function get($json) {
	$entity = $json->entity;

	switch($entity) {
		case "product":
			getProducts($json->data);
			break;
		case "recipe":
			getRecipes($json->data);
			break;
	}
}

function getProducts($data) {
	$mysqli = dbConnect();
	$products = [];

	$ids = "";

	foreach($data as $id) {
		$ids = $id->id . ',';
	}
	$ids = rtrim($ids, ",");

	$sql = sprintf("SELECT * FROM Product WHERE ProductId IN (%s);", $ids);

	if($products_result = $mysqli->query($sql)) {
		while($row = $products_result->fetch_row()) {
			$product = ["id"=>$row[0],"product_code"=>$row[1], "barcode_format"=>$row[2], "name"=>$row[3]];
			$products[] = $product;
		}
	}
	$response = ["products"=>$products];
	echo json_encode($response);
}

function getRecipes($data) {
	$mysqli = dbConnect();
	$recipes = [];

	$sql_recipe = "SELECT RecipeId,Title,Description,ImagePath,Username
						FROM Recipe,User
						WHERE Recipe.UserId=User.UserId;";
	$sql_product = "SELECT * FROM Product;";
	$sql_recProd = "SELECT ProductId FROM RecipeProduct WHERE RecipeId=%d;";

	$ids = "";

	foreach($data as $id) {
		$ids = $id->id . ',';
	}
	$ids = rtrim($ids, ",");

	$sql = sprintf("SELECT * FROM Recipe WHERE RecipeId IN (%s);", $ids);
	
	if($recipe_result = $mysqli->query($sql_recipe)) {
		while($row = $recipe_result->fetch_row()) {
			$recProds = [];

			$recProds_query = sprintf($sql_recProd, $row[0]);
			if($recProds_result = $mysqli->query($sql_recipe)) {
				while($recProd = $recProds_result->fetch_row()) {
					$recProds[] = $recProd[0];
				}
			}

			$recipe = ["id"=>$row[0], "title"=>$row[1], "description"=>$row[2], "image"=>$row[3], "creator"=>$row[4], "products"=>$recProds];				
			$recipes[] = $recipe;
		}
	}
	$response = ["recipes"=>$recipes];
	echo json_encode($response);
}
?>

