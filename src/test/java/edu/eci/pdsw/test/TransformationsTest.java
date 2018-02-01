/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.test;

import eci.pdsw.draw.controller.Controller;
import eci.pdsw.draw.model.ElementType;
import eci.pdsw.draw.model.Shape;
import eci.pdsw.util.Pair;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.quicktheories.core.Gen;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.Generate.*;
import static org.quicktheories.generators.SourceDSL.*;

/**
 *
 * @author hcadavid, fchaves
 */
public class TransformationsTest {
    
    public TransformationsTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void duplicateTestSize(){
        qt().forAll(linesController()
                    .describedAs((c) -> "Controller Shapes size = " + c.getShapes().size()))
            .check((controller) -> {
                    int n = controller.getShapes().size();
                    controller.duplicateShapes();
                    int m = controller.getShapes().size();
                    
                    return m == 2 * n;
                });
    }
    /**
     *Esta clase gira 90 grados una de las figuras seleccionadas, no necesita parametros y acepta cualquier figura.
     * La prueba es para corroborar el funcionamiento del metodo de rotateSelectedShape.
     * EL resultado esperado es que la ficha gire 90 grados con respecto a su posicion.
     */
    @Test
    public void rotateTransformation90Grades(){
    
        qt().forAll(linesController()
                .describedAs((c) -> "Control de la rotacion de las piezas"+ c.getShapes().size()))
                .check((controller) ->{
                
                    int n = controller.getShapes().size();
                    controller.rotateSelectedShape();           
                    
                }

    }
    
        /**
     *Esta clase gira 180 grados una de las figuras seleccionadas, no necesita parametros y acepta cualquier figura.
     * La prueba es para corroborar el funcionamiento del metodo de rotateSelectedShape.
     * EL resultado esperado es que la ficha gire 180 grados con respecto a su posicion.
     */
    @Test
    public void rotateTransformation180Grades(){
    
        qt().forAll(linesController()
                .describedAs((c) -> "Control de la rotacion de las piezas"+ c.getShapes().size()))
                .check((controller) ->{
                
                    int n = controller.getShapes().size();
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           

                           
                }

    }
        
        /**
     *Esta clase gira 270 grados una de las figuras seleccionadas, no necesita parametros y acepta cualquier figura.
     * La prueba es para corroborar el funcionamiento del metodo de rotateSelectedShape.
     * EL resultado esperado es que la ficha gire 270 grados con respecto a su posicion.
     */
    @Test
    public void rotateTransformation270Grades(){
    
        qt().forAll(linesController()
                .describedAs((c) -> "Control de la rotacion de las piezas"+ c.getShapes().size()))
                .check((controller) ->{
                
                    int n = controller.getShapes().size();
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                }

    }
    
            /**
     *Esta clase gira 360 grados una de las figuras seleccionadas, no necesita parametros y acepta cualquier figura.
     * La prueba es para corroborar el funcionamiento del metodo de rotateSelectedShape.
     * EL resultado esperado es que la ficha gire 360 grados con respecto a su posicion.
     */
    @Test
    public void rotateTransformation360Grades(){
    
        qt().forAll(linesController()
                .describedAs((c) -> "Control de la rotacion de las piezas"+ c.getShapes().size()))
                .check((controller) ->{
                
                    int n = controller.getShapes().size();
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                    controller.rotateSelectedShape();           
                }

    }
    
    
    @Test
    public void duplicateTestNotDuplicatedReferences() {
        qt().forAll(linesController()
                    .describedAs((c) -> "Controller Shapes size = " + c.getShapes()))
            .check((controller) -> {
                    controller.duplicateShapes();
                    List<Shape> shapes = controller.getShapes();

                    return shapes.stream()
                        .allMatch((si) -> 
                                  shapes.stream().filter((sj) -> si == sj ).count() == 1);
                });
    }
    /**
     *Esta clase gira 90 grados una de las figuras seleccionadas, no necesita parametros y acepta cualquier figura.
     * La prueba es para corroborar el funcionamiento del metodo de rotateSelectedShape.
     * EL resultado esperado es que la ficha gire 90 grados con respecto a su posicion.
     */
    @Test
    public void rotateTransformation90Grades(){
    
        qt().forAll(linesController()
                .describedAs((c) -> "Control de la rotacion de las piezas"+ c.getShapes().size()))
                .check((controller) ->{
                
                    int n = controller.getShapes().size();
                    controller.rotateSelectedShape();           
                }

    }
    // Test cases generators

    /**
     **/
    private Gen<Controller> linesController() {
        return listsLineAsPoints().map( (ls) -> {
                    Controller guictrl=new Controller();        
                    guictrl.setSelectedElementType(ElementType.Line);
                    for(Pair<java.awt.Point,java.awt.Point> p : ls) {
                        guictrl.addShapeFromScreenPoints(p.getFirst(),p.getSecond());                        
                    }
                    return guictrl;
                });
    }

    private Gen<List<Pair<java.awt.Point,java.awt.Point>>> listsLineAsPoints() {        
        return lists().of(pairOfPoints()).ofSizeBetween(0,20);
    }

    private Gen<Pair<java.awt.Point,java.awt.Point>> pairOfPoints() {
        return points().zip(points(),(p1,p2) -> new Pair<>(p1,p2));
    }

    private Gen<Pair<java.awt.Point,java.awt.Point>> pairOfPointsEqY() {
        return pairOfIntegers().zip(integers().allPositive(),(pair,y) -> {
            java.awt.Point p1 = new java.awt.Point(pair.getFirst(),y);
            java.awt.Point p2 = new java.awt.Point(pair.getSecond(),y);
            return new Pair<>(p1,p2);
        });
    }

    private Gen<Pair<java.awt.Point,java.awt.Point>> pairOfPointsEqX() {
        return integers().allPositive().zip(pairOfIntegers(),(x,pair) -> {
            java.awt.Point p1 = new java.awt.Point(x, pair.getFirst());
            java.awt.Point p2 = new java.awt.Point(x, pair.getSecond());
            return new Pair<>(p1,p2);
        });
    }

    private Gen<java.awt.Point> points() {
        return pairOfIntegers().map(p -> new java.awt.Point(p.getFirst(),p.getSecond()));
    }

    private Gen<Pair<Integer,Integer>> pairOfIntegers() {
        return integers().allPositive()
            .zip(integers().allPositive(), (x,y) -> new Pair<>(x,y));
    }
   
}
