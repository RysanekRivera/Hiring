package io.github.rysanekrivera.hiring

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.github.rysanekrivera.common.utils.TestTags.LOADING_SCREEN_PROGRESS_INDICATOR
import io.github.rysanekrivera.data.models.Employee
import io.github.rysanekrivera.ui.composables.AllEmployeesSuccessScreen
import io.github.rysanekrivera.ui.composables.ErrorScreen
import io.github.rysanekrivera.ui.composables.LoadingScreen
import io.github.rysanekrivera.ui.composables.SingleEmployeeScreen
import org.junit.Rule
import org.junit.Test

class EmployeeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val employees = listOf(
        Employee(id = 1, name = "John Doe", listId = 101),
        Employee(id = 2, name = "Jane Smith", listId = 102)
    )

    @Test
    fun testAllEmployeesSuccessScreen_displaysEmployeeNames() {
        composeTestRule.setContent {
            AllEmployeesSuccessScreen(employees = employees, onClickCharacter = {})
        }

        employees.forEach { employee ->
            composeTestRule.onNodeWithText(employee.name).assertIsDisplayed()
        }
    }

    @Test
    fun testAllEmployeesSuccessScreen_searchEmployee() {
        composeTestRule.setContent {
            AllEmployeesSuccessScreen(employees = employees, onClickCharacter = {})
        }

        // Type in the search field
        composeTestRule.onNodeWithText("Search Employee").performTextInput("John")

        // Check that only John Doe is displayed
        composeTestRule.onAllNodesWithText("John Doe").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Jane Smith").assertCountEquals(0)
    }

    @Test
    fun testLoadingScreen_displaysLoadingIndicator() {
        composeTestRule.setContent {
            LoadingScreen()
        }

        composeTestRule.onNodeWithTag(LOADING_SCREEN_PROGRESS_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun testErrorScreen_displaysErrorMessage() {
        val errorMessage = "An error occurred"
        composeTestRule.setContent {
            ErrorScreen(Throwable(errorMessage))
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun testSingleEmployeeScreen_displaysEmployeeDetails() {
        val employee = Employee(id = 1, name = "John Doe", listId = 101)

        composeTestRule.setContent {
            SingleEmployeeScreen(employee = employee, onShowSnackBar = {}, onShowAlertDialog = {})
        }

        composeTestRule.onNodeWithText("Name:").assertIsDisplayed()
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("ID:").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("List ID:").assertIsDisplayed()
        composeTestRule.onNodeWithText("101").assertIsDisplayed()
    }

    @Test
    fun testSingleEmployeeScreen_snackBarButton_click() {
        val employee = Employee(id = 1, name = "John Doe", listId = 101)
        var snackBarShown = false

        composeTestRule.setContent {
            SingleEmployeeScreen(
                employee = employee,
                onShowSnackBar = { snackBarShown = true },
                onShowAlertDialog = {}
            )
        }

        composeTestRule.onNodeWithText("Show SnackBar").performClick()

        assert(snackBarShown) { "SnackBar should be shown after button click" }
    }

    @Test
    fun testSingleEmployeeScreen_alertDialogButton_click() {
        val employee = Employee(id = 1, name = "John Doe", listId = 101)
        var alertDialogShown = false

        composeTestRule.setContent {
            SingleEmployeeScreen(
                employee = employee,
                onShowSnackBar = {},
                onShowAlertDialog = { alertDialogShown = true }
            )
        }

        composeTestRule.onNodeWithText("Show Alert Dialog").performClick()

        assert(alertDialogShown) { "AlertDialog should be shown after button click" }
    }
}
