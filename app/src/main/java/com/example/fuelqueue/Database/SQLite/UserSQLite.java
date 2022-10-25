package com.example.fuelqueue.Database.SQLite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.fuelqueue.HelperClasses.UserModel;

import java.sql.PreparedStatement;

public class UserSQLite {

    SQLiteDatabase db;

    public UserSQLite(Activity activity) {
        try {
            db = activity.openOrCreateDatabase("users", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users (id VARCHAR, full_name VARCHAR, mobile_number VARCHAR, type VARCHAR, token VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // add user info
    public void addUserInfo(UserModel user) {
        try {
            db.execSQL("DELETE FROM users");

            String sql = "INSERT INTO users (id, full_name, mobile_number, type, token) VALUES (?, ?, ?, ?, ?)";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, user.getId());
            statement.bindString(2, user.getFullName());
            statement.bindString(3, user.getMobileNumber());
            statement.bindString(4, user.getType());
            statement.bindString(5, user.getToken());

            long rowId = statement.executeInsert();

            Cursor cursor = db.rawQuery("SELECT * FROM users where mobile_number = " + user.getMobileNumber(), null);
            if(cursor.getCount() > 0) {

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get user info
    public UserModel getUserInfo() {
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM users LIMIT 1", null);

            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("full_name");
            int mobileIndex = cursor.getColumnIndex("mobile_number");
            int typeIndex = cursor.getColumnIndex("type");
            int tokenIndex = cursor.getColumnIndex("token");

            cursor.moveToFirst();

            UserModel user = new UserModel();
            user.setId(cursor.getString(idIndex));
            user.setFullName(cursor.getString(nameIndex));
            user.setMobileNumber(cursor.getString(mobileIndex));
            user.setType(cursor.getString(typeIndex));
            user.setToken(cursor.getString(tokenIndex));

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // get token
    public String getToken() {
        try {
            UserModel user = getUserInfo();
            return user.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // logout
    public void logout() {
        try {
            db.execSQL("DELETE FROM users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
