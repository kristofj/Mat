package com.j3nsen.hiof.mat.service;

import android.app.IntentService;
import android.content.Intent;

public class DataService extends IntentService{

	public static final String NOTIFICATION = "com.j3nsen.hiof.mat.service";
	
	private static final String URL = "https://j3nsen.com/android_mat.php";
	private static final String OPERATION_UPDATE = "update";
	private static final String ENTITY = "entity";
	
	
	public DataService() {
		super("DataService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String operation = intent.getStringExtra("operation");
		String entity = intent.getStringExtra("entity");
		
		if(operation.equals("add")) {
			handleAddOperation(intent);
		}
		
		if(entity.equals("user")) {
			
		} else if(entity.equals("product")) {
			
		} else if(entity.equals("")) {
			
		}
	}
	
	private void handleAddOperation(Intent intent) {
		String entity = intent.getStringExtra(ENTITY);
		
		
	}
	
	protected void handleUser(String operation) {
		
	}
}
