package com.nema.rajshree.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
Button  sub;
EditText edittextname,aadhaarno,mobile,gen,addresses;
    TextView logintv;
    FirebaseAuth auth;

DatabaseReference databasePeople;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasePeople= FirebaseDatabase.getInstance().getReference("people");
        sub=(Button)findViewById(R.id.rsubmit);
        mobile=(EditText)findViewById(R.id.et2);
        aadhaarno=(EditText)findViewById(R.id.et4);
        addresses=(EditText)findViewById(R.id.et3);
        gen=(EditText)findViewById(R.id.et5);
        edittextname=(EditText)findViewById(R.id.et1) ;
        logintv=(TextView)findViewById(R.id.tv);

        auth=FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


     /*   if (user != null) {

            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
*/


        CheckBox mCheckBox= (CheckBox) findViewById( R.id.cb1);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sub.setEnabled(true);

                    } else {
                        sub.setEnabled(false);
                    }

                }
            });



    sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        addPeople();

        /*Intent intent;
        intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
*/



    }
});

        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Login.class);
            }
        });
    }


    private void addPeople(){

        String name=edittextname.getText().toString().trim();
        String contactno=mobile.getText().toString().trim();
        String aadhaarcard=aadhaarno.getText().toString().trim();
        String address=addresses.getText().toString().trim();

        String gender=gen.getText().toString().trim();


        if(contactno.isEmpty()){
            mobile.setError("Phone number is required");

            return;
        }
        if(contactno.length()!=10){
            mobile.setError("Please enter a valid phone");

            return;
        }

        for(int i=0; i< contactno.length();i++){
            char ch= contactno.charAt(i);
            if (!Character.isDigit(ch)){
                mobile.setError("Enter valid mobile number");
                return;

            }
        }
        if(aadhaarcard.isEmpty()){
            aadhaarno.setError("Aadhaar number is required");

            return;
        }

        if (aadhaarcard.length()!=12)
        {
            aadhaarno.setError("Please enter valid Aadhaar number");
            return;
        }

        for(int i2=0; i2< aadhaarcard.length();i2++){
            char cha= aadhaarcard.charAt(i2);
            if (!Character.isDigit(cha)){
                aadhaarno.setError("Enter valid Aadhaar card number");
                return;

            }
        }

     // doubt
         /* auth.createUserWithPhoneNumber(contactno, aadhaarcard)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(MainActivity.this, Login.class));
                            finish();
                        }
                    }
                });*/


        if(!TextUtils.isEmpty(name)) {
            //String id = databasePeople.push().getKey();
            People people = new People(name, contactno, address, aadhaarcard, gender);


            databasePeople.child(contactno).setValue(people);
            Toast.makeText(this, "People added", Toast.LENGTH_SHORT).show();

            Intent intent;
            intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        else{
            Toast.makeText(this, "Add Details", Toast.LENGTH_SHORT).show();
        }
    }

}
