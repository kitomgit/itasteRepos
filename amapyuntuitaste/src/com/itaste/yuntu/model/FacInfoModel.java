package com.itaste.yuntu.model;

import java.io.Serializable;
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
public class FacInfoModel implements Serializable{
 private int _id;//唯一标示
 private String _location;//经纬度
 private String _name;//名称
 private String _address;//地址
 private String fac_area;//面积
 private String fac_struct;//结构
 private String fac_height;//高度
 private String fac_peidian;//配电
 private String fac_price;//价格
 private String fac_sushe_area;//宿舍面积
 private String fac_lift;//电梯
 private String fac_floor;//楼层
 private String rent_sale_type;//租售类型
 private String fac_desc;//描述
 private String _createtime;//创建时间
 private String _updatetime;//修改时间
 private String fac_mobile;//电话
 private String fac_region;//区域
 private String qq_code;
 private String weixin_code;
 private double distance;//距离当前位置距离
//field string
 public final static String _ID="_id";//唯一标示
 public final static String _LOCATION="_location";//经纬度
 public final static String _NAME="_name";//名称
 public final static String _ADDRESS="_address";//地址
 public final static String FAC_AREA="fac_area";//面积
 public final static String FAC_STRUCT="fac_struct";//结构
 public final static String FAC_HEIGHT="fac_height";//高度
 public final static String FAC_PEIDIAN="fac_peidian";//配电
 public final static String FAC_PRICE="fac_price";//价格
 public final static String FAC_SUSHE_AREA="fac_sushe_area";//宿舍面积
 public final static String FAC_LIFT="fac_lift";//电梯
 public final static String FAC_FLOOR="fac_floor";//楼层
 public final static String RENT_SALE_TYPE="rent_sale_type";//租售类型
 public final static String FAC_DESC="fac_desc";//描述
 public final static String _CREATETIME="_createtime";//创建时间
 public final static String _UPDATETIME="_updatetime";//修改时间
 public final static String FAC_MOBILE="fac_mobile";//电话
 public final static String FAC_REGION="fac_region";//区域
 public final static  String QQ_CODE="qq_code";
 public final static String WEIXIN_CODE="weixin_code";
 
 private List<DtoImage> _image = new ArrayList<DtoImage>();//图片信息
public int get_id() {
	return _id;
}
public void setId(int _id) {
	this._id = _id;
}
public LatLng getLocation() {
	if (_location!=null&&!_location.trim().equals("")) {
		try{ 
		String[] localLat = _location.split(",");
		return new LatLng(Double.valueOf(localLat[1]),Double.valueOf(localLat[0]));
		}catch(Exception e){
		}
	}
	return new LatLng(0d,0d);
}
public String getLocationStr(){
	return _location;
}

public void setLocation(String _locationStr) {
	this._location = _locationStr;
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

public String getFac_price() {
	return fac_price;
}
public void setFac_price(String fac_price) {
	this.fac_price = fac_price;
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

public String getFac_mobile() {
	return fac_mobile;
}
public void setFac_mobile(String fac_mobile) {
	this.fac_mobile = fac_mobile;
}
public List<DtoImage> getImage() {
	return _image;
}
public void setImage(List<DtoImage> _image) {
	this._image = _image;
}
public DtoImage getFistImage() {
	return _image==null||_image.size()<1?null:_image.get(0);
}
public String getFac_region() {
	return fac_region;
}
public void setFac_region(String fac_region) {
	this.fac_region = fac_region;
}
public String getQq_code() {
	return qq_code;
}
public void setQq_code(String qq_code) {
	this.qq_code = qq_code;
}
public String getWeixin_code() {
	return weixin_code;
}
public void setWeixin_code(String weixin_code) {
	this.weixin_code = weixin_code;
}
public double getDistance() {
	return distance;
}
public void setDistance(double distance) {
	this.distance = distance;
}
}
