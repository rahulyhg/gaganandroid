package order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gagan.khanavali_main.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dganeshappa on 11/11/2015.
 */
public class OrderAdapter extends ArrayAdapter<order.Order> {

    ArrayList<order.Order> orderList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public OrderAdapter(Context context, int resource, ArrayList<order.Order> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        orderList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            //   holder.itemid = (TextView) v.findViewById(R.id.itemid);
            holder.itemname = (TextView) v.findViewById(R.id.customer_name);
            holder.itemphone = (TextView) v.findViewById(R.id.customer_phone);
            holder.itemTime = (TextView) v.findViewById(R.id.datetime_list);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //  holder.imageview.setImageResource(R.drawable.ic);
        // new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.itemname.setText(orderList.get(position).getCustomer().getName());
        //   holder.itemid.setText(customerList.get(position).getid());
        holder.itemphone.setText(orderList.get(position).getCustomer().getPhone());


        SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date getDate = null;
        try {
            getDate = existingUTCFormat.parse(orderList.get(position).getTrackerDetail().get(0).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate);
        cal.add(Calendar.HOUR, 5);
        cal.add(Calendar.MINUTE, 30);
        String newTime = requiredFormat.format(cal.getTime());

        holder.itemTime.setText(newTime);
        return v;

    }

    static class ViewHolder {
        //  public ImageView imageview;
        public TextView itemname;
        public TextView itemphone;
        public TextView itemavailability;
        public TextView itemTime;
    }

   /* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }*/
}
