package com.example.passwordgenerator.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordgenerator.PasswordGeneratorApplication
import com.example.passwordgenerator.R
import com.example.passwordgenerator.generateTimestampString
import com.example.passwordgenerator.viewmodel.MainActivityViewModel

/* MainActivity содержит ActivityResultLauncher'ы, которые нужны, чтобы работать с ContentProvider
*  и получать uri для импорта/экспорта. Больше MainActivity не имеет никакой логики, содержит лишь
*  контейнер для экранов, которым управляет Navigation. Про экспорт данных подробнее см. описание
*  MainActivityViewModel. */
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val exportData = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data!!.data!!
            val pfd: ParcelFileDescriptor =
                this.contentResolver.openFileDescriptor(uri, "w")!!
            PasswordGeneratorApplication.instance.passwords.exportData(viewModel.exportListName, pfd)
        }
    }
    private val importData = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data!!.data!!
            PasswordGeneratorApplication.instance.passwords.importData(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /* Как выяснилось при написании, стандартный DocumentsProvider на верхних уровнях API (замечено на 31+)
    *  теряется при виде mime-type text/csv. При этом на более низких уровнях API все хорошо.
    *  Поэтому дается более широкий mime-type. В любом случае при импортировании есть проверка форматирования файла  */
    fun importData() {
        importData.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).also {intent ->
            //intent.type = "text/csv"
            intent.type = "*/*"
        })
    }

    fun exportData(listName: String) {
        viewModel.exportListName = listName
        exportData.launch(Intent(
            Intent.ACTION_CREATE_DOCUMENT
        ).also { intent ->
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/csv"
            intent.putExtra(Intent.EXTRA_TITLE, generateTimestampString())
        })
    }
}