/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.pdsw.draw.controller;

import eci.pdsw.draw.gui.shapes.Renderer;
import eci.pdsw.draw.model.ElementType;
import eci.pdsw.draw.model.Point;
import eci.pdsw.draw.model.Shape;
import eci.pdsw.pattern.observer.Observer;
import java.util.List;

/**
 *
 * @author 2098325
 */
public class ComandoConcretoDibujar implements Comando{

    private IController dibuj;
    public ComandoConcretoDibujar(IController dibuj){
        this.dibuj=dibuj;
    
    }
    
    
    @Override
    public void execute() {
    
        //dibuj.addShapeFromScreenPoints();
        
    }

    
}
