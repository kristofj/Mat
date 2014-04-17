<?php
	function dbConnect() {
		$username = "";
		$password = "";
		$database = "android_mat";

		$mysqli = mysqli_connect("localhost", $username, $password, $android_mat);
		if(mysqli_connect_errno($mysqli)) {
			die("Error connecting to MySQL: " . mysqli_connect_error();
		}

		return $mysqli;
	}

	function dbClose($mysqli) {
		$mysqli->close();
	}

	function addUsers($json) {
		$mysqli = dbConnect();

		$sql = "INSERT INTO User (Username, Password, Token) VALUES (%s, %s, %s);";

		foreach($json->{"data"} as $user) {
			$username = $user->{"username"};
			$password = $user->{"password"};
			$token = getToken($username, $password);

			$query = sprintf($sql, $username, $password, $token);

			mysqli_query($mysqli, $query);
		}
		
		dbClose($mysqli);
	}

	function getToken($s1, $s2) {
		return hash("sha256", $s1.$s2);
	}

	function addProducts($json) {
		$mysqli = dbConnect();
		
		$sql = "INSERT INTO Product VALUES (%s, %s, %s);";

		foreach($json->{"data"} as $product) {
			$productCode = $product->{"product_code"};
			$barcode = $product->{"barcode_format"};
			$name = $product->{"name"};

			$query = sprintf($sql, $productCode, $barcode, $name);

			mysqli_query($mysqli, $query);
		}

		dbClose($mysqli);
	}

	function addRecipes($json) {
		$mysqli = dbConnect();
		
		$sql = "INSERT INTO Recipe (Title, Description, ImagePath, UserId) VALUES (%s, %s, %s, %d);";
		
		foreach($json->{"data"} as $recipe) {
			$title = $recipe->{"recipe"};
			$description = $recipe->{"description"};
			$imagePath = $recipe->{"image"};
			$userId = $recipe->{"user_id"};

			$query = sprintf($sql, $title, $description, $imagePath, $userId);

			mysqli_query($mysqli, $query);
		}
		
		respond();

		dbClose($mysqli);

	}

	function update($version) {
		
	}
	
	function getAll() {
		$mysqli = dbConnect();

		$recipes = [];
		$products = [];

		$sql_recipe = "SELECT Recipe.RecipeId,Title,Description,ImagePath,Username
										FROM Recipe,User
										WHERE Recipe.UserId=User.UserId;";
		$sql_product = "SELECT * FROM Product;";
		$sql_recProd = "SELECT ProductCode FROM RecipeProduct WHERE RecipeId=%d;";
		
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
		
		if($product_result = $mysqli->query($sql_product)) {
			while($row = $product_result->fetch_row()) {
				$product = ["product_code"=>$row[0], "barcode_format"=>$row[1], "name"=>$row[2]];
				$products[] = $product;
			}
		}

		$result = ["recipes"=>$recipes, "products"=>$products];
		respond($result);
	}

	function get($version) {
		
	}

	function respond($json) {

	}
?>

