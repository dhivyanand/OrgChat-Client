import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by System on 1/12/18.
 */

public class AdminListAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> name , notification;
    ArrayList<Bitmap> image;

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
        return null;
    }
}
