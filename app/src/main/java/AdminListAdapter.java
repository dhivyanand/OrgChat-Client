import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.system.orgchat_client.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by System on 1/12/18.
 */

public class AdminListAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> name , notification;
    ArrayList<Bitmap> image;

    ImageView icon;
    TextView text_name,text_notification;


    AdminListAdapter(Context c , ArrayList<String> name , ArrayList<String> notification , ArrayList<Bitmap> image){
        this.c = c;
        this.name = name;
        this.notification = notification;
        this.image = image;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return name.size();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = layoutInflater.inflate(R.layout.adminlist_adapter,null);

        icon = (ImageView)root.findViewById(R.id.image);
        text_name = (TextView)root.findViewById(R.id.name);
        text_notification = (TextView)root.findViewById(R.id.notification);

        text_name.setText(name.get(i).toString());
        text_notification.setText(notification.get(i).toString());
        icon.setImageBitmap(image.get(i));

        return null;
    }
}
