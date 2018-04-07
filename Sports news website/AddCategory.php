<?php

$category_name=$_POST["category_name"];


$con=mysql_connect("localhost","root","password");

mysql_select_db("sports.com");
$query=mysql_query("INSERT INTO `category`(`name`) VALUES ('$category_name')");

if ($query)
{
	header("Location: index.php");
}
else
{
echo "not inserted";
}

mysql_close($con);

?>