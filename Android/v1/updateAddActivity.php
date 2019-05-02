<?php

	require_once dirname(__FILE__).'/../includes/constants.php';
	
	$conn=new mysqli(DB_HOST,DB_USER,DB_PASSWORD,'shelter');
	
	if(mysqli_connect_errno())
	{
		echo 'Unable to connect to database '.mysqli_connect_errno();
	}
	
	if($_SERVER['REQUEST_METHOD']=='POST')
	{	
		$conn->query('UPDATE data SET name="'.$_POST['name'].'" WHERE id=20');
		echo $_POST['name'];
		$conn->close();
	}
?>