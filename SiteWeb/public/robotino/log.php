<?php
    if(DEV == 1){
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

        //Mise à jour de la détection
        $log = fopen('/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt', 'r');
        $content='';
        if($log){
            while(!feof($log)){
                $ligne=fgets($log);
                App::getInstance()->detection_en_cours = strstr($ligne,'script:=False') ? True : False;
            }
            fclose($log);
        }

    }


?>