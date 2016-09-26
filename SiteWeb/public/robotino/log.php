<?php
    //    $monfichier = fopen('/var/www/html/Watch_Bot/scriptsD6T/log.txt', 'r');
    $content='';
    $log = fopen('C:\wamp64\www\watchbot/scriptsD6T/log.txt', 'r');
    while(!feof($log)){
        $ligne=fgets($log);
        echo '<p>'.$ligne.'</p>';
    }
    fclose($log);
?>