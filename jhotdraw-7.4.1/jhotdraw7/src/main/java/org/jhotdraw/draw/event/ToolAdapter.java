/*
 * @(#)ToolAdapter.java
 * 
 * Copyright (c) 2009-2010 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 * 
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.draw.event;

/**
 * An abstract adapter class for receiving {@link ToolEvent}s. This class
 * exists as a convenience for creating {@link ToolListener} objects.
 *
 * @author Werner Randelshofer
 * @version $Id: ToolAdapter.java 604 2010-01-09 12:00:29Z rawcoder $
 */
public class ToolAdapter implements ToolListener {

    public void toolStarted(ToolEvent event) {
    }

    public void toolDone(ToolEvent event) {
    }

    public void areaInvalidated(ToolEvent e) {
    }

    public void boundsInvalidated(ToolEvent e) {
    }

}
