<div class="robotino col-sm-12">
    <div class="video col-sm-9">
        <div class="overlayWrapper ">
            <video id="remote-video" class="img-responsive" autoplay=""></video>
            <p class="overlay">Live</p>
        </div>
        <div id="controls">
            <button type=button id="pause" onclick="pause();" title="pause or resume local player">Pause/Play</button>
            <button type=button id="mute" onclick="mute();" title="mute or unmute remote audio source">Muter/Demuter</button>
            <button type=button id="fullscreen" onclick="fullscreen();">Plein écran</button>
            <button type=button id="record" onclick="start_stop_record();" title="start or stop recording audio/video">Enregistrer</button>
        </div>
    </div>

    <div id="commands" class="video-commands col-sm-3">
        <details open>
            <summary><b>Options de connexion</b></summary>
            <fieldset>
               <input required type="text" id="signalling_server" value="193.48.125.196:8080" title="<host>:<port>, default address is autodetected"/><br>
             </fieldset>
        </details>
        <button id="start" style="background-color: green; color: white" onclick="start();">Connexion</button>
        <button disabled id="stop" style="background-color: red; color: white" onclick="stop();">Stop</button>
    </div>

    <!--<div class="navbar-menu">
       <dl>
           <dt>Menu</dt>
           <dd>
               <ul>
                   <li><a href="#">Un truc</a></li>
                   <li><a href="#">Un deuxième truc</a></li>
               </ul>
           </dd>
       </dl>
    </div>-->

    <div class="carte">
        <img class="img_carte" src="<?= BASE_URL.'/public/img/carte.png';?>" alt="">
    </div>

    <div class="commandes">
        <div class="commandes-manuelles">
            <form method="post" >
                <table>
                    <thead><h2>Commandes manuelles</h2></thead>
                    <tr>
                        <td></td>
                        <td>
                            <?= $form->bouttonRobotino('avancer', 'primary');?>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <?= $form->bouttonRobotino('gauche', 'primary');?>
                        </td>
                        <td></td>
                        <td>
                            <?= $form->bouttonRobotino('droite', 'primary');?>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <?= $form->bouttonRobotino('reculer', 'primary');?>
                        </td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            <?= $form->bouttonRobotino('start-detection', 'success', 'Lancer la detection');?>
                        </td>
                        <td>
                            <?= $form->bouttonRobotino('stop-detection','danger', 'Arreter la detection');?>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="logs">
            <h2>Logs</h2>
            <div class="scroll">
                <table>
                    <tbody id="log"></tbody>
                </table>
            </div>
            <?= $form->bouttonRobotino('clear-logs', 'secondary', 'Effacer la console');?>
        </div>
    </div>


</div>



<?php
if(isset($_GET['action'])){
    if($_GET['action']==='avancer' || $_GET['action']==='reculer' || $_GET['action']==='droite' || $_GET['action'] ==='gauche'){

        error_reporting(E_ALL);

        /* Interdit l'exécution infinie du script, en attente de connexion. */
        set_time_limit(0);

        /* Active le vidage implicite des buffers de sortie, pour que nous
         * puissions voir ce que nous lisons au fur et à mesure. */
        ob_implicit_flush();

        $address = '193.48.125.'.NUM_ROBOTINO;
        $port = 50000;


        if (($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === false) {
            echo "socket_create() a échoué : raison :  " . socket_strerror(socket_last_error()) . "\n";
        } else {
            echo "socket_create() a réussi\n";
        }


        echo "Essai de connexion à '$address' sur le port '$port'...";
        $result = socket_connect($socket, $address, $port);
        if ($socket === false) {
            echo "socket_connect() a échoué : raison : ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
        } else {
            echo "socket_connect() a réussi\n";
        }

        $msg = "POST /?action=".$_GET['action']." HTTP/1.1\r\n\r\n";
        $msg .= "Connection: Close\r\n\r\n";
        $out = "";

        echo "Envoi de la requête :".$msg;
        socket_write($socket, $msg, strlen($msg));



        echo "Fermeture du socket...";
        socket_close($socket);
        echo "Socket détruite\n\n";
    }
    else if($_GET['action']==='start-detection'){
        $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
        fwrite($flagscript, 'script=True');
        fclose($flagscript);
        exec('sudo  -u www-data python '.ROOT_SCRIPT.'mainscript.py');
        //exec('sudo  -u www-data python '.ROOT_SCRIPT.'mainscript.py 2>&1', $msg);
        //var_dump($msg);die();

    }
    else if($_GET['action']==='stop-detection'){
        $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
        fwrite($flagscript, 'script=False');
        fclose($flagscript);
    }
    else if($_GET['action']==='clear-logs'){
        $log = fopen(ROOT_SCRIPT.'log.txt', 'w');
        fwrite($log, ' ');
        fclose($log);
    }
    header('Location: '.BASE_URL.'/admin/robotino');
    exit();
}


?>

