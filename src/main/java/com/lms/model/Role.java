package com.lms.model;

import javax.persistence.Embeddable;

@Embeddable
public class Role {
   String type;

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}
}
