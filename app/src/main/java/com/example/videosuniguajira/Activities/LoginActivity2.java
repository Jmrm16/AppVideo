package com.example.videosuniguajira.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videosuniguajira.R;

public class LoginActivity2 extends AppCompatActivity {
    private EditText userEdit,passEdit;
    private Button LoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
       initView();
    }
    private void  initView(){
        userEdit=findViewById(R.id.editTextText);
        passEdit=findViewById(R.id.editTexPassword);
        LoginBtn=findViewById(R.id.LoginBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEdit.getText().toString().isEmpty() || passEdit.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity2.this, "Por favor ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show();
                }else if (userEdit.getText().toString().equals("test") && passEdit.getText().toString().equals("test"))
                    startActivity(new Intent(LoginActivity2.this, MainActivity.class));
                else {
                    Toast.makeText(LoginActivity2.this, "Tu usuario o contraseña no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        });


}
}