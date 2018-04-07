<?php
session_start();
$username=$_POST["username"];
$email=$_POST["email"];
$password=$_POST["password"];
$phone=$_POST["phonenumber"];
$type=$_POST["type"];

$con=mysql_connect("localhost","root","password");
mysql_select_db("sports.com");

$query=mysql_query("INSERT INTO `users` (`id`, `name`, `email`, `password`, `phone-number`, `type`) 
VALUES (NULL, '$username', '$email', '$password', '$phone', '$type')");

if ($query)
{   
    $query=mysql_query("SELECT * FROM `users` WHERE `name` LIKE '$username' AND `password` LIKE '$password'");
    $row=mysql_fetch_array($query);
    $id=$row['id'];
    $_SESSION["id"] = $id;
	header("Location: login.php");
}
else
{
    header("location: signup.php");
}

mysql_close($con);
?>
