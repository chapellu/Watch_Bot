<form method="post">
    <?= $form->input('title', 'Titre de l\'article'); ?>
    <?= $form->input('content', 'Contenu', ['type'=>'textarea']); ?>
    <?= $form->select('category_id', 'Categorie', $categories);?>
    <?= $form->submit('Ajouter',BASE_URL.'/admin/group/'); ?>
</form>
