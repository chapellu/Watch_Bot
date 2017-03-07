<?php
namespace Core\HTML;

class BootstrapForm extends Form{
    
    /**
     * @param $html string Code HTML à entourer
     * @return string
     */
    protected function surround($html, $otherclasses = ""){
        return '<div class="form-group '.$otherclasses.'">'.$html.'</div>';
    }

    /**
     * @param $name S
     * @return string
     */
    public function input($name, $label, $options = array()){
        $error=false;
        $classError='';
        if(isset($this->errors[$name])){
            $error=$this->errors[$name];
            $classError='has-error has-feedback';
        }
        $value = $this->getValue($name);
        if($label=='hidden'){
            return '<input type="hidden" class="form-control" name="'.$name.'" value="'.$value.'">';
        }
        $input='<label for="$input'.$name.'">'.$label.'</label>
 					<div class="form-group '.$classError.'">';
        $attr=' ';
        foreach ($options as $k => $v) {
            if($k!='type'){
                $attr.="$k=\"$v\"";
            }
        }
        if(!isset($options['type'])){
            $input.='<input type="text" class="form-control" name="'.$name.'" id="input'.$name.'" value="'.$value.'"'.$attr.'>';
        }elseif ($options['type']=='textarea') {
            $input.='<textarea type="text" class="form-control"  id="input'.$name.'" name="'.$name.'"'.$attr.'>'.$value.'</textarea>';
        }elseif ($options['type']=='checkbox') {
            $input.='<label class="form-check-label">
                    <input class="form-check-input" type="hidden" name="'.$name.'" value="0" />
                    <input type="checkbox" value="1" class="form-check-input "'.$classError.' name="'.$name.'" '.(empty($value)?'':'checked').'/>
                    </label>';
        }elseif ($options['type']=='password'){
            $input.='<input type="password" class="form-control" name="'.$name.'" id="input'.$name.'" value="'.$value.'"'.$attr.'>';
        }
        if($error){
            $input.='<span class="help-block">'.$error.'</span>';
        }
        $input.='</div>';
        return $input;
    }

    public function select($name, $label, $options){
        $label = '<label for="$input'.$name.'">'.$label.'</label>';
        $input = '<select class="form-control" name="'.$name.'">';
        foreach($options as $k=>$v){
            $attributes = '';
            if($k == $this->getValue($name)){
                $attributes = ' selected';
            }
            $input .= "<option value='$k'$attributes>$v</option>";
        }
        $input .= '</select>';

        return $this->surround($label . $input);
    }


    public function submit($name){
        return $this->surround('<button type="submit" name="'.$name.'" class="btn btn-primary">'.$name.'</button>');
    }
}