package com.itaste.yuntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.itaste.yuntu.model.AMapDTO;
import com.itaste.yuntu.model.DtoImage;
import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.LBSCloudSearch;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;


public class LBSFacMapActivity  extends FragmentActivity 
implements LocationSource, AMapLocationListener,OnCheckedChangeListener, OnMarkerClickListener, OnInfoWindowClickListener, InfoWindowAdapter, OnClickListener
{
	    private AMap aMap;
	    private MapView mapView;
	    private OnLocationChangedListener mListener;
	    private LocationManagerProxy mAMapLocationManager;
	    private RadioGroup mGPSModeGroup;//地图定位模式切换
		private LatLng latlng = new LatLng(36.061, 103.834);
	    private Button searchBtn;
	  
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fac_map);
	        mapView = (MapView) findViewById(R.id.mainmap);
	        mapView.onCreate(savedInstanceState);
	        init();
	    }
	 
	    /**
	     * 初始化AMap对象
	     */
	    private void init() {
	        if (aMap == null) {
	            aMap = mapView.getMap();
	            setUpMap();
	        }
	    }
	 
	    private void setUpMap() {
	    	aMap.setLocationSource(this);// 设置定位监听
	        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
	        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
	        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
	        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	        mGPSModeGroup=(RadioGroup) findViewById(R.id.gps_radio_group);
			mGPSModeGroup.setOnCheckedChangeListener(this);
			//searchBtn = (Button) this.findViewById(R.id.search);
			//searchBtn.setOnClickListener(this);
			/**设置自定义信息窗体**/
			//aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
			aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
			aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
			aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
	    }
	 
	    /**
	     * 此方法需存在
	     */
	    @Override
	    protected void onResume() {
	        super.onResume();
	        mapView.onResume();
	    }
	 
	    /**
	     * 此方法需存在
	     */
	    @Override
	    protected void onPause() {
	        super.onPause();
	        mapView.onPause();
	        deactivate();
	    }
	 
	    /**
	     * 此方法需存在
	     */
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        mapView.onDestroy();
	    }
	 
	    /**
	     * 此方法已经废弃
	     */
	    @Override
	    public void onLocationChanged(Location location) {
	    }
	 
	    /**
	     * 定位成功后回调函数
	     */
		@SuppressLint("NewApi")
		@Override
	    public void onLocationChanged(AMapLocation amapLocation) {
	        if (mListener != null && amapLocation != null) {
	            if (amapLocation.getAMapException().getErrorCode() == 0) {
	                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
	                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
	                		new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude()), 18, 0, 30)), 1000, null);
	            }
	        }
	    }
	 
	    /**
	     * 激活定位
	     */
	    @Override
	    public void activate(OnLocationChangedListener listener) {
	        mListener = listener;
	        if (mAMapLocationManager == null) {
	            mAMapLocationManager = LocationManagerProxy.getInstance(this);
	            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
	            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
	            //在定位结束后，在合适的生命周期调用destroy()方法     
	            //其中如果间隔时间为-1，则定位只定一次
	            mAMapLocationManager.requestLocationData(
	                    LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
	        }
	    }
	 
	 
	    /**
	     * 停止定位
	     */
	    @Override
	    public void deactivate() {
	        mListener = null;
	        if (mAMapLocationManager != null) {
	            mAMapLocationManager.removeUpdates(this);
	            mAMapLocationManager.destroy();
	        }
	        mAMapLocationManager = null;
	    }

	  //更改定位模式
	  		@Override
	  		public void onCheckedChanged(RadioGroup group, int checkedId) {
	  		switch(checkedId){
	  		case R.id.gps_locate_button:
	  			//设置定位的类型为定位模式 
	  			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	  			break;
	  		case R.id.gps_follow_button:
	  			//设置定位的类型为 跟随模式
	  			aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
	  			break;
	  		case R.id.gps_rotate_button:
	  			//设置定位的类型为根据地图面向方向旋转 
	  			aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
	  			break;
	  		}
	  	}
	  		
	    //点击搜索按钮
	  	public void search(){
	  		HashMap<String, String> filterParams = new HashMap<String, String>();
	  		filterParams.put("city", "全国");
	  		filterParams.put("keywords", "");
	  		filterParams.put("filter", "fac_area:200");
			LBSCloudSearch.request(LBSCloudSearch.SEARCH_TYPE_LOCAL, filterParams,new AsyncHttpResponseHandler(){
				 @Override
				public void onSuccess(int arg0, Header[] arg1, byte[] data) {
					String dataStr = new String(data);
					dataStr = dataStr.replaceAll("\"_", "\"");
					 AMapDTO<FacInfoModel>  dto = JSON.parseObject(dataStr, new TypeReference<AMapDTO<FacInfoModel>>(){});
					
					List<FacInfoModel> facInfos = dto.getDatas();
					Toast.makeText(
							LBSFacMapActivity.this
							,"符合条件数据:"+dto.getCount()+"个"
							,Toast.LENGTH_LONG).show();
					
					addMarkersToMap(facInfos);
				}
				 @Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
					Toast.makeText(LBSFacMapActivity.this, "读取数据失败，请重试", Toast.LENGTH_LONG).show();
					
				}
			 });
	  	}
	  		

		/**
		 * 在地图上添加marker
		 */
		private void addMarkersToMap(List<FacInfoModel> facInfos) {
			
			//gif标注图标
			ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			Builder latLngbuilder = new LatLngBounds.Builder();
			Marker addMarker = null;
			FacInfoModel fac = null;
			for (int i=0;i<facInfos.size();i++) {
				fac = facInfos.get(i);
				//添加标注
				MarkerOptions markerOption = 
						new MarkerOptions()
						.anchor(0.5f, 0.5f)
						.title(fac.getName())
						.snippet(fac.getAddress())
						.position(fac.getLocation())
						.icons(giflist)
						.perspective(true)//设置标记的近大远小效果，在marker初始化时使用。
						.draggable(true)//设置标记是否可拖动。
						.period(50);//设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快。
				
				addMarker = aMap.addMarker(markerOption);
				addMarker.setObject(fac);//传递数据
				latLngbuilder.include(fac.getLocation());
			}
			if (addMarker!=null) {
				addMarker.showInfoWindow();
			}
			// 设置所有maker显示在当前可视区域地图中
			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngbuilder.build(),110));
		}	
	  		
	    
	
		
		@Override
		public View getInfoContents(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			View infoWindow = getLayoutInflater().inflate(
					R.layout.map_info_window, null);
			render(marker, infoWindow);
			return infoWindow;
		}
		
		/**
		 * 自定义infowinfow窗口
		 */
		public void render(Marker marker, View view) {
			
				FacInfoModel fac = (FacInfoModel) marker.getObject();
				SmartImageView badge = (SmartImageView) view.findViewById(R.id.badge);
			    TextView fac_name = ((TextView) view.findViewById(R.id.fac_name));
			    TextView fac_address = ((TextView) view.findViewById(R.id.fac_address));
			    TextView fac_area = ((TextView) view.findViewById(R.id.fac_area));
			    TextView fac_sushe_area = ((TextView) view.findViewById(R.id.fac_sushe_area));
			    TextView fac_price = ((TextView) view.findViewById(R.id.fac_price));
			    TextView fac_height = ((TextView) view.findViewById(R.id.fac_height));
			    TextView fac_struct = ((TextView) view.findViewById(R.id.fac_struct));
			    TextView fac_peidian = ((TextView) view.findViewById(R.id.fac_peidian));
			    TextView fac_desc = ((TextView) view.findViewById(R.id.fac_desc));
			    DtoImage image = fac.getFistImage();
				if (image!=null) {
					badge.setImageUrl(image.getPreurl());
				}
			    fac_name.setText(marker.getTitle());
			    fac_address.setText(marker.getSnippet());
			    fac_area.setText(fac.getFac_area());
			    fac_sushe_area.setText(fac.getFac_sushe_area());
			    fac_price.setText(fac.getFac_rent_orsale_price());
			    fac_height.setText(fac.getFac_floor());
			    fac_struct.setText(fac.getFac_struct());
			    fac_peidian.setText(fac.getFac_peidian());
			    fac_desc.setText(fac.getFac_desc());
		}

		
		
		@Override
		public void onInfoWindowClick(Marker arg0) {
			Toast.makeText(this, "ni dian ji le infowin", Toast.LENGTH_LONG).show();
			
		}

		@Override
		public boolean onMarkerClick(Marker arg0) {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public void onClick(View v) {
		search();
			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
			
}
