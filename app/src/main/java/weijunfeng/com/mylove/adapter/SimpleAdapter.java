package weijunfeng.com.mylove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import weijunfeng.com.mylove.R;
import weijunfeng.com.mylove.bean.WeiXin;

/**
 * Created by hexin on 2016/9/19.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
    private ArrayList<WeiXin.Item> datas;
    private Context context;

    public SimpleAdapter(Context context, ArrayList<WeiXin.Item> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_list, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ViewHolder holder, int position) {
        holder.textView.setText(datas.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
