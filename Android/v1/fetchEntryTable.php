<?php
	
	include_once dirname(__FILE__).'/../includes/Constants.php';
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASSWORD,'shelter');
	
	if(mysqli_connect_errno())
	{
		echo 'Unable to connect to database '.mysqli_connect_errno();
	}
	//if($_SERVER['REQUEST_METHOD']=='POST')
	{
		$stmt=$conn->prepare("SELECT * FROM entrytable");
		$stmt->execute();
		$stmt->bind_result($id,$name,$EntryText,$buildingNumber,$EntryNumber,$isEntered,$RollNumber,$EntryTime);
	
		$product=array();
		
		while($stmt->fetch())
		{
			$temp=array();
			$temp['id']=$id;
			$temp['name']=$name;
			$temp['EntryText']=$EntryText;
			$temp['buildingNumber']=$buildingNumber;
			$temp['EntryNumber']=$EntryNumber;
			$temp['isEntered']=$isEntered;
			$temp['RollNumber']=$RollNumber;
			$temp['EntryTime']=$EntryTime;
			array_push($product,$temp);
		}
	
		echo json_encode($product);
	}
	

?>