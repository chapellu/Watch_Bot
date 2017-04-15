<?php
//Mise à jour de la détection
$flagscript = fopen('/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt', 'r');
$ligne = fgets($flagscript);
$seuil = fgets($flagscript);
$detection = strstr($ligne,'script=False') ? false : true;
fclose($flagscript);

?>


<table class="table-with-spaces">
    <tr>
        <td>
            <input class="btn btn-success <?php if($detection){echo 'disabled';}?>" type="submit" value="Lancer la detection">
        </td>
        <td>

            <?php
            if($detection){
                echo '<a class="btn btn-danger boutonsRobotino" id="stop-detection" type="submit" name="stop-detection" href="http://193.48.125.196/admin/robotino/?action=stop-detection">Arreter la détection</a>';
            } else {
                echo '<a class="btn btn-danger disabled boutonsRobotino" id="stop-detection" type="submit" name="stop-detection" href="http://193.48.125.196/admin/robotino/?action=stop-detection">Arreter la détection</a>';
            }

            ?>

        </td>
    </tr>
</table>



