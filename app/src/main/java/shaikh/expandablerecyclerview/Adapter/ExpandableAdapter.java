package shaikh.expandablerecyclerview.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import shaikh.expandablerecyclerview.Model.DataModel;
import shaikh.expandablerecyclerview.R;

/**
 * Created by Adil Shaikh on 3/2/16.
 */
public class ExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int PARENT = 0;
    public static final int CHILD = 1;

    private Context mContext;
    private ArrayList<DataModel> mFoodModels;
    private ArrayMap<String, ArrayList<DataModel>> backup = new ArrayMap<>();
    private OnChildClickListener mChildListener;

    public ExpandableAdapter(Context context, ArrayList<DataModel> models) {
        mFoodModels = models;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PARENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_header, parent, false);
            return new ParentViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_child, parent, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ParentViewHolder) {
            ((ParentViewHolder) holder).mTxtHeader.setText(mFoodModels.get(position).getItemName());
            if (position != mFoodModels.size() - 1 && mFoodModels.get(position + 1).getType() == 0) {
                ((ParentViewHolder) holder).mImgExpandCollapse.setVisibility(View.GONE); //remove expand/collapse if item has no child
            }
            if (position == mFoodModels.size() - 1) {   //incase last item has no childs
                ((ParentViewHolder) holder).mImgExpandCollapse.setVisibility(View.GONE);
                ((ParentViewHolder) holder).mViewShadow.setVisibility(View.VISIBLE);
            }
        } else {
            ((ChildViewHolder) holder).mTxtChild.setText(mFoodModels.get(position).getItemName());
        }
    }

    @Override
    public int getItemCount() {
        return mFoodModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mFoodModels.get(position).getType();
    }

    public void setOnChildClickListener(OnChildClickListener listener) {
        mChildListener = listener;
    }

    public interface OnChildClickListener {
        void onChildClick(int position);
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTxtHeader;
        private View mViewShadow;
        private ImageView mImgExpandCollapse;

        public ParentViewHolder(View itemView) {
            super(itemView);
            mTxtHeader = (TextView) itemView.findViewById(R.id.header_title);
            mImgExpandCollapse = (ImageView) itemView.findViewById(R.id.header_expand_click);
            mViewShadow = itemView.findViewById(R.id.shadow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mFoodModels.get(getLayoutPosition()).isCollapsed()) {
                mFoodModels.get(getLayoutPosition()).setIsCollapsed(false);
                expand(mFoodModels.get(getLayoutPosition()).getItemName(), getLayoutPosition());
                mImgExpandCollapse.animate().rotation(0f).setDuration(300).start();
                if (getLayoutPosition() != mFoodModels.size() - 1) {
                    mViewShadow.setVisibility(View.GONE);
                }
            } else {
                mFoodModels.get(getLayoutPosition()).setIsCollapsed(true);
                mImgExpandCollapse.animate().rotation(180f).setDuration(300).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewShadow.setVisibility(View.VISIBLE);
                    }
                }, 500);
                collapse();
            }

        }

        private void collapse() {
            if (getLayoutPosition() != mFoodModels.size() - 1) {
                int pos = getLayoutPosition() + 1;
                int removeLastIndex = 0;
                int removeStartIndex = pos;
                ArrayList<DataModel> temp = new ArrayList<>();
                while (mFoodModels.get(pos).getType() != 0) {
                    temp.add(mFoodModels.get(pos));
                    removeLastIndex++;
                    mFoodModels.remove(pos);
                }
                backup.put(mFoodModels.get(getLayoutPosition()).getItemName(), temp);
                notifyItemRangeRemoved(removeStartIndex, removeLastIndex);
            }
        }

        private void expand(String item, int pos) {
            int addStartIndex = pos + 1;
            int addLastIndex = 0;
            if (backup.containsKey(item)) {
                ArrayList<DataModel> model = backup.get(item);
                for (DataModel temp : model) {
                    mFoodModels.add(++pos, temp);
                    addLastIndex = addLastIndex + 1;
                }
            }
            notifyItemRangeInserted(addStartIndex, addLastIndex);
        }
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTxtChild;

        public ChildViewHolder(View itemView) {
            super(itemView);
            mTxtChild = (TextView) itemView.findViewById(R.id.child_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mChildListener != null) {
                mChildListener.onChildClick(getLayoutPosition());
            }
        }
    }
}
