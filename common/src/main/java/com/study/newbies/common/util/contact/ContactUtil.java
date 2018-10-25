package com.study.newbies.common.util.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2018/10/12
 */

public class ContactUtil {
    /**
     * 读取联系人方法
     */
    public static ArrayList<ContactBean> readContacts(Activity activity) {
        ArrayList<ContactBean> contactBeans = new ArrayList<>();
        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人号码
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactBeans.add(new ContactBean(name, number));
                }
            }
            return contactBeans;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 添加联系人
     *
     * @param name
     * @param phone
     */
    public static void addContact(Activity activity, String name, String phone) {
        // 获取内容解析者
        ContentResolver resolver = activity.getContentResolver();

        // 获取raw_contacts表的uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // 创建内容值对象（键值对）
        ContentValues values = new ContentValues();
        // 添加新记录，返回其id
        long id = ContentUris.parseId(resolver.insert(uri, values));

        // 获取data表的uri
        uri = Uri.parse("content://com.android.contacts/data");

        // 添加联系人姓名
        values.put("raw_contact_id", id);
        values.put("data2", name);
        values.put("mimetype", "vnd.android.cursor.item/name");
        // 将数据插入data表
        resolver.insert(uri, values);

        // 添加联系人电话
        values.clear(); // 清空上次的数据
        values.put("raw_contact_id", id);
        values.put("data1", phone);
        values.put("data2", "2");
        values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        // 将数据插入data表
        resolver.insert(uri, values);

    }

    public static void delete(Activity activity, String contactName) {
        //根据名字求id
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        ContentResolver resolver = activity.getContentResolver();
        String where = ContactsContract.PhoneLookup.DISPLAY_NAME;
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, where + "=?", new String[]{contactName}, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, where + "=?", new String[]{contactName});
            uri = ContactsContract.Data.CONTENT_URI;
            resolver.delete(uri, ContactsContract.Data.RAW_CONTACT_ID + "=?", new String[]{id + ""});
        }
    }

    public static class ContactBean {

        private String name;

        private String phoneNum;

        public ContactBean(String name, String phoneNum) {
            this.name = name;
            this.phoneNum = phoneNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }
    }
}
