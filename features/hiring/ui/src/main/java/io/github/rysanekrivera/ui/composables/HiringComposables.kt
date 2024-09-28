package io.github.rysanekrivera.ui.composables

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.rysanekrivera.common.composables.BodyText
import io.github.rysanekrivera.common.composables.SmallText
import io.github.rysanekrivera.common.composables.TitleText
import io.github.rysanekrivera.common.utils.TestTags.LOADING_SCREEN_PROGRESS_INDICATOR
import io.github.rysanekrivera.data.models.Employee
import io.github.rysanekrivera.ui.R
import io.github.rysanekrivera.ui.constants.Constants.GRID_VALUE
import io.github.rysanekrivera.ui.constants.Constants.PREFS_NAME
import io.github.rysanekrivera.ui.enums.GridType

@Composable
fun AllEmployeesSuccessScreen(employees: List<Employee>, onClickCharacter: (employeeId: Int) -> Unit) {

    val context = LocalContext.current
    var currentEmployeesList by remember { mutableStateOf(employees) }
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var text by remember { mutableStateOf("") }
    var grids by remember { mutableIntStateOf(prefs.getInt(GRID_VALUE, 2)) }
    val listState = rememberLazyGridState()
    var showTextField by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = grids) {
        prefs.edit().putInt(GRID_VALUE, grids).apply()
    }

    val nestedScrollConnection = remember {
        provideNestedScrollConnection(showTextField){ showTextField = it }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        topBar = {
            AllEmployeesTopAppBar(grids){ grids = it }
        }
    ) {

        Column(
            modifier = Modifier.padding(it)
        ) {

            AnimatedVisibility(showTextField) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = { displayText ->
                            text = displayText
                            currentEmployeesList = employees.filter { employee -> employee.name.lowercase().contains(displayText.lowercase()) }
                        },
                        label = { SmallText(text = stringResource(R.string.search_employee), color = colorResource(id = R.color.dark_blue)) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = colorResource(id = R.color.light_blue),
                            unfocusedIndicatorColor = colorResource(id = R.color.dark_blue),
                            focusedTextColor = colorResource(id = R.color.light_blue),
                            unfocusedTextColor = colorResource(id = R.color.dark_blue),
                            cursorColor = colorResource(id = R.color.light_blue),
                            focusedContainerColor = colorResource(id = R.color.light_blue).copy(.2f),
                            unfocusedContainerColor = colorResource(id = R.color.light_blue).copy(.2f),
                        )
                    )
                }
            }

            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(grids),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {

                items(
                    currentEmployeesList,
                ) { employee ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .wrapContentHeight()
                            .clickable { onClickCharacter(employee.id) },
                        colors = CardDefaults.cardColors().copy(containerColor = colorResource(id = R.color.light_blue).copy(.2f))
                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .aspectRatio(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Column {

                                Row {
                                    BodyText(text = R.string.list_id, bold = true)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    BodyText(text = employee.listId.toString())
                                }

                                Row {
                                    BodyText(text = R.string.name, bold = true)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    BodyText(text = employee.name)
                                }

                                Row {
                                    BodyText(text = R.string.id, bold = true)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = employee.id.toString())
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

private fun provideNestedScrollConnection(showTextField: Boolean, onShouldShowTextField: (Boolean) -> Unit): NestedScrollConnection {
    return object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            if (delta > 0) {
                onShouldShowTextField(true)
            } else if (delta < 0 && showTextField) {
                onShouldShowTextField(false)
            }
            return Offset.Zero
        }
    }
}

@Composable
private fun AllEmployeesTopAppBar(grids: Int, onClickGridIcon: (Int) -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {
                Spacer(modifier = Modifier.width(32.dp))

                TitleText(
                    stringResource(R.string.employees),
                    bold = true,
                    color = colorResource(id = R.color.light_blue)
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClickGridIcon(GridType.Single.grids) },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_one_grid),
                    contentDescription = stringResource(R.string.one_grid),
                    tint = if (grids == GridType.Single.grids) colorResource(id = R.color.light_blue) else colorResource(
                        id = R.color.light_blue
                    ).copy(.3f)
                )

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClickGridIcon(GridType.Double.grids) },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_two_grid),
                    contentDescription = stringResource(R.string.two_grids),
                    tint = if (grids == GridType.Double.grids) colorResource(id = R.color.light_blue) else colorResource(
                        id = R.color.light_blue
                    ).copy(.3f)
                )

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClickGridIcon(GridType.Triple.grids) },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_three_grid),
                    contentDescription = stringResource(R.string.three_grids),
                    tint = if (grids == GridType.Triple.grids) colorResource(id = R.color.light_blue) else colorResource(
                        id = R.color.light_blue
                    ).copy(.3f)

                )

            }
        }
    }
}

@Composable
fun ErrorScreen(error: Throwable?) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error?.message ?: stringResource(R.string.an_error_occurred))
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().testTag(LOADING_SCREEN_PROGRESS_INDICATOR),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SingleEmployeeScreen(employee: Employee, onShowSnackBar: (Employee) -> Unit, onShowAlertDialog: (Employee) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Row {
                BodyText(text = R.string.name, bold = true)
                Spacer(modifier = Modifier.width(8.dp))
                BodyText(text = employee.name)
            }

            Row {
                BodyText(text = R.string.id, bold = true)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = employee.id.toString())
            }

            Row {
                BodyText(text = R.string.list_id, bold = true)
                Spacer(modifier = Modifier.width(8.dp))
                BodyText(text = employee.listId.toString())
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Column {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                content = {
                    BodyText(text = stringResource(R.string.show_snackbar), bold = true, color = MaterialTheme.colorScheme.background)
                },
                onClick = { onShowSnackBar(employee) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                content = {
                    BodyText(text = stringResource(R.string.show_alert_dialog), bold = true, color = MaterialTheme.colorScheme.background)
                },
                onClick = { onShowAlertDialog(employee) }
            )
        }
    }
}


