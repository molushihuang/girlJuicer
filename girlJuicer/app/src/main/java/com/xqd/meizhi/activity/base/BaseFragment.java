package com.xqd.meizhi.activity.base;

import com.anthole.quickdev.QFragment;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.QProgressDialog;


public abstract class BaseFragment extends QFragment {

	@Override
	public IProgressDialog createIProgressDialog() {
		return new QProgressDialog(mContext) ;
	}

}
