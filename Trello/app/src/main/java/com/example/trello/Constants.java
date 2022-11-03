package com.example.trello;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public class Constants {
    @NotNull
    public static final String USERS = "users";
    @NotNull
    public static final String BOARDS = "boards";
    @NotNull
    public static final String IMAGE = "image";
    @NotNull
    public static final String NAME = "name";
    @NotNull
    public static final String MOBILE = "mobile";
    @NotNull
    public static final String ASSIGNED_TO = "assignedto";
    @NotNull
    public static final String DOCUMENT_ID = "documentId";
    @NotNull
    public static final String TASK_LIST = "taskList";
    @NotNull
    public static final String ID = "id";
    @NotNull
    public static final String EMAIL = "email";
    @NotNull
    public static final String BOARD_DETAIL = "board_detail";
    @NotNull
    public static final String TASK_LIST_ITEM_POSITION = "task_list_item_position";
    @NotNull
    public static final String CARD_LIST_ITEM_POSITION = "card_list_item_position";
    @NotNull
    public static final String BOARD_MEMBERS_LIST = "board_members_list";
    @NotNull
    public static final String SELECT = "Select";
    @NotNull
    public static final String UN_SELECT = "UnSelect";
    public static final int READ_STORAGE_PERMISSION_CODE = 1;
    public static final int PICK_IMAGE_REQUEST_CODE = 2;
    @NotNull
    public static final String PROGEMANAG_PREFERENCES = "ProjemanagPrefs";
    @NotNull
    public static final String FCM_TOKEN = "fcmToken";
    @NotNull
    public static final String FCM_TOKEN_UPDATED = "fcmTokenUpdated";
    @NotNull
    public static final String FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send";
    @NotNull
    public static final String FCM_AUTHORIZATION = "authorization";
    @NotNull
    public static final String FCM_KEY = "key";
    @NotNull
    public static final String FCM_SERVER_KEY = "AAAA-_vvGNI:APA91bF9xfSzbacs2j9RKkmEg7aYqY4pmRr89vYoy8pOfr0Ds2yHyVlhkhDiryrPndNYbXHUYyCdzZakvrlxxDLjsyQv5Ybtom5dFr7VWaMzDOL6YcSF-09GAOoxHU7SAisyZ222PW3w";
    @NotNull
    public static final String FCM_KEY_TITLE = "title";
    @NotNull
    public static final String FCM_KEY_MESSAGE = "message";
    @NotNull
    public static final String FCM_KEY_DATA = "data";
    @NotNull
    public static final String FCM_KEY_TO = "to";
    @NotNull
    public static final Constants INSTANCE;

    public final void showImageChooser(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE);
    }

    @Nullable
    public final String getFileExtension(@NotNull Activity activity, @Nullable Uri uri) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        ContentResolver contentResolver = activity.getContentResolver();
        if (uri == null) {
            Intrinsics.throwNpe();
        }

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    Constants() {
    }

    static {
        Constants constants = new Constants();
        INSTANCE = constants;
    }
}
