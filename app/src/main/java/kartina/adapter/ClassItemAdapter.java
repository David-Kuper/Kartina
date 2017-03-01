package kartina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kartina.R;
import kartina.model.ClassItem;
import kartina.view.ClassItemView;

/**
 * Created by David on 2016/11/9.
 */

public class ClassItemAdapter extends RecyclerView.Adapter<ClassIteViewHolder> {
    private List<ClassItem> list;
    private Context context;
    private boolean hashNext = false;

    public ClassItemAdapter(Context context, List<ClassItem> list,boolean hashNext) {
        this.context = context;
        this.list = list;
        this.hashNext = hashNext;
    }


    public void setData(List<ClassItem> list,boolean hashNext) {
        this.list = list;
        this.hashNext = hashNext;
        notifyDataSetChanged();
    }

    @Override
    public ClassIteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ClassIteViewHolder itemViewHolder;
        if (viewType == 1) {
            view = new ClassItemView(context);
            view.setTag(1);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.check_more, parent, false);
            view.setTag(2);
        }
        itemViewHolder = new ClassIteViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ClassIteViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            holder.setData(list.get(position));
        } else if (2 == getItemViewType(position)) {
            holder.checkMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked 查看更多", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < list.size()) {
            return 1;
        } else {
            return 2; // 加载更多
        }
    }

    /**
     * 附加一个：查看更多
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
}
class ClassIteViewHolder extends RecyclerView.ViewHolder {
    ClassItemView itemView;
    View checkMore;

    public ClassIteViewHolder(View itemView) {
        super(itemView);
        if (1 == (Integer) itemView.getTag()) {
            this.itemView = (ClassItemView) itemView;
        } else if (2 == (Integer) itemView.getTag()) {
            checkMore = itemView;
        }
    }

    public void setData(ClassItem data) {
        if (itemView != null)
            itemView.setData(data);
    }
}
