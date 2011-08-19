<?php
if (is_numeric($_GET['employ_id'])) {
    mysql_connect('localhost','root','');
    mysql_select_db('ritchie');
    $query="select * from employ where id=$_GET[employ_id]";
    $result=mysql_query($query);
    $employ=mysql_fetch_array($result);
    echo "<table border=\"1\">
        <tr>
            <td>Name:</td>
            <td>".$employ[name]."</td>
        </tr>
        <tr>
            <td>Appoint Date:</td>
            <td>".$employ[appoint_date]."</td>
        </tr>
        <tr>
            <td>Country:</td>
            <td>".$employ[country]."</td>
        </tr>
        <tr>
            <td>City:</td>
            <td>".$employ[city]."</td>
        </tr>
        <tr>
            <td>E-mail:</td>
            <td>".$employ[email]."</td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td>".$employ[phone]."</td>
        </tr>
    </table>";
}
?>