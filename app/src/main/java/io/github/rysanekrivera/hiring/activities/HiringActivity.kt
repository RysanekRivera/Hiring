package io.github.rysanekrivera.hiring.activities

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import io.github.rysanekrivera.common.composables.BodyText
import io.github.rysanekrivera.common.composables.TitleText
import io.github.rysanekrivera.common.state.RemoteRequestState
import io.github.rysanekrivera.common.uiEvents.UiEvent
import io.github.rysanekrivera.hiring.R
import io.github.rysanekrivera.hiring.ui.theme.HiringTheme
import io.github.rysanekrivera.ui.composables.ErrorScreen
import io.github.rysanekrivera.ui.composables.LoadingScreen
import io.github.rysanekrivera.ui.composables.SingleEmployeeScreen
import io.github.rysanekrivera.ui.composables.AllEmployeesSuccessScreen
import io.github.rysanekrivera.ui.viewmodels.HiringViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface HiringListener {
    fun onClickEmployee(employeeId: Int)
    fun onShowSnackBar(message: String, scope: CoroutineScope, snackBarState: SnackbarHostState,  actionLabel: String? = null, withDismissAction: Boolean = false, duration: SnackbarDuration = SnackbarDuration.Short)
    fun onShowAlertDialog(
        message: String,
        positiveText: String? = null,
        negativeText: String? = null,
        neutralText: String? = null,
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        neutralAction: (() -> Unit)? = null,
    ): AlertDialog
}

@AndroidEntryPoint
class HiringActivity : ComponentActivity() {

    private val viewModel: HiringViewModel by viewModels()

    private lateinit var navController: NavHostController

    private var listener: HiringListener? = null

    companion object {
        private const val SPLASH_SCREEN_ROUTE = "splash_screen_route"
        private const val ALL_EMPLOYEES_ROUTE = "all_employees_route"
        private const val EMPLOYEE_ROUTE = "employee_route"
        private const val EMPLOYEE_ARG = "employeeId"
        private const val SPLASH_SCREEN_DELAY = 5000L
    }

    override fun onResume() {
        super.onResume()
        listener = provideHiringListener()
    }

    override fun onPause() {
        super.onPause()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val state by viewModel.state.collectAsState()
            val snackBarState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            navController = rememberNavController()

            LaunchedEffect(Unit) {
                viewModel.uiEvents.collect { uiEvent ->
                    when (uiEvent) {
                        is UiEvent.NavigateToEmployeeDetails -> navController.navigate("$EMPLOYEE_ROUTE/${uiEvent.employeeId}")
                        is UiEvent.ShowSnackBar ->  listener?.onShowSnackBar(uiEvent.message, scope, snackBarState)
                        is UiEvent.ShowAlertDialog -> listener?.onShowAlertDialog(message = uiEvent.message, positiveText = getString(
                            R.string.ok
                        ))
                        else -> Unit
                    }
                }
            }

            HiringTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarState) {data ->
                            Snackbar(
                                modifier = Modifier.padding(8.dp)
                            ){
                                BodyText(text = data.visuals.message)
                            }
                        }
                    }
                ) { innerPadding ->

                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        NavHost(navController = navController, startDestination = if (savedInstanceState != null) ALL_EMPLOYEES_ROUTE else SPLASH_SCREEN_ROUTE) {

                            composable(SPLASH_SCREEN_ROUTE) {
                                SplashScreen()
                            }

                            composable(ALL_EMPLOYEES_ROUTE){

                                LaunchedEffect(Unit) {
                                    viewModel.getAllEmployees()
                                }

                                AllEmployeesScreen(onClickEmployee = { employeeId -> listener?.onClickEmployee(employeeId) })
                            }

                            composable("$EMPLOYEE_ROUTE/{$EMPLOYEE_ARG}") { navStack ->
                                val employee = state.allEmployeesList.find { it.id.toString() == navStack.arguments?.getString(
                                    EMPLOYEE_ARG
                                ) }

                                Scaffold(
                                    topBar = {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            IconButton(
                                                modifier = Modifier.padding(start = 16.dp),
                                                onClick = { navController.popBackStack() },
                                                content = {
                                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, tint = colorResource(id = R.color.dark_blue), contentDescription = stringResource(
                                                        R.string.back_arrow
                                                    ))
                                                }
                                            )

                                            TitleText(text = stringResource(R.string.employee), bold = true, color = colorResource(id = R.color.dark_blue))

                                        }
                                    }
                                ) {

                                    Column(
                                        modifier = Modifier.padding(it)
                                    ) {
                                        employee?.let {
                                            SingleEmployeeScreen(
                                                employee,
                                                onShowSnackBar = { emp ->
                                                    viewModel.onShowEmployeeSnackBar(getString(R.string.employee_name, emp.name))
                                                },
                                                onShowAlertDialog = { emp ->
                                                    viewModel.onShowAlertDialog(
                                                        message = getString(R.string.employee_name, emp.name)
                                                    )
                                                },
                                            )
                                        } ?: BodyText(text = stringResource(R.string.error_getting_employee))

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SplashScreen() {
        LaunchedEffect(key1 = Unit) {
            delay(SPLASH_SCREEN_DELAY)
            navController.navigate(ALL_EMPLOYEES_ROUTE) { popUpTo(navController.currentDestination?.route ?: SPLASH_SCREEN_ROUTE){ inclusive = true} }
        }

        Scaffold(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                GlideImage(
                    imageModel = { R.drawable.workplace },
                    modifier = Modifier.fillMaxWidth(),
                    imageOptions = ImageOptions(Alignment.Center)
                )

            }
        }
    }

    @Composable
    private fun AllEmployeesScreen(
        onClickEmployee: (employeeId: Int) -> Unit,
    ) {
        val state by viewModel.state.collectAsState()
        val employeesRequestState by remember { derivedStateOf { state.allEmployeesRequestState } }

        Box {
            when  {

                employeesRequestState is RemoteRequestState.Success -> {
                    val allEmployees by remember { derivedStateOf { state.allEmployeesList } }
                    AllEmployeesSuccessScreen(allEmployees, onClickEmployee)
                }

                employeesRequestState is RemoteRequestState.Error -> {
                    val requestError by remember { derivedStateOf { state.error } }
                    ErrorScreen(requestError)
                }

                state.isLoading -> LoadingScreen()

                else -> Unit
            }
        }
    }

    private fun provideHiringListener() : HiringListener = object : HiringListener {

        override fun onClickEmployee(employeeId: Int) {
            viewModel.navigateToEmployeeDetails(employeeId)
        }

        override fun onShowSnackBar(
            message: String,
            scope: CoroutineScope,
            snackBarState: SnackbarHostState,
            actionLabel: String?,
            withDismissAction: Boolean,
            duration: SnackbarDuration

        ) {

            scope.launch {
                snackBarState.showSnackbar(message, actionLabel, withDismissAction, duration)
            }

        }

        override fun onShowAlertDialog(
            message: String,
            positiveText: String?,
            negativeText: String?,
            neutralText: String?,
            positiveAction: (() -> Unit)?,
            negativeAction: (() -> Unit)?,
            neutralAction: (() -> Unit)?
        ): AlertDialog = AlertDialog.Builder(this@HiringActivity)
            .setMessage(message)
            .let { dialog ->

                with(dialog) {
                    positiveText?.let { text ->
                        setPositiveButton(text) { di, _ ->
                            positiveAction?.invoke() ?: di.dismiss()
                        }
                    }

                    neutralText?.let { text ->
                        setPositiveButton(text) { di, _ ->
                            neutralAction?.invoke() ?: di.dismiss()
                        }
                    }

                    negativeText?.let { text ->
                        setPositiveButton(text) { di, _ ->
                            negativeAction?.invoke() ?: di.dismiss()
                        }
                    }
                }

                dialog
            }
            .show()
    }
}



