<?php
if(isset($_GET['action'])){
    $action = $_GET['action'];
    if($action==='avancer' || $action==='reculer' || $action==='droite' || $action ==='gauche'){
        $msg = "POST /?action=".$action." HTTP/1.1\r\n\r\n";
        $msg .= "Connection: Close\r\n\r\n";
        App::sendSocket('193.48.125.'.NUM_ROBOTINO,50000,$msg);
    }
    else if($action==='stop-detection'){
        if(SERVEUR == 0){
            $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
            fwrite($flagscript, 'script=False');
            fclose($flagscript);
        }
        else{
            $msg = '{"AuteurPrecedent":{"nom":"Site web","IP":"193.48.125.196"},"Destinataire":{"nom":"Raspberry","IP":"193.48.125.196"},"Date":{"date_string":'.date("Y-m-d-H-i-s").',"date":"'.date("M d, Y H:i:s a").'"},"type":Ordre,"message":"stopSurveillance"}
        ';
            App::sendSocket('193.48.125.196',50003,$msg);
        }
    }
    else if($action==='clear-logs'){
        $log = fopen(ROOT_SCRIPT.'log.txt', 'w');
        fwrite($log, ' ');
        fclose($log);
    }
    else if($action==='camera'){
        exec('sudo -u www-data bash /etc/init.d/watchbot-camera start > /dev/null 2>/dev/null &', $msg);
    }
    header('Location: '.BASE_URL.'/admin/robotino');
    exit();
}
if(isset($_POST['seuil'])){
    if($_POST['seuil']==='' || $_POST['seuil']<0){
        echo '<script>alert("Vous devez saisir un seuil (positif)")</script>';
    } else{
        if(SERVEUR == 0){
            $flagscript = fopen(ROOT_SCRIPT.'flagscript.txt', 'w');
            fwrite($flagscript, 'script=True'."\n".$_POST['seuil']);
            fclose($flagscript);
            if(DEV == 0) {
                exec('sudo  -u www-data python ' . ROOT_SCRIPT . 'mainscript.py > /dev/null 2>/dev/null &');
                //exec('sudo  -u www-data python '.ROOT_SCRIPT.'mainscript.py 2>&1', $msg);
                //var_dump($msg);die();
            }
        }else{

            $msg = '{"AuteurPrecedent":{"nom":"Site web","IP":"193.48.125.196"},"Destinataire":{"nom":"Raspberry","IP":"193.48.125.196"},"Date":{"date_string":'.date("Y-m-d-H-i-s").',"date":"'.date("M d, Y H:i:s a").'"},"type":Ordre,"message":"startSurveillance :'.$_POST['seuil'].'"}
        ';
            App::sendSocket('193.48.125.196',50003,$msg);

        }

    }
}

?>



<div class="robotino col-sm-12">
    <div class="video col-sm-9">
        <img src="http://193.48.125.196:8080/?action=stream" alt=""/>
        <!--<form action="<?=BASE_URL.'/admin/robotino/?action=camera';?>" method="post">
            <input class="btn btn-success" type="submit" value="Lancer la camera">
        </form>-->

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



