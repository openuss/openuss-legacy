/*
 * Created on 13.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package freestyleLearningGroup.independent.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author johe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FLGEditToolBarToggleButton extends FLGEditToolBarButton {

	protected boolean m_checked = false;
	
	protected Image m_checkedImages[] = new Image[4];
	protected Image m_unCheckedImages[] = new Image[4];

	/**
	 * @param image
	 * @param toolTipText
	 * @param actionListener
	 */
	public FLGEditToolBarToggleButton(
		Image checkedImage,
		Image unCheckedImage,
		String toolTipText) {
			super(unCheckedImage,
				toolTipText,
				null);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setChecked(!isChecked());
				}
			});
		// Create the images for toggeling...
		m_checkedImages[0] = FLGImageUtility.createAntiAliasedImage(checkedImage, imageWidth, imageHeight);
		m_checkedImages[1] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createImageWithDifferentBrightness(checkedImage, 0.75),
			imageWidth, imageHeight);
		m_checkedImages[2] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createImageWithDifferentBrightness(checkedImage, 1.5),
			imageWidth, imageHeight);
		m_checkedImages[3] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createGrayImage(checkedImage),
			imageWidth, imageHeight);

		m_unCheckedImages[0] = FLGImageUtility.createAntiAliasedImage(unCheckedImage, imageWidth, imageHeight);
		m_unCheckedImages[1] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createImageWithDifferentBrightness(unCheckedImage, 0.75),
			imageWidth, imageHeight);
		m_unCheckedImages[2] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createImageWithDifferentBrightness(unCheckedImage, 1.5),
			imageWidth, imageHeight);
		m_unCheckedImages[3] = FLGImageUtility.createAntiAliasedImage(FLGImageUtility.createGrayImage(unCheckedImage),
			imageWidth, imageHeight);
	}

	public void setChecked(boolean checked) {
		m_checked = checked;
		// Toggle...
		if (m_checked) {
			image_normal = m_checkedImages[0];
			image_pressed = m_checkedImages[1];
			image_rollover = m_checkedImages[2];
			image_disabled = m_checkedImages[3];
		}
		else {
			image_normal = m_unCheckedImages[0];
			image_pressed = m_unCheckedImages[1];
			image_rollover = m_unCheckedImages[2];
			image_disabled = m_unCheckedImages[3];
		}

		repaint();
	}
	
	public boolean isChecked() {		
		return m_checked;
	}
}
