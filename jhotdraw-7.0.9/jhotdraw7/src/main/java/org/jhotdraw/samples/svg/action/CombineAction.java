/*
 * @(#)CombinePathsAction.java  1.0  2006-07-12
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

package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.undo.*;
import org.jhotdraw.util.*;
import java.util.*;
import javax.swing.*;
import javax.swing.undo.*;

/**
 * CombinePathsAction.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2006-07-12 Created.
 */
public class CombineAction extends GroupAction {
    public final static String ID = "selectionCombine";
    
    /** Creates a new instance. */
    public CombineAction(DrawingEditor editor) {
        super(editor, new SVGPathFigure());
        
        labels = ResourceBundleUtil.getLAFBundle(
                "org.jhotdraw.samples.svg.Labels",
                Locale.getDefault()
                );
        labels.configureAction(this, ID);
    }
    
   @Override protected boolean canGroup() {
        boolean canCombine = getView().getSelectionCount() > 1;
        if (canCombine) {
            for (Figure f : getView().getSelectedFigures()) {
                if (!(f instanceof SVGPathFigure)) {
                    canCombine = false;
                    break;
                }
            }
        }
        return canCombine;
    }
    public Collection<Figure> ungroupFigures(DrawingView view, CompositeFigure group) {
        LinkedList<Figure> figures = new LinkedList<Figure>(group.getChildren());
        view.clearSelection();
        group.basicRemoveAllChildren();
        LinkedList<Figure> paths = new LinkedList<Figure>();
        for (Figure f : figures) {
            SVGPathFigure path = new SVGPathFigure();
            path.removeAllChildren();
            for (Map.Entry<AttributeKey,Object> entry : group.getAttributes().entrySet()) {
                path.setAttribute(entry.getKey(), entry.getValue());
            }
            path.add(f);
            view.getDrawing().basicAdd(path);
            paths.add(path);
        }
        view.getDrawing().remove(group);
        view.addToSelection(paths);
        return figures;
    }
    public void groupFigures(DrawingView view, CompositeFigure group, Collection<Figure> figures) {
        Collection<Figure> sorted = view.getDrawing().sort(figures);
        view.getDrawing().basicRemoveAll(figures);
        view.clearSelection();
        view.getDrawing().add(group);
        group.willChange();
      ((SVGPathFigure) group).removeAllChildren();
        for (Map.Entry<AttributeKey,Object> entry : figures.iterator().next().getAttributes().entrySet()) {
            group.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Figure f : sorted) {
            SVGPathFigure path = (SVGPathFigure) f;
            // XXX - We must fire an UndoableEdito for the flattenTransform!
            path.flattenTransform();
            for (Figure child : path.getChildren()) {
                group.basicAdd(child);
            }
        }
        group.changed();
        view.addToSelection(group);
    }
}
