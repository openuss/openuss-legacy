/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.checkUp.elementsContentsPanel;

import java.awt.Image;
import java.awt.event.ActionListener;

import freestyleLearningGroup.independent.gui.FLGAbstractImageToggleButton;

public class FLGCheckUpRelatorButton extends FLGAbstractImageToggleButton {
	private static Image BUTTON_NORMAL;

	private static Image BUTTON_PRESSED;

	private static Image BUTTON_ROLLOVER;

	private static Image BUTTON_DISABLED;

	public FLGCheckUpRelatorButton(Image unselectedImage, Image selectedImage,
			ActionListener actionListener) {
		buttonWidth = 36;
		buttonHeight = 36;
		imageWidth = 14;
		imageHeight = 14;
		if (BUTTON_NORMAL == null) {
			BUTTON_NORMAL = createButtonImage("images/relatorButtonNormal.gif");
			BUTTON_ROLLOVER = BUTTON_NORMAL;
			BUTTON_PRESSED = createButtonImage("images/relatorButtonPressed.gif");
			BUTTON_DISABLED = createButtonImage("images/relatorButtonDisabled.gif");
		}
		button_normal = BUTTON_NORMAL;
		button_pressed = BUTTON_PRESSED;
		button_rollover = BUTTON_ROLLOVER;
		button_disabled = BUTTON_DISABLED;
		init(unselectedImage, selectedImage);
		if (actionListener != null) {
			addActionListener(actionListener);
		}
	}

	public FLGCheckUpRelatorButton copy(ActionListener actionListener) {
		FLGCheckUpRelatorButton button = new FLGCheckUpRelatorButton();
		button.image_selected_normal = image_selected_normal;
		button.image_notSelected_normal = image_notSelected_normal;
		button.image_selected_disabled = image_selected_disabled;
		button.image_notSelected_disabled = image_notSelected_disabled;
		button.image_pressed = image_pressed;
		button.image_selected_rollover = image_selected_rollover;
		button.image_notSelected_rollover = image_notSelected_rollover;
		button.button_normal = button_normal;
		button.button_pressed = button_pressed;
		button.button_disabled = button_disabled;
		button.button_rollover = button_rollover;
		button.buttonWidth = buttonWidth;
		button.buttonHeight = buttonHeight;
		button.imageWidth = imageWidth;
		button.imageHeight = imageHeight;
		button.initAllButImages();
		if (actionListener != null)
			button.addActionListener(actionListener);
		return button;
	}

	private FLGCheckUpRelatorButton() {
	}
}