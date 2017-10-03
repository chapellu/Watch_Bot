<div class="container-fluid">
    <div class="col-md-6 col-md-offset-3 profile colored">
        <div class="img_profile">
            <img src="<?= BASE_URL . '/public/img/team/'.strtolower(str_replace(' ','.', $post->title)).'.jpg';?>" alt="..." class="img-circle img_profile">
        </div>
        <div class="row">
            <h1><?= $post->title; ?></h1>
            <p><?= $post->content; ?></p>
        </div>
    </div>
</div>

