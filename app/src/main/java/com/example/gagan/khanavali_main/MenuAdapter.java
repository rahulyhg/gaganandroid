package com.example.gagan.khanavali_main;

/**
 * Created by dganeshappa on 11/10/2015.
 */


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ParseException;
        import android.os.AsyncTask;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpDelete;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.net.URI;
        import java.util.ArrayList;

/**
 * Created by gagan on 10/31/2015.
 */
public class MenuAdapter extends ArrayAdapter<Menu> {

    public ArrayList<Menu> getCustomerList() {
        return customerList;
    }

    ArrayList<Menu> customerList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    Context mContext;
    String mVendor_email;

    public MenuAdapter(Context context, int resource, ArrayList<Menu> objects, String vendor_email) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        customerList = objects;
        mContext = context;
        mVendor_email = new String(vendor_email);
    //    customerList = new ArrayList<Menu>();

    //    bindView();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
         //   holder.itemid = (TextView) v.findViewById(R.id.itemid);
            holder.itemavailability = (TextView) v.findViewById(R.id.itemavailability);
            holder.itemname = (TextView) v.findViewById(R.id.itemname);
            holder.itemprice = (TextView) v.findViewById(R.id.itemprice);
        Button deleteButton = (Button) v.findViewById(R.id.delete_menu_Item_button);
        deleteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                deleteMenuItem(customerList.get(position).getName());
            }
        });

        v.setTag(holder);
    } else {
        holder = (ViewHolder) v.getTag();
    }
    //  holder.imageview.setImageResource(R.drawable.ic);
    // new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
    holder.itemname.setText(customerList.get(position).getName());
    //   holder.itemid.setText(customerList.get(position).getid());
    holder.itemavailability.setText(customerList.get(position).getAvailability());
    holder.itemprice.setText(customerList.get(position).getPrice());

    return v;

}
static class ViewHolder {
    //  public ImageView imageview;
        public TextView itemname;
        public TextView itemprice;
        public TextView itemavailability;
        public TextView itemid;
    }
    public void deleteMenuItem(String ItemName)
    {
        String order_url = "http://oota.herokuapp.com/v1/vendor/menu/item/";
        order_url= order_url.concat(mVendor_email);
        order_url= order_url.concat("/");
        String item = ItemName.replace(" ", "%20");
        order_url= order_url.concat(item);
        new DeleteJSONAsyncTask().execute(order_url);
    }

    public void bindView() {
        String order_url = "http://oota.herokuapp.com/v1/vendor/menu/";
        order_url= order_url.concat(mVendor_email);
        new GetJSONAsyncTask().execute(order_url);
    }
    public  class GetJSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        //        ListView mListView;
//        Activity mContex;
        public  GetJSONAsyncTask()
        {
//            this.mListView=gview;
//            this.mContex=contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = new ProgressDialog(mContext);
//            dialog.setMessage("Loading, please wait");
//            dialog.setTitle("Connecting server");
//            dialog.show();
//            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();

                    String data = EntityUtils.toString(entity);
                    JSONArray jarray = new JSONArray(data);
                    customerList.clear();
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Menu cus = new Menu();

                        cus.setName(object.getString("name"));
                        cus.setAvailability(object.getString("availability"));
                        cus.setid(object.getString("_id"));
                        cus.setPrice(object.getString("price"));
                        // cus.setImage(object.getString("image"));

                        customerList.add(cus);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            //    dialog.cancel();
            notifyDataSetChanged();
            if (result == false)
                Toast.makeText(mContext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
    public  class DeleteJSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        //        ListView mListView;
//        Activity mContex;
        public  DeleteJSONAsyncTask()
        {
//            this.mListView=gview;
//            this.mContex=contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = new ProgressDialog(mContext);
//            dialog.setMessage("Loading, please wait");
//            dialog.setTitle("Connecting server");
//            dialog.show();
//            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpDelete httppost = new HttpDelete(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
              int status = response.getStatusLine().getStatusCode();

                if (status == 200) {


                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            //    dialog.cancel();
            bindView();
       //     notifyDataSetChanged();
            if (result == false)
                Toast.makeText(mContext, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
