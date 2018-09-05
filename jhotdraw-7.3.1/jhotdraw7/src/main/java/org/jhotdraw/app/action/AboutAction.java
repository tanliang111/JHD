/*
 * @(#)AboutAction.java
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.app.*;

/**
 * Displays a dialog showing information about the application.
 *
 * @author  Werner Randelshofer
 * @version $Id: AboutAction.java 560 2009-09-09 11:50:38Z rawcoder $
 */
public class AboutAction extends AbstractApplicationAction {
    public final static String ID = "application.about";
    
    /** Creates a new instance. */
    public AboutAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        }
    
    public void actionPerformed(ActionEvent evt) {
        Application app = getApplication();
        JOptionPane.showMessageDialog(app.getComponent(),
                app.getName()+" "+app.getVersion()+"\n"+app.getCopyright()+
                "\n\nRunning on" +
                "\n  Java: "+System.getProperty("java.version")+
                ", "+System.getProperty("java.vendor")+
                "\n  JVM: "+System.getProperty("java.vm.version")+
                ", "+System.getProperty("java.vm.vendor")+
                "\n  OS: "+System.getProperty("os.name")+" "+System.getProperty("os.version")+
                ", "+System.getProperty("os.arch"),
                "About", JOptionPane.PLAIN_MESSAGE);
    }
}
