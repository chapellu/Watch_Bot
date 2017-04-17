<h1>Administrer les articles</h1>

<table class="table">
    <thead>
        <tr>
            <td>ID</td>
            <td>Titre</td>
            <td>Actions</td>
        </tr>
    </thead>

    <tbody>
        <?php foreach($posts as $post):?>
            <tr>
                <td><?= $post->id;?></td>
                <td><?= $post->title; ?></td>
                <td>
                    <a href="<?= BASE_URL.'/admin/post/edit/?id='.$post->id;?>" class="btn btn-primary">Editer</a>

                    <form action="<?= BASE_URL.'/admin/post/delete';?>" method="post" style="display:inline">
                        <input type="hidden" name="id" value="<?= $post->id;?>">
                        <button type="submit" class="btn btn-danger" href="<?= BASE_URL.'/admin/post/delete/?id='.$post->id;?>">Supprimer</button>
                    </form>
                </td>
            </tr>


        <?php endforeach; ?>
    </tbody>
</table>

<p><a href="<?= BASE_URL.'/admin/post/add';?>" class="btn btn-success">Ajouter un article</a></p>