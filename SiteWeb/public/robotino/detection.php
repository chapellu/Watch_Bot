<?php
var_dump("ok");
//Mise à jour de la détection
$detection = fopen('/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt', 'r');
$content='';
if($detection){
    var_dump($detection);
    $ligne = fgets($detection);
    $seuil = fgets($detection) ? fgets($detection) : '0';
    App::getInstance()->detection_en_cours = strstr($ligne,'script=False') ? True : False;
    fclose($detection);
} else{
    $seuil = "";
}
?>


