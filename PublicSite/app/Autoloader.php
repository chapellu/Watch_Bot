<?php
namespace App;

class Autoloader{
	
	static function register(){

		spl_autoload_register(array(__CLASS__, 'autoload'));
	}

	/**
	 * Inclue le fichier coorespond à la classe
	 */
	static function autoload($class)
    {
    	if (strpos($class, 'stdClass') === false) {
			if(strpos($class, __NAMESPACE__ . '\\') === 0) {
				$class = str_replace(__NAMESPACE__ . '\\', '', $class);
				$class = str_replace('\\', DS, $class);
				require __DIR__ . DS . $class . '.php';
			}    		
    	} 
	}


}



?>