package org.openuss.services.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Role")
public enum Role {
	NONE, 
	PARTICIPANT,
	ASSISTANT
}
