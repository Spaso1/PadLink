package org.astral.padlinkandroid.been;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import org.astral.padlinkandroid.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private List<SettingItem> items;
    private OnItemClickListener listener;

    public SettingsAdapter(List<SettingItem> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SettingItem item = items.get(position);
        holder.title.setText(item.getTitle());

        // 根据类型显示不同的控件
        switch (item.getType()) {
            case SettingItem.TYPE_HOST:
                holder.switchCompat.setVisibility(View.GONE);
                break;
            case SettingItem.TYPE_SELECT:
                holder.arrow.setVisibility(View.VISIBLE);
                break;
            case SettingItem.TYPE_INFO:
                holder.info.setVisibility(View.VISIBLE);
                break;
        }

        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public SwitchCompat switchCompat;
        public ImageView arrow;
        public TextView info;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            switchCompat = view.findViewById(R.id.switchCompat);
            arrow = view.findViewById(R.id.arrow);
            info = view.findViewById(R.id.info);
        }
    }

    // 点击事件接口
    public interface OnItemClickListener {
        void onItemClick(int position, SettingItem item);
    }
}
