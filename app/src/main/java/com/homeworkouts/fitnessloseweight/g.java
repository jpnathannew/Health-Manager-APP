package com.homeworkouts.fitnessloseweight;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/* compiled from: lambda */
public final /* synthetic */ class g implements MaterialDialog.SingleButtonCallback {


    private final /* synthetic */ MainExcerciseActivity f6a;

    public /* synthetic */ g(MainExcerciseActivity mainExcerciseActivity) {
        this.f6a = mainExcerciseActivity;
    }

    public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.f6a.a(materialDialog, dialogAction);
    }
}
