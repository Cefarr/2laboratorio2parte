/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.pdsw.draw.gui;

import eci.pdsw.draw.controller.Controller;
import eci.pdsw.draw.controller.IController;
import eci.pdsw.draw.model.ElementType;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

/**
 *
 * @author fchaves
 */
public class GUI extends JFrame {

    private ShapeCanvas shapeCanvas; 
    private ShapeList shapeList;
    private ShapeNumber shapeNumber;
    private JSplitPane splitPane;
    private IController controller;
    private RenderStyle renderStyle;
    private JMenu styleMenu;
        
    public final JMenuBar menu() {
        JMenuBar result = new JMenuBar();
        JMenu shapeMenu = new JMenu("Shape");
        
        for(final ElementType elem : ElementType.values()) {
            JMenuItem shapeMenuItem = new JMenuItem(new AbstractAction(elem.name()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                     controller.setSelectedElementType(elem);
                }
            });
            shapeMenu.add(shapeMenuItem);                    
        }

        styleMenu = new JMenu("View Style");

        JMenuItem undoMenu = new JMenuItem(new AbstractAction("Undo") {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undo();
                
            }
            
        });

        JMenuItem redoMenu = new JMenuItem(new AbstractAction("Redo") {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.redo();
            }
            
        });
        
        JMenu transformsMenu=new JMenu("Transformations");
        
        JMenuItem duplicate = new JMenuItem(new AbstractAction("Duplicate") {

            @Override
            public void actionPerformed(ActionEvent e) {                
                shapeCanvas.duplicateShapes();                
            }
            
        });

        JMenuItem rotate = new JMenuItem(new AbstractAction("Rotate selected shape clockwise") {

            @Override
            public void actionPerformed(ActionEvent e) {                
                
                if (shapeList.getSelectedShapeIndex()==-1){
                    JOptionPane.showMessageDialog(rootPane, "No shape selected.");
                }
                else{
                    shapeCanvas.rotateSelectedShape(shapeList.getSelectedShapeIndex());                    
                }
                
            }
            
        });


        
        transformsMenu.add(duplicate);
        transformsMenu.add(rotate);
        
        
        
        

        result.add(shapeMenu);
        result.add(styleMenu);
        result.add(undoMenu);
        result.add(redoMenu);
        result.add(transformsMenu);
        result.setLayout(new FlowLayout(FlowLayout.LEFT));       
        return result;
    }
    
    public GUI() {
        super("CAD-Tool");
        this.controller=new Controller();
        this.renderStyle=new RenderStyleFill();
    }

    public final void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.add(menu(),BorderLayout.NORTH);
        this.controller.setRenderer(renderStyle.styles()[0].getRenderer());
        this.shapeCanvas = new ShapeCanvas(controller);
        this.shapeList = new ShapeList(controller);
        this.shapeNumber = new ShapeNumber(controller);
        this.add(shapeNumber,BorderLayout.SOUTH);
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           shapeCanvas, shapeList);
        this.add(splitPane,BorderLayout.CENTER);
        this.setVisible(true);
        splitPane.setDividerLocation(0.80);
        replaceStyleMenu();
    }
    
    private void replaceStyleMenu() {
        while(styleMenu.getItemCount() > 0) {
              styleMenu.remove(0);
        }
        
        for (final Style style : renderStyle.styles()) {
        	JMenuItem styleMenuItem = new JMenuItem(new AbstractAction(style.getName()) {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			controller.setRenderer(style.getRenderer());
        		}
        	});
            styleMenu.add(styleMenuItem);
        }
    }
    
}

