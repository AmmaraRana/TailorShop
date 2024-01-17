package com.example.adminpanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Customer.LoginActivity;
import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {
    String check = "";

    TextView pageTitle, txt_question;
    EditText question1, question2, phonenumber;

    Button verifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");
        pageTitle = findViewById(R.id.page_name);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.quetion_2);
        phonenumber = findViewById(R.id.find_phone);
        verifyBtn = findViewById(R.id.verify_btn);
        txt_question = findViewById(R.id.text_question);

    }

    @Override
    protected void onStart() {
        super.onStart();
        phonenumber.setVisibility(View.GONE);
        if (check.equals("settings")) {
            pageTitle.setText("Set Question");
            txt_question.setText("Answer these questions");
            verifyBtn.setText("Set");
            displaypreciouAnswer();

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();


                }
            });

        } else if (check.equals("login")) {
            phonenumber.setVisibility(View.VISIBLE);
            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pageTitle.setText("Reset password");
                    verifyuser();
                }
            });


        }
    }

    private void setAnswers() {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
        if (TextUtils.isEmpty(answer1)) {
            Toast.makeText(ResetPasswordActivity.this, "Answer Security questions", Toast.LENGTH_SHORT).show();
            question2.setError("Empty field");
            question2.findFocus();
        } else if (TextUtils.isEmpty(answer2)) {
            Toast.makeText(ResetPasswordActivity.this, "Answer Security questions", Toast.LENGTH_SHORT).show();
            question2.setError("Empty field");
            question2.findFocus();
        } else {


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Prevalent.currentonlineUser.getPhone());

            HashMap<String, Object> userDataMap = new HashMap<>();
            userDataMap.put("answer1", answer1);
            userDataMap.put("answer2", answer2);
            ref.child("Security Question").updateChildren(userDataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this,
                                        "Process Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, nav_bar.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }

    private void displaypreciouAnswer() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Prevalent.currentonlineUser.getPhone());

        ref.child("Security Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String ans1 = snapshot.child("answer1").getValue().toString();
                    String ans2 = snapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verifyuser() {
        String phone = phonenumber.getText().toString();
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
if(!phone .equals("") && !answer1.equals("")&& !phone.equals("")){
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .child(phone);

    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                String mphone = snapshot.child("phone").getValue().toString();

                if (snapshot.hasChild("Security Question")) {
                    String ans1 = snapshot.child("Security Question").child("answer1").getValue().toString();
                    String ans2 = snapshot.child("Security Question").child("answer2").getValue().toString();
                    if (!ans1.equals(answer1)) {
                        Toast.makeText(ResetPasswordActivity.this, "Answer 1 is wrnon", Toast.LENGTH_SHORT).show();
                    } else if (!ans2.equals(answer2)) {
                        Toast.makeText(ResetPasswordActivity.this, "Answer 2 is wrnon", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                        builder.setTitle("New Password");
                        final EditText newpassword = new EditText(ResetPasswordActivity.this);
                        newpassword.setHint("Writet Password");
                        builder.setView(newpassword);
                        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!newpassword.getText().toString().equals("")) {
                                    ref.child("password").setValue(newpassword.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ResetPasswordActivity.this, "Password changed sucessfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
builder.show();

                    }
                }

                else {
                    Toast.makeText(ResetPasswordActivity.this, "no security question", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(ResetPasswordActivity.this, "Number not exist", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

        Toast.makeText(this, "Fill required sessions", Toast.LENGTH_SHORT).show();
    }
}