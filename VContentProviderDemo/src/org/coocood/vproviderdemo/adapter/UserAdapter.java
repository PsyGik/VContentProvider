package org.coocood.vproviderdemo.adapter;

import org.coocood.vcontentprovider.VCursorAdapter;
import org.coocood.vproviderdemo.R;
import org.coocood.vproviderdemo.activity.PostViewActivity;
import org.coocood.vproviderdemo.model.Post;
import org.coocood.vproviderdemo.model.User;
import org.coocood.vproviderdemo.util.CursorBinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class UserAdapter extends VCursorAdapter {
	public UserAdapter(Context context) {
		super(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.user_item, null);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		CursorBinder binder = new CursorBinder(view, cursor);
		final long id = cursor.getLong(cursor.getColumnIndex("_id"));
		binder.bindText(R.id.name, User.NAME).click(0, new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PostViewActivity.class);
				intent.putExtra(Post.AUTHOR_ID, id);
				context.startActivity(intent);
			}
		}).longClick(0, new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				new AlertDialog.Builder(context)
						.setTitle("Are you sure to delete?")
						.setPositiveButton("YES",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										context.getContentResolver().delete(
												User.URI, "_id="+id,
												null);
									}
								})
						.setNegativeButton("Cancel", null).create().show();
				return true;
			}
		});
	}

}
