package com.itaste.yuntu.model;

import java.util.ArrayList;
import java.util.List;

/*
 通用DTO类
 数据格式：
 {
 "count": "2", 
 "info": "OK", 
 "status": 1, 
 "datas": [
 {model:json},
 {
 "_id": "86", 
 "_location": "116.402199,39.86937", 
 "_name": "景泰公园中门", 
 "_address": "永定门外街道民主北街社区居委会北侧", 
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
 "_updatetime": "2014-11-05 16:07:46"
 } 
 ]
 }*/
public class AMapDTO<T> {
	private int count;
	private String info;
	private int status;
	private List<T> datas = new ArrayList<T>();// 表数据

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	// 由于listview adapter缘故，要持有相同的datas内存，必须copy，不能重新复制
	public void copy(AMapDTO<T> dto) {

		if (dto != null) {
			this.count = dto.count;
			this.info = dto.info;
			this.status = dto.status;
			this.datas.clear();
			this.datas.addAll(dto.getDatas());
		}
	}
	public void add(AMapDTO<T> dto) {

		if (dto != null) {
			this.count = dto.count;
			this.info = dto.info;
			this.status = dto.status;
			this.datas.addAll(dto.getDatas());
		}
	}
	
}
