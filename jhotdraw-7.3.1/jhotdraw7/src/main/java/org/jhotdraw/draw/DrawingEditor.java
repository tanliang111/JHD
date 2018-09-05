/*
 * @(#)DrawingEditor.java
 *
 * Copyright (c) 1996-2009 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.draw;

import java.awt.*;
import java.beans.*;
import java.util.*;
import javax.swing.ActionMap;
import javax.swing.InputMap;

/**
 * A <em>drawing editor</em> coordinates drawing tools and drawing views.
 * 
 * <p><b>Usage of drawing editors with the application framework</b>
 * For {@link org.jhotdraw.app.Application}s which provide a single document
 * interface (SDI) there is typically one drawing editor instance per
 * {@link org.jhotdraw.app.View}. So that each view can have its own toolbars
 * and drawing palettes.
 * <p>
 * For applications with a Windows-style multiple document interface (MDI) there
 * is typically one drawing editor instance per parent window. All views within
 * a parent window share the toolbars and drawing palettes provided be the
 * parent window.
 * <p>
 * For applications with a Mac OS X-style application document interface (OSX) 
 * there is typically a single drawing editor instance for the application. All
 * views within the application share a single set of toolbars and
 * drawing palettes.
 *
 * <hr>
 * <b>Design Patterns</b>
 *
 * <p><em>Framework</em><br>
 * The following interfaces define the contracts of a framework for structured
 * drawing editors:<br>
 * Contract: {@link Drawing}, {@link Figure}, {@link CompositeFigure},
 * {@link ConnectionFigure}, {@link Connector}, {@link DrawingView},
 * {@link DrawingEditor}, {@link Handle} and {@link Tool}.
 *
 * <p><em>Mediator</em><br>
 * {@code DrawingEditor} acts as a mediator for coordinating drawing tools
 * and drawing views:<br>
 * Mediator: {@link DrawingEditor}; Colleagues: {@link DrawingView}, {@link Tool}.
 *
 * <p><em>Mediator</em><br>
 * {@code DrawingEditor} acts as a mediator for coordinating keyboard input from
 * {@code Tool}s and Swing action objects:<br>
 * Mediator: {@link DrawingEditor}; Colleagues: {@link Tool}, javax.swing.Action.
 *
 * <p><em>Model-View-Controller</em><br>
 * The following classes implement together the Model-View-Controller design
 * pattern:<br>
 * Model: {@link Drawing}; View: {@link DrawingView}; Controller:
 * {@link DrawingEditor}.
 *
 * <p><em>Observer</em><br>
 * State changes of tools can be observed by other objects. Specifically
 * {@code DrawingEditor} observes area invalidations of tools and repaints
 * its active drawing view accordingly.<br>
 * Subject: {@link Tool}; Observer: {@link ToolListener}; Event:
 * {@link ToolEvent}; Concrete Observer: {@link DrawingEditor}.
 *
 * <p><em>Proxy</em><br>
 * To remove the need for null-handling, {@code AbstractTool} makes use of
 * a proxy for {@code DrawingEditor}.
 * Subject: {@link DrawingEditor}; Proxy: {@link DrawingEditorProxy};
 * Client: {@link AbstractTool}.
 * <hr>
 * 
 * @author Werner Randelshofer
 * @version $Id: DrawingEditor.java 574 2009-10-14 21:38:03Z rawcoder $
 */
public interface DrawingEditor {

    /**
     * The property name for the active view property.
     */
    public final static String ACTIVE_VIEW_PROPERTY = "activeView";
    /**
     * The property name for the active tool property.
     */
    public final static String TOOL_PROPERTY = "tool";
    /**
     * The property name for the input map property.
     */
    public final static String INPUT_MAP_PROPERTY = "inputMap";
    /**
     * The property name for the action map property.
     */
    public final static String ACTION_MAP_PROPERTY = "actionMap";

    /**
     * Gets the editor's current drawing.
     * /
    Drawing getDrawing();
    /**
     * Sets the editor's current drawing.
     * /
    void setDrawing(Drawing drawing);
     */
    /**
     * Adds a drawing view to the editor.
     * The editor invokes addNotify on the view, and it registers its tool
     * as an event listener on the view.
     */
    void add(DrawingView view);

    /**
     * Removes a drawing view from the editor.
     * The editor invokes removeNotify on the view, and it unregisters its tool
     * on the view.
     */
    void remove(DrawingView view);

    /**
     * Gets all drawing views associated with this editor.
     */
    Collection<DrawingView> getDrawingViews();

    /**
     * Gets the editor's active drawing view.
     * This can be null, if the editor has no views.
     */
    DrawingView getActiveView();

    /**
     * Sets the editor's active drawing view.
     * This can be set to null, if the editor has no views.
     */
    void setActiveView(DrawingView newValue);

    /**
     * Calls deactivate on the previously active tool of this drawing editor.
     * Calls activate on the provided tool.
     * Forwards all mouse, mouse moation and keyboard events that occur on the
     * DrawingView to the provided tool.
     * <p>
     * This is a bound property.
     */
    void setTool(Tool t);

    /**
     * Gets the current tool.
     * <p>
     * This is a bound property.
     */
    Tool getTool();

    /**
     * Sets the cursor on the view(s) of the drawing editor.
     */
    void setCursor(Cursor c);

    /**
     * Finds a handle at the given coordinates.
     * @return A handle, null if no handle is found.
     * /
    public Handle findHandle(Point p);
     */
    /**
     * Finds a drawing view.
     * This is used by Tool to identify the view of which it has received
     * an event.
     */
    public DrawingView findView(Container c);

    /**
     * Sets a default attribute of the editor.
     * The default attribute will be used by creation tools, to create a new
     * figure.
     */
    public <T> void setDefaultAttribute(AttributeKey<T> key, T value);

    /**
     * Gets a default attribute from the editor.
     * The default attribute will be used by creation tools, to create a new
     * figure.
     */
    public <T> T getDefaultAttribute(AttributeKey<T> key);

    /**
     * Applies the default attributes to the specified figure.
     */
    public void applyDefaultAttributesTo(Figure f);

    /**
     * Returns an immutable Map with the default attributes of this editor.
     */
    public Map<AttributeKey, Object> getDefaultAttributes();

    /**
     * Sets a handle attribute of the editor.
     * The default attribute will be used by creation tools, to create a new
     * figure.
     * 
     * @param key AttributeKey. 
     * @param value Attribute value. 
     */
    public <T> void setHandleAttribute(AttributeKey<T> key, T value);

    /**
     * Gets a handle attribute from the editor.
     * The default attribute will be used by creation tools, to create a new
     * figure.
     * 
     * @param key AttributeKey. 
     * 
     * @return If the handle attribute has been set, returns the previously
     * set value. If the handle attribute has not been set, returns key.getDefaultValue().
     */
    public <T> T getHandleAttribute(AttributeKey<T> key);

    /**
     * Sets the input map used by the tool of this drawing editor as a fall back
     * for handling keyboard events.
     * <p>
     * This is a bound property.
     */
    public void setInputMap(InputMap newValue);
    /**
     * Sets the input map used by the tool of this drawing editor as a fall back
     * for handling keyboard events.
     * <p>
     * {@code Tool}s use the input map of the drawing editor to determine what
     * to do when a key was pressed that the tool can not handle.
     * <p>
     * This is a bound property.
     */
    public InputMap getInputMap();

    /**
     * Sets the action map used by the tool of this drawing editor as a fall back
     * for performing actions.
     * <p>
     * This is a bound property.
     */
    public void setActionMap(ActionMap newValue);

    /**
     * Gets the action map used by the tool of this drawing editor as a fall back
     * for performing actions.
     * <p>
     * {@code Tool}s use the action map of the drawing editor to determine what
     * to do when an action needs to be invoked from the InputMap of the
     * drawing editor.
     * <p>
     * This is a bound property.
     */
    public ActionMap getActionMap();

    /**
     * Sets the enabled state of the drawing editor.
     * This is a bound property.
     */
    public void setEnabled(boolean newValue);

    /**
     * Gets the enabled state of the drawing editor.
     */
    public boolean isEnabled();

    /**
     * Adds a {@code PropertyChangeListener}.
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a {@code PropertyChangeListener}.
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);
}
