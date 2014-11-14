package com.itaste.yuntu.model;

import java.io.Serializable;

/**
 * 高德地图记录的图片信息
 *
 */
public class DtoImage implements Serializable {
private String _id;
private String _preurl;
private String _url;
public String getId() {
	return _id;
}
public void setId(String id) {
	this._id = id;
}
public String getPreurl() {
	return _preurl;
}
public void setPreurl(String preurl) {
	this._preurl = preurl;
}
public String getUrl() {
	return _url;
}
public void setUrl(String url) {
	this._url = url;
}




}
