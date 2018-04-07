<html>
<head>
<link type="text/css" rel="stylesheet" href="style.css">
</head>
</html>
<?php
if(isset($_POST['submit'])) 
{  
$title=$_POST['title'];
$post=$_POST['post'];
$catgory=$_POST['category'];
$image=$_POST['image'];

$con=mysql_connect("localhost","root","password");
mysql_select_db("sports.com");
 $query=mysql_query("INSERT INTO `news` (`id`, `title`, `category`, `post`, `image-source`) 
 VALUES (NULL, '$title', '$catgory', '$post', '$image')");
 echo mysql_error();

 if ($query)
 {
    header("Location: index.php");
 }
 else
 {
 echo "not inserted";
 }
 mysql_close($con);
}
?>

<div class="editform">
<form method="post" action="<?php echo $_SERVER['PHP_SELF']; ?>">
title :<input name="title" type="text" ><br>
post :-<br>
<textarea name="post"  rows="20" cols="61"></textarea><br>
<?php
$con2=mysql_connect("localhost","root","password");
mysql_select_db("sports.com");
echo 'select category : <br><select name="category"  type="select">';
$query=mysql_query("SELECT * FROM `category`");
while($row2=mysql_fetch_array($query))
{
echo "<option> ";
echo $row2["name"];
echo "<br>";
}
echo '</select><br>';
mysql_close($con2);
?>
image :<input name="image" type="text" ><br>
<input type="submit" name="submit" value="Submit Form"><br>
</form>
</div>