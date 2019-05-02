<?php

	require_once dirname(__FILE__).'/../includes/constants.php';
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASSWORD,'shelter');
	
	if(mysqli_connect_errno())
	{
		echo 'Unable to connect to database '.mysqli_connect_errno();
	}
	
	if($_SERVER['REQUEST_METHOD']=='POST')
	{	
		$conn->query('UPDATE entrytable SET EntryText="'.$_POST['EntryText'].'" , isEntered = 2 WHERE EntryNumber='.$_POST['EntryNumber'] );
		$response=array();
		$response['message']="Person Exited Successfully";
		echo json_encode($response);
		$conn->close();
	}
?>