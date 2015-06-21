package com.alorma.github.libs.contributors.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alorma.github.libs.contributors.R;
import com.alorma.github.libs.contributors.api.dto.Contributor;
import com.alorma.github.libs.contributors.ui.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class UsersAdapter extends LazyAdapter<Contributor> {

	public UsersAdapter(Context context, List<Contributor> contributors) {
		super(context, 0, contributors);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View v = inflate(R.layout.row_user_contributors, viewGroup, false);
		Contributor contributor = getItem(position);

		CircularImageView imageView = (CircularImageView) v.findViewById(R.id.avatarAuthorImage);

		ImageLoader.getInstance().displayImage(contributor.author.avatar_url, imageView);

		TextView textView = (TextView) v.findViewById(R.id.textAuthorLogin);

		textView.setText(contributor.author.login);

		View divider = v.findViewById(R.id.divider);

		if (position == getCount()) {
			divider.setVisibility(View.GONE);
		}

		return v;
	}
}
