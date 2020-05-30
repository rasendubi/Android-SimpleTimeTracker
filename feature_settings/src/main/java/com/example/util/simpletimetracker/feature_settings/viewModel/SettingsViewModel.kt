package com.example.util.simpletimetracker.feature_settings.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.util.simpletimetracker.core.repo.ResourceRepo
import com.example.util.simpletimetracker.domain.interactor.BackupInteractor
import com.example.util.simpletimetracker.domain.interactor.PrefsInteractor
import com.example.util.simpletimetracker.domain.resolver.BackupRepo
import com.example.util.simpletimetracker.feature_settings.R
import com.example.util.simpletimetracker.navigation.Router
import com.example.util.simpletimetracker.navigation.Screen
import com.example.util.simpletimetracker.navigation.params.FileChooserParams
import com.example.util.simpletimetracker.navigation.params.StandardDialogParams
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val router: Router,
    private val resourceRepo: ResourceRepo,
    private val prefsInteractor: PrefsInteractor,
    private val backupInteractor: BackupInteractor
) : ViewModel() {

    val sortRecordTypes: LiveData<Boolean> by lazy {
        return@lazy MutableLiveData<Boolean>().let { initial ->
            viewModelScope.launch {
                initial.value = prefsInteractor.getSortRecordTypesByColor()
            }
            initial
        }
    }

    fun onSaveClick() {
        router.navigate(
            Screen.CREATE_FILE,
            FileChooserParams(::onFileOpenError)
        )
    }

    fun onRestoreClick() {
        router.navigate(
            Screen.STANDARD_DIALOG,
            StandardDialogParams(
                tag = ALERT_DIALOG_TAG,
                message = resourceRepo.getString(R.string.settings_dialog_message),
                btnPositive = resourceRepo.getString(R.string.ok),
                btnNegative = resourceRepo.getString(R.string.cancel)
            )
        )
    }

    fun onRecordTypeSortChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            prefsInteractor.setSortRecordTypesByColor(isEnabled)
        }
    }

    fun onPositiveDialogClick(tag: String?) {
        when (tag) {
            ALERT_DIALOG_TAG -> router.navigate(
                Screen.OPEN_FILE,
                FileChooserParams(::onFileOpenError)
            )
        }
    }

    fun onSaveBackup(uriString: String) = viewModelScope.launch {
        val resultCode = backupInteractor.saveBackupFile(uriString)

        if (resultCode == BackupRepo.ResultCode.SUCCESS) {
            R.string.settings_backup_saved
        } else {
            R.string.settings_save_error
        }.let(::showMessage)
    }

    fun onRestoreBackup(uriString: String) = viewModelScope.launch {
        val resultCode = backupInteractor.restoreBackupFile(uriString)

        if (resultCode == BackupRepo.ResultCode.SUCCESS) {
            R.string.settings_backup_restored
        } else {
            R.string.settings_restore_error
        }.let(::showMessage)
    }

    private fun onFileOpenError() {
        showMessage(R.string.settings_file_error)
    }

    private fun showMessage(stringResId: Int) {
        stringResId.let(resourceRepo::getString).let(router::showSystemMessage)
    }

    companion object {
        private const val ALERT_DIALOG_TAG = "alert_dialog_tag"
    }
}
