package com.alexis11hm.flightswidgetapp.ui.screens

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexis11hm.flightswidgetapp.R
import com.alexis11hm.flightswidgetapp.core.SwitchFlag
import com.alexis11hm.flightswidgetapp.databinding.ActivityWidgetConfigurationBinding
import com.alexis11hm.flightswidgetapp.ui.registry.WidgetConfigurationRegistry
import com.alexis11hm.flightswidgetapp.ui.viewmodels.WidgetConfigurationViewModel
import kotlinx.coroutines.launch

class WidgetConfigurationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWidgetConfigurationBinding

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    private val widgetConfigurationViewModel by viewModels<WidgetConfigurationViewModel> {
        WidgetConfigurationRegistry(this).provide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWidgetConfigurationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()

    }

    private fun init() {
        setResult(RESULT_CANCELED)
        getSourceWidgetId(intent)
        if (isInvalidAppWidget()) {
            finish()
        }
        listenChangeConfiguration()
        getWidgetConfiguration()
        initListeners()
    }
    private fun getSourceWidgetId(intent: Intent?) {
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    private fun isInvalidAppWidget(): Boolean {
        return appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID
    }

    private fun initListeners() {

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveWidgetConfiguration(isChecked, SwitchFlag.DARK_THEME)
        }

        binding.hideDateSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveWidgetConfiguration(isChecked, SwitchFlag.HIDE_DATE)
        }

        binding.smallTextSizeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveWidgetConfiguration(isChecked, SwitchFlag.SMALL_TEXT_SIZE)
        }

        binding.saveChangesButton.setOnClickListener {
            applyWidgetChanges()
        }

    }

    private fun applyWidgetChanges(){
        val appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetManager.updateAppWidget(appWidgetId, RemoteViews(this.packageName, R.layout.flight_widget))
        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }

    private fun getWidgetConfiguration() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                widgetConfigurationViewModel.apply {
                    getData(SwitchFlag.DARK_THEME)
                    getData(SwitchFlag.HIDE_DATE)
                    getData(SwitchFlag.SMALL_TEXT_SIZE)
                }
            }
        }
    }

    private fun saveWidgetConfiguration(value : Boolean, switchFlag: SwitchFlag){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                widgetConfigurationViewModel.saveData(value, switchFlag)
            }
        }
    }

    private fun listenChangeConfiguration() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                widgetConfigurationViewModel.data.collect {
                    with(binding){
                        when(it.first){
                            SwitchFlag.DARK_THEME -> darkThemeSwitch.isChecked = it.second
                            SwitchFlag.HIDE_DATE -> hideDateSwitch.isChecked = it.second
                            SwitchFlag.SMALL_TEXT_SIZE -> smallTextSizeSwitch.isChecked = it.second
                        }
                    }
                }
            }
        }
    }
}