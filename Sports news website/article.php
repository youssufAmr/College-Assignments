<?php
session_start();
echo "<style type='text/css'>"; 
include'style.css';
echo "</style>";
if(!(isset($_SESSION["id"]))){
    header("location: login.php");
}
?>
<div class="outdiv">
<div name="head">
        <img src="images\\logo_.png" >
    </div>
    <div name="menu">
        <ul>
            <li><a href="index.php">Home</a></li>
           <li><?php
            echo "</form>";
        $ID=$_SESSION["id"];
        echo "<a href='logout.php?id=".$ID."'>log out</a>";
        ?>
        </li>
        </ul>
    </div>
<?php
   $id=$_GET["id"];
   $con=mysql_connect("localhost","root","password");
mysql_select_db("sports.com");
 $query=mysql_query("SELECT * FROM `news` WHERE `id` = '$id' ");
 $row=mysql_fetch_array($query);
    echo '<div class="article">';
         echo'<div class="title">';
             echo'<h3 name="category">';
                echo $row["category"];
            echo' </h3>';
             echo '<h1 name="mainHead">';
             echo $row["title"];
            echo'</h1>';
       echo '</div>';
       echo '<div class="articleImage">';
           echo "<img src='".$row["image-source"]."'>";
        echo'</div>';
       echo '<div class="post">'; 
        echo $row["post"];
       echo' <div>';
    echo'</div>';
    $ID=$_SESSION["id"];
    $query=mysql_query("SELECT * FROM `users` WHERE id='$ID'");
    $row=mysql_fetch_array($query);
    if($row["type"]=="admin"){
            echo "<a href='updatePost.php?id=".$id."'>updatePost</a>";
            echo "<br>";
            echo "<a href='deletePost.php?id=".$id."'>deletePost</a>";
    }
    mysql_close($con);
?>
</div>