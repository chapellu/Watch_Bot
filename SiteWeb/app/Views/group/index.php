<?php
    foreach ($posts as $post) :?>
    <div class="col-sm-8 post-layout">



            <h2 class="title"><?= $post->title;?></h2>

            <p><?= $post->extrait; ?>...<a href="<?= $post->url ?>">Lire la suite &rarr;</a></p><!--&rarr = fleche a droite-->





    </div>
    <?php endforeach; ?>