package org.openuss.web.documents;

import java.awt.Image;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Checks whether or not an uploaded files contains valid image data in JPG, PNG or GIF format.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@FacesValidator(value="imageValidator")
public class ImageValidator extends BaseBean implements Validator {
	
	private static final String ILLEGAL_IMAGE_DATA_MESSAGE = "wiki_error_illegal_image_data";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final UploadedFile uploadedFile = (UploadedFile) value;
		if (!isValidImageFile(uploadedFile)) {
			((UIInput) component).setValid(false);
			addError(component.getClientId(context), i18n(ILLEGAL_IMAGE_DATA_MESSAGE), null);
		}
	}
	
	private boolean isValidImageFile(UploadedFile uploadedFile) {
		try {
			Image image = ImageIO.read(uploadedFile.getInputStream());
			return (image != null);
		} catch (IOException exception) {
			return false;
		}
	}
	
}
