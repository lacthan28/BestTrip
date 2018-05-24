package com.github.besttrip.widgets

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import org.jetbrains.anko.act
import com.github.besttrip.R
import com.github.besttrip.extensions.LOADING_TAG


class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(act).apply {
            setContentView(R.layout.dialog_loading)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    companion object {
        fun show(fm: FragmentManager) {
            if (fm.findFragmentByTag(LOADING_TAG) == null) fm.beginTransaction().add(newInstance(), LOADING_TAG).commit()
        }

        fun hide(fm: FragmentManager) {
            val fragment = fm.findFragmentByTag(LOADING_TAG) as? DialogFragment
            if (fragment != null) fm.beginTransaction().remove(fragment).commit()
        }

        private fun newInstance(): ProgressDialogFragment {
            return ProgressDialogFragment()
        }
    }
}