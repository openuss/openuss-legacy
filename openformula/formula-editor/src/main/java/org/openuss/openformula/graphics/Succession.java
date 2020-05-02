/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.*;

import java.util.Vector;

import org.openuss.openformula.io.Cursor;
import org.openuss.openformula.mathml.in.BuildStructure;
import org.openuss.openformula.mathml.in.MathMLImport;
import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Succession stellt ein Sammelobjekt dar. Hier werden horizontal "hintereinanderstehende" Objekte gespeichert
 * und können als ein Objekt dargestellt werden. Dieses Objekt ist einerseits immer das Objekt
 * in der h�chsten Ebene, wird aber auch (fast) immer für Unterobjekte verwendet.
 */
public final class Succession extends Basic implements BuildStructure {
    /**
     * Definiert den Abstand, den die einzelnen Elemente haben.
     */
    private static final int horizontalerAbstandZwischenElementen = 2;
    private static final int vertikalerAbstandZwischenElementen = 4;

    //final int showEmptyXSize=15;
    //final int showEmptyYSize=27;
    private static final boolean acceptLineFeeds = true;
    private boolean useCorrectSize = false;
    private Vector elements;
    private int selStart;
    private int selStop;
    private int cursorPosition;
    private boolean showIfEmpty;

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Erzeugt ein neues Succession-Objekt, die Gr��e des übergeordneten Objektes wird beibehalten.
    //     * Ein leeres Succession-Objekt wird nicht dargestellt.
    //     * @param parent übergeordnetes Objekt
    //     */
    //
    //    public Succession(final Basic parent) {
    //        super(parent);
    //        elements = new Vector();
    //        init(false);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Erzeugt ein neues Succession-Objekt, die Gr��e des übergeordneten Objektes wird übernommen und
    //     * um <code>sizeClass</code> Stufen verkleinert.
    //     * Ein leeres Succession-Objekt wird nicht dargestellt.
    //     * @param parent übergeordnetes Objekt
    //     * @param sizeClass Das Objekt um diese Anzahl von Stufen verkleinern. Die tats�chliche Verkleinerung ist Abh�ngig von der Verkleinerung des Ursprungsobhejtes.
    //     */
    //
    //    public Succession(final Basic parent, final int sizeClass) {
    //        super(parent, sizeClass);
    //        init(false);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /*
    *
                                    
         * @param parent übergeordnetes Objekt
         */

    /**
     * Erzeugt ein neues Succession-Objekt, die Gr��e des übergeordneten Objektes wird beibehalten.
     * Es kann festgelegt werden, dass, falls das Objekt keine Unterelemente enh�lt, es als rechteckiges
     * kleines K�stchen dargestellt werden soll.
     * @param parent übergeordnetes Objekt
     * @param showIfEmpty Falls true: Wenn Objekt leer, rechteckiges K�stchen anzeigen.
     */
    public Succession(final Basic parent, final boolean showIfEmpty) {
        super(parent);
        init(showIfEmpty);
    }

    /**
     * Erzeugt ein neues Succession-Objekt, die Gr��e des übergeordneten Objektes wird übernommen und
     * um <code>sizeClass</code> Stufen verkleinert.
     * Es kann festgelegt werden, dass, falls das Objekt keine Unterelemente enh�lt, es als rechteckiges
     * kleines K�stchen dargestellt werden soll.
     * @param parent übergeordnetes Objekt
     * @param showIfEmpty Falls true: Wenn Objekt leer, rechteckiges K�stchen anzeigen.
     */
    public Succession(final Basic parent, final int sizeClass, 
                      final boolean showIfEmpty) {
        super(parent, sizeClass);
        elements = new Vector();
        init(showIfEmpty);
    }

    /**
     * Prim�rer Konstrukor, wird bei Erzeugung des obersten Elementes der Baumstruktur aufgerufen.
     * @param cursor Cursor-Objekt, gilt automatisch für alle untergeordneten Objekte
     * @param showIfEmpty Falls true: Wenn Objekt leer, rechteckiges K�stchen anzeigen.
     */
    public Succession(final Cursor cursor, final boolean showIfEmpty) {
        super(cursor, 40, 8);
        elements = new Vector();
        init(showIfEmpty);
    }

    /**
     * Prim�rer Konstrukor, wird bei Erzeugung des obersten Elementes der Baumstruktur aufgerufen.
     * Erm�glicht die Variation der Gr��e der Objekte.
     * @param cursor Cursor-Objekt, gilt automatisch für alle untergeordneten Objekte
     * @param showIfEmpty Anzeigen, wenn Objekt leer ist.
     * @param startSize (Buchstaben)gr��e in Pixeln der in der Hierachie h�chsten Objekte
     * @param stopSize (Buchstaben)Gr��e in Pixeln der untersten Objekte
     */
    public Succession(final Cursor cursor, final boolean showIfEmpty, 
                      final int startSize, final int stopSize) {
        super(cursor, startSize, stopSize);
        elements = new Vector();
        init(showIfEmpty);
    }

    //private boolean containsLineFeeds = true;
    private void init(final boolean showIfEmpty) {
        this.showIfEmpty = showIfEmpty;
        elements = new Vector();

        selStart = -1;
        selStop = -1;
        cursorPosition = -1;
    }

    /**
     * F�gt das Objekt hinten in der Liste an
     * @param element Einzufügendes Objekt
     */
    public final void add(final Basic element) {
        if (element instanceof Succession) {
            throw new RuntimeException(
                    "Can't add an Succession Object to an Sucession Object");
        }

        elements.addElement(element);

        objectChanged();
    }

    /**
     * F�gt den Cursor an die letzte Position an.
     */
    public final void insertCursor() {
        getCursor().setNewParent(this);
        cursorPosition = elements.size();
    }

    /**
     * Erzeugt ein BuildStructure-Objekt, dass im gegensatz zum direkten Succession-Interface
     * das Objekt bei der Position des Cursors und nicht am Ende des Objektes einf�gt.
     *
     * Das Objekt muss bei Nutzung dieses Objektes den Cursor haben.
     * @return Variante des BuildStructure-Objekt
     */
    public final BuildStructure getInsertAtCursorStructure() {
        return new InsertAtCursor();
    }

    /**
     * Normalerweise wird bei Anfragen an die Höhe ein Minimalwert zurückgegeben. Sollte dies
     * nicht gew�nscht werden, so kann dieses Verhalten hier deaktiviert werden
     * @param flag mit true wird die korrekte Gr��e zurückgegeben.
     */
    public final void setUseCorrectSize(final boolean flag) {
        useCorrectSize = flag;
    }

    /**
     * Gibt das übergeorndete Objekt zurück. Ergibt <code>null</code>, falls dieses Objekt
     * das h�chste in der Hirachie ist.
     * @return übergeordnetes Objekt.
     */
    public final Basic getParentObject() {
        return this;
    }

    public final int getWidthUncached(final Graphics g) {
        int max = 0;
        int count = 0;

        //if (showIfEmpty && (elements.size() == 0))
        // return getMySize() / 3;
        boolean empty = true;

        for (int i = 0; i < elements.size(); i++) {
            final Basic elem = (Basic) elements.elementAt(i);

            if (!(elem instanceof CarriageReturn)) {
                count += (elem.getWidth(g) + horizontalerAbstandZwischenElementen);
                empty = false;
            } else // Line Feed
            {
                if (max < count) {
                    max = count;
                }

                count = 0;
            }
        }

        if (empty) {
            return getMySize() / 3;
        }

        if (max < count) {
            max = count;
        }

        return max;
    }

    private boolean containsLineFeed() {
        for (int i = 0; i < elements.size(); i++) {
            final Object elem = (Basic) elements.elementAt(i);

            if (elem instanceof CarriageReturn) {
                return true;
            }
        }

        return false;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        if (showIfEmpty && (elements.size() == 0)) {
            return getMySize();
        }

        if (!containsLineFeed()) {
            int max = 0;
            int cur;

            //final int count = 0;
            for (int i = 0; i < elements.size(); i++) {
                final Basic elem = (Basic) elements.elementAt(i);
                cur = elem.getHeightAboveBaseline(g);

                if (max < cur) {
                    max = cur;
                }
            }

            if ((max < getMySize()) && (!useCorrectSize)) {
                max = getMySize();
            }

            return max;
        } else {
            //final int k = getHeight(g);
            int height = (getHeight(g) / 2) + (getMySize() / 2);

            if ((height < (getMySize())) && (!useCorrectSize)) {
                height = getMySize() / 5;
            }

            return height;
        }
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        if (showIfEmpty && (elements.size() == 0)) {
            return getMySize() / 5;
        }

        if (!containsLineFeed()) {
            int max = 0;
            int cur;

            for (int i = 0; i < elements.size(); i++) {
                cur = ((Basic) elements.elementAt(i)).getHeightUnderBaseline(g);

                if (max < cur) {
                    max = cur;
                }
            }

            if ((max < (getMySize() / 5)) && (!useCorrectSize)) {
                max = getMySize() / 5;
            }

            return max;
        } else {
            //final int k = getHeight(g);
            int height = (getHeight(g) / 2) - (getMySize() / 2);

            if ((height < (getMySize() / 5)) && (!useCorrectSize)) {
                height = getMySize() / 5;
            }

            return height;
        }
    }

    public final int getHeightUncached(final Graphics g) {
        if (showIfEmpty && (elements.size() == 0)) {
            return (getMySize() * 6) / 5;
        }

        //System.out.println("War hier!");
        final int minAbove = useCorrectSize ? 0 : getMySize();
        final int minUnder = useCorrectSize ? 0 : getMySize() / 5;
        int curAbove = minAbove;
        int curUnder = minUnder;
        int height = 0;

        for (int i = 0; i < elements.size(); i++) {
            final Basic elem = (Basic) elements.elementAt(i);

            if (!(elem instanceof CarriageReturn)) {
                final int ab = elem.getHeightAboveBaseline(g);
                final int un = elem.getHeightUnderBaseline(g);

                if (ab > curAbove) {
                    curAbove = ab;
                }

                if (un > curUnder) {
                    curUnder = un;
                }
            } else {
                if ((curAbove == 0) && (curUnder == 0) && showIfEmpty) {
                    curAbove = getMySize();
                    curUnder = getMySize() / 5;
                }

                height += (curAbove + curUnder + vertikalerAbstandZwischenElementen);
                curAbove = minAbove;
                curUnder = minUnder;

                //System.out.println("War auch hier!");
            }
        }

        if ((curAbove == 0) && (curUnder == 0) && showIfEmpty) {
            curAbove = getMySize();
            curUnder = getMySize() / 5;
        }

        return height + curAbove + curUnder;
    }

    public final void moveLeft(final boolean shift) {
        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Element verbindet mit falschem Parent-Objekt!");
        }

        // todo: Ggf. Objekt verlassen einfügen
        if (!shift) { // Shift-Taste nicht gedr�ckt
            selStart = -1;
            selStop = -1;

            if (cursorPosition == 0) {
                if (parent != null) {
                    parent.insertCurorLeftFromMe(this);
                    cursorPosition = -1;
                }

                return;
            }

            final Basic obj = (Basic) elements.elementAt(cursorPosition - 1);

            if (obj.canEnterLeftOrRighWithCursor()) {
                obj.insertCursorAt(Integer.MAX_VALUE);
                cursorPosition = -1;

                return;
            }
        } else { // Shift-Taste gedr�ckt

            if (cursorPosition == 0) {
                return;
            }

            if (selStart == -1) {
                selStart = cursorPosition - 1;
                selStop = cursorPosition - 1;
            } else if (selStart == cursorPosition) {
                selStart--;
            } else {
                selStop--;
            }

            if (selStart > selStop) {
                selStart = -1;
                selStop = -1;
            }
        }

        cursorPosition--;
    }

    public final void moveRight(final boolean shift) {
        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Element verbindet mit falschem Parent-Objekt!");
        }

        // xxxx Ggf. Objekt verlassen einfügen
        // if (cursorPosition == elements.size()) return;
        //cursorPosition++
        // todo: Ggf. Objekt verlassen einfügen
        if (!shift) {
            selStart = -1;
            selStop = -1;

            if (cursorPosition == elements.size()) {
                if (parent != null) {
                    parent.insertCurorRightFromMe(this);
                    cursorPosition = -1;
                }

                return;
            }

            final Basic obj = (Basic) elements.elementAt(cursorPosition);

            if (obj.canEnterLeftOrRighWithCursor()) {
                obj.insertCursorAt(0);
                cursorPosition = -1;

                return;
            }
        } else {
            if (cursorPosition == elements.size()) {
                return;
            }

            if (selStart == -1) {
                selStart = cursorPosition;
                selStop = cursorPosition;
            } else if (selStart == cursorPosition) {
                selStart++;
            } else {
                selStop++;
            }

            if (selStart > selStop) {
                selStart = -1;
                selStop = -1;
            }
        }

        cursorPosition++;
    }

    // Das Objekt (in der Regel den Cursor) nach oben verschieben. Dabei etwa bei toYPosition positionieren
    public final void moveUp(final Basic from, int toXPosition) {
        final Graphics g = getGraphicsHandle();

        int pos = cursorPosition;

        if (pos == -1) {
            pos = elements.indexOf(from);

            if (pos == -1) {
                throw new RuntimeException(
                        "Succession: Objekt verbindet mit falschem Parent!");
            }
        }

        //System.out.println("pos:"+cursorPosition);
        int lineStart = -1;

        //System.out.println("pos:"+cursorPosition);
        int lineEnd = -1;

        for (int i = pos - 1; i >= 0; i--) {
            final Basic b = (Basic) elements.elementAt(i);

            if (b instanceof CarriageReturn) {
                if (lineEnd == -1) {
                    lineEnd = i;
                } else {
                    lineStart = i;

                    break;
                }
            }
        }

        //System.out.println(pos+";"+lineStart + "," + lineEnd);
        if (lineEnd == -1) {
            for (int i = 0; i < pos; i++) // ????
            {
                final Basic b = (Basic) elements.elementAt(i);
                toXPosition += b.getWidth(g);
            }

            //System.out.println("LEFT!");
            if (parent != null) {
                parent.moveUp(this, toXPosition);
            }
        } else {
            int toX = toXPosition;

            //System.out.println(toX);
            for (int i = lineEnd + 1; i < pos; i++) // ????
            {
                final Basic b = (Basic) elements.elementAt(i);
                toX += (b.getWidth(g) - horizontalerAbstandZwischenElementen);
            }

            //System.out.println(toX);
            for (int i = lineStart + 1; i < lineEnd; i++) {
                final Basic b = (Basic) elements.elementAt(i);
                final int width = b.getWidth(g);


                //System.out.println("width:"+width);
                toX -= ((width / 2) - (horizontalerAbstandZwischenElementen / 2));

                if (toX < 0) {
                    //System.out.println("Set cursor to "+i);
                    selStart = -1;
                    selStop = -1;
                    cursorPosition = i;

                    return;
                }

                toX -= ((width / 2) - (horizontalerAbstandZwischenElementen / 2));
            }

            selStart = -1;
            selStop = -1;
            cursorPosition = lineEnd;
        }
    }

    // Das Objekt (in der Regel den Cursor) nach unten verschieben. Dabei etwa bei toYPositon positionieren
    public final void moveDown(final Basic from, int toXPosition) {
        final Graphics g = getGraphicsHandle();

        int pos = cursorPosition;

        if (pos == -1) {
            pos = elements.indexOf(from);

            if (pos == -1) {
                throw new RuntimeException(
                        "Succession: Objekt verbindet mit falschem Parent!");
            }
        }

        final int size = elements.size();

        int lineStart = size;
        int lineEnd = size;

        for (int i = pos; i < size; i++) {
            final Basic b = (Basic) elements.elementAt(i);

            if (b instanceof CarriageReturn) {
                if (lineStart == size) {
                    lineStart = i;
                } else {
                    lineEnd = i;

                    break;
                }
            }
        }

        //System.out.println(pos+";"+lineStart + "," + lineEnd);
        if (lineStart == size) {
            for (int i = pos - 1; i >= 0; i--) // ????
            {
                final Basic b = (Basic) elements.elementAt(i);

                if (b instanceof CarriageReturn) {
                    break;
                }

                toXPosition += b.getWidth(g);
            }

            if (parent != null) {
                parent.moveDown(this, toXPosition);
            }
        } else {
            int toX = toXPosition;

            //System.out.println(toX);
            for (int i = pos - 1; i >= 0; i--) // ????
            {
                final Basic b = (Basic) elements.elementAt(i);

                if (b instanceof CarriageReturn) {
                    break;
                }

                toX += (b.getWidth(g) - horizontalerAbstandZwischenElementen);
            }

            //System.out.println(toX);
            for (int i = lineStart + 1; i < lineEnd; i++) {
                final Basic b = (Basic) elements.elementAt(i);
                final int width = b.getWidth(g);


                //System.out.println("width:"+width);
                toX -= ((width / 2) - (horizontalerAbstandZwischenElementen / 2));

                if (toX < 0) {
                    //System.out.println("Set cursor to "+i);
                    cursorPosition = i;
                    selStart = -1;
                    selStop = -1;

                    return;
                }

                toX -= ((width / 2) - (horizontalerAbstandZwischenElementen / 2));
            }

            selStart = -1;
            selStop = -1;
            cursorPosition = lineEnd;
        }
    }

    /**
     * überpr�ft, ob die Objekte vor und hinter dem Cursor eine Bewegegung nach oben oder unten
     * unterst�tzen. In diesem Fall wird moveUp von diesem Objekt aufgerufen, sonst wird direkt an den
     * Parent weitergegeben.
     * @param from Aufrufendes Objekt
     * @param toXPosition Horizontale Verschiebung
     */
    public final void moveUpWithCursorCheck(final Basic from, 
                                            final int toXPosition) {
        if (elements.size() == 0) {
            parent.moveUp(this, toXPosition);

            return;
        }

        if (cursorPosition > 0) {
            final Basic b = (Basic) elements.elementAt(cursorPosition - 1);

            if (b.canEnterUpOrDownWithCursor()) {
                b.moveUp(this, toXPosition + b.getWidth(getGraphicsHandle()));

                return;
            }
        }

        //if (cursorPosition==-1) return;
        if (cursorPosition < elements.size()) {
            final Basic b = (Basic) elements.elementAt(cursorPosition);

            if (b.canEnterUpOrDownWithCursor()) {
                b.moveUp(this, toXPosition);

                return;
            }
        }


        //if (cursorPosition==-1) return;
        moveUp(this, toXPosition);
    }

    /**
     * überpr�ft, ob die Objekte vor und hinter dem Cursor eine Bewegegung nach oben oder unten
     * unterst�tzen. In diesem Fall wird moveUp von diesem Objekt aufgerufen, sonst wird direkt an den
     * Parent weitergegeben.
     * @param from Aufrufendes Objekt
     * @param toXPosition Horizontale Verschiebung
     */
    public final void moveDownWithCursorCheck(final Basic from, 
                                              final int toXPosition) {
        if (elements.size() == 0) {
            parent.moveDown(this, toXPosition);

            return;
        }

        if (cursorPosition > 0) {
            final Basic b = (Basic) elements.elementAt(cursorPosition - 1);

            if (b.canEnterUpOrDownWithCursor()) {
                b.moveDown(this, toXPosition + 
                           b.getWidth(getGraphicsHandle()));

                return;
            }
        }

        //if (cursorPosition==-1) return;
        if (cursorPosition < elements.size()) {
            final Basic b = (Basic) elements.elementAt(cursorPosition);

            if (b.canEnterUpOrDownWithCursor()) {
                b.moveDown(this, toXPosition);

                return;
            }
        }


        //if (cursorPosition==-1) return;
        moveDown(this, toXPosition);
    }

    /*  public final void moveUpWithShift() {
                                    
          int pos = cursorPosition;
          if (pos == -1) {
              throw new RuntimeException("Succession: Objekt verbindet mit falschem Parent!");
          }
                                    
          int lineStart = -1, lineEnd = -1;
                                    
          for (int i = pos - 1; i >= 0; i--) {
              final Basic b = (Basic) elements.elementAt(i);
              if (b instanceof CarriageReturn) {
                  if (lineEnd == -1)
                      lineEnd = i;
                  else {
                      lineStart = i;
                      break;
                  }
              }
          }
                                    
          if (lineEnd < 0) return;
                                    
          Graphics g = getGraphicsHandle();
                                    
                                    
          int toX = 0;
                                    
          int target = 0;
                                    
                                    
          for (int i = lineEnd + 1; i < pos; i++)  // ????
          {
              final Basic b = (Basic) elements.elementAt(i);
              toX += b.getWidth(g) - horizontalerAbstandZwischenElementen;
          }
                                    
                                    
          int i;
          for (i = lineStart + 1; i < lineEnd; i++) {
              final Basic b = (Basic) elements.elementAt(i);
              final int width = b.getWidth(g);
              toX -= width / 2 - horizontalerAbstandZwischenElementen / 2;
                                    
              if (toX < 0) {
                  target = i;
                  break;
              }
                                    
              toX -= width / 2 - horizontalerAbstandZwischenElementen / 2;
          }
                                    
                                    
          if (selStart==-1) {
              selStart=target;
              selStop=cursorPosition-1;
          } else
          {
              if ((target<selStart) && (selStop+1==pos)) {
                  int x=selStart;
                  selStart=target;
                  selStop=x-1;
              } else if ((target==selStart) && (selStop+1==pos)) {
                  selStart=-1;
                  selStop=-1;
              } else if ((target>selStart) && (selStop+1==pos)) {
                  selStop=target-1;
              } else
              {
                  selStart=target;
              }
          }
                                    
                                    
                                    
          cursorPosition=target;
      }
                                    
      public final void moveDownWithShift() {
                                    
              int pos = cursorPosition;
              if (pos == -1) {
                  throw new RuntimeException("Succession: Objekt verbindet mit falschem Parent!");
              }
                                    
              int lineStart = -1, lineEnd = -1;
                                    
              for (int i = pos - 1; i >= 0; i--) {
                  final Basic b = (Basic) elements.elementAt(i);
                  if (b instanceof CarriageReturn) {
                      if (lineEnd == -1)
                          lineEnd = i;
                      else {
                          lineStart = i;
                          break;
                      }
                  }
              }
                                    
              if (lineEnd < 0) return;
                                    
              Graphics g = getGraphicsHandle();
                                    
                                    
              int toX = 0;
                                    
              int target = 0;
                                    
                                    
              for (int i = lineEnd + 1; i < pos; i++)  // ????
              {
                  final Basic b = (Basic) elements.elementAt(i);
                  toX += b.getWidth(g) - horizontalerAbstandZwischenElementen;
              }
                                    
                                    
              int i;
              for (i = lineStart + 1; i < lineEnd; i++) {
                  final Basic b = (Basic) elements.elementAt(i);
                  final int width = b.getWidth(g);
                  toX -= width / 2 - horizontalerAbstandZwischenElementen / 2;
                                    
                  if (toX < 0) {
                      target = i;
                      break;
                  }
                                    
                  toX -= width / 2 - horizontalerAbstandZwischenElementen / 2;
              }
                                    
                                    
              if (selStart==-1) {
                  selStart=target;
                  selStop=cursorPosition-1;
              } else
              {
                  if ((target<selStart) && (selStop+1==pos)) {
                      int x=selStart;
                      selStart=target;
                      selStop=x-1;
                  } else if ((target==selStart) && (selStop+1==pos)) {
                      selStart=-1;
                      selStop=-1;
                  } else if ((target>selStart) && (selStop+1==pos)) {
                      selStop=target-1;
                  } else
                  {
                      selStart=target;
                  }
              }
                                    
             cursorPosition=target;
      }          */

    /**
     * Ein Objekt wird beim Cursor eingefügt. Der Cursor wird hinter das neue Objekt verschoben.
     * @param insert Einzufügendes Objekt.
     */
    public final void insertObjectAtCursor(final Basic insert) {
        String str = "";

        if (selStart != -1) {
            str = getCursor().generateMathMLCodeFromSelected();
            deleteSelection();
        }

        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Cursor verbindet mit falschem Parent-Objekt!");
        }

        elements.insertElementAt(insert, cursorPosition);

        if (insert.canEnterLeftOrRighWithCursor()) {
            insert.insertCursorAt(0);

            if (!str.equals("") && (insert instanceof HorizontalEnter)) {
                MathMLImport.parseMathML(str, 
                                         ((HorizontalEnter) insert).getMainStructure());
            }
        } else {
            cursorPosition++;
        }

        objectChanged();
    }

    // --Recycle Bin START (20.01.04 18:36):
    //    /**
    //     * Ein Objekt wird an das Ende angefügt. Der Cursor wird nicht verschoben. Dient Debug-Zwecken.
    //     * @param insert Einzufügendes Objekt.
    //     */
    //    public final void addObject(final Basic insert) {
    //
    //        if (selStart != -1)
    //            deleteSelection();
    //
    //        elements.addElement(insert);
    //
    //        objectChanged();
    //    }
    // --Recycle Bin STOP (20.01.04 18:36)

    /**
     * L�scht alle markierten Zeichen. Muss in der Ebene aufgerufen werden, in der die
     * Zeichen markiert wurden.
     */
    public final void deleteSelection() {
        if (selStart < 0) {
            return;
        }

        for (int i = selStart; i <= selStop; i++)
            elements.removeElementAt(selStart);

        cursorPosition = selStart;

        selStart = -1;
        selStop = -1;

        objectChanged();
    }

    /**
     * Das Element links neben dem Cursor wird entfernt.
     */
    public final void deleteLeftElement() {
        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Cursor verbindet mit falschem Parent-Objekt!");
        }

        if (selStart != -1) {
            deleteSelection();

            return;
        }

        if (cursorPosition == 0) {
            return;
        }

        elements.removeElementAt(--cursorPosition);

        objectChanged();
    }

    /**
     * Das Element rechts neben dem Cursor wird entfernt.
     */
    public final void deleteRightElement() {
        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Cursor verbindet mit falschem Parent-Objekt!");
        }

        if (selStart != -1) {
            deleteSelection();

            return;
        }

        if (elements.size() == cursorPosition) {
            return;
        }

        elements.removeElementAt(cursorPosition);

        objectChanged();
    }

    // Alle Elemente + Cursor zeichnen
    public final void paint(final Graphics g, final int atX, final int atY) {
        modifyGraphicsHandle(g);

        final Color defaultColor = g.getColor();

        int posY = atY - getHeightAboveBaseline(g);

        //g.setColor(Color.red);
        //g.drawRect(atX, atY - getHeightAboveBaseline(g), getWidth(g), getHeight(g));

        /*g.setColor(Color.blue);
        g.drawRect(atX, atY, getWidth(g), getHeightUnderBaseline(g));  */

        //g.setColor(defaultColor);
        final int minAbove = useCorrectSize ? 0 : getMySize();
        final int minUnder = useCorrectSize ? 0 : getMySize() / 5;

        int current = 0;

        final int half = horizontalerAbstandZwischenElementen / 2;

        if (showIfEmpty && (elements.size() == 0) && 
                (!getCursor().isReadonly())) {
            g.setColor(Color.gray);

            final int size = getMySize() - 2;
            g.drawRect(atX + 1, atY - size, (size / 3) - 2, (size * 10) / 9);

            g.setColor(defaultColor);

            if (0 == cursorPosition) {
                getCursor()
                    .setPositon(atX, atY - size, (atX + (size / 3)) - 2, 
                                atY + ((size * 1) / 9), atX - 1, posY);
            }

            //getCursor().setPositon(atX-1, atY, atX, atX + size/3);
            return;
        }

        final int minWidth = getMySize() / 3;

        boolean containsLineFeed = false;

        while (current < elements.size()) { //for (int i=0;i<elements.size();i++) {

            //Basic elem = (Basic) elements.elementAt(i);
            int above = minAbove;
            int under = minUnder;
            int lineWidth = 0;
            int count = 0;

            // Position der Zeile ermitteln
            for (int i = current; i < elements.size(); i++) {
                final Basic lelem = (Basic) elements.elementAt(i);

                if (lelem instanceof CarriageReturn) {
                    containsLineFeed = true;

                    break;
                }

                count++;

                final int ab = lelem.getHeightAboveBaseline(g);
                final int un = lelem.getHeightUnderBaseline(g);
                lineWidth += (lelem.getWidth(g) + horizontalerAbstandZwischenElementen);

                if (above < ab) {
                    above = ab;
                }

                if (under < un) {
                    under = un;
                }
            }

            if (lineWidth < minWidth) {
                lineWidth = minWidth;
            }

            posY += (above + (vertikalerAbstandZwischenElementen / 2));

            if (count == 0) //Keine Elemente in Zeile - K�stchen zeichnen
            {
                if (!useCorrectSize) {
                    final Color c = g.getColor();
                    g.setColor(Color.gray);

                    final int size = getMySize() - 2;
                    g.drawRect(atX + 1, posY - size, (size / 3) - 2, 
                               (size * 10) / 9);

                    g.setColor(c);
                    current++;

                    //System.out.println(current + "==" + cursorPosition);
                    if ((cursorPosition + 1) == current) {
                        getCursor()
                            .setPositon(atX, posY - minAbove, atX + 
                                        lineWidth, posY + minUnder, atX - 1, 
                                        posY);
                    }
                }
            } else // Elemente zeichnen
            {
                int posX = atX + half;
                int i;

                if ((selStop > -1) && (current > selStop)) {
                    g.setColor(Color.black);
                }

                for (i = current; i < (current + count); i++) {
                    final Basic elem = (Basic) elements.elementAt(i);

                    //if (elem==null) break;
                    final int width = elem.getWidth(g);

                    //int heightAbove=elem.getHeightAboveBaseline(g);
                    //int heigtUnder=elem.getHeightUnderBaseline(g);
                    if ((i >= selStart) && (i < selStop)) {
                        g.setColor(new Color(127));
                        g.fillRect(posX, posY - above, 
                                   width + 
                                   horizontalerAbstandZwischenElementen, 
                                   above + under);
                        g.setColor(Color.white);
                    }

                    if (i == selStop) {
                        g.setColor(new Color(127));
                        g.fillRect(posX, posY - above, width, above + under);
                        g.setColor(Color.white);
                    }

                    if ((i == selStop + 1) && (selStop != -1)) {
                        g.setColor(Color.black);
                    }

                    elem.paint(g, posX, containsLineFeed ? posY : atY);

                    if (i == cursorPosition) {
                        getCursor()
                            .setPositon(atX, posY - above, atX + lineWidth, 
                                        posY + under, posX - 1, posY);
                    }


                    //getCursor().setPositon(posX-1, posY, atX, atX + getWidth(g));
                    posX += (width + horizontalerAbstandZwischenElementen);
                }

                current += (count + 1);

                if (i == cursorPosition) {
                    getCursor()
                        .setPositon(atX, posY - above, atX + lineWidth, 
                                    posY + under, posX - 1, posY);
                }
            }

            posY += (under + (vertikalerAbstandZwischenElementen / 2));

            if (current >= elements.size()) {
                final Object elem = elements.elementAt(elements.size() - 1);

                if (elem instanceof CarriageReturn) {
                    final Color c = g.getColor();
                    g.setColor(Color.gray);

                    final int size = getMySize() - 2;

                    posY += size;

                    g.drawRect(atX + 1, posY - size, (size / 3) - 2, 
                               (size * 10) / 9);

                    g.setColor(c);
                    current++;

                    //System.out.println((cursorPosition)+"=="+(elements.size()));
                    if (cursorPosition == elements.size()) {
                        getCursor()
                            .setPositon(atX, posY - size, 
                                        (atX + (size / 3)) - 2, 
                                        posY + ((size * 1) / 9), atX - 1, posY);
                    }

                    //    getCursor().setPositon(atX,posY-minAbove,atX+lineWidth,posY+minUnder,atX-1,posY);
                }
            }

            //if (current==elements.size()) current--;
            //System.out.println("Loop!");
        }

        g.setColor(defaultColor);
    }

    private BasicObjectDescripion findObject(final int relX, int relY, 
                                             final Graphics g) {
        final int size = elements.size();
        relY += getHeightAboveBaseline(g);

        int ypos = -getHeightAboveBaseline(g);
        int currentWidth = 0;
        int currentHeightAbove = useCorrectSize ? 0 : getMySize();
        int currentHeightUnder = useCorrectSize ? 0 : getMySize() / 5;

        BasicObjectDescripion bod = null;

        for (int i = 0; i < size; i++) {
            final Basic b = (Basic) elements.elementAt(i);

            if (b instanceof CarriageReturn) {
                relY -= (currentHeightAbove + currentHeightUnder + vertikalerAbstandZwischenElementen);

                //System.out.println(relY);
                if (relY < 0) {
                    if (bod != null) {
                        return bod;
                    } else {
                        return new BasicObjectDescripion(i, -1, -1, false, null); // hinter eine Zeile geklickt
                    }
                }

                bod = null;
                currentWidth = 0;

                ypos += (currentHeightAbove + currentHeightUnder);
                currentHeightAbove = useCorrectSize ? 0 : getMySize();
                currentHeightUnder = useCorrectSize ? 0 : getMySize() / 5;
            } else { // not Carriage Return

                final int width = b.getWidth(g);

                final int heightAbove = b.getHeightAboveBaseline(g);
                final int heightUnder = b.getHeightUnderBaseline(g);

                if (currentHeightAbove < heightAbove) {
                    currentHeightAbove = heightAbove;
                }

                if (currentHeightUnder < heightUnder) {
                    currentHeightUnder = heightUnder;
                }

                if ((bod == null) && 
                        ((currentWidth + width + 
                            horizontalerAbstandZwischenElementen) > relX)) {
                    if ((currentWidth + (width / 2) + 
                            horizontalerAbstandZwischenElementen / 2) > relX) {
                        bod = new BasicObjectDescripion(i, currentWidth, 
                                                        ypos + 
                                                        currentHeightAbove, 
                                                        true, b);
                    } else {
                        bod = new BasicObjectDescripion(i + 1, currentWidth, 
                                                        ypos + 
                                                        currentHeightAbove, 
                                                        false, b);
                    }
                }

                currentWidth += (width + horizontalerAbstandZwischenElementen);
            }
        }

        //if (b instanceof CarriageReturn)
        if (bod != null) {
            return bod;
        } else {
            return new BasicObjectDescripion(size, -1, -1, false, null);
        }
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        if ((elements.size() == 0) && showIfEmpty) {
            cursorPosition = 0;
            getCursor().setNewParent(this);
            getCursor().repaint();

            return true;
        }

        final BasicObjectDescripion from = findObject(toX - currentX, 
                                                      toY - currentY, g);
        final BasicObjectDescripion to = findObject(fromX - currentX, 
                                                    fromY - currentY, g);

        //final int i = determinateLayer();

        //System.out.println(i + ": " + from + " ///////////// " + to);
        // Fallunterscheidung:
        // Gleiches Objekt angeklickt:
        if ((to.x != -1) && (to.x == from.x) && (to.y == from.y)) {
            // Innerhalb des Objektes markiert ?

            /*Graphics gg=getGraphicsHandle();
            int x=currentX - to.x;
            int y=currentY - to.y;
            g.drawLine(x-2,y-2,x+2,y+2);
            g.drawLine(x-2,y+2,x+2,y-2);*/
            if (to.object.findSelectedObjects(currentX + to.x, currentY + to.y, 
                                              fromX, fromY, toX, toY, g)) {
                return true;
            }

            //return true;
        }

        cursorPosition = to.index;

        if (to.index == from.index) {
            selStart = -1;
            selStop = -1;
        } else if (to.index > from.index) {
            selStart = from.index;
            selStop = to.index - 1;
        } else {
            selStart = to.index;
            selStop = from.index - 1;
        }

        getCursor().setNewParent(this);
        getCursor().repaint();

        return true;
    }

    /**
     * Cursor freigeben. <br> <b>Achtung:</b> Der Cursor wird über die �nderung nicht informiert - das liegt daran,
     * dass diese Funktion vom Cursor aufgerufen wird.
     */
    public final void deleteCursor() {
        selStart = -1;
        selStop = -1;

        cursorPosition = -1;
    }

    /**
     * Den Cursor an die gegebene Positon einfügen. Sollte der Parameter kleiner null sein, so wird er am Anfang
     * der Liste eingefügt. Bei einem Wert gr��er als die L�nge der Liste wird er an das Ende der Liste angefügt.
     * Falls der Cursor einem anderen Objekt zugeordnet war, so wird die Zuordnung angepasst.
     * @param position Neue Position des Cursors.
     */
    public final void insertCursorAt(int position) {
        final Cursor c = getCursor();

        if (c != null) {
            c.setNewParent(this);
        }

        if (position < 0) {
            position = 0;
        }

        if (position > elements.size()) {
            position = elements.size();
        }

        cursorPosition = position;
    }

    /**
     * Der Cursor wird links von diesem Objekt eingefügt. Das Objekt muss in der Liste der Unterobjekte existieren.
     * Falls der Cursor einem anderen Objekt zugeordnet war, so wird die Zuordnung angepasst.
     * @param object Objekt, neben dem der Cursor eingefügt werden soll.
     */
    public final void insertCurorLeftFromMe(final Basic object) {
        final int position = elements.indexOf(object);

        if (position == -1) {
            throw new RuntimeException(
                    "Succession: Passendes Objekt nicht gefunden.");
        }

        getCursor().setNewParent(this);
        cursorPosition = position;
    }

    /**
     * Der Cursor wird rechts von diesem Objekt eingefügt. Das Objekt muss in der Liste der Unterobjekte existieren.
     * Falls der Cursor einem anderen Objekt zugeordnet war, so wird die Zuordnung angepasst.
     * @param object Objekt, neben dem der Cursor eingefügt werden soll.
     */
    public final void insertCurorRightFromMe(final Basic object) {
        insertCurorLeftFromMe(object);
        cursorPosition++;
    }

    /**
     * Setzt den Cursor an die Position, zu der "am besten" die übergebenen relativen(!) <code>xPosition</code> passt.
     * Dabei werden die Objekte halbiert und der Cursor an die Positon gesetzt, die neben der
     * H�lfte ist, in der die übergebenen Koordinate liegt. (Die Positon orientiert sich an der linken Kante des Objektes.)
     * Falls eine Bewegung nach oben ausgeführt wird und ein Zeilenbruch vorhanden ist, wird der Cursor
     * in der untersten Linie eingefügt.
     * @param g Grafikkontext
     * @param xPosition relative XPosition.
     * @param goUp Eine Bewegung nach oben wurde ausgeführt.
     */
    public final void searchObjectAtRelativeXPosition(final Graphics g, 
                                                      int xPosition, 
                                                      final boolean goUp) {
        int i;
        int half;

        int startLine;

        //System.out.println("exec:" + goUp + "," + xPosition);
        if (goUp) {
            for (startLine = elements.size() - 1; startLine >= 0; startLine--) {
                if (elements.elementAt(startLine) instanceof CarriageReturn) {
                    break;
                }
            }

            if ((startLine == -1) && (elements.size() == 0)) {
                getCursor().setNewParent(this);
                cursorPosition = 0;

                //System.out.println("Chilling out...");
                return;
            }

            startLine++;
        } else {
            startLine = 0;
        }

        for (i = startLine; i < elements.size(); i++) {
            final Basic b = (Basic) elements.elementAt(i);

            if (b instanceof CarriageReturn) {
                break;
            }


            // Die Halbschritte sorgen dafür, dass nicht der linke Rand, sondern die Mitte des Objektes
            // entscheidet, ob der Cursor links oder rechts positioniert wird.
            half = b.getWidth(g) / 2;
            xPosition -= half;

            if (xPosition <= 0) {
                getCursor().setNewParent(this);
                cursorPosition = i;

                break;
            }

            xPosition -= half;
        }

        if (i > elements.size()) {
            i = elements.size();
        }

        getCursor().setNewParent(this);
        cursorPosition = i;
    }

    /**
     * Bewegt den Cursor an den Anfang des Objektes. Falls es bereits am Anfang steht, wird
     * das darüberliegende Objekt aufgerufen.
     */
    public final void moveToFirstElement() {
        if ((cursorPosition == 0) && (parent != null)) {
            parent.moveToFirstElement();
        } else {
            getCursor().setNewParent(this);
            cursorPosition = 0;
        }
    }

    /**
     *  Bewegt den Cursor an das Ende des Objektes.
     */
    public final void moveToLastElement() {
        if ((cursorPosition == elements.size()) && (parent != null)) {
            parent.moveToFirstElement();
        } else {
            getCursor().setNewParent(this);
        }

        cursorPosition = elements.size();
    }

    /**
     * Informiert, ob das Succession-Element Daten enth�lt
     * @return true, wenn Daten vorhanden
     */
    public final boolean containsElements() {
        return elements.size() != 0;
    }

    /**
     * Erzeugt den mathml-Code für alle Objekte im Feld
     * @param mmle MathMl-Exportier-Objekt
     */
    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.addTextLine("<mrow>");
        mmle.increaseLayer();

        int i;

        for (i = 0; i < elements.size(); i++) {
            if (i == cursorPosition) {
                mmle.insertCursorIfNessesary();
            }

            final Basic cur = (Basic) elements.elementAt(i);
            cur.generateMathMLCode(mmle);
        }

        if (i == cursorPosition) {
            mmle.insertCursorIfNessesary();
        }

        mmle.decreaseLayer();
        mmle.addTextLine("</mrow>");
    }

    /**
     * Erzeugt den MahtML-Code für den markierten Bereich. Pratkisch für Zwischenablage.
     * @param mmle MathMl-Exportier-Objekt
     */
    public final void generateMathMLCodeForSelected(final MathMLExport mmle) {
        if ((selStart < 0) || (selStart > selStop)) {
            return;
        }

        mmle.addTextLine("<mrow>");
        mmle.increaseLayer();

        for (int i = selStart; i <= selStop; i++) {
            final Basic cur = (Basic) elements.elementAt(i);
            cur.generateMathMLCode(mmle);
        }

        mmle.decreaseLayer();
        mmle.addTextLine("</mrow>");
    }

    /**
     * Tauscht das Objekt "out" gegen das Objekt "in" ein. Praktisch, um ein anderes Objekt "überzust�lpen" -
     * oder diese "überst�lpung" zu entfernen.
     * @param out zu entfernendes Objekt
     * @param in einzufügendes Objekt
     */
    public final void exchangeObject(final Basic out, final Basic in) {
        final int k = elements.indexOf(out);

        if (k == -1) {
            throw new RuntimeException("<out> nicht gefunden.");
        }

        elements.removeElement(out);
        elements.insertElementAt(in, k);
        objectChanged();
    }

    /**
     * Erfragt das Objekt beim Cursor. Wenn dieser ganz links ist, so wird das rechte Objekt
     * zurückgegeben, ansonsten das davorliegende
     * @return Objekt beim Cursor
     */
    public final Basic getObjectAtCursor() {
        if (cursorPosition == -1) {
            throw new RuntimeException(
                    "Succession: Element verbindet mit falschem Parent-Objekt!");
        }

        int k = cursorPosition;

        if (k != 0) {
            k--;
        }

        if (elements.size() == 0) {
            return null;
        }

        return (Basic) elements.elementAt(k);
    }

    /**
     * Alles (im aktuellen Objekt) markieren
     */
    public final void selectAll() {
        getCursor().setNewParent(this);

        selStart = 0;
        cursorPosition = elements.size();
        selStop = cursorPosition - 1;
    }

    public final void newLine() {
        if (acceptLineFeeds) {
            if (cursorPosition == elements.size()) {
                elements.addElement(new CarriageReturn(this));
            } else {
                elements.insertElementAt(new CarriageReturn(this), 
                                         cursorPosition);
            }

            cursorPosition++;


            //containsLineFeeds = true;
            //System.out.println("ENTER at " + cursorPosition + "!");
            objectChanged();
        } else {
            if (parent != null) {
                parent.newLine();
            }
        }
    }

    public final int getHeightAboveBaselineOfPreviousObject(final Graphics g, 
                                                            final Basic object) {
        final int k = elements.indexOf(object);
        final int min = getMySize();

        if (k == -1) {
            throw new RuntimeException("Objekt nicht gefunden.");
        }

        if (k == 0) {
            return min;
        }

        int i = ((Basic) elements.elementAt(k - 1)).getHeightAboveBaseline(g);

        if (i < min) {
            i = min;
        }

        return i;
    }

    /**
     * Realisation des mit getImplementAtCursorStructure() benötigten Objektes.
     */
    protected final class InsertAtCursor implements BuildStructure {
        /**
         * F�gt Objekt beim Cursor und nicht hinten an
         * @param element Einzufügendes Element
         */
        public final void add(final Basic element) {
            if (element instanceof Succession) {
                throw new RuntimeException(
                        "Can't add an Succession Object to an Sucession Object");
            }

            if (selStart != -1) {
                deleteSelection();
            }

            if (cursorPosition == -1) {
                throw new RuntimeException(
                        "Succession: Cursor verbindet mit falschem Parent-Objekt!");
            }

            elements.insertElementAt(element, cursorPosition);

            cursorPosition++;

            objectChanged();
        }

        public final Basic getParentObject() {
            return Succession.this;
        }

        public final void insertCursor() {
            throw new RuntimeException("Cursor einfügen hier nicht m�glich.");
        }
    }

    protected static final class BasicObjectDescripion {
        public final int index;
        public final int x;
        public final int y;
        public final boolean left;

        // --Recycle Bin (20.01.04 18:36): public boolean behindObject;
        public final Basic object;

        public BasicObjectDescripion(final int index, final int x, final int y, 
                                     final boolean left, final Basic object) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.left = left;
            this.object = object;
        }

        public final String toString() {
            return "index:" + index + ", pos:(" + x + "," + y + 
                   "), leftSide:" + left;
        }

        //public Object clone() {
        //    return new BasicObjectDescripion(index, x, y, left);
        //}
    }

    // --Recycle Bin START (20.01.04 18:36):
    //    public final int getHeightUnderBaselineOfPreviousObject(final Graphics g, final Basic object) {
    //        final int k = elements.indexOf(object);
    //        final int min = getMySize() / 5;
    //        if (k == -1)
    //            throw new RuntimeException("Objekt nicht gefunden.");
    //        if (k == 0)
    //            return min;
    //        int i = ((Basic) elements.elementAt(k - 1)).getHeightUnderBaseline(g);
    //        if (i < min) i = min;
    //        return i;
    //    }
    // --Recycle Bin STOP (20.01.04 18:36)
}