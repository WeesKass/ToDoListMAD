package com.example.to_do_list_mad;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_do_list_mad.model.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> tasks;
    private OnItemClickListener listener;

    public TaskAdapter(List<Task> tasks, Context context) {
        this.context = context;
        this.tasks = tasks;
    }

    public interface OnItemClickListener {
        void onItemDelete(int position, Task task);

        void onItemEdit(int position, Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.itemView.setTag(tasks.get(position));
        Task currentTask = tasks.get(position);
        holder.itemName.setText(currentTask.getTaskName());
        holder.date.setText(currentTask.getDate());
        holder.priority.setText(currentTask.getPriority());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView priority;
        TextView date;
        ImageView edit;
        ImageView delete;

        public TaskViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            priority = itemView.findViewById(R.id.priority);
            date = itemView.findViewById(R.id.date);
            edit = itemView.findViewById(R.id.edit_task_button);
            delete = itemView.findViewById(R.id.delete_task);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position, tasks.get(getAdapterPosition()));
                            notifyItemRemoved(position);
                        }
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemEdit(position, tasks.get(getAdapterPosition()));
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("todo", tasks.get(getAdapterPosition()));
                    Navigation.findNavController(view).navigate(R.id.action_listFragment_to_updateFragment, bundle);
                }
            });
        }
    }
}
