package com.delxmobile.geovani.amiguinhosfacebookkk.adapter;

import java.util.ArrayList;

import com.delxmobile.geovani.amiguinhosfacebookkk.R;
import com.delxmobile.geovani.amiguinhosfacebookkk.modelo.Amigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AmigoAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Amigo> lista;

	
	public AmigoAdapter(Context c, ArrayList<Amigo> l) {
		this.context = c;
		this.lista = l;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		Amigo amigo = lista.get(position);
		
		View layout;
		if (contentView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = inflater.inflate(R.layout.amigo_model, null);
			
		} else {
			layout = contentView;
		}
		
		TextView nomeText = (TextView) layout.findViewById(R.id.nomeText);
		nomeText.setText(amigo.getNome());
		
		return layout;
	}

}
