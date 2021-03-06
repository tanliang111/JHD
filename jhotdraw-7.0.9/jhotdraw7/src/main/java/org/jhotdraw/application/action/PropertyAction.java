/*
 * @(#)PropertyAction.java  1.0  June 18, 2006
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package org.jhotdraw.application.action;

import java.awt.event.*;
import java.beans.*;
import org.jhotdraw.application.DocumentOrientedApplication;
import org.jhotdraw.application.DocumentView;

/**
 * PropertyAction.
 * 
 * 
 * @author Werner Randelshofer.
 * @version 1.0 June 18, 2006 Created.
 */
public class PropertyAction extends AbstractDocumentViewAction {
    private String propertyName;
    private Class[] parameterClass;
    private Object propertyValue;
    private String setterName;
    private String getterName;
    
    private PropertyChangeListener projectListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName() == propertyName) { // Strings get interned
                updateSelectedState();
            }
        }
    };
    
    /** Creates a new instance. */
    public PropertyAction(String propertyName, Object propertyValue) {
        this(propertyName, propertyValue.getClass(), propertyValue);
    }
    public PropertyAction(String propertyName, Class propertyClass, Object propertyValue) {
        this.propertyName = propertyName;
        this.parameterClass = new Class[] { propertyClass };
        this.propertyValue = propertyValue;
        setterName = "set"+Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        getterName = ((propertyClass == Boolean.TYPE || propertyClass == Boolean.class) ? "is" : "get")+
                Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        updateSelectedState();
    }
    
    public void actionPerformed(ActionEvent evt) {
        DocumentView p = getCurrentView();
        try {
            p.getClass().getMethod(setterName, parameterClass).invoke(p, new Object[] {propertyValue});
        } catch (Throwable e) {
            InternalError error = new InternalError("Method invocation failed");
            error.initCause(e);
            throw error;
        }
    }
    
    protected void installProjectListeners(DocumentView p) {
        super.installProjectListeners(p);
        p.addPropertyChangeListener(projectListener);
        updateSelectedState();
    }
    /**
     * Installs listeners on the documentView object.
     */
    protected void uninstallProjectListeners(DocumentView p) {
        super.uninstallProjectListeners(p);
        p.removePropertyChangeListener(projectListener);
    }
    
    private void updateSelectedState() {
        boolean isSelected = false;
        DocumentView p = getCurrentView();
        if (p != null) {
            try {
                Object value = p.getClass().getMethod(getterName, (Class[]) null).invoke(p);
                isSelected = value == propertyValue ||
                        value != null && propertyValue != null &&
                        value.equals(propertyValue);
            } catch (Throwable e) {
                InternalError error = new InternalError("Method invocation failed");
                error.initCause(e);
                throw error;
            }
        }
        putValue(Actions.SELECTED_KEY, isSelected);
    }
}
