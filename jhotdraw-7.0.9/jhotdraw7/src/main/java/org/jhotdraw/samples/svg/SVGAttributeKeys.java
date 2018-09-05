/*
 * @(#)SVGAttributeKeys.java  1.2  2007-04-22
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

package org.jhotdraw.samples.svg;

import java.awt.*;
import java.awt.geom.*;
import org.jhotdraw.draw.*;

/**
 * SVGAttributeKeys.
 *
 * @author Werner Randelshofer
 * @version 1.2 2007-04-22 Attribute Key LINK added. 
 * <br>1.1 2007-04-10 Attribute key TEXT_ALIGN added. 
 * <br>1.0 December 9, 2006 Created.
 */
public class SVGAttributeKeys extends AttributeKeys {
    
    public enum TextAnchor {
        START, MIDDLE, END
    }
    /**
     * Specifies the text anchor of a SVGText figure.
     */
    public final static AttributeKey<TextAnchor> TEXT_ANCHOR = new AttributeKey<TextAnchor>("textAnchor",TextAnchor.START, false);
    
    public enum TextAlign {
        START, CENTER, END
    }
    /**
     * Specifies the text alignment of a SVGText figure.
     */
    public final static AttributeKey<TextAlign> TEXT_ALIGN = new AttributeKey<TextAlign>("textAlign",TextAlign.START, false);
    /**
     * Specifies the fill gradient of a SVG figure.
     */
    public final static AttributeKey<Gradient> FILL_GRADIENT = new AttributeKey<Gradient>("fillGradient", null);
    
    /**
     * Specifies the fill opacity of a SVG figure.
     * This is a value between 0 and 1 whereas 0 is translucent and 1 is fully opaque.
     */
    public final static AttributeKey<Double> FILL_OPACITY = new AttributeKey<Double>("fillOpacity", 1d, false);
    /**
     * Specifies the overall opacity of a SVG figure.
     * This is a value between 0 and 1 whereas 0 is translucent and 1 is fully opaque.
     */
    public final static AttributeKey<Double> OPACITY = new AttributeKey<Double>("opacity", 1d, false);
    
    
    /**
     * Specifies the stroke gradient of a SVG figure.
     */
    public final static AttributeKey<Gradient> STROKE_GRADIENT = new AttributeKey<Gradient>("strokeGradient", null);
    /**
     * Specifies the stroke opacity of a SVG figure.
     * This is a value between 0 and 1 whereas 0 is translucent and 1 is fully opaque.
     */
    public final static AttributeKey<Double> STROKE_OPACITY = new AttributeKey<Double>("strokeOpacity", 1d, false);
    
    /**
     * Specifies a link.
     * In an SVG file, the link is stored in a "a" element which encloses the
     * figure.
     * http://www.w3.org/TR/SVGMobile12/linking.html#AElement
     */
    public final static AttributeKey<String> LINK = new AttributeKey<String>("link", null);
    
    
    /**
     * Gets the fill paint for the specified figure based on the attributes
     * FILL_GRADIENT, FILL_OPACITY, FILL_PAINT and the bounds of the figure.
     * Returns null if the figure is not filled.
     */
    public static Paint getFillPaint(Figure f) {
        double opacity = FILL_OPACITY.get(f);
        if (FILL_GRADIENT.get(f) != null) {
            return FILL_GRADIENT.get(f).getPaint(f, opacity);
        }
        Color color = FILL_COLOR.get(f);
        if (color != null) {
            if (opacity != 1) {
                color = new Color(
                        (color.getRGB() & 0xffffff) | (int) (opacity * 255) << 24,
                        true);
            }
        }
        return color;
    }
    /**
     * Gets the stroke paint for the specified figure based on the attributes
     * STROKE_GRADIENT, STROKE_OPACITY, STROKE_PAINT and the bounds of the figure.
     * Returns null if the figure is not filled.
     */
    public static Paint getStrokePaint(Figure f) {
        double opacity = STROKE_OPACITY.get(f);
        if (STROKE_GRADIENT.get(f) != null) {
            return STROKE_GRADIENT.get(f).getPaint(f, opacity);
        }
        Color color = STROKE_COLOR.get(f);
        if (color != null) {
            if (opacity != 1) {
                color = new Color(
                        (color.getRGB() & 0xffffff) | (int) (opacity * 255) << 24,
                        true);
            }
        }
        return color;
    }
    
    
    /** Sets SVG default values. */
    public static void setDefaults(Figure f) {
        // Fill properties
        // http://www.w3.org/TR/SVGMobile12/painting.html#FillProperties
        FILL_COLOR.basicSet(f, Color.black);
        WINDING_RULE.basicSet(f, WindingRule.NON_ZERO);
        
        // Stroke properties
        // http://www.w3.org/TR/SVGMobile12/painting.html#StrokeProperties
        STROKE_COLOR.basicSet(f, null);
        STROKE_WIDTH.basicSet(f, 1d);
        STROKE_CAP.basicSet(f, BasicStroke.CAP_BUTT);
        STROKE_JOIN.basicSet(f, BasicStroke.JOIN_MITER);
        STROKE_MITER_LIMIT.basicSet(f, 4d);
        IS_STROKE_MITER_LIMIT_FACTOR.basicSet(f, false);
        STROKE_DASHES.basicSet(f, null);
        STROKE_DASH_PHASE.basicSet(f, 0d);
        IS_STROKE_DASH_FACTOR.basicSet(f, false);
    }
    /**
     * Returns the distance, that a Rectangle needs to grow (or shrink) to
     * make hit detections on a shape as specified by the FILL_UNDER_STROKE and STROKE_POSITION
     * attributes of a figure.
     * The value returned is the number of units that need to be grown (or shrunk)
     * perpendicular to a stroke on an outline of the shape.
     */
    public static double getPerpendicularHitGrowth(Figure f) {
        double grow;
        if (STROKE_COLOR.get(f) == null && STROKE_GRADIENT.get(f) == null) {
            grow = getPerpendicularFillGrowth(f);
        } else {
            double strokeWidth = AttributeKeys.getStrokeTotalWidth(f);
            grow = getPerpendicularDrawGrowth(f) + strokeWidth / 2d;
        }
        return grow;
    }
}
