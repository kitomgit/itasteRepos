package com.itaste.yuntu.model;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps.model.LatLng;

/*数据格式：{
            "_id": "101", 
            "_location": "116.40316,39.908447", 
            "_name": "菖蒲河公园(东一门)、", 
            "_address": "东城区", 
            "fac_area": 200, 
            "fac_struct": "", 
            "fac_height": "", 
            "fac_peidian": "", 
            "fac_rent_orsale": "", 
            "sushe_area": "", 
            "fac_lift": "", 
            "fac_floor": "", 
            "rent_sale_type": "", 
            "fac_desc": "", 
            "_createtime": "2014-11-04 17:39:00", 
            "_updatetime": "2014-11-05 16:07:05"
        }*/
public class FacInfoModel{
 private int _id;//唯一标示
 private LatLng _location;//经纬度
 private String _name;//名称
 private String _address;//地址
 private String fac_area;//面积
 private String fac_struct;//结构
 private String fac_height;//高度
 private String fac_peidian;//配电
 private String fac_rent_orsale_price;//价格
 private String fac_sushe_area;//宿舍面积
 private String fac_lift;//电梯
 private String fac_floor;//楼层
 private String rent_sale_type;//租售类型
 private String fac_desc;//描述
 private String _createtime;//创建时间
 private String _updatetime;//修改时间
 private List<DtoImage> _image = new ArrayList<DtoImage>();//图片信息
public int get_id() {
	return _id;
}
public void setId(int _id) {
	this._id = _id;
}
public LatLng getLocation() {
	return _location;
}

public void setLocation(String _locationStr) {
	if (_locationStr!=null&&!_locationStr.trim().equals("")) {
		try{
		String[] localLat = _locationStr.split(",");
		this._location = new LatLng(Double.valueOf(localLat[1]),Double.valueOf(localLat[0]));
		}catch(Exception e){
			this._location = new LatLng(0d,0d);
		}
	}
}
public String getName() {
	return _name;
}
public void setName(String _name) {
	this._name = _name;
}
public String getAddress() {
	return _address;
}
public void setAddress(String _address) {
	this._address = _address;
}
public String getFac_area() {
	return fac_area;
}
public void setFac_area(String fac_area) {
	this.fac_area = fac_area;
}
public String getFac_struct() {
	return fac_struct;
}
public void setFac_struct(String fac_struct) {
	this.fac_struct = fac_struct;
}
public String getFac_height() {
	return fac_height;
}
public void setFac_height(String fac_height) {
	this.fac_height = fac_height;
}
public String getFac_peidian() {
	return fac_peidian;
}
public void setFac_peidian(String fac_peidian) {
	this.fac_peidian = fac_peidian;
}
public String getFac_rent_orsale_price() {
	return fac_rent_orsale_price;
}
public void setFac_rent_orsale_price(String fac_rent_orsale_price) {
	this.fac_rent_orsale_price = fac_rent_orsale_price;
}
public String getFac_sushe_area() {
	return fac_sushe_area;
}
public void setFac_sushe_area(String sushe_area) {
	this.fac_sushe_area = sushe_area;
}
public String getFac_lift() {
	return fac_lift;
}
public void setFac_lift(String fac_lift) {
	this.fac_lift = fac_lift;
}
public String getFac_floor() {
	return fac_floor;
}
public void setFac_floor(String fac_floor) {
	this.fac_floor = fac_floor;
}
public String getRent_sale_type() {
	return rent_sale_type;
}
public void setRent_sale_type(String rent_sale_type) {
	this.rent_sale_type = rent_sale_type;
}
public String getFac_desc() {
	return fac_desc;
}
public void setFac_desc(String fac_desc) {
	this.fac_desc = fac_desc;
}
public String getCreatetime() {
	return _createtime;
}
public void setCreatetime(String _createtime) {
	this._createtime = _createtime;
}
public String getUpdatetime() {
	return _updatetime;
}
public void setUpdatetime(String _updatetime) {
	this._updatetime = _updatetime;
}
public List<DtoImage> getImage() {
	return _image;
}
public void setImage(List<DtoImage> _image) {
	this._image = _image;
}
public DtoImage getFistImage() {
	return _image.get(0);
}
}
