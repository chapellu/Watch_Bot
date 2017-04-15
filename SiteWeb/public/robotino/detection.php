<?php
//Mise à jour de la détection
$detection = fopen('/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt', 'r');
$content='';
if($detection){
    $ligne = fgets($detection);
    $seuil = fgets($detection) ? fgets($detection) : '0';
    var_dump($ligne,$seuil);die();
    App::getInstance()->detection_en_cours = strstr($ligne,'script=False') ? True : False;
    fclose($detection);
} else{
    $seuil = "";
}

?>

<form action="<?=BASE_URL.'/admin/robotino/';?>" method="post">
    <div class="input-group">
        <span class="input-group-addon" id="basic-addon1">Seuil</span>
        <input type="number" class="form-control" name="seuil" value="<?= $seuil;?>" placeholder="Veuillez saisir une température" aria-describedby="basic-addon1">
    </div>
    <table class="table-with-spaces">
        <tr>
            <td>
                <input class="btn btn-success <?php if(App::getInstance()->detection_en_cours){echo 'disabled';}?>" type="submit" value="Lancer la detection">
                <!-- <?= $form->bouttonRobotino('start-detection', 'success', 'Lancer la detection');?>-->
            </td>
            <td>
                <?php
                if(App::getInstance()->detection_en_cours){
                    echo $form->bouttonRobotino('stop-detection','danger ', 'Arreter la detection');
                } else {
                    echo $form->bouttonRobotino('stop-detection','danger disabled', 'Arreter la detection');
                }

                ?>

            </td>
        </tr>
    </table>
</form>

