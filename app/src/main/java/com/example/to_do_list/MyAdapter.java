package com.example.to_do_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class MyAdapter extends FirebaseRecyclerAdapter<Model, MyAdapter.Holder> {

    private LayoutInflater inflater;
    private Context context;

    private String key= "", taskName, description;
    private Dialog dialog;

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int i, @NonNull Model model) {
        holder.taskName.setText(model.getTaskName());
        holder.description.setText(model.getDescription());

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hiiiii see", Toast.LENGTH_SHORT).show();
               /* taskName = model.getTaskName();
                description = model.getDescription();
                key = getRef(i).getKey();

                updateTask();*/
            }
        });
    }

/*
    private void updateTask() {
        */
/*AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.update_task, null);
        dialog.setView(view);*//*


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.update_task);

        */
/*EditText uTask = dialog.findViewById(R.id.etUpdateTaskNameId);
        EditText uDes = dialog.findViewById(R.id.etUpdateDescriptionName);

        uDes.setText(description);
        uDes.setSelection(description.length());

        uTask.setText(taskName);
        uTask.setSelection(taskName.length());*//*


        dialog.show();
    }
*/

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.demo_todo_show, parent, false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView taskName, description;
        View mview;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            taskName = itemView.findViewById(R.id.tvDemoTaskName);
            description = itemView.findViewById(R.id.tvDemoDescription);
        }
    }
}