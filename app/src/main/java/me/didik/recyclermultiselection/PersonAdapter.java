package me.didik.recyclermultiselection;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by didik on 5/19/17.
 * S
 */

class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyHolder> {
    private List<Person> dataset;
    private SparseBooleanArray checked = new SparseBooleanArray();
    private ClickListener listener;
    private boolean isMultiselect;
    private int itemSelected;

    interface ClickListener {
        void onClick(int position);

        void onTap(int totalSelected);

        void onLongClick();
    }

    PersonAdapter(List<Person> dataset, ClickListener listener) {
        this.dataset = dataset;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Person item = dataset.get(position);

        holder.name.setText(item.getName());
        holder.job.setText(item.getJob());
        holder.itemView.setBackgroundColor(checked.get(position) ? Color.LTGRAY : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    void exitMultiselectMode() {
        isMultiselect = false;
        itemSelected = 0;
        checked.clear();
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView name, job;

        MyHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_name);
            job = (TextView) itemView.findViewById(R.id.tv_job);

            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isMultiselect) {
                boolean currentState = checked.get(getAdapterPosition());
                checked.put(getAdapterPosition(), !currentState);
                notifyItemChanged(getAdapterPosition());
                listener.onTap(currentState ? --itemSelected : ++itemSelected);
            } else listener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (!isMultiselect) {
                isMultiselect = true;
                checked.put(getAdapterPosition(), true);
                notifyItemChanged(getAdapterPosition());
                listener.onLongClick();
                listener.onTap(++itemSelected);
                return true;
            }

            return false;
        }
    }
}
