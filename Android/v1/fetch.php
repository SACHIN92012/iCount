<?php
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASS','');
	define('DB_NAME','android');
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
	
	if(mysqli_connect_errno()){
		die('Unable to connect to database'.mysqli_connect_errno());
	}
	$stmt=$conn->prepare("SELECT * FROM users");
	$stmt->execute();
	$stmt->bind_result($id,$username,$password,$email);
	$product=array();
	while($stmt->fetch()){
		$temp=array();
		$temp['id']=$id;
		$temp['username']=$username;
		$temp['password']=$password;
		$temp['email']=$email;
		array_push($product,$temp);
	}
	echo json_encode($product);
?>