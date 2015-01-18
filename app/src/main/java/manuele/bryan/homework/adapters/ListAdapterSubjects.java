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

import manuele.bryan.homework.helpers.ColorHelper;
import manuele.bryan.homework.R;

public class ListAdapterSubjects extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> subjects;

    public ListAdapterSubjects(Context context, List<String> subjects) {
        if (subjects == null) {
            throw new IllegalArgumentException("subjects list null");
        }
        this.context = context;
        this.subjects = subjects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder holder;
        if (itemView == null) {
            itemView = layoutInflater.inflate(R.layout.add_subject_item_layout, null);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        Typeface robotoFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Light.ttf");

        holder.listTitle.setTypeface(robotoFont);

        String subject = subjects.get(position);
        holder.listImageView.setBackgroundColor(ColorHelper.generateIntFromString(subject));
        holder.listTitle.setText(subject);

        return itemView;
    }

    public final static class ViewHolder {
        ImageView listImageView;
        TextView listTitle;

        public ViewHolder(View itemView) {
            listImageView = (ImageView) itemView.findViewById(R.id.coloredImageView);
            listTitle = (TextView) itemView.findViewById(R.id.addSubjectSubjectTitle);
        }
    }


}
