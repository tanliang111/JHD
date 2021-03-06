/*
 * @(#)ToggleLineWrapAction.java  1.0  October 1, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package org.jhotdraw.samples.teddyapplication.action;

import application.*;
import org.jhotdraw.application.*;
import org.jhotdraw.application.action.*;
import org.jhotdraw.samples.teddyapplication.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * ToggleLineWrapAction.
 *
 * @author  Werner Randelshofer
 * @version 1.0 October 1, 2005 Created.
 */
public class ToggleLineWrapAction extends AbstractDocumentViewAction {
    public final static String ID = "View.wrapLines";
    
    /**
     * Creates a new instance.
     */
    public ToggleLineWrapAction() {
        initActionProperties(ID);
    }
    
    public ResourceMap getResourceMap() {
        return ApplicationContext.getInstance().getResourceMap(TeddyView.class);
    }
    
    public void actionPerformed(ActionEvent e) {
        getCurrentView().setLineWrap(! getCurrentView().isLineWrap());
    }
    
    public TeddyView getCurrentView() {
        return (TeddyView) super.getCurrentView();
    }
    
    protected void updateProperty() {
        putValue(
                Actions.SELECTED_KEY,
                getCurrentView() != null && getCurrentView().isLineWrap()
                );
    }
}
