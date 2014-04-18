<?php
include 'functions.php';

$input = file_get_contents("php://input");
$json = json_decode($input);

$operation = $json->operation;

switch($action) {
	case "add":
		$entity = $json->entity;
		switch($entity) {
			case "user":
				addUser($json);
				break;
			case "product":
				addProducts($json);
				break;
			case "recipe":
				addRecipes($json);
				break;
		}
		break;

	case "update": //Sender nyeste oppdateringer.
		update($json);
		break;

	case "get_all":
		getAll();
		break;
	case "get":
		get($json);
		break;
	case "login":
		login($json);
		break;
}
?>

