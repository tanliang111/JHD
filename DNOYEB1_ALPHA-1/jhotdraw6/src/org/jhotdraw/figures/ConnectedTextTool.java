/*
 * @(#)ConnectedTextTool.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.figures;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.util.Undoable;


/**
 * Tool to create new or edit existing text figures.
 * A new text figure is connected with the clicked figure.
 *
 * @see CH.ifa.draw.standard.TextHolder
 *
 * @version <$CURRENT_VERSION$>
 */
public  class ConnectedTextTool extends TextTool {

	private boolean fConnected = false;
	private Figure myConnectedFigure;

	public ConnectedTextTool(DrawingEditor editor, Figure prototype) {
		super(editor, prototype);
	}

	/**
	 * If the pressed figure is a TextHolder it can be edited otherwise
	 * a new text figure is created.
	 */
	public void mouseDown(DrawingViewMouseEvent dvme) {
		super.mouseDown(dvme);

		setConnectedFigure(drawing().findFigureInside(dvme.getX(), dvme.getY()));
		TextHolder textHolder = getTypingTarget();
		if (!fConnected && (getConnectedFigure() != null) && (textHolder != null) && (getConnectedFigure() != textHolder)) {
			textHolder.connect(getConnectedFigure());
			getConnectedFigure().addDependendFigure(getAddedFigure());
			fConnected = true;
		}
	}

	protected void endEdit() {
		super.endEdit();
		if (getUndoActivity() != null) {
			((ConnectedTextTool.UndoActivity)getUndoActivity()).setConnectedFigure(getConnectedFigure());
		}
		else {
			getConnectedFigure().removeDependendFigure(getAddedFigure());
		}
	}

	protected void setConnectedFigure(Figure pressedFigure) {
		myConnectedFigure = pressedFigure;
	}

	public Figure getConnectedFigure() {
		return myConnectedFigure;
	}

	/**
	 * If the pressed figure is a TextHolder it can be edited otherwise
	 * a new text figure is created.
	 */
	public void activate() {
		super.activate();
		fConnected = false;
	}

	/**
	 * Factory method for undo activity
	 */
	protected Undoable createUndoActivity() {
		return new ConnectedTextTool.UndoActivity(view(), getTypingTarget().getText());
	}

	public static class UndoActivity extends TextTool.UndoActivity {
		private Figure myConnectedFigure;

		public UndoActivity(DrawingView newDrawingView, String newOriginalText) {
			super(newDrawingView, newOriginalText);
		}

		/*
		 * Undo the activity
		 * @return true if the activity could be undone, false otherwise
		 */
		public boolean undo() {
			if (!super.undo()) {
				return false;
			}

			FigureEnumeration fe = getAffectedFigures();
			while (fe.hasNextFigure()) {
				Figure currentFigure = fe.nextFigure();

				if (currentFigure.getTextHolder() != null) {
					// the text figure didn't exist before
					if (!isValidText(getOriginalText())) {
						currentFigure.getTextHolder().disconnect(getConnectedFigure());
					}
					// the text figure did exist but was remove
					else if (!isValidText(getBackupText())) {
						currentFigure.getTextHolder().connect(getConnectedFigure());
					}
				}
			}

			return true;
		}

		/*
		 * Redo the activity
		 * @return true if the activity could be redone, false otherwise
		 */
		public boolean redo() {
			if (!super.redo()) {
				return false;
			}

			FigureEnumeration fe = getAffectedFigures();
			while (fe.hasNextFigure()) {
				Figure currentFigure = fe.nextFigure();
				if (currentFigure.getTextHolder() != null) {
					// the text figure did exist but was remove
					if (!isValidText(getBackupText())) {
						currentFigure.getTextHolder().disconnect(getConnectedFigure());
					}
					// the text figure didn't exist before
					else if (!isValidText(getOriginalText())) {
						currentFigure.getTextHolder().connect(getConnectedFigure());
					}
				}
			}

			return true;
		}

		public void setConnectedFigure(Figure newConnectedFigure) {
			myConnectedFigure = newConnectedFigure;
		}

		public Figure getConnectedFigure() {
			return myConnectedFigure;
		}
	}
}
