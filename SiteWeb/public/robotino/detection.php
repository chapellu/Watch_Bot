<?php
//Mise à jour de la détection
$flagscript = fopen('/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt', 'r');
$ligne = fgets($flagscript);
$seuil = fgets($flagscript);
$detection = strstr($ligne,'script=False') ? false : true;
fclose($flagscript);

echo '

<form action="'.BASE_URL.'/admin/robotino/" method="post">
    <div class="input-group">
        <span class="input-group-addon" id="basic-addon1">Seuil</span>
        <input type="number" class="form-control" name="seuil" value="'.$seuil.'" placeholder="Veuillez saisir une température" aria-describedby="basic-addon1">
    </div>
    <table class="table-with-spaces">
        <tr>
            <td>
                
                <input class="btn btn-success';if($detection){echo 'disabled';}echo '" type="submit" value="Lancer la detection">
                <!-- <?= $form->bouttonRobotino(\'start-detection\', \'success\', \'Lancer la detection\');?>-->
            </td>
            <td>';


                if($detection){
                    echo $form->bouttonRobotino('stop-detection','danger', 'Arreter la detection');
                } else {
                    echo $form->bouttonRobotino('stop-detection','danger disabled', 'Arreter la detection');
                }

echo '

            </td>
        </tr>
    </table>
</form>
';
?>



