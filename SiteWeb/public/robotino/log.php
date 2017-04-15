<?php
    $dev = false;

    if($dev == TRUE){
        echo 'Mode de développement';
    } else{
        //Mise à jour du log
        $log = fopen('/var/www/html/Watch_Bot/scriptsD6T/log.txt', 'r');
        $content='';
        if($log){
            while(!feof($log)){
                $ligne=fgets($log);
                echo '<p>'.$ligne.'</p>';
            }
            fclose($log);
        }
    }

