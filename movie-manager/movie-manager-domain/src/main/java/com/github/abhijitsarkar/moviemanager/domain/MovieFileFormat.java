package com.github.abhijitsarkar.moviemanager.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "movieFileFormat")
@XmlEnum
public enum MovieFileFormat {
	@XmlEnumValue("avi")
	AVI, @XmlEnumValue("mkv")
	MKV, @XmlEnumValue("mp4")
	MP4, @XmlEnumValue("divx")
	DIVX, @XmlEnumValue("mov")
	MOV;
}
