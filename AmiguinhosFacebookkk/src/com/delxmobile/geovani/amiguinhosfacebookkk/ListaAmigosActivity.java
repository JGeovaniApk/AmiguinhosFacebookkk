package com.delxmobile.geovani.amiguinhosfacebookkk;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.delxmobile.geovani.amiguinhosfacebookkk.adapter.AmigoAdapter;
import com.delxmobile.geovani.amiguinhosfacebookkk.modelo.Amigo;



public class ListaAmigosActivity extends Activity {

//	public static ArrayList<Amigo> dados;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_amigos);


//		ArrayList<Amigo> dados = (ArrayList<Amigo>) getIntent().getSerializableExtra(MainActivity.KEY);
		
//		ArrayList<Amigo> dados = ListaAmigosActivity.dados;
//		ListaAmigosActivity.dados = null;

		ArrayList<Amigo> dados = getIntent().getParcelableArrayListExtra(MainActivity.KEY);
		
		ListView listView = (ListView) findViewById(R.id.amigoslistView);
		listView.setAdapter(new AmigoAdapter(ListaAmigosActivity.this, dados));	
	}
	
	
	@Override
		public void onBackPressed() {
			super.onBackPressed();
			(ListaAmigosActivity.this).finish();
		}
}
