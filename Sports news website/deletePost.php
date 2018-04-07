<?php
$id=$_GET["id"];
$con=mysql_connect("localhost","root","password");
mysql_select_db("sports.com");
 $query=mysql_query("DELETE FROM `news` WHERE `news`.`id` = $id");
 if ($query)
 {
    header("Location: index.php");
 }
 else
 {
    echo "not deleted";
 }
 ?>