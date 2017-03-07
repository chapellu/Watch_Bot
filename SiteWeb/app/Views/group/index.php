<div class="row">
    <div class="col-sm-8">

        <?php
        foreach ($posts as $post) :?>

            <h2><?= $post->title;?></h2>

            <p><?= $post->extrait; ?>...<a href="<?= $post->url ?>">Lire la suite &rarr;</a></p><!--&rarr = fleche a droite-->


        <?php endforeach; ?>


    </div>
</div>