/*
 * @(#)AbstractAttributedDecoratedFigure.java  1.0  January 5, 2007
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors ("JHotDraw.org")
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * JHotDraw.org ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * JHotDraw.org.
 */

package org.jhotdraw.draw;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.xml.*;

/**
 * AbstractAttributedDecoratedFigure.
 *
 * @author Werner Randelshofer
 * @version 1.0 January 5, 2007 Created.
 */
public abstract class AbstractAttributedDecoratedFigure
        extends AbstractAttributedFigure implements DecoratedFigure {
    private Figure decorator;
    
    public final void draw(Graphics2D g) {
        if (decorator != null) {
            drawDecorator(g);
        }
        drawFigure(g);
    }
    protected void drawFigure(Graphics2D g) {
        super.draw(g);
    }
    protected void drawDecorator(Graphics2D g) {
        updateDecoratorBounds();
        decorator.draw(g);
    }
    
    public final Rectangle2D.Double getDrawingArea() {
        Rectangle2D.Double r = getFigureDrawingArea();
        if (decorator != null) {
            updateDecoratorBounds();
            r.add(decorator.getDrawingArea());
        }
        return r;
    }
    protected Rectangle2D.Double getFigureDrawingArea() {
        return super.getDrawingArea();
    }
    
    public void setDecorator(Figure newValue) {
        willChange();
        decorator = newValue;
        if (decorator != null) {
            decorator.setBounds(getStartPoint(), getEndPoint());
        }
        changed();
    }
    
    public Figure getDecorator() {
        return decorator;
    }
    protected void updateDecoratorBounds() {
        if (decorator != null) {
            Point2D.Double sp = getStartPoint();
            Point2D.Double ep = getEndPoint();
            Insets2D.Double decoratorInsets = DECORATOR_INSETS.get(this);
            sp.x -= decoratorInsets.left;
            sp.y -= decoratorInsets.top;
            ep.x += decoratorInsets.right;
            ep.y += decoratorInsets.bottom;
            decorator.setBounds(sp, ep);
        }
    }
    
    public final boolean contains(Point2D.Double p) {
        if (decorator != null) {
            updateDecoratorBounds();
            if (decorator.contains(p)) {
                return true;
            }
        }
        return figureContains(p);
    }
    protected abstract boolean figureContains(Point2D.Double p);
    
    public void read(DOMInput in) throws IOException {
        super.read(in);
        readDecorator(in);
    }
    
    
    public void write(DOMOutput out) throws IOException {
        super.write(out);
        writeDecorator(out);
    }
    protected void writeDecorator(DOMOutput out) throws IOException {
        if (decorator != null) {
            out.openElement("decorator");
            out.writeObject(decorator);
            out.closeElement();
        }
    }
    protected void readDecorator(DOMInput in) throws IOException {
        if (in.getElementCount("decorator") > 0) {
            in.openElement("decorator");
            decorator = (Figure) in.readObject();
            in.closeElement();
        } else {
            decorator = null;
        }
    }
    public AbstractAttributedDecoratedFigure clone() {
        AbstractAttributedDecoratedFigure that = (AbstractAttributedDecoratedFigure) super.clone();
        if (this.decorator != null) {
            that.decorator = (Figure) this.decorator.clone();
        }
        return that;
    }
}
