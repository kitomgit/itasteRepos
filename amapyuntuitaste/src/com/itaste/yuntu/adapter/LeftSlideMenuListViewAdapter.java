package com.itaste.yuntu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.itaste.yuntu.R;

public class LeftSlideMenuListViewAdapter extends SimpleAdapter implements
		OnItemClickListener {
	private  Context context;
	private static LeftSlideMenuListViewAdapter viewAdapter;
	public static LeftSlideMenuListViewAdapter getInstance(Context context){
		if(viewAdapter!=null) return viewAdapter;
		//数据
		 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
		 HashMap<String, Object> map1 = new HashMap<String, Object>();  
         map1.put("ItemImage", R.drawable.ic_launcher);//加入图片            map.put("ItemTitle", "第"+i+"行");  
         map1.put("ItemText", "高级搜索");
         
         HashMap<String, Object> map2 = new HashMap<String, Object>();  
         map2.put("ItemImage", R.drawable.ic_launcher);//加入图片            map.put("ItemTitle", "第"+i+"行");  
         map2.put("ItemText", "语音搜索");
         
		 for(int i=0;i<8;i++)  
		        {  
		            HashMap<String, Object> map3 = new HashMap<String, Object>();  
		            map3.put("ItemImage", R.drawable.ic_launcher);//加入图片            map.put("ItemTitle", "第"+i+"行");  
		            map3.put("ItemText", "这是第"+i+"行");  
		            listItem.add(map3);  
		        } 
		 listItem.add(map1);
		 listItem.add(map2);
		 
		return new LeftSlideMenuListViewAdapter(
				 context,
				 listItem,
				 R.layout.left_menu_listviewitem,
				 new String[]{"ItemImage","ItemTitle", "ItemText"},
				 new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText});
		
	}
	private LeftSlideMenuListViewAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		
	}
	
	//左侧菜单的点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id){
		Toast.makeText(context, "ni dian ji le left slide menu ", Toast.LENGTH_LONG).show();
		
	}

}
