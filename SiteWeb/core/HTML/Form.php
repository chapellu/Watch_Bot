<?php

namespace Core\HTML;

class Form {


    private $data;
    public $surround = 'p';

    public function __construct($data = array())
    {
        $this->data = $data;
    }

    /**
     * @param $html string Code HTML à entourer
     * @return string
     */
    protected function surround($html){
        return "<{$this->surround}>{html}</{$this->surround}>";
    }

    /**
     * @param $name
     * @param $label
     * @param array $options
     * @return string
     */
    public function input($name, $label, $options = []){
        $type = isset($options['type'])?$options['type']:'text';
        return '<input type="'. $type .'" name="'.$name.'" value="'.$this->getValue($name).'"/>';
    }

    public function getValue($index){
        if(is_object($this->data)){
            return $this->data->$index;
        }             
        return isset($this->data[$index])?$this->data[$index]:null;

    }

    public function submit($name){
        return $this->surround('<button type="submit" name="'.$name.'">'.$name.'</button>');
    }

    public function password($name){
        return '<input type="text" name="'.$name.'" value="'.$this->getValue($name).'"/>';
    }

    public function bouttonRobotino($name){
        return $this->surround('<a class="btn btn-primary boutonsRobotino" type="submit" name="'.$name.'" href="'.BASE_URL.'/admin/robotino/?action='.$name.'">'.ucfirst($name).'</a>', 'liensBoutonsRobotino');

    }
}