package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private Button save, cancel;
    private Dialog dialog;
    private EditText etTaskName, etDescription;
    private RecyclerView recyclerView;
    private ArrayList<String> taskName;

    private String task, description;

    private String key;
    private String updateTask;
    private String updateDescription;

    private Model model;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineuserId;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineuserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("task").child(onlineuserId);

        findView();

        dialog = new Dialog(this);
        taskName = new ArrayList<>();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });


    }


    private void findView() {
        addBtn = findViewById(R.id.addItemBtn);
        recyclerView = findViewById(R.id.recyclerViewId);
    }

    private void addTask() {

        dialog.setContentView(R.layout.input_layout);

        cancel = dialog.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cancel Dialog", Toast.LENGTH_SHORT).show();
            }
        });

        save = dialog.findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTaskName = dialog.findViewById(R.id.etTaskNameId);
                etDescription = dialog.findViewById(R.id.etDescriptionId);

                task = etTaskName.getText().toString();
                description = etDescription.getText().toString();

                String id = reference.push().getKey();
                model = new Model(id, task, description);
                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Succesfully Save", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Not Save Your Task", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

              /* MyAdapter adapter = new MyAdapter(getApplicationContext(), model);
               recyclerView.setAdapter(adapter);
               recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/
            }
        });
        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference().child("task").child(onlineuserId), Model.class).build();

        FirebaseRecyclerAdapter <Model, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Model model) {
                myViewHolder.setTask(model.getTaskName());
                myViewHolder.setDescription(model.getDescription());

                myViewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(i).getKey();
                        updateTask = model.getTaskName();
                        updateDescription = model.getDescription();

                        update();
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_todo_show, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void update() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.update_task, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        final EditText uptask = view.findViewById(R.id.etUpdateTaskNameId);
        final EditText updesc = view.findViewById(R.id.etUpdateDescriptionName);

        uptask.setText(updateTask);
        updesc.setText(updateDescription);

        Button delBtn = view.findViewById(R.id.deleteBtn);
        Button updateBtn = view.findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask = uptask.getText().toString().trim();
                updateDescription = updesc.getText().toString().trim();

                Model model = new Model(key, updateTask, updateDescription);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Not Updated !!! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                dialog.dismiss();
            }

        });


        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Not Deleted!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setTask(String t){
            TextView tName = mview.findViewById(R.id.tvDemoTaskName);
            tName.setText(t);
        }

        public void setDescription (String t){
            TextView tDes = mview.findViewById(R.id.tvDemoDescription);
            tDes.setText(t);
        }
    }

}



   /* @Override
    protected void onStart() {
        super.onStart();


//        TASK SHOW HERE . WE DO HERE DATA RETRIVED FROM DATABASE
        FirebaseRecyclerOptions <Model> options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(reference, Model.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);


        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}*/