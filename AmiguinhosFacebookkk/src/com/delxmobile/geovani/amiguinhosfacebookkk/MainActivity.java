/**
 * Documentação:
 * https://developers.facebook.com/docs/android
 * 
 * Requisitos:
 * - Deve ter a tela com o botão de login do facebook: Ok!
 * - Deve ser criada uma app no facebook developer: Ok!
 * - Quando clicar no login do facebook ir para uma tela que carrega o nome, email e foto do usuário logado
 * - Deve ter um botão para listar os amigos
 * - Deve ir para uma tela com listview com todos amigos dele no facebook
 * 
 * 
 * ***********************************INTEGRAÇÃO COM FACEBOOK***********************************
 * 0º Criar projeto e fazer Dependencia com FacebookSDK
 * 
 * 1º Baixar openssl-for-windows;
 * 
 * 2º Localizar:
 * 		C:\Users\EngJGeovani.exe\openssl-0.9.8k_X64\bin
 * 
 * 3º No cmd, direcionar para cd C:\Program Files\Java\jre7\bin
 * 
 * 4º Ainda, no cmd, C:\Program Files\Java\jre7\bin>, digitar:
 * "C:\Program Files\Java\jdk1.7.0_25\bin\keytool.exe" -exportcert -alias androiddebugkey -keystore "C:\Users\EngJGeovani.exe\.android\debug.keystore" |
 "C:\Users\EngJGeovani.exe\openssl-0.9.8k_X64\bin\openssl" sha1 -binary | "C:\Users\EngJGeovani.exe\openssl-0.9.8k_X64\bin\openssl" base64
 * Informe a senha da ßrea de armazenamento de chaves:  android
 * 
 * 5º Obtém-se o Key Hashes:
 * 		eN2WTYk46BPwzw6LxexGQF53PC0=
 * 
 * 6º Facebook Developer > Apps > Create a New App
 * 
 * 7º No Facebook Developer, configurar com:
 * Display Name: Amiguinhos
 * Namespace: amigokkk
 * + Add Platform: Android
 * Single Sign On: Sim
 * Package Name: com.delxmobile.geovani.amiguinhosfacebookkk
 * Class Name: MainActivity
 * Key Hashes: eN2WTYk46BPwzw6LxexGQF53PC0=
 * 
 * 8º Definir uma string no value com o "Número de identificação do Aplicativo"
 * '<string name="app_id">733946546663323</string>'
 *************************************************************************************************************
 *
 *
 */
package com.delxmobile.geovani.amiguinhosfacebookkk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delxmobile.geovani.amiguinhosfacebookkk.modelo.Amigo;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;


public class MainActivity extends Activity {

	private static final String TAG = "Facebook";
	
	public static final String KEY = "key_amigos";	
	private ArrayList<Amigo> listaAmigos;

	private UiLifecycleHelper uiHelper;	
	
	private com.facebook.Session.StatusCallback callback = new com.facebook.Session.StatusCallback() {

		@Override
		public void call(com.facebook.Session session, SessionState state, Exception exception) {
			onSessionStateChanged(session, state, exception);
		}
	};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listaAmigos = new ArrayList<Amigo>();
		
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		LoginButton fbLogin = (LoginButton) findViewById(R.id.fbLogin);
		fbLogin.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		com.facebook.Session session = com.facebook.Session.getActiveSession();
		if (session != null && (session.isClosed() || session.isOpened())) {
			onSessionStateChanged(session, session.getState(), null);
		}
		
		uiHelper.onResume();
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	
	public void onSessionStateChanged(final com.facebook.Session session, SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			
			Log.i(TAG, "Usuário conectado!");
			
			// fazer pedido para o /me API
			Request req = Request.newMeRequest(session, new Request.GraphUserCallback() {
				
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						TextView nameText = (TextView) findViewById(R.id.name);
						nameText.setText(user.getFirstName() + " " + user.getLastName());
						
						TextView emailText = (TextView) findViewById(R.id.email);
						emailText.setText(user.getProperty("email").toString());
						
						ProfilePictureView ppv = (ProfilePictureView) findViewById(R.id.fbImg);
						ppv.setVisibility(View.VISIBLE);
						ppv.setProfileId(user.getId());
						
						
						Button listarButton = (Button) findViewById(R.id.listarButton);
						listarButton.setVisibility(View.VISIBLE);
						
						recoverFriends(session);
					}
				}
			});
			req.executeAsync();
		}
		else {
			Log.i(TAG, "Usuário desconectado!");
		}
	}
	
	
	public void recoverFriends(com.facebook.Session session) {
		
		Request req = Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
			
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				
				
				if (users != null) {
					for (int i = 0; i < users.size(); i++) {
						GraphUser user = users.get(i);
						Amigo amigo = new Amigo
						(
//							Long.parseLong(user.getId()), 
							user.getName()
						);
						
						listaAmigos.add(amigo);
						
						Log.d(TAG, "User " + user.getId() + ": " + user.getName());
					}
				}
//				Log.i(TAG, "resposta: " + response);
			}
			
		});
		req.executeAsync();
	}
	
	
	public void listarAmigosOnClick(View view) {
		Intent intent = new Intent(MainActivity.this, ListaAmigosActivity.class);
		
		// Passagem de ArrayList<Object> entre duas Actvity
//		ListaAmigosActivity.dados = listaAmigos;				// Método estático
//		intent.putSerializableExtra(KEY, listaAmigos);			// Usando Serializable
		intent.putParcelableArrayListExtra(KEY, listaAmigos);	// Usando Parcelable
		
		startActivity(intent);
	}
	
	

}
