/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

// NOCH GUT DOKUMENTIEREN
// Ist ideal geeignet, um Buttons in einem Dialog unten anzuordnen
// Alle Buttons erhalten die gleiche Gr��e
public class FLGEqualSizeLayout implements LayoutManager {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int CENTER = 2;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public int horizontalSpace = 5;
    private int horizontalAlignment = CENTER;
    private int verticalAlignment = CENTER;

    public FLGEqualSizeLayout() {
    }

    public FLGEqualSizeLayout(int horizontalAlignment, int verticalAlignment, int horizontalSpacing) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
        this.horizontalSpace = horizontalSpacing;
    }

    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        Dimension target_size = target.getSize();
        Component child;
        int nChildren = target.getComponentCount();
        int visibleHeight = target_size.height - insets.top - insets.bottom;
        int totalPrefWidth = 0;
        int sameWidth = 0;
        int sameHeight = 0;
        int numVisibleChildren = 0;
        for (int i = 0; i < nChildren; i++) {
            child = target.getComponent(i);
            if (!child.isVisible()) continue;
            numVisibleChildren++;
            Dimension pref = child.getPreferredSize();
            sameWidth = Math.max(sameWidth, pref.width);
            sameHeight = Math.max(sameHeight, pref.height);
        }
        totalPrefWidth = numVisibleChildren * sameWidth;
        int availEmptySpace = target_size.width - insets.left - insets.right - totalPrefWidth -
            (numVisibleChildren - 1) * horizontalSpace;
        if (availEmptySpace < 0) availEmptySpace = 0;
        int xOffset = 0;
        if (numVisibleChildren > 0) {
            if (horizontalAlignment == CENTER) xOffset = availEmptySpace / 2;
            if (horizontalAlignment == RIGHT) xOffset = availEmptySpace;
        }
        int x = insets.left + xOffset;
        int y = insets.top;
        for (int i = 0; i < nChildren; i++) {
            child = target.getComponent(i);
            if (!child.isVisible()) continue;
            Dimension pref = child.getPreferredSize();
            if (verticalAlignment == TOP) child.setBounds(x, y, sameWidth, sameHeight);
            if (verticalAlignment == BOTTOM)
                child.setBounds(x, y + (int)Math.max(visibleHeight - sameHeight, 0), sameWidth, sameHeight);
            if (verticalAlignment == CENTER)
                child.setBounds(x, y + (int)Math.max((visibleHeight - sameHeight) / 2, 0), sameWidth, sameHeight);
            x = x + sameWidth + horizontalSpace;
        }
    }

    public Dimension preferredLayoutSize(Container target) {
        Component child;
        int nChildren = target.getComponentCount();
        Insets insets = target.getInsets();
        int maxHeight = 0;
        int maxWidth = 0;
        int visibleChildrenCount = 0;
        for (int i = 0; i < nChildren; i++) {
            child = target.getComponent(i);
            if (!child.isVisible()) continue;
            visibleChildrenCount++;
            Dimension pref = child.getPreferredSize();
            if (pref.height > maxHeight) maxHeight = pref.height;
            if (pref.width > maxWidth) maxWidth = pref.width;
        }
        return new Dimension(visibleChildrenCount * maxWidth + insets.left + insets.right,
            maxHeight + insets.top + insets.bottom);
    }

    public Dimension minimumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    public void addLayoutComponent(String constraint, Component comp) { }

    public void addLayoutComponent(Component comp, Object constraint) { }

    public void removeLayoutComponent(Component comp) { }

    public void invalidateLayout(Container target) { }

    public float getLayoutAlignmentX(Container target) { return 0.5f; }

    public float getLayoutAlignmentY(Container target) { return 0.5f; }
}