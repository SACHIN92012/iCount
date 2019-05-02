<?php
	define ('DB_HOST','localhost');
	define ('DB_USER','root');
	define ('DB_PASS','');
	define ('DB_NAME','shelter');
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
	if(mysqli_connect_errno())
	{
		die ('failed to connect with database '.mysqli_connect_errno());
	}
	
	$response=array();
	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		if(isset($_POST['name'] )&&isset($_POST['buildingNumber']))
		{
			$stmt=$conn->prepare("INSERT INTO entrytable (id,name,EntryText,buildingNumber,EntryNumber,isEntered,RollNumber,EntryTime) values (NULL,?,?,?,?,?,?,?) ;");
			
			$stmt->bind_param("ssiiiss",
			$_POST['name'],
			$_POST['EntryText'],
			$_POST['buildingNumber'],
			$_POST['EntryNumber'],
			$_POST['isEntered'],
			$_POST['RollNumber'],
			$_POST['EntryTime']);
			
			if($stmt->execute())
			$response['message']='Person entered successfully';
			else
			$response['message']='Registration failed';
		}
		else
		{
			$response['message']='Required fields are missing';
		}
	}
	else
		$response['message']='Registration failed 2';
	echo json_encode($response);
?>