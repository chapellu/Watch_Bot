<?php
/**
 * Created by PhpStorm.
 * User: alan
 * Date: 20/01/2017
 * Time: 15:10
 */

namespace Core\Entity;


class Entity
{
    public function __get($key){
        $method = 'get' . ucfirst($key);
        $this->$key = $this->$method();
        return $this->$key;
    }
}