package com.delxmobile.geovani.amiguinhosfacebookkk.modelo;

import android.os.Parcel;
import android.os.Parcelable;


public class Amigo 
					implements
								// Serializable
								Parcelable {

	// private static final long serialVersionUID = 1L;
	// private Long id;
	private String nome;

	
	public Amigo(
			// Long id,
			String nome) {
		// this.id = id;
		this.nome = nome;
	}

	
	public Amigo(Parcel parcel) {
		// this.id = parcel.readLong();
		this.nome = parcel.readString();
	}

	
	/*
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	*/

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// dest.writeLong(id);
		dest.writeString(nome);
	}

	
	public static final Parcelable.Creator<Amigo> CREATOR = new Parcelable.Creator<Amigo>() 
	{
		@Override
		public Amigo createFromParcel(Parcel source) {
			return new Amigo(source);
		}

		@Override
		public Amigo[] newArray(int size) {
			return new Amigo[size];
		}
		
	};
	
	
	
}