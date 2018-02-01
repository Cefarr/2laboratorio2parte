/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.pdsw.draw.controller;

import eci.pdsw.pattern.command.Command;
import eci.pdsw.draw.gui.shapes.Renderer;
import eci.pdsw.draw.model.ElementType;
import eci.pdsw.draw.model.ShapeFactory;
import eci.pdsw.draw.model.Point;
import eci.pdsw.draw.model.Shape;
import eci.pdsw.pattern.observer.Observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author fchaves
 */
public class Controller implements IController {
    private ElementType selectedElement = ElementType.Line;
    
    private final ShapeFactory shapeFactory = new ShapeFactory();
    private final List<Shape> shapes = new ArrayList<>();
    
    private List<Observer> observers = new ArrayList<>();
        
    private Renderer renderer;
    
    public Controller() {
    }
    
    @Override
    public void addShapeFromScreenPoints(java.awt.Point p1,java.awt.Point p2) {
        Point mp1 = Point.newPoint(new Float(p1.x), new Float(p1.y));
        Point mp2 = Point.newPoint(new Float(p2.x), new Float(p2.y));
       
        ElementType actualElementType = getSelectedElementType();
    	setSelectedElementType(actualElementType);
        addShape(mp1, mp2);                 

    }

    
     /**
     * Duplica todas las figuras, y las ubica en una nueva posicion.
     * @pre la coleccion 'shapes' no tiene referencias duplicadas
     * @pos la coleccion 'shapes' contiene el doble de figuras
     * @pos la coleccion 'shapes' no tiene referencias duplicadas
     */
    @Override
    public void duplicateShapes(){
        
        List<Point> newShapesFirstPoints=new LinkedList<>();
        List<Point> newShapesSecondPoints=new LinkedList<>();
        
        int displacementDelta=10+new Random(System.currentTimeMillis()).nextInt(50);
        
        for (Shape s:shapes){
            newShapesFirstPoints.add(new Point(s.getPoint1().getX(),s.getPoint1().getY()+displacementDelta));
            newShapesSecondPoints.add(new Point(s.getPoint2().getX(),s.getPoint2().getY()+displacementDelta));
        }
        Iterator<Point> it1=newShapesFirstPoints.iterator();
        Iterator<Point> it2=newShapesSecondPoints.iterator();
        
        while (it1.hasNext() && it2.hasNext()){
            addShape(it1.next(), it2.next());
        }
                
        
    }
  
    
    @Override
    public void addShape(Point p1,Point p2) {
        shapes.add(shapeFactory.createShape(selectedElement, p1, p2));
        notifyObservers();
    }
    
    @Override
    public void undo() {
    	throw new RuntimeException("No se ha implemenado UNDO");
    }

    @Override
    public void redo() {
    	throw new RuntimeException("No se ha implemenado UNDO");
    }

    @Override
    public void addShape(Integer index, Shape shape) {
        shapes.add(index,shape);
        notifyObservers();
    }   
    
    @Override
    public void deleteShape(Integer index) {
        int idx = index;
        shapes.remove(idx);
        
        //notificar a la capa de presentación
        notifyObservers();
    }
    
    /**
     * Rota la figura correspondiente a la posicion 'index' un angulo
     * de 90 grados a la derecha, usando como eje de rotación la esquina
     * inferior izquierda del rectángulo que contenga a la figura.
     * @param index la posicion de la figura en el conjunto de figuras
     * del controlador
     */
    @Override
    public void rotateSelectedShape(Integer index) {
        
        double angulo = 90; //grados
        double anguloRadianes = Math.toRadians(angulo);
        int idx=index;
        Shape prob=shapes.get(index);
        
        int pru=prob.getGir();
        Point pp1=prob.getPoint1();
        Point pp2=prob.getPoint2();
        
        float p1x=pp1.getX();
        float p1y=pp1.getY();
        float p2x=pp2.getX();
        float p2y=pp2.getX();
        switch (pru){
            case 0:        p1x=pp1.getY()*1;        
                           p1y=pp1.getX();
                           p2x=pp2.getY()*1;        
                           p2y=pp2.getX();                
                           pp1.setX(p1x);
                           pp1.setY(p1y);
                           pp2.setX(p2x);
                           pp2.setY(p2y);
                           prob.setGir(1);
                           break;
           case 1:         p1x=pp1.getX()*-1;        
                           p1y=pp1.getY()*-1;
                           p2x=pp2.getX()*1;        
                           p2y=pp2.getY()*-1;                
                           pp1.setX(p1x);
                           pp1.setY(p1y);
                           pp2.setX(p2x);
                           pp2.setY(p2y);
                           prob.setGir(2);
                           break;
           case 2:         p1x=pp1.getY();        
                           p1y=pp1.getX()*-1;
                           p2x=pp2.getY();        
                           p2y=pp2.getX()*-1;                
                           pp1.setX(p1x);
                           pp1.setY(p1y);
                           pp2.setX(p2x);
                           pp2.setY(p2y);
                           prob.setGir(0);
                           break;
               
        }
         
        prob.setPoint1(pp1);
        prob.setPoint2(pp2); 
        //notificar a la capa de presentación
        notifyObservers();        
    }    
    
    
    @Override
    public void setRenderer(Renderer renderer) {
    	this.renderer = renderer;
    	notifyObservers();
    }
    
    @Override
    public Renderer getRenderer() {
    	return this.renderer;
    }

    
    @Override
    public List<Shape> getShapes() {
        return shapes;
    }
    
    @Override
    public void setSelectedElementType(ElementType elementType) {
        this.selectedElement = elementType;
    }  
    
    @Override
    public ElementType getSelectedElementType() {
        return this.selectedElement;
    }

    @Override
    public void addObserver(Observer o) {
            observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
            observers.remove(o);
    }

    @Override
    public void notifyObservers() {
            for(Observer o : observers) {
                    o.update();
            }
    }


}
