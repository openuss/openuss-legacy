package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses;

import java.awt.*;
import javax.swing.*;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElementGridObject;
import freestyleLearningGroup.independent.gui.FLGImageUtility;

/**
 * FLGSelectorElementGridObject.
 * Manager Class for editting grid objects.
 * @author Carsten Fiedler
 */
public class FLGSelectorElementGridObject extends ViewElementGridObject {
    public static String ELEMENT_TYPE_IMAGE = "image";
    public static String ELEMENT_TYPE_TEXT = "text";
	private String learningUnitViewDirectory;
	
	public void init (String learningUnitViewDirectory) {
		this.learningUnitViewDirectory = learningUnitViewDirectory;
	}

	private Image loadImage(String imageFileName) {
    	return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource(learningUnitViewDirectory +
            imageFileName));
    }
}

