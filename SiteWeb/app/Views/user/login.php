<?php if($errors): ?>
<div class="alert alert-danger">
    Identifiants incorrects
</div>
<?php endif; ?>


<form method="post" style="padding-top: 50px;">
    <?= $form->input('login', 'Pseudo'); ?>
    <?= $form->input('password', 'Mot de passe', ['type'=>'password']); ?>
    <?= $form->submit('Envoyer'); ?>
</form>
