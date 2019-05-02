<?php
	
	include_once dirname(__FILE__).'/../includes/Constants.php';
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASSWORD,'shelter');
	
	if(mysqli_connect_errno())
	{
		echo 'Unable to connect to database '.mysqli_connect_errno();
	}
	$stmt=$conn->prepare("SELECT * FROM data");
	$stmt->execute();
	$stmt->bind_result($id,$name,$buildingNumber);
	
	$product=array();
	
	while($stmt->fetch())
	{
		$temp=array();
		$temp['id']=$id;
		$temp['name']=$name;
		$temp['buildingNumber']=$buildingNumber;
		array_push($product,$temp);
	}
	
	echo json_encode($product);

?>