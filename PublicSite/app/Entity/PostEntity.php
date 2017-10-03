<?php
namespace App\Entity;

use \Core\Entity\Entity;

class PostEntity extends Entity{

    public function getUrl(){
        return BASE_URL . '/group/view/?name=' . str_replace(' ', '.', $this->title);
    }
    
    public function getExtrait(){
        return substr($this->content, 0, 100);
    }

    public function getCategory(){
        $posts = \App::getInstance()->getTable('post');


        $post = $posts->query("
            SELECT category.title as category 
            FROM post 
            LEFT JOIN category ON category_id = category.id
            WHERE post.category_id = ?",[$this->category_id],true);

        return $post->category;
    }



    
}