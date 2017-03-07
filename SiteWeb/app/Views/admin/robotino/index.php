<div class="col-sm-12">

</div>

<div class="col-sm-12">
    <form method="post" >
        <table class="tableBoutonsRobotino">
            <thead><h2>Commandes mannuelles</h2></thead>
            <tr>
                <td></td>
                <td>
                    <?= $form->bouttonRobotino('avancer', 'boutonsRobotino');?>
                </td>
            </tr>
            <tr>
                <td>
                    <?= $form->bouttonRobotino('gauche', 'boutonsRobotino');?>
                </td>
                <td></td>
                <td>
                    <?= $form->bouttonRobotino('droite', 'boutonsRobotino');?>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <?= $form->bouttonRobotino('reculer', 'boutonsRobotino');?>
                </td>
            </tr>
        </table>
    </form>
</div>

<?php
if(isset($_GET['action'])){
    echo '<meta http-equiv="Refresh" content="0; url=http://193.48.125.'.NUM_ROBOTINO.':50000?action='.$_GET['action'].'">';
    header('Location: '.BASE_URL.'/admin/robotino/');
    exit();
}