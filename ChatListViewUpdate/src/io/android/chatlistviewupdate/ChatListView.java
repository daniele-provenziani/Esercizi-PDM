package io.android.chatlistviewupdate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatListView extends Activity{
	private EditText pass;
	private EditText user;
	private Button login;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        pass=(EditText)findViewById(R.id.password);
        user=(EditText)findViewById(R.id.username);
        login=(Button)findViewById(R.id.login);
        user.setHint("Inserire Username");
        pass.setHint("Inserire Password");
        //final String password=pass.getText().toString();
        //pass.setTransformationMethod(PasswordTransformationMethod.getInstance());//nasconde la password digitata con ***
        
        login.setOnClickListener(new OnClickListener() {
			//ascolto evento click sul bottone LOGIN
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ChatListView.this,ChatListViewUpdateActivity.class);
				//creo nuovo intent per lancio seconda activity
				intent.putExtra("username", pass.getText().toString());
				intent.putExtra("password", user.getText().toString());
				/*aggiungo 2 extra all'intent associandogli una chiave per la password e username digitati
				 * cosi da poter essere ripresi dall'activity chiamata per essere usati per il login*/
				
				//controllo se i dati sono corretti prima di chiamare la seconda actvity
				if(pass.getText().toString().equals("provenziani")&user.getText().toString().equals("provenziani"))
				{
					startActivity(intent);//call second activity
				}else Toast.makeText(getApplicationContext(), "DATI ERRATI" , Toast.LENGTH_LONG).show();
				//con il toast visualizzo un messaggio temporaneo sul display avvertendo che i dati sono errati
				
				
				
			}
		});
        
    }

}
