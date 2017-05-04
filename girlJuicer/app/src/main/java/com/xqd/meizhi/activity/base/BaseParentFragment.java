package com.xqd.meizhi.activity.base;

import com.anthole.quickdev.ParentFragment;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.QProgressDialog;


public abstract class BaseParentFragment extends ParentFragment {

	@Override
	public IProgressDialog createIProgressDialog() {
		return new QProgressDialog(mContext) ;
	}

}
