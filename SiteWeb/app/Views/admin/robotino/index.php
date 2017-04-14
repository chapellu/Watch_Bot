<?php
if(isset($_GET['action'])){
    $action = $_GET['action'];
    var_dump($action);
    if($action==='avancer' || $action==='reculer' || $action==='droite' || $action ==='gauche'){
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

        $msg = "POST /?action=".$action." HTTP/1.1\r\n\r\n";
        $msg .= "Connection: Close\r\n\r\n";
        $out = "";

        echo "Envoi de la requête :".$msg;
        socket_write($socket, $msg, strlen($msg));

        echo "Fermeture du socket...";
        socket_close($socket);
        echo "Socket détruite\n\n";
    }
    else if($action==='stop-detection'){
        $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
        fwrite($flagscript, 'script=False');
        fclose($flagscript);
    }
    else if($action==='clear-logs'){
        $log = fopen(ROOT_SCRIPT.'log.txt', 'w');
        fwrite($log, ' ');
        fclose($log);
    }
    else if($action==='camera'){
        echo 'ok';
        exec('sudo  -u www-data bash /etc/init.d/watchbot-camera start > /dev/null 2>/dev/null &', $msg);
        $camera = True;die();
    }
    header('Location: '.BASE_URL.'/admin/robotino');
    exit();
} else {
    $camera = False;
}
if(isset($_POST['seuil'])){
    if($_POST['seuil']==='' || $_POST['seuil']<0){
        echo '<script>alert("Vous devez saisir un seuil (positif)")</script>';
    } else{
        $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
        fwrite($flagscript, 'script=True'."\n".$_POST['seuil']);
        fclose($flagscript);
        if(DEV == 0) {
            exec('sudo  -u www-data python ' . ROOT_SCRIPT . 'mainscript.py > /dev/null 2>/dev/null &');
            //exec('sudo  -u www-data python '.ROOT_SCRIPT.'mainscript.py 2>&1', $msg);
            //var_dump($msg);die();
        }

    }
}

?>



<div class="robotino col-sm-12">
    <div class="video col-sm-9">
        <?php var_dump($camera);?>
        <?php if($camera): ?>
            <img src="http://193.48.125.196:8080/?action=stream" alt=""/>
        <?php else: ?>
            <form action="<?=BASE_URL.'/admin/robotino/?action=camera';?>" method="post">
                <input class="btn btn-success" type="submit" value="Lancer la camera">
            </form>
        <?php endif; ?>
    </div>

    <div class="padding col-sm-12"><br></div>

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
        <div class="commandes-manuelles bordered">
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
            </form>
        </div>

        <div class="d6t bordered">
            <h2>Capteur D6T</h2>


            <form action="<?=BASE_URL.'/admin/robotino/';?>" method="post">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">Seuil</span>
                    <input type="number" class="form-control" name="seuil" placeholder="Veuillez saisir une température" aria-describedby="basic-addon1">
                </div>
                <table class="table-with-spaces">
                    <tr>
                        <td>
                            <input class="btn btn-success" type="submit" value="Lancer la detection">
                           <!-- <?= $form->bouttonRobotino('start-detection', 'success', 'Lancer la detection');?>-->
                        </td>
                        <td>
                            <?= $form->bouttonRobotino('stop-detection','danger', 'Arreter la detection');?>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="logs bordered">
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



