package com.itaste.yuntu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.itaste.yuntu.model.DtoImage;
import com.itaste.yuntu.model.FacInfoModel;
import com.itaste.yuntu.util.ItasteApplication;
import com.itaste.yuntu.util.LBSCloudUtils;
import com.loopj.android.image.SmartImageView;


public class LBSFacMapActivity  extends Activity 
implements LocationSource, AMapLocationListener,
OnCheckedChangeListener, OnMarkerClickListener,
OnInfoWindowClickListener, InfoWindowAdapter,
OnMapLongClickListener
{
	    private AMap aMap;
	    private MapView mapView;
	    private OnLocationChangedListener mListener;
	    private LocationManagerProxy mAMapLocationManager;
	    private RadioGroup mGPSModeGroup;//地图定位模式切换
	    private LatLng userClickLatlng;//用户点击的位置坐标
	  
	 
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
	        //将当前activity加入到application中
	        ItasteApplication.getInstance().facMapActivity = this;
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
			//长按时为添加或者修改标记处的数据
			aMap.setOnMapLongClickListener(this);
	    }
	 
	    /**
	     * 此方法需存在
	     */
	    @Override
	    protected void onResume() {
	        super.onResume();
	        mapView.onResume();
	        //如果有搜索数据，刷新地图标记
	        if(ItasteApplication.getInstance().getValidateDto().getDatas().size()>0){
	        	addMarkersToMap();
	        }
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
	            	ItasteApplication.getInstance().currentLocation = amapLocation;
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
	  		
	   
	  		



		/**
		 * 在地图上添加marker
		 */
		public void addMarkersToMap() {
			//从地图上删除所有的Marker，Overlay，Polyline 等覆盖物。
			ItasteApplication app = ItasteApplication.getInstance();
			List<FacInfoModel> facInfos = app.getValidateDto().getDatas();
			aMap.clear();
			// 显示用户的当前位置
	
			//gif标注图标
			ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			giflist.add(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			Builder latLngbuilder = new LatLngBounds.Builder();
			Marker addMarker = null;
			FacInfoModel fac = null;
			for (int i=0;facInfos!=null&&i<facInfos.size();i++) {
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
			//添加用当前位置
			LatLng currentLocation = app.getCurrentLocation();
			if(currentLocation!=null){
				 
		    aMap.addMarker(
						 new MarkerOptions()
						.anchor(0.5f, 0.5f)
						.title(getString(R.string._mylocation))
						.snippet(app.currentLocation.getAddress())
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.start))
						.perspective(true)//设置标记的近大远小效果，在marker初始化时使用。
						.period(50)
						.position(currentLocation)
						);
				// 自定义系统定位小蓝点
				MyLocationStyle myLocationStyle = new MyLocationStyle();
				myLocationStyle.myLocationIcon(BitmapDescriptorFactory
						.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
				myLocationStyle.strokeColor(Color.BLUE);// 设置圆形的边框颜色
				myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
				// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
				myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
				aMap.setMyLocationStyle(myLocationStyle);
				//地图显示范围包括当前用户位置
				latLngbuilder.include(currentLocation);
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
			
				final FacInfoModel fac = (FacInfoModel) marker.getObject();
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
					badge.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Bundle bundle = new Bundle();
							bundle.putSerializable("fac_images", (Serializable) fac.getImage());
							Intent intent = new Intent(LBSFacMapActivity.this, FacImageGalleryActivity.class);
							intent.putExtras(bundle);
							LBSFacMapActivity.this.startActivity(intent);
						}
					});
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
		public void onMapLongClick(LatLng latlng) {
			
			Toast.makeText(this, "纬度："+latlng.latitude+"精度："+latlng.longitude, Toast.LENGTH_LONG).show();
			/*String url="mqqwpa://im/chat?chat_type=wpa&uin=501863587";  
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));*/
			userClickLatlng = latlng;
			Intent addFacInfoIntent = new Intent(this, FacInfoAddActivity.class);
			startActivityForResult(addFacInfoIntent,ItasteApplication.ADD_FAC_REQUEST_CODE);
			
		}
		//添加数据
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(ItasteApplication.ADD_FAC_REQUEST_CODE==requestCode&&ItasteApplication.ADD_FAC_RESULT_CODE==resultCode&&data!=null){
				HashMap<String,String> facinfo = (HashMap<String, String>) data.getSerializableExtra("addfacinfo");
				facinfo.put(FacInfoModel._LOCATION, userClickLatlng.longitude+","+userClickLatlng.latitude);
				String facinfostr = JSON.toJSONString(facinfo);
				LBSCloudUtils.addinfo(this, facinfostr);
				
			}
			
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
