package freestyleLearningGroup.independent.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class FLGBorders {
    public static Border standardDialogBorder =
        BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
        BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
}
