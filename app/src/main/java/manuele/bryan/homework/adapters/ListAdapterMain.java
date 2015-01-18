package manuele.bryan.homework.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import manuele.bryan.homework.helpers.DatesHelper;
import manuele.bryan.homework.R;

public class ListAdapterMain extends BaseAdapter {
    private Context context;
    
    private List<ListModel> mData;
    private LayoutInflater layoutInflater;

    public ListAdapterMain(Context context, List<ListModel> mData) {
        if (mData == null) {
            throw new IllegalArgumentException("mData null");
        }
        this.context = context;
        this.mData = mData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder holder;

        if (itemView == null) {
            itemView = layoutInflater.inflate(R.layout.main_item_layout, null);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        ListModel data = mData.get(position);

        Typeface robotoFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Light.ttf");
        
        holder.listTitle.setTypeface(robotoFont);
        holder.listSubject.setTypeface(robotoFont);
        holder.listDate.setTypeface(robotoFont);

        holder.listImageView.setBackgroundColor(data.imageViewColor);
        holder.listTitle.setText(data.title);
        holder.listSubject.setText(data.subject);

        String dateString;
        double difference = DatesHelper.difference(data.date);

        if (difference < 0) {
            dateString = "Deadline passed ):";
        } else if (difference < 1) {
            dateString = "Due Today!!";
        } else if (difference < 2) {
            dateString = "Due tomorrow";
        } else if (difference < 7) {
            dateString = "Due in " + ((int) difference) + " days";
        } else {
            dateString = "Due: " + DatesHelper.dateToString(data.date);
        }

        holder.listDate.setText(dateString);

        return itemView;
    }

    public final static class ViewHolder {
        ImageView listImageView;
        TextView listTitle;
        TextView listSubject;
        TextView listDate;

        public ViewHolder(View itemView) {
            listImageView = (ImageView) itemView.findViewById(R.id.listImage);
            listTitle = (TextView) itemView.findViewById(R.id.listTitle);
            listSubject = (TextView) itemView.findViewById(R.id.listSubject);
            listDate = (TextView) itemView.findViewById(R.id.listDate);
        }
    }


}
