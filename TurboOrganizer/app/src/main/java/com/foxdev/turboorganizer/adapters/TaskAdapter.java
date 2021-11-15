package com.foxdev.turboorganizer.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.foxdev.turboorganizer.R;
import com.foxdev.turboorganizer.databinding.TaskItemBinding;
import com.foxdev.turboorganizer.objects.Task;

public final class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    @NonNull
    private Task[] _tasks;

    private Consumer<Task> _deleteTask;
    private Consumer<Task> _changeCompleteStatus;

    public TaskAdapter() {
        _tasks = new Task[0];
    }

    public void SetTaskListener(@NonNull Consumer<Task> deleteTask,
                                @NonNull Consumer<Task> changeCompleteStatus) {
        _deleteTask = deleteTask;
        _changeCompleteStatus = changeCompleteStatus;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding binding = TaskItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.Bind(_tasks[position], _deleteTask, _changeCompleteStatus);
    }

    @Override
    public int getItemCount() {
        return _tasks.length;
    }

    public void SetTasks(@NonNull Task[] tasks) {
        notifyItemRangeRemoved(0, _tasks.length);
        _tasks = tasks;
        notifyItemRangeChanged(0, _tasks.length);
    }

    public int FindTask(@NonNull Task task) {
        int index = -1;
        int pos = 0;

        while (index == -1 && pos < _tasks.length) {
            if (_tasks[pos] == task) {
                index = pos;
            } else {
                ++pos;
            }
        }

        return pos;
    }

    public void UpdateTasks(int index) {
        notifyItemChanged(index);
    }

    public void DeleteTask(@NonNull Task task) {
        Task[] tasks = new Task[_tasks.length - 1];
        int current = 0;

        for (Task value : _tasks) {
            if (task != value) {
                tasks[current] = value;
                ++current;
            }
        }

        SetTasks(tasks);
    }

    protected static final class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TaskItemBinding _binding;

        public TaskViewHolder(@NonNull TaskItemBinding binding) {
            super(binding.getRoot());
            _binding = binding;
        }

        public void Bind(@NonNull Task task,
                         @NonNull Consumer<Task> deleteTask,
                         @NonNull Consumer<Task> changeCompleteStatus) {

            _binding.setTask(task);
            _binding.executePendingBindings();

            if (task.taskCompleted != 0) {
                TextView taskName = _binding
                        .getRoot().findViewById(R.id.task_name_box);

                taskName.setPaintFlags(taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                ((Button)_binding.getRoot()
                        .findViewById(R.id.complete_task_button))
                        .setText("Не выполнена");
            }

            _binding.getRoot()
                    .findViewById(R.id.complete_task_button)
                    .setOnClickListener(v -> changeCompleteStatus.accept(task));

            _binding.getRoot()
                    .findViewById(R.id.delete_task_button)
                    .setOnClickListener(v -> deleteTask.accept(task));
        }
    }
}
