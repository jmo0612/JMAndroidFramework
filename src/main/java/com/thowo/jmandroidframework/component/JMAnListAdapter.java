package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.thowo.jmandroidframework.JMFunctions;
import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.table.JMRow;
//import com.thowo.jmandroidframework.db.JMRowObject;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 7/7/2017.
 */

public class JMAnListAdapter extends ArrayAdapter<JMRow> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private String imagePath;

    public static JMAnListAdapter create( Context ctx, int resourceId, List objects, String imgPath ) {
        return new JMAnListAdapter ( ctx, resourceId, objects, imgPath );
    }

    public JMAnListAdapter ( Context ctx, int resourceId, List objects, String imgPath ) {

        super( ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context=ctx;
        imagePath=imgPath;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent ) {

        /* create a new view of my layout and inflate it in the row */

        JMFunctions.trace(""+position);

        JMAnListViewCache viewCache;

        JMRow cur= getItem(position);

        JMAnRowListView rObj=new JMAnRowListView(cur);


        if ( convertView == null ) {



            convertView = (RelativeLayout) inflater.inflate(resource, parent,false);

            if(convertView.getBackground()==null){
                convertView.setBackgroundResource(R.drawable.glossy_button_selector);
            }

            viewCache = new JMAnListViewCache(convertView);

            convertView.setTag( viewCache );
        }
        else {
            viewCache = ( JMAnListViewCache ) convertView.getTag();
            convertView=(RelativeLayout)viewCache.getViewBase();
        }

        rObj.displayRowObjectData(viewCache,imagePath);



        return convertView;
    }
}
