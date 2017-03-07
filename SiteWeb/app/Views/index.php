<!-- Carousel
================================================== -->
<div id="myCarousel" class="carousel slide">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="" data-slide-to="0" class="active"></li>
			<li data-target="" data-slide-to="1"></li>
		</ol>
		<div class="carousel-inner">
			<div class="item active">
				<div class="carousel-page">
					<img src="http://localhost/watchbot/public/img/carousel-1.jpg" class="img-responsive" style="margin:0px auto;">
				</div>
				<!--<div class="container-fluid">
					<div class="carousel-caption">
						<h1 id="blue">Bienvenue sur le site de l'APP Watchbot</h1>
					</div>
				</div>-->
			</div>
			<div class="item">
				<div class="carousel-page">
				<img src="http://localhost/watchbot/public/img/carousel-2.jpg" class="img-responsive" style="margin:0px auto;">
				</div>
			</div>
			<!--Model :
			<div class="item">
				<img src="http://mooxidesign.com/wp-content/uploads/2014/04/New-york-1500x500.jpg" class="img-responsive">
				<div class="container">
					<div class="carousel-caption">
						<h1>Percentage-based sizing</h1>
						<p>This Site is fully responsive and adjusts to the size of the browser your using. Meaning that it looks great on smart-phones</p>
						<p><a class="btn btn-large btn-primary" href="#">Browse gallery</a></p>
					</div>
				</div>
			</div>-->
		</div>
		<!-- Controls-->
		<a class="left carousel-control" href="#myCarousel" data-slide="prev">
			<span class="icon-prev"></span>
		</a>
		<a class="right carousel-control" href="#myCarousel" data-slide="next">
			<span class="icon-next"></span>
		</a>
	</div>
<!-- /.carousel -->
<div>


<!-- Page Content -->

<div class="container-fluid">
    <div class="col-sr-7 post-layout">
        <h2 class="title">Bienvenue !</h2>
        <p>Bonjour à tous, nous vous souhaitons la bienvenue sur notre site.</p>
        <p>Bonne visite :)</p>
    </div>

    <div class="col-sr-7 post-layout">
        <?php foreach ($posts as $k):?>
            <h2 class="title"><?= $k->title; ?></h2>
            <p class="date">Publié le <?= $k->date; ?></p>
            <p><?= $k->content; ?></p>
        <?php endforeach; ?>
    </div>
    <!-- <div class="col-sm-4">
         <h2>Contact Us</h2>
         <address>
             <strong>Start Bootstrap</strong>
             <br>3481 Melrose Place
             <br>Beverly Hills, CA 90210
             <br>
         </address>
         <address>
             <abbr title="Phone">P:</abbr>(123) 456-7890
             <br>
             <abbr title="Email">E:</abbr> <a href="mailto:#">name@example.com</a>
         </address>
     </div>

     <hr>-->

         <div class="col-sl-4">
             <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
             <h2>Marketing Box #1</h2>
             <p>These marketing boxes are a great place to put some information. These can contain summaries of what the company does, promotional information, or anything else that is relevant to the company. These will usually be below-the-fold.</p>
         </div>
         <div class="col-sm-4">
             <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
             <h2>Marketing Box #2</h2>
             <p>The images are set to be circular and responsive. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
         </div>
         <div class="col-sm-4">
             <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
             <h2>Marketing Box #3</h2>
             <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
         </div>
     </div>
     <!-- /.row -->





</div>
<!-- /.container -->
