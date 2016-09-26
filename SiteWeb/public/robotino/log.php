<?php
    $log = fopen('/var/www/html/Watch_Bot/scriptsD6T/log.txt', 'r');
    $content='';
    //$log = fopen('C:\wamp64\www\watchbot/scriptsD6T/log.txt', 'r');
    if($log){
        while(!feof($log)){
            $ligne=fgets($log);
            echo '<p>'.$ligne.'</p>';
        }
        fclose($log);
    }

?>